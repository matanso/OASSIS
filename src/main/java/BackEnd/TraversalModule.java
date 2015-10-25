/***
 * @author <a href="mailto:dannySarge@gmail.com">Danny V.</a>
 * @version $Id: code-standards.xml,v 1.1 2015/10/24 taylor Exp $
 */

package Oasis;

import org.openrdf.query.BindingSet;
import org.openrdf.query.TupleQueryResult;

abstract public class TraversalModule 
{
	/***
	 * Every instance of a TraversalModule has a field to store all valid assignments.
	 */
    TupleQueryResult validAssignments;

    public TraversalModule(TupleQueryResult validAssignments)
    {
        this.validAssignments = validAssignments;
    }
    
    public abstract void extendAssignments();    
    
    /***
	 * @param userID
	 * @return next assignment to query this user, null if none are left.
	 */
    public abstract BindingSet next(int userID);
        
    /**
	 * input: assignment and userID and his support for said assignment.
	   assumes assignment hasnt been answered by user yet.
	 * @param assignment, userID, suuport.
	 */
    public abstract void update(BindingSet assignment, int userID, int support);
    
    /**
	 * Receives assignment and returns 0 if undecided, 1 if insignificant, 2 if significant.
	 * @param assignment.
	 */
    public abstract int aggregator(BindingSet assignment);
    
    
}
