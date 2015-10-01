package Oasis;

import org.openrdf.query.BindingSet;
import org.openrdf.query.TupleQueryResult;

abstract public class TraversalModule {

	TupleQueryResult validAssignments;

	public TraversalModule(TupleQueryResult validAssignments){
		this.validAssignments = validAssignments;
	}
	
	public abstract void extendAssignments();
		
	
	public abstract BindingSet next(int userID);
		
	public abstract void update(BindingSet assignment, int userID, int support);
	
	public abstract int aggregator(BindingSet assignment);
	
	
}