package Oasis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.TupleQueryResult;

public class NaiveAlgorithm extends TraversalModule {

	class Assignment {
		int significanceBit; // 0 for undecided, 1 for insignificant, 2 for significant
		Map<Integer, Integer> supportDic; // a dictionary of users as keys, and each user has it's support for the assignment.

		Assignment() {
			this.significanceBit = 0;
			this.supportDic = new HashMap<Integer, Integer>();
		}
	}

	// +++++++++++++Fields Here+++++++++
	Map<BindingSet, Assignment> answers;
	List<BindingSet> undecidedKeys; // a list to hold all the keys of the map, to use for random access. (because we have to make this list
							// everytime next is called, so I made it a field to save time)
	List<BindingSet> MSP; //a list to keep all MSP's

	public NaiveAlgorithm(TupleQueryResult validAssignments) {
		super(validAssignments);
		this.answers = new HashMap<BindingSet, Assignment>();
		this.undecidedKeys = new ArrayList<BindingSet>();
		this.MSP = new ArrayList<BindingSet>();
		try {
			while (validAssignments.hasNext()) {
				BindingSet tempBindingSet = validAssignments.next();
				answers.put(tempBindingSet, null);// Remember that at the
													// beginning all
													// BindingSet's Assignment
													// class are null.
				undecidedKeys.add(tempBindingSet);
			}
		} catch (QueryEvaluationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void extendAssignments() {

	}

	@Override
	public BindingSet next(int userID) {
		Random random = new Random();
		BindingSet randomKey = null;
		Assignment tempAssignment;
		while (!undecidedKeys.isEmpty()) { // iterates over random assignments until an undiscovered one is found.
			randomKey = undecidedKeys.get(random.nextInt(undecidedKeys.size()));
						
			tempAssignment = answers.get(randomKey);
			if (tempAssignment == null){//assignment's never been asked before
				tempAssignment = new Assignment();
				answers.put(randomKey, tempAssignment);
				break;
			}
			else if ((tempAssignment.supportDic.get(userID) == null)) { // if the user didn't answer assignment
				break;
			}
		}
		if (undecidedKeys.isEmpty()){
			return null;
		}else{
			return randomKey;
		}
	}

	public void update(BindingSet assignment, int userID, int support) {
		// assumes assignment hasnt been answered by user yet.
		
		Assignment tempAssignment = answers.get(assignment);
		
		tempAssignment.supportDic.put(userID, support); // maybe here we need to
														// put tempAssignment
														// back into answers,
														// but doesnt seem like
														// it.
		if (aggregator(assignment) == 2) {
			undecidedKeys.remove(assignment);
			MSP.add(assignment);
		}else if (aggregator(assignment) == 1){
			undecidedKeys.remove(assignment);
		}

	}

	@Override
	public int aggregator(BindingSet assignment) {
		Map<Integer, Integer> temp = this.answers.get(assignment).supportDic;
		double counter = 0;
		double res = 0;
		double epsilon = 30; // if overall support is greater than this it is
								// regarded as significant.
		double delta = 0; // if more than 30 users answered it is considered
							// decided
		if (temp.size() > delta) {//it's decided
			for (Map.Entry<Integer, Integer> entry : temp.entrySet()) {
				res += entry.getValue();
				counter++;
			}
			res = res / counter;
			if (res > epsilon){
				return 2;
			}else{
				return 1;
			}
		}else{ //it's undecided
			return 0;
		}
	}
}
