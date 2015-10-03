package Oasis;

import java.util.HashMap;
import java.util.Map;

import org.openrdf.query.BindingSet;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.RepositoryException;

public class Wrapper {
	
	public static class Result{
		int validAnswer;
		BindingSet bindingSet;
		public Result(){
			validAnswer = 0;
			bindingSet = null;
		}
		
		public Result(int validAnswer, BindingSet bindingSet){
			this.validAnswer = validAnswer;
			this.bindingSet = bindingSet;
		}
	}
	
	
	
	private static Map<String, NaiveAlgorithm> algoDic = new HashMap<String, NaiveAlgorithm>();/*query string is key, the algo that goes with it is value*/
	private static Map<Integer, String> queryStringDic = new HashMap<Integer, String>();/*queryID is key, the query string is value.*/
	
	public static int init(String ontology, String DBPath){
		/* input: ontology is the ontology that we'll query, DBPath is where to store the graphDB.
		 * output: 0 = success
		   		   1 = Repository Exception - if something went wrong with connecting to rep (such as rep doesn't exist)
		   		   2 = if an error was found parsing the ontology (i.e wrong format)
		   		   3 = if data couldn't be written into rep (i.e rep not writable, data stream failed to write).
		   		   4 = if connection couldn't close.
		   		   */
		int res;
		System.out.println("Wrapper.init");
		res =  SPARQLQueryManager.init(ontology, DBPath);
		System.out.println("wrapper done init");
		return res;
	}
	
	public static Result getQuestion(String query, int userID){
		/* input: query representing the question to ask user that's represented by userID. 
		 * output: Result class where its validAnswer field means:
		 *         0 = success 
		 * 		   1 = couldn't connect to repository.
		 * 		   2 = query is malformed.
		 *         3 = query couldn't be answered.
		 *         if it is indeed 0, then its bindingSet field contains the question to be answered by userID, 
		 *         in BindingSet format, otherwise contains null*/
		System.out.println("wrapper get question");
		TupleQueryResult queryResult;
		NaiveAlgorithm algo;
		if (algoDic.containsKey(query)){
			algo = algoDic.get(query);
		}else{
			try {
				queryResult = SPARQLQueryManager.evaluateQuery(query);
			} catch (RepositoryException e) {
				return new Result(1, null);
			} catch (MalformedQueryException e) {
				return new Result(2, null);
			} catch (QueryEvaluationException e) {
				return new Result(3, null);
			}
			algo = new NaiveAlgorithm(queryResult);
			algoDic.put(query, algo);
		}
		System.out.println("wrapper get question done");
		return new Result(1, algo.next(userID));		
	}
	
	
	public static Result answerQuestion(BindingSet bindingSet, String query, int userID, int support){
		/*input: bindingSet is the question that the user answered, queryID and userID same as the next function. support is the answer given byuserID.
		 * output: result.validAnswer = 0: success
		 * 		   result.validAnswer = 1: bindingSet or query were null.*/
		System.out.println("wrapper answer question");
		Result result = new Result();
		if (bindingSet == null || query == null ){
			result.validAnswer = 1;
			return result;
		}
		NaiveAlgorithm algo = algoDic.get(query);
		algo.update(bindingSet, userID, support);
		System.out.println("wrapper answer question done");
		return result;
	}
	
	
}
