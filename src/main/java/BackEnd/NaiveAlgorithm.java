/***
 * @author <a href="mailto:dannySarge@gmail.com">Danny V.</a>
 * @version $Id: code-standards.xml,v 1.1 2015/10/24 taylor Exp $
 */

package Oasis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.TupleQueryResult;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class NaiveAlgorithm extends TraversalModule 
{
/*Notes: each instance of NaiveAlgorithm represents a different query. in each instance we start a graphDB where each node 
 * represents a valid assignment for said query. each node has metadata saved in the following way:
 * "__bindingSet" = saves the actual assignment.
 * "__significanceBit"  = saves the significance bit of the assignment representing if the assignment is currently significant or not, 
 * 0 for undecided, 1 for insignificant, 2 for significant.
 * "__supportDic" = a Map<String,Integer> where keys are userID in string format (only way FlexJson will work..) and its associated 
 * given support for said query.*/
	

	// +++++++++++++Fields Here+++++++++
	/**
	 * Where the graphDb goes.
	 */
	private static String DB_PATH;/*where the graphDb goes.*/

	public static GraphDatabaseService graphDB;
	/**
	 * a list of all undecided assignments to the query.
	 */
	private List<BindingSet> undecidedKeys; /* a list of all undecided assignments to the query.*/
	/**
	 * a list to keep all MSP's
	 */
	private List<BindingSet> MSP; /*a list to keep all MSP's*/
	/**
	 * a dictionary to be able to access each node given it's assignment.
	 */
	private Map<BindingSet, Node> nodeDic; /*a dictionary to be able to access each node given it's assignment.*/
	
	/**
	 * amount of users needed to answer assignment to make it significant.
	 */
	private int usersToMakeSig;
	
	/**
	 * amount of support to make assignment significant.
	 */
	private int suppToMakeSig;
	
	
	
	
	/*used for serialization and deserialization of objects*/
	private JSONDeserializer<BindingSet> deserializerBindingSet = new JSONDeserializer<BindingSet>();
	private JSONDeserializer<Integer> deserializerInteger = new JSONDeserializer<Integer>();
	private JSONDeserializer<Map<String, Integer>> deserializerMap = new JSONDeserializer<Map<String, Integer>>();
	private JSONSerializer serializer = new JSONSerializer();
	
	
	/**
	 * First off we start a graphDB. then we go over all valid assignments and add them all to undecided keys.
	 * We have nodeDic to keep track of all entered nodes, one for each assignment which we also do in the 
	 * while loop (enter a node with it's relevant matadata using json and Gson, for each assignment)
	 * @param TupleQueryResult validAssignments, int suppToMakeSig, int usersToMakeSig.
	 */
	@SuppressWarnings("deprecation")
	public NaiveAlgorithm(TupleQueryResult validAssignments, int suppToMakeSig, int usersToMakeSig) 
	{
		/*first off we start a graphDB. then we go over all valid assignments and add them all to undecided keys.
		 * We have nodeDic to keep track of all entered nodes, one for each assignment which we also do in the 
		 * while loop (enter a node with it's relevant matadata using json and Gson, for each assignment) */
		super(validAssignments);
		this.nodeDic = new HashMap<BindingSet, Node>();
		this.MSP = new ArrayList<BindingSet>();
		this.undecidedKeys = new ArrayList<BindingSet>();
		this.suppToMakeSig =suppToMakeSig;
		this.usersToMakeSig = usersToMakeSig;
		
		BindingSet tempBindingSet = null;
		Node node = null;
		Transaction tx = graphDB.beginTx();
		try
		{
			while(validAssignments.hasNext())
			{ 
				tempBindingSet = validAssignments.next();
				node = graphDB.createNode();
				setBindingSet(node, tempBindingSet);
				setSupportDic(node, new HashMap<String,Integer>());
				setSignificanceBit(node, 0);
				nodeDic.put(tempBindingSet, node);
				this.undecidedKeys.add(tempBindingSet);
			}
			tx.success();
		}catch (QueryEvaluationException e)
		{
			e.printStackTrace();
		}finally
		{
			tx.finish();
		}
	}

	
	/***
	 * @param userID
	 * @return next assignment to query this user, null if none are left.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public BindingSet next(int userID) 
	{
		/*TODO: maybe make the node making lazy, that is only make the node when the assignment is first asked.
		 * input: userID
		 * output: next assignment to query this user, null if none are left.*/
		String userIDString = new Integer(userID).toString();
		Random random = new Random();
		BindingSet randomKey = null;
		Node tempNode = null;
		Map<String, Integer> tempDic = null;
		Transaction tx = graphDB.beginTx();
		List<BindingSet> listOfAssignments = new ArrayList<BindingSet>(undecidedKeys.size());
		int index;
		
		
		for (BindingSet bindingSet: undecidedKeys)
		{ /*makes a copy of undecidedkeys so that when we iterate over it we can 
													  delete those this userID has answered even though they aren't significant yet. 
													  Remember these two lists contain the same elements and not copies of the elements!*/
			listOfAssignments.add(bindingSet);
		}
		
		try
		{
			while (!listOfAssignments.isEmpty()) 
			{ // iterates over random assignments until an undiscovered one is found.
				index = random.nextInt(listOfAssignments.size());
				randomKey = listOfAssignments.get(index);
				tempNode = nodeDic.get(randomKey);
				tempDic = getSupportDic(tempNode);
				//System.out.println(tempDic.containsKey(userIDString));
				
				if (!tempDic.containsKey(userIDString))
				{ //if user hasn't been asked this assignment before then return it.
					break;
				}else
				{
					listOfAssignments.remove(index);
				}
			}
			tx.success();
		}finally
		{
			tx.finish();
		}
		if (listOfAssignments.isEmpty())
		{
			return null;
		}else
		{
			return randomKey;
		}
	}
	

	
	
	@SuppressWarnings("deprecation")
	/**
	 * input: assignment and userID and his support for said assignment.
	 * @param assignment, userID, suuport.
	 */
	public void update(BindingSet assignment, int userID, int support)
	{
		/* input: assignment and userID and his support for said assignment.
		 */
		String userIDString = new Integer(userID).toString();
		int aggAnswer;
		Map<String, Integer> tempDic = null;
		Transaction tx = graphDB.beginTx();
		try
		{
			Node tempNode = nodeDic.get(assignment);
			tempDic = getSupportDic(tempNode);		
			tempDic.put(userIDString, support);
			setSupportDic(tempNode, tempDic);
			aggAnswer = aggregator(assignment);	

			if (aggAnswer == 2) 
			{
				
				undecidedKeys.remove(assignment);
				MSP.add(assignment);
				setSignificanceBit(tempNode, 2);
			}else if (aggAnswer == 1)
			{
				setSignificanceBit(tempNode, 1);
				undecidedKeys.remove(assignment);
			}
			tx.success();
		}finally
		{
			tx.finish();
		}

	}

	
	
	@Override
	/**
	 * Receives assignment and returns 0 if undecided, 1 if insignificant, 2 if significant.
	 * @param assignment.
	 */
	public int aggregator(BindingSet assignment)
	{
		Node node = nodeDic.get(assignment);		
		Map<String, Integer> tempDic = getSupportDic(node);
		double counter = 0;
		double res = 0;
		double epsilon = suppToMakeSig; // if overall support is greater than this it is
							// regarded as significant.
		double delta = usersToMakeSig; // if more than 30 users answered it is considered
							// decided		
		if (tempDic.size() > delta)
		{//it's decided
			for (Map.Entry<String, Integer> entry : tempDic.entrySet()) 
			{
				res += entry.getValue();
				counter++;
			}
			res = res / counter;
			if (res > epsilon)
			{
				return 2; //is significant
			}else
			{
				return 1; //isn't significant
			}
		}else
		{ //it's undecided
			return 0;
		}
	}
	
	
	
	@Override
	public void extendAssignments()
	{
	}
	
	/**
	 * 
	 * @param node
	 * @return significanceBit field of given node.
	 */
	public  int getSignificanceBit(Node node)
	{
		String tempString = node.getProperty( "__significanceBit" ).toString();
        int temp = deserializerInteger.deserialize(tempString);
        return temp;
	}
	/***
	 * 
	 * @param node
	 * @return SupportDic field of given node.
	 */
	public  Map<String,Integer> getSupportDic(Node node)
	{
		String tempString = node.getProperty( "__supportDic" ).toString();
        Map<String, Integer> temp = deserializerMap.deserialize(tempString);
        return temp;
	}
	/***
	 * 
	 * @param node
	 * @return BindingSet field of given node.
	 */
	public  BindingSet getBindingSet(Node node)
	{
		String tempString = node.getProperty( "__significanceBit" ).toString();
		BindingSet temp = deserializerBindingSet.deserialize(tempString);
        return temp;
	}
	
	@SuppressWarnings("deprecation")
	/***
	 * Sets significanceBit field of node to significanceBit.
	 * @param node
	 * @param significanceBit
	 */
	public void setSignificanceBit(Node node, int significanceBit)
	{
		String tempString = serializer.serialize(significanceBit);
		Transaction tx = graphDB.beginTx();
		try
		{
			node.setProperty("__significanceBit", tempString);
			tx.success();
		}finally
		{
			tx.finish();
		}
	}
	
	/***
	 * Sets SupportDic field of node to supportDic.
	 * @param node
	 * @param supportDic
	 */
	@SuppressWarnings("deprecation")
	public void setSupportDic(Node node, Map<String,Integer> supportDic){
		String tempString = serializer.deepSerialize(supportDic);
		Transaction tx = graphDB.beginTx();
		try
		{
			node.setProperty("__supportDic", tempString);
			tx.success();
		}finally
		{
			tx.finish();
		}
	}
	
	/***
	 * Sets BindingSet field of node to bindingSet.
	 * @param node
	 * @param bindingSet
	 */
	@SuppressWarnings("deprecation")
	public void setBindingSet(Node node, BindingSet bindingSet)
	{
		String tempString = serializer.serialize(bindingSet);
		Transaction tx = graphDB.beginTx();
		try
		{
			node.setProperty("__bindingSet", tempString);
			tx.success();
		}finally
		{
			tx.finish();
		}
	}
	
	/***
	 * Sets DB_PATH to DBPath.
	 * @param DBPath
	 */
	public static void setDBPath(String DBPath){
		DB_PATH = DBPath;
	}
	
	/***
	 * Initializes the graphDB.
	 * @param DBPath
	 */
	public static void setGraphDB(String DBPath){
		setDBPath(DBPath);
		graphDB = new GraphDatabaseFactory().newEmbeddedDatabase( DB_PATH );
	}
	
	/***
	 * 
	 * @return graphDB.
	 */
	public static GraphDatabaseService getGraphDB(){
		return graphDB;
	}
	
	public void setUsersToMakeSig(int usersToMakeSig){
		this.usersToMakeSig = usersToMakeSig;
	}
	
	public void setSuppToMakeSig(int suppToMakeSig){
		this.suppToMakeSig = suppToMakeSig;
	}
	
}
