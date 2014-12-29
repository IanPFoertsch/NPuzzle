/**
 * Author: Ian Foertsch
 * Date: 12/15/14
 * Project: Search Tools
 */

package searchTools;

import java.util.LinkedList;

/**
 * The State interface holds problem-specific state information regarding the "state" of the problem. For example, in a graph navigation
 * search problem, the state information would describe the current vertex in the graph, connections to other vertices, etc.
 * @author Ian Foertsch
 *
 */
public interface State {

	/**
	 * The .equals method overrides the Object .equals method, returning true if the two states are considered equivalent. 
	 * @param compareMe
	 * @return
	 */
	public boolean equals(Object compareMe);
	
	
	public State getDeepCopy();
	// get current state method or something to return relevant data
	// To avoid passing objects around.
	/**
	 * The getLegalActions method describes all the legal actions possible to take from a particular state.
	 * @return
	 */
	public LinkedList<Action> getLegalActions();
}
