package Oasis;

import java.io.*;
import java.util.*;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.openrdf.query.BindingSet;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.RepositoryException;

public class Wrapper {
	
	public static class MyResult{
		int validAnswer;
		BindingSet bindingSet;
		public MyResult(){
			validAnswer = 0;
			bindingSet = null;
		}
		
		public MyResult(int validAnswer, BindingSet bindingSet){
			this.validAnswer = validAnswer;
			this.bindingSet = bindingSet;
		}
	}
	
	
	
	private static Map<String, TraversalModule> algoDic = 
							new HashMap<String, TraversalModule>();/*query string is key, the algo that goes with it is value
																	* private static Map<Integer, String> queryStringDic = 
	  																* new HashMap<Integer, String>()*/
	
	
	@SuppressWarnings("deprecation")
	public static int init(String neo4jDBPath, String rdfDBPath){
		/* input: ontology is the ontology that we'll query, DBPath is where to store the graphDB.
		 * output: 0 = success
		   		   1 = Repository Exception - if something went wrong with connecting to rep (such as rep doesn't exist)
		   		   2 = if an error was found parsing the ontology (i.e wrong format)
		   		   3 = if data couldn't be written into rep (i.e rep not writable, data stream failed to write).
		   		   4 = if connection couldn't close.
		   		   */
		int res;
		/*System.out.println("Wrapper.init starting..");*/
		res =  SPARQLQueryManager.init(rdfDBPath);
		NaiveAlgorithm.setGraphDB(neo4jDBPath);
		
		
		
		
		
		
		/*TODO: also part of my implementation of using neo4j to save algoDIc*/
		/*Transaction tx = NaiveAlgorithm.graphDB.beginTx();
		try{
			Map<String, TraversalModule> tempAlgoDic = 	new HashMap<String, TraversalModule>();
			String tempString = mySerialize(tempAlgoDic);
			Node node = NaiveAlgorithm.graphDB.createNode();
			Label myLabel = DynamicLabel.label("algoDic");
			node.addLabel(myLabel);
			node.setProperty("__metadata", tempString);
			tx.success();
		}finally{
			tx.finish();
		}*/
		
		
		
		
		/*System.out.println("Wrapper.init done.");*/
		return res;
	}
	
	
	
