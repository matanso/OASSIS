package Oasis;

import java.io.File;
import java.io.IOException;

import org.openrdf.query.BindingSet;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFParseException;
import org.openrdf.sail.memory.MemoryStore;

import com.google.gson.Gson;

public class NaiveAlgorithmTest {

	public static void main(String[] args) throws RepositoryException, RDFParseException, IOException, MalformedQueryException, QueryEvaluationException {
		//note: TupleQueryResult has to have it's connection open in order to access.
			

		String queryString = "SELECT ?x ?y WHERE { ?x ?p ?y } ";

		/*Test 1: load 2 ontologies each containing one tuple. Then userID 1 gets a question, answers it to make it an MSP
		then he asks for another question, gets the second tuple makes it an MSP as well, then asks for another question 
		and with none left gets null*/
		
		String ontology4 = "C:\\Users\\vain\\danny2\\eclipse_workspace\\sesame-getting-started\\foursquare.ttl";
		String ontology3 = "C:\\Users\\vain\\danny2\\eclipse_workspace\\sesame-getting-started\\threesquare.ttl";
		File ontology4File = new File(ontology4);
		File ontology3File = new File(ontology3);
		
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
		
		/*test 1: ontology has 2 entries. userID 1 asks for question makes it significant then asks for another question and makes that significant too.*/
		
		Wrapper.init(ontology3);
		
		Wrapper.submitQuery(queryString, "/Users/duanenickull/Software/neo4j-community-1.8.M01/");
		
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
		
		System.out.println("yay");
	}

	
	
	
	
	
	
	
	
}
