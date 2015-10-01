package Oasis;

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

import com.google.gson.Gson;

public class SPARQLQueryManager {

	public static void loadOntology(File ontology, Repository rep,
						String baseURI) throws RepositoryException, RDFParseException, IOException {
		// input:
		// assumes ontology in TURTLE format. assumes repository is initialized.

		RepositoryConnection conn = rep.getConnection();// get connection to
														// repository
		try {
			conn.add(ontology, baseURI, RDFFormat.TURTLE); // add ontology to
															// repository
		} finally {
			conn.close();
		}
	}

	public static TupleQueryResult evaluateQuery(String queryString, String ontology, Repository rep, String baseURI)
																			throws RepositoryException, RDFParseException, IOException,
																						MalformedQueryException, QueryEvaluationException {
		// input: assumes repository is already initialized. assusmes String
		// ontology is a file name.
		// assumes queryString is a SPARQL query.
		TupleQueryResult result = null;
		File ontologyFile = new File(ontology);

		loadOntology(ontologyFile, rep, baseURI);

		// connect to repository
		RepositoryConnection conn = rep.getConnection();

		// evaluate given query
		TupleQuery tupleQuery = conn.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
		result = tupleQuery.evaluate();
		return result;
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

	public static void main(String[] args) throws RepositoryException, RDFParseException, MalformedQueryException,
																					QueryEvaluationException, IOException {
		
		
		//note: TupleQueryResult has to have it's connection open in order to access.
		
		Gson gson = new Gson();
		BindingSet assignment = null;
		NaiveAlgorithm naiveAlgo = null;
		String ontology4 = "C:\\Users\\vain\\danny2\\eclipse_workspace\\sesame-getting-started\\foursquare.ttl";
		String ontology3 = "C:\\Users\\vain\\danny2\\eclipse_workspace\\sesame-getting-started\\threesquare.ttl";
		File ontology4File = new File(ontology4);
		File ontology3File = new File(ontology3);
		String namespace = "http://example.org/";
		Repository rep = new SailRepository(new MemoryStore());
		rep.initialize();
		String queryString = "SELECT ?x ?y WHERE { ?x ?p ?y } ";
		loadOntology(ontology4File, rep, namespace);
		loadOntology(ontology3File, rep, namespace);
		TupleQueryResult result = evaluateQuery(queryString, ontology4, rep, namespace);
		naiveAlgo = new NaiveAlgorithm(result);
		System.out.println("this is the assignment given to userID 1:");
		assignment = naiveAlgo.next(1);
		printBindingSet(assignment);
		System.out.println("userID 1 gives support 31 to the assignment. We update it..(making it an MSP)");
		naiveAlgo.update(assignment, 1, 31);
		
		System.out.println("this is the second assignment given to userID 1");
		assignment = naiveAlgo.next(1);
		printBindingSet(assignment);
		System.out.println("userID 1 again gives support 31, making the assignment an MSP");
		naiveAlgo.update(assignment, 1, 31);
		
		System.out.println("userID 1 asks for another question, but there are none left (there were 2 in the ontology)");
		assignment = naiveAlgo.next(1);
		printBindingSet(assignment);
		System.out.println("yay");
	}

}
