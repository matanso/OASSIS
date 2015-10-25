/***
 * @author <a href="mailto:dannySarge@gmail.com">Danny V.</a>
 * @version $Id: code-standards.xml,v 1.1 2015/10/24 taylor Exp $
 */

package Oasis;

import java.io.IOException;
import org.openrdf.query.BindingSet;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.repository.RepositoryException;
import org.openrdf.rio.RDFParseException;
/*import org.openrdf.sail.memory.MemoryStore;
import com.google.gson.Gson;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.Repository;*/

public class NaiveAlgorithmTest 
{
	
	public static void main(String[] args) throws RepositoryException, RDFParseException, IOException, MalformedQueryException, QueryEvaluationException 
	{
		//note: TupleQueryResult has to have it's connection open in order to access.
			

		String queryString = "SELECT ?x ?y WHERE { ?x ?p ?y } ";

		/*Test 1: load 2 ontologies each containing one tuple. Then userID 1 gets a question, answers it to make it an MSP
		then he asks for another question, gets the second tuple makes it an MSP as well, then asks for another question 
		and with none left gets null*/
		
		String ontology3 = "C:\\Users\\vain\\danny2\\eclipse_workspace\\sesame-getting-started\\threesquare.ttl";

		/*SPARQLQueryManager.init(ontology3, "/Users/duanenickull/Software/neo4j-community-1.8.M01/");
		SPARQLQueryManager.loadOntology(ontology4File);
		SPARQLQueryManager.loadOntology(ontology3File);
		
		TupleQueryResult result = SPARQLQueryManager.evaluateQuery(queryString);
		naiveAlgo = new NaiveAlgorithm(result);
		assignment = naiveAlgo.next(1);
		naiveAlgo.update(assignment, 1, 31);
		assignment = naiveAlgo.next(1);
		naiveAlgo.update(assignment, 1, 31);
		assignment = naiveAlgo.next(1);

		System.out.println("yay");*/
		
		/*test: users 1 and 2 make 2 assignemnts significant.*/
		
		/*Wrapper.init( "/Users/duanenickull/Software/neo4j-community-1.8.M01/", "Users/vain/danny2/CrowdSourcingTest2/");
		Wrapper.loadOntology(ontology3);
		
		Wrapper.submitQuery(queryString, 30, 1);
		
		BindingSet bindingSet = (Wrapper.getQuestion(queryString, 1)).bindingSet;
		System.out.print("for user = " + 1 + " assignment = ");
		SPARQLQueryManager.printBindingSet(bindingSet);
		Wrapper.answerQuestion(bindingSet, queryString, 1, -1);
		
		
		bindingSet = (Wrapper.getQuestion(queryString,  1)).bindingSet;
		System.out.print("for user = " + 1 + " assignment = ");
		SPARQLQueryManager.printBindingSet(bindingSet);
		Wrapper.answerQuestion(bindingSet, queryString, 1, 1);
		
		
		bindingSet = (Wrapper.getQuestion(queryString,  2)).bindingSet;
		System.out.print("for user = " + 2 + " assignment = ");
		SPARQLQueryManager.printBindingSet(bindingSet);
		Wrapper.answerQuestion(bindingSet, queryString, 2, 30);
		
		
		bindingSet = (Wrapper.getQuestion(queryString,  1)).bindingSet;
		System.out.print("for user = " + 1 + " assignment = ");
		SPARQLQueryManager.printBindingSet(bindingSet);
		Wrapper.answerQuestion(bindingSet, queryString, 1, 30);
		
		
		bindingSet = (Wrapper.getQuestion(queryString,  2)).bindingSet;
		System.out.print("for user = " + 2 + " assignment = ");
		SPARQLQueryManager.printBindingSet(bindingSet);
		Wrapper.answerQuestion(bindingSet, queryString, 2, 30);
		
		bindingSet = (Wrapper.getQuestion(queryString,  2)).bindingSet;
		System.out.print("for user = " + 2 + " assignment = ");
		SPARQLQueryManager.printBindingSet(bindingSet);
		Wrapper.answerQuestion(bindingSet, queryString, 2, 30);
		
		System.out.println("yay");*/
		
		
		
		
		/*test: userID 3 tries to get question after all assignments have been deemed significant by users 1 and 2.*/
		
		/*Wrapper.init( "/Users/duanenickull/Software/neo4j-community-1.8.M01/", "Users/vain/danny2/CrowdSourcingTest2/");
		Wrapper.loadOntology(ontology3);
		
		Wrapper.submitQuery(queryString, 30, 1);
		
		BindingSet bindingSet = (Wrapper.getQuestion(queryString, 1)).bindingSet;
		System.out.print("for user = " + 1 + " assignment = ");
		SPARQLQueryManager.printBindingSet(bindingSet);
		Wrapper.answerQuestion(bindingSet, queryString, 1, -1);
		
		
		bindingSet = (Wrapper.getQuestion(queryString,  1)).bindingSet;
		System.out.print("for user = " + 1 + " assignment = ");
		SPARQLQueryManager.printBindingSet(bindingSet);
		Wrapper.answerQuestion(bindingSet, queryString, 1, 1);
		
		
		bindingSet = (Wrapper.getQuestion(queryString,  2)).bindingSet;
		System.out.print("for user = " + 2 + " assignment = ");
		SPARQLQueryManager.printBindingSet(bindingSet);
		Wrapper.answerQuestion(bindingSet, queryString, 2, 30);
		
		
		bindingSet = (Wrapper.getQuestion(queryString,  1)).bindingSet;
		System.out.print("for user = " + 1 + " assignment = ");
		SPARQLQueryManager.printBindingSet(bindingSet);
		Wrapper.answerQuestion(bindingSet, queryString, 1, 30);
		
		
		bindingSet = (Wrapper.getQuestion(queryString,  2)).bindingSet;
		System.out.print("for user = " + 2 + " assignment = ");
		SPARQLQueryManager.printBindingSet(bindingSet);
		Wrapper.answerQuestion(bindingSet, queryString, 2, 30);
		
		bindingSet = (Wrapper.getQuestion(queryString,  2)).bindingSet;
		System.out.print("for user = " + 2 + " assignment = ");
		SPARQLQueryManager.printBindingSet(bindingSet);
		Wrapper.answerQuestion(bindingSet, queryString, 2, 30);
		
		bindingSet = (Wrapper.getQuestion(queryString,  3)).bindingSet;
		System.out.print("for user = " + 3 + " assignment = ");
		SPARQLQueryManager.printBindingSet(bindingSet);
		Wrapper.answerQuestion(bindingSet, queryString, 3, 30);*/
		
		
		
		/*test: given a random folder for both RDF and neo4j.*/
		
		/*Wrapper.init( "blasdfl", "blabalasdf");
		Wrapper.loadOntology(ontology3);
		
		Wrapper.submitQuery(queryString, 30, 1);
		
		BindingSet bindingSet = (Wrapper.getQuestion(queryString, 1)).bindingSet;
		System.out.print("for user = " + 1 + " assignment = ");
		SPARQLQueryManager.printBindingSet(bindingSet);
		Wrapper.answerQuestion(bindingSet, queryString, 1, -1);*/
		
		/*test: negative values for support threshold and user number threshold - works like it's zero. */
		
		Wrapper.init( "/Users/duanenickull/Software/neo4j-community-1.8.M01/", "Users/vain/danny2/CrowdSourcingTest2/");
		Wrapper.loadOntology(ontology3);
		
		Wrapper.submitQuery(queryString, -1, -1);
		
		BindingSet bindingSet = (Wrapper.getQuestion(queryString, 1)).bindingSet;
		System.out.print("for user = " + 1 + " assignment = ");
		SPARQLQueryManager.printBindingSet(bindingSet);
		Wrapper.answerQuestion(bindingSet, queryString, 1, -1);
		
		
		bindingSet = (Wrapper.getQuestion(queryString,  1)).bindingSet;
		System.out.print("for user = " + 1 + " assignment = ");
		SPARQLQueryManager.printBindingSet(bindingSet);
		Wrapper.answerQuestion(bindingSet, queryString, 1, 1);
		
		
		bindingSet = (Wrapper.getQuestion(queryString,  2)).bindingSet;
		System.out.print("for user = " + 2 + " assignment = ");
		SPARQLQueryManager.printBindingSet(bindingSet);
		Wrapper.answerQuestion(bindingSet, queryString, 2, 30);
		
		
		bindingSet = (Wrapper.getQuestion(queryString,  1)).bindingSet;
		System.out.print("for user = " + 1 + " assignment = ");
		SPARQLQueryManager.printBindingSet(bindingSet);
		Wrapper.answerQuestion(bindingSet, queryString, 1, 30);
		
		
		bindingSet = (Wrapper.getQuestion(queryString,  2)).bindingSet;
		System.out.print("for user = " + 2 + " assignment = ");
		SPARQLQueryManager.printBindingSet(bindingSet);
		Wrapper.answerQuestion(bindingSet, queryString, 2, 30);
		
		bindingSet = (Wrapper.getQuestion(queryString,  2)).bindingSet;
		System.out.print("for user = " + 2 + " assignment = ");
		SPARQLQueryManager.printBindingSet(bindingSet);
		Wrapper.answerQuestion(bindingSet, queryString, 2, 30);
	}
}
