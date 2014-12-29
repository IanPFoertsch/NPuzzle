/**
 * Author: Ian Foertsch
 * Date: 12/15/14
 * Project: Search Tools
 */

package searchTools;

/**
 * The heuristicFunction interface is designed to provide a standard interface for search algorithms to estimate the
 * proximity of search nodes to a goal state. 
 * @author Ian Foertsc
 *
 */
public interface HeuristicFunction {
	/**
	 * the h method estimates the distance from a specified search Node argument to a goal state.
	 * @param node
	 * @return
	 */
	public double h(Node node);
}
