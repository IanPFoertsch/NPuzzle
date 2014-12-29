/**
 * Author: Ian Foertsch
 * Date: 12/15/14
 * Project: Search Tools
 */

package searchTools;


/**
 * The Action interface is designed to contain the information required to transform a parent state into a child state, as a search
 * algorithm expands nodes while exploring the search tree.
 * @author Ian Foertsch
 *
 */
public interface Action {
	
	/**
	 * The perform action method accepts a current State as an arguement, and performs an action upon that state, and returns 
	 * the resulting child state.
	 * @param currentState
	 * @return
	 */
	public State performAction(State currentState);

	public Action deepCopy();
	
	/**
	 * equals method overrides the .equals Object class method.
	 * @param action
	 * @return
	 */
	public boolean equals(Object action);

	
}