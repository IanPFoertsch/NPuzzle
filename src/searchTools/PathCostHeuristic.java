/**
 * Author: Ian Foertsch
 * Date:12/16/14
 * Project: Search Tools Package.
 */

package searchTools;

/**
 * The path Cost heuristic is not a true heuristic function: For convenience's sake it accesses a search node's getPathCost method
 * while implementing the heuristic function interface.
 * @author Ian
 *
 */
public class PathCostHeuristic implements HeuristicFunction {
	
	/**
	 * Access and return the node's getPathCost function.
	 */
	public double h(Node node)
	{
		return node.getPathCost();
	}
	

}
