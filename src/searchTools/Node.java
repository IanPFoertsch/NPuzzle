/**
 * Author: Ian Foertsch
 * Date: 12/15/14
 * Project: Search Tools
 */

package searchTools;
import java.util.LinkedList;

/**
 * The Node interface is an interface designed to allow search algorithms to handle search nodes in a uniform manner regardless
 * of problem definition.
 * @author Ian
 *
 */
public interface Node {
	/**
	 * The expand method expands all of the Node's children, effectively performing every legal action on the state and 
	 * returning a collection of child nodes.
	 * @param pathCost
	 * @return
	 */
	public LinkedList<Node> expand(double pathCost);
	/**
	 * The get parent Node returns the address of the Node's parent. This is useful in pathtracing from a goal node back to the 
	 * origin in order to build a path.
	 * @return
	 */
	public Node getParentNode();
	/**
	 * The getPathCost method returns the existing path cost from the origin to the node in question.
	 */
	public double getPathCost();
	/**
	 * getCurrentState returns a copy of the state information  the search node contains.
	 * @return
	 */
	public State getCurrentState();
	public Node deepCopy();
	/**
	 * BuildPath returns a list of actions describing the path from the origin to the node in question.
	 * @param existingPath
	 * @return
	 */
	public LinkedList<Action> buildPath(LinkedList<Action> existingPath);
	/**
	 * getHeuristicScore returns the heuristic evaluation score of the State object the node contains.
	 * @return
	 */
	public double getHeuristicScore();
	public void setHeuristicScore(double heuristicScore);
}
