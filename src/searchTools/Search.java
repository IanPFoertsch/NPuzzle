package searchTools;

import java.util.LinkedList;
import java.util.PriorityQueue;


/**
 * Search interface specifies a search method returning a linkedLIst of action type objects.
 * @author Ian
 */
public interface Search  {
	
	/**
	 * The search method returns a linked list of action types.
	 * @return
	 */
	public LinkedList<Action> search();
	
	/**
	 * The get goal node method returns a node which the search algorithm has determined to be the goal.
	 * As such, it should not be called prior to executing the search method, or it will return a null pointer.
	 * @return
	 */
	public Node getGoalNode();
	
}
