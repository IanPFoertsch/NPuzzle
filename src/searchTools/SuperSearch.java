package searchTools;

import java.util.LinkedList;

/**
 * The Supersearch class contains methods common to all implementing search classes. These methods include the goal test, as well as the success
 * routine, which returns a linked list of action objects upon a successful goal test. 
 * @author Ian
 *
 */
public class SuperSearch {
	
	protected Problem problem;
	protected Node goal;
	
	/**
	 * Constructor accepting a problem object. The goal node is set to null. 
	 * @param problem
	 */
	public SuperSearch(Problem problem)
	{
		goal = null;
		this.problem = problem;
	}
	
	/**
	 * Success Routine method accesses the buildPath method of the goalNode, and returns a linked list of actions.
	 * @param goal
	 * @return
	 */
	public LinkedList<Action> successRoutine(Node goal)
	{
		this.goal = goal;
		return goal.buildPath(null);
	}
	
	/**
	 * Returns a deep copy of the goalNode;
	 * @return
	 */
	public Node getGoalNode()
	{
		return goal.deepCopy();
	}
	
	public void setGoalNode(Node goal)
	{
		this.goal = goal;
	}
	
}
