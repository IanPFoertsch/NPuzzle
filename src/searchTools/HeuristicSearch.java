/**
 * Author: Ian Foertsch
 * Date: 12/13/14
 * Project: Heuristic Search Algorithm
 */

package searchTools;

import java.util.LinkedList;
import java.util.PriorityQueue;


/**
 * The Heuristic Search class uses a heuristic function to search the state-space for a solution. This heuristic search class
 * can be used to implement different search algorithms depending upon the heuristic used during construction. For example, an aggregate heuristic
 * incorporating path cost to a search node, and some heuristic score of that search node can be used to implement the A* algorithm 
 * @author Ian
 *
 */
public class HeuristicSearch extends SuperSearch implements Search {

	protected HeuristicFunction heuristic;
	private PriorityQueue<Node> frontierNodes;
	private LinkedList<Node> exploredNodes;
	private final int DEFAULT_CAPACITY = 100;
	//Max_capacity defines the maximum number of nodes the search algorithm will explore before h
	//halting the search and returning the best candidate.
	private final int MAX_CAPACITY = 1000000;
	
	/**
	 * Constructor requires a Problem object, as well as a heuristic function object upon construction.
	 * @param problem
	 * @param heuristic
	 */
	public HeuristicSearch(Problem problem, HeuristicFunction heuristic)
	{
		super(problem);
		this.heuristic = heuristic;
		this.frontierNodes = new PriorityQueue<Node>(this.DEFAULT_CAPACITY, new NodeComparator());
		this.exploredNodes = new LinkedList<Node>();
	}

	/**
	 * The search action gets the origin node for the search from the problem object, and searches the state space drawing from 
	 * a priority queue to select the best nodes to expand. If no solution is found before the MAX_CAPACITY parameter is exceeded, the 
	 * search terminates by selecting a candidate at random from among the best search candidates, builds the path to that 
	 * search node and returns that list of actions as the solution. 
	 */
	public LinkedList<Action> search() {
		//To kick things off, test if the origin node is infact a solution to the problem.  
		if(this.goalTest(this.problem.getOriginNode()))
		{
			//The origin node fulfills the goal test, return null.
			return null;
		}
		
		//Otherwise, get the origin node, setting it's heuristic score and add it to the frontier
		this.problem.getOriginNode().setHeuristicScore(this.heuristic.h(this.problem.getOriginNode()));
		this.frontierNodes.add(this.problem.getOriginNode());
		
		//While there are search candidates, continue the search algorithm.
		while(!this.frontierNodes.isEmpty())
		{	
			//expand the node at the head of the priority queue holding the frontier nodes.
			//add the expanded node to the exploredNodes list.
			LinkedList<Node> newNodes = this.frontierNodes.peek().expand(1.0);
			this.exploredNodes.add(this.frontierNodes.remove());
			
			//For each newly expanded node,
			for(Node node: newNodes)
			{	
				//Perform the goal test, returning the path to that node if successful. 
				if(this.goalTest(node))
				{
					return this.successRoutine(node);
				}
				//otherwise, evaluate the node's heuristic score and add it to the frontier. 
				else
				{		
					node.setHeuristicScore(this.heuristic.h(node));
					this.frontierNodes.add(node);
				}
			}
			if(this.frontierNodes.size() > MAX_CAPACITY)
			{	
				
				Node lastNode= this.frontierNodes.peek();
				return this.successRoutine(lastNode);
			}			
		}
		return null;
	}
	
	/**
	 * Goaltest method accesses the problme object to evaluate the goal condition of a Node arguement.
	 * @param node
	 * @return
	 */
	public boolean goalTest(Node node)
	{
		if(problem.goalTest(node))
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	

}
