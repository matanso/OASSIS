package Libs.BackEnd;

import java.io.File;
import java.io.IOException;

import org.openrdf.model.Value;
import org.openrdf.query.BindingSet;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParseException;
import org.openrdf.sail.memory.MemoryStore;


public class SPARQLQueryManager {
	private static Repository rep; /*same repository for every instance of query*/
	private static String baseURI = "http://example.org/";
	public SPARQLQueryManager() throws RepositoryException{
		
	}
	
	public static int loadOntology(File ontology)  {
		/* input:
		   assumes ontology in TURTLE format. assumes repository is initialized.
		   output: 0 = success
		   		   1 = Repository Exception - if something went wrong with connecting to rep (such as rep doesn't exist)
		   		   2 = if an error was found parsing the ontology (i.e wrong format)
		   		   3 = if data couldn't be written into rep (i.e rep not writable, data stream failed to write).
		   		   4 = if connection couldn't close.
		   		   */
		
		
		
		int res = 0;
		RepositoryConnection conn = null;
		try {
			conn = (rep).getConnection();
		} catch (RepositoryException e1) {
			res = 1;
			e1.printStackTrace();
		}
														
		try {
			conn.add(ontology, baseURI, RDFFormat.TURTLE); // add ontology to repository
															
		} catch (RDFParseException e) {
			res = 2;
			e.printStackTrace();
		} catch (RepositoryException e) {
			res = 3;
			e.printStackTrace();
		} catch (IOException e) {
			res = 3;
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (RepositoryException e) {
				res = 4;
				e.printStackTrace();
			}
		}
		return res;
	}

	public static TupleQueryResult evaluateQuery(String queryString) throws RepositoryException, MalformedQueryException, QueryEvaluationException{
		/* input: assumes repository is already initialized. assumes queryString is a SPARQL query.
		   output: queries rep with queryString. returns answer.*/
		TupleQueryResult result = null;

		// connect to repository
		RepositoryConnection conn = rep.getConnection();

		// evaluate given query
		TupleQuery tupleQuery = conn.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
		result = tupleQuery.evaluate();
		return result;
	}
	
	
	public static int init(String ontology){
		/*input: receives only one ontology which every query works with.
		 * output: starts up the repository, and loads ontology into it. 
		 * 		   0 = success
		   		   1 = Repository Exception - if something went wrong with connecting to rep (such as rep doesn't exist)
		   		   2 = if an error was found parsing the ontology (i.e wrong format)
		   		   3 = if data couldn't be written into rep (i.e rep not writable, data stream failed to write).
		   		   4 = if connection couldn't close.
		   		   */
		
		int res = 0;
		File ontologyFile = null;
		try {
			rep = new SailRepository(new MemoryStore());
			rep.initialize();
			ontologyFile = new File(ontology);
			
		} catch (RepositoryException e) {
			return 3;
		}
		res = loadOntology(ontologyFile);
		return res;
	}
	

	
	public static void printTupleQueryResult(TupleQueryResult result) throws QueryEvaluationException {
		while (result.hasNext()) { /*
										 * iterate over the result and print the
										 * bindings.
										 */
			BindingSet bindingSet = result.next();
			Value valueOfX = bindingSet.getValue("x");
			Value valueOfY = bindingSet.getValue("y");
			System.out.print("value of ?x " + valueOfX + " ++++++ ");
			System.out.println("value of ?y " + valueOfY);
		}
		
	}
	
	public static void printBindingSet(BindingSet assignment){
		if (assignment == null){
			System.out.println("null");
		}else{
		Value valueOfX = assignment.getValue("x");
		Value valueOfY = assignment.getValue("y");
		System.out.print("value of ?x " + valueOfX + " ++++++ ");
		System.out.println("value of ?y " + valueOfY);
		}
	}
	

}