	public static MyResult loadOntology(String ontology){
		/* input: ontology location
		   assumes ontology in TURTLE format. assumes repository is initialized.
		   output: MyResult.validAnswer = 0 : success
		   		   MyResult.validAnswer = 1 : Repository Exception - if something went wrong with connecting to rep (such as rep doesn't exist)
		   		   MyResult.validAnswer = 2 : if an error was found parsing the ontology (i.e wrong format)
		   		   MyResult.validAnswer = 3 : if data couldn't be written into rep (i.e rep not writable, data stream failed to write).
		   		   MyResult.validAnswer = 4 : if connection couldn't close.
		   		   MyResult.validAnswer = 5 : String ontology = null
		   		   */
		int temp;
		
		if (ontology == null){
			return new MyResult(5, null);
		}
		File ontologyFile = new File(ontology);
		temp = SPARQLQueryManager.loadOntology(ontologyFile);
		return new MyResult(temp, null);
		
	}
	
	
	public static MyResult submitQuery(String query){
		/*input: query is in string format
		 * output: Init's query (making it ready to be asnwered) and returns: MyResult class where its validAnswer field means:
		 *         0 = success 
		 * 		   1 = couldn't connect to repository.
		 * 		   2 = query is malformed.
		 *         3 = query couldn't be answered.
		 *         4 = query is null*/
		if (query == null){
			return new MyResult(4, null);
		}else{
			return initQuery(query);
		}
	}
	
	
	private static MyResult initQuery(String query){
		/*input: query is in string format
		 * output: MyResult class where its validAnswer field means:
		 *         0 = success 
		 * 		   1 = couldn't connect to repository.
		 * 		   2 = query is malformed.
		 *         3 = query couldn't be answered.*/
		
		/*System.out.println("Wrapper.submitQuery starting..");*/
		Map<String, TraversalModule> algoDic = getAlgoDic();
		TupleQueryResult queryResult;
		try {
			queryResult = SPARQLQueryManager.evaluateQuery(query);
		} catch (RepositoryException e) {
			return new MyResult(1, null);
		} catch (MalformedQueryException e) {
			return new MyResult(2, null);
		} catch (QueryEvaluationException e) {
			return new MyResult(3, null);
		}
		TraversalModule algo = new NaiveAlgorithm(queryResult);
		algoDic.put(query, algo);
		setAlgoDic(algoDic);
		/*System.out.println("Wrapper.submitQuery done.");*/
		return new MyResult();
	}
	
	
	
	
	public static MyResult getQuestion(String query, int userID){
		/* input: query representing the question to ask user that's represented by userID. 
		 * output: MyResult class where its validAnswer field means:
		 *         0 = success 
		 * 		   1 = couldn't connect to repository.
		 * 		   2 = query is malformed.
		 *         3 = query couldn't be answered.
		 *         if it is indeed 0, then its bindingSet field contains the question to be answered by userID, 
		 *         in BindingSet format, otherwise contains null*/
		
		
		/*System.out.println("Wrapper.getQuestion starting..");*/
		Map<String, TraversalModule> algoDic = getAlgoDic();
		TraversalModule algo = algoDic.get(query);
		/*System.out.println("Wrapper.getQuestion done");*/
		return new MyResult(0, algo.next(userID));		
	}
	
	
	public static MyResult answerQuestion(BindingSet bindingSet, String query, int userID, int support){
		/*input: bindingSet is the question that the user answered, queryID and userID same as the next function. support is the answer given byuserID.
		 * output: MyResult.validAnswer = 0: success
		 * 		   MyResult.validAnswer = 1: bindingSet or query were null.*/
		
		
		/*System.out.println("Wrapper.answerQuestion starting..");*/
		Map<String, TraversalModule> algoDic = getAlgoDic();
		MyResult MyResult = new MyResult();
		if (bindingSet == null || query == null ){
			MyResult.validAnswer = 1;
			return MyResult;
		}
		TraversalModule algo = algoDic.get(query);
		algo.update(bindingSet, userID, support);
		/*System.out.println("Wrapper.answerQuestion done");*/
		return MyResult;
	}
	
	
	private static Map<String, TraversalModule> getAlgoDic(){
		return algoDic;
	}
	
	private static void setAlgoDic(Map<String, TraversalModule> tempAlgoDic){
		algoDic = tempAlgoDic;
	}
	
	
	
	
	/*TODO: here's my implmentation using the neo4j server for saving algoDic.*/
	
	/*
	@SuppressWarnings("deprecation")
	private static Map<String, TraversalModule> getAlgoDic(){
		Transaction tx = NaiveAlgorithm.graphDB.beginTx();
		Map<String, TraversalModule> tempMap = null;
		try{
			Result result = NaiveAlgorithm.graphDB.execute( "match (n {name: 'algoDic'}) return n" );
			Map<String, Object> row = result.next();
			Node tempNode = (Node) row.get("n");
			tempMap = myDeserialize((String)tempNode.getProperty("__metadata"));
			tx.success();
		}finally{
			tx.finish();
		}
		return tempMap;
	}
	
	@SuppressWarnings("deprecation")
	private static void setAlgoDic(Map<String, TraversalModule> tempAlgoDic){
		Transaction tx = NaiveAlgorithm.graphDB.beginTx();
		try{
			Result result = NaiveAlgorithm.graphDB.execute( "match (n {name: 'algoDic'}) return n" );
			Map<String, Object> row = result.next();
			Node tempNode = (Node) row.get("n");
			tempNode.setProperty("__metadata", mySerialize(tempAlgoDic));
			tx.success();
		}finally{
			tx.finish();
		}
	}
	*/
	
	/*private static String mySerialize(Map<String, TraversalModule> algoDic){
		
	}
	
	private static Map<String, TraversalModule> myDeserialize(String algoDicString){
		
	}*/
		
}
