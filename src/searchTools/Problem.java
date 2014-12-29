/**
 * Author: Ian Foertsch
 * Date: 12/15/14
 * Project: Search Tools
 */
package searchTools;

/**
 * The problem interface is designed to evaluate the goal condition of a search problem, as well as storing the goal and origin nodes. 
 * @author Ian
 *
 */
public interface Problem {
	
	/**
	 * Returns true if the .equals method returns true for the argument node.
	 * @param node
	 * @return
	 */
	public boolean goalTest(Node node);
	public Node getGoalNode();
	
	public Node getOriginNode();
	
}
