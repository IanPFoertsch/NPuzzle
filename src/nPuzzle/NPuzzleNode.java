/**
 * Author: Ian Foertsch
 * Date: 12/14/14
 * Project: N Puzzle Problem Domain
 */

package nPuzzle;
import java.util.LinkedList;
import searchTools.*;

/**
 * Node class for the NPuzzle Domain
 * @author Ian
 *
 */
public class NPuzzleNode implements Node {

	private State currentPuzzleState;
	private Action parentAction;
	private Node parentNode;
	private double pathCost;
	private double heuristicScore;
	public final double HEURISTIC_INITIALIZED_TO = 0.0;
	
	
	/**
	 * Constructor accepts state, action, and node objects as arguments as well as a double representing the path cost to the new node.
	 * @param state
	 * @param action
	 * @param parentNode
	 * @param pathCost
	 */
	public NPuzzleNode(State state, Action action, Node parentNode, double pathCost)	
	{
		this.currentPuzzleState = state;
		this.parentAction = action;
		this.parentNode = parentNode;
		this.pathCost = pathCost;
		this.heuristicScore = HEURISTIC_INITIALIZED_TO;
	}
	
	
	/**
	 * Constructor method accepts and copies the fields of an existing NPuzzleNode Object.
	 * @param copyThis
	 */
	public NPuzzleNode(NPuzzleNode copyThis)
	{
		this.currentPuzzleState = copyThis.getCurrentPuzzleState();
		this.parentAction = null;
		this.parentNode = null;
		this.pathCost = copyThis.getPathCost();
		this.heuristicScore = HEURISTIC_INITIALIZED_TO;
	}
	
	

	public State getCurrentPuzzleState() {
		return currentPuzzleState.getDeepCopy();
	}
	public Action getParentAction() {
		return parentAction.deepCopy();
	}

	
	/**
	 * Expand expands the node in question, returning a linked list of newly instantiated nodes. 
	 * This operation calls the node's state's getLegalActions method, and further processes that list
	 * by removing actions which will undo the parent action of this node(thus eliminating one source of infinite
	 * search loops.
	 */
	public LinkedList<Node> expand(double pathCostFunction)
	{
		LinkedList<Node> childNodes = new LinkedList<Node>();
		
		LinkedList<Action> possibleActions = this.currentPuzzleState.getLegalActions();
		
		possibleActions = this.removeParentAction(possibleActions);
		
		for(Action action: possibleActions)
		{
			childNodes.add(new NPuzzleNode(action.performAction(this.currentPuzzleState), action.deepCopy(), this, this.pathCost + pathCostFunction));
		}
		
		return childNodes;
	}
	
	/**
	 * Remove Parent action processes a list of actions and removes any actions which undo or replicate the 
	 * action which created the node in question.
	 * @param possibleActions
	 * @return
	 */
	private LinkedList<Action> removeParentAction(LinkedList<Action> possibleActions)
	{
		if(this.parentAction == null)
		{
			return possibleActions;
		}
		else
		{
			LinkedList<Action> validActions = new LinkedList<Action>();
			for(Action action: possibleActions)
			{
				if(!action.equals(this.parentAction))
				{
					validActions.add(action);
				}
			}		
			return validActions;
		}
	}
	
	/**
	 * The buildPath method returns a linked list of action objects which lead from the Originating node (The node with a null parent action), 
	 * to the node in question
	 */
	public LinkedList<Action> buildPath(LinkedList<Action> existingPath)
	{
		//Handle the edgeCases of the call. First: existingPath will be null if this is the first time the function is called.
		if(existingPath == null)
		{
			existingPath = new LinkedList<Action>();
		}
		//If this Node's parent action == null then this is the origin Node, and we should return the existing path.
		if(this.parentAction == null)
		{
			return existingPath;
		}
		else
		{
			//Otherwise add this Node's parent action to the actionList.
			//the addFirst method is used in order to build a list which will be in the correct order. 
			existingPath.addFirst(this.parentAction.deepCopy());
		}
		//Call the buildPath method on the parent Node, feeding it the existingPath to which we have added 
		//this node's action. 
		return this.parentNode.buildPath(existingPath);	
	}
	
	
	@Override
	/**
	 * Equals method requires accessing the underlying puzzle state object to compare the arrangements of the 
	 * puzzle arrays.
	 */
	public boolean equals(Object node)
	{
		if(this.currentPuzzleState.equals(((NPuzzleNode)node).getCurrentState()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public Node getParentNode() {
		return parentNode;
	}
	
	public double getPathCost() {
		return pathCost;
	}

	public State getCurrentState() {
		return currentPuzzleState.getDeepCopy();
	}
	
	
	public Node deepCopy()
	{	
		Action action = null;
		Node parent = null;
		if(this.parentAction != null)
		{
			action = this.parentAction.deepCopy();
		}
		if(this.parentNode != null)
		{
			parent = this.parentNode.deepCopy();
		}
		
		
		return new NPuzzleNode(this.currentPuzzleState.getDeepCopy(), action, parent, this.pathCost);
	}

	public double getHeuristicScore() {
		return heuristicScore;
	}

	public void setHeuristicScore(double heuristicScore) {
		this.heuristicScore = heuristicScore;
	}
	
	
	
		
}
