package searchTools;

import java.util.LinkedList;
import java.util.PriorityQueue;

public class DecomposedHeuristicSearch extends SuperSearch implements Search {

	private PriorityQueue<Node> frontierNodes;
	private LinkedList<Node> exploredNodes;
	private final int DEFAULT_CAPACITY = 100;
	//Max_capacity defines the maximum number of nodes the search algorithm will explore before h
	//halting the search and returning the best candidate.
	private final int MAX_CAPACITY = 1000000;
	private HeuristicFunction individualHeuristic;
	private HeuristicFunction aggregateHeuristic;
	
	public DecomposedHeuristicSearch(Problem problem, HeuristicFunction individualHeuristic, HeuristicFunction aggregateHeuristic)
	{
		super(problem);
		this.aggregateHeuristic = aggregateHeuristic;
		this.individualHeuristic = individualHeuristic;
		this.frontierNodes = new PriorityQueue<Node>(this.DEFAULT_CAPACITY, new NodeComparator());
		this.exploredNodes = new LinkedList<Node>();
	}
	
	
	public boolean goalTest(Node node)
	{
		
		//the comparison of the heurstic score to 1 is another way of saying heuristic == 0
		//while maintaining confidence that this statement will no result in faulty double comparisons. 
		if(problem.goalTest(node) || (this.individualHeuristic.h(node) < 1))
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	
	public LinkedList<Action> search() {
		//To kick things off... evaluate the origin node's heuristic score, then test origin node for goal condition  
		this.problem.getOriginNode().setHeuristicScore(this.aggregateHeuristic.h(this.problem.getOriginNode()));
		if(this.goalTest(this.problem.getOriginNode()))
		{
			return this.successRoutine(this.problem.getOriginNode());
		}
		
		//if the originating node doesn't pass the goal test, then add it to the frontier
		this.frontierNodes.add(this.problem.getOriginNode());
		
		while(!this.frontierNodes.isEmpty())
		{	
			
			LinkedList<Node> newNodes = this.frontierNodes.peek().expand(1.0);
			this.exploredNodes.add(this.frontierNodes.remove());
			
			for(Node node: newNodes)
			{
				if(this.goalTest(node))
				{
					return this.successRoutine(node);
				}
				else
				{		
					node.setHeuristicScore(this.aggregateHeuristic.h(node));
					System.out.println(this.aggregateHeuristic.h(node));
					this.frontierNodes.add(node);
				}
			}
			if(this.frontierNodes.size() > MAX_CAPACITY)
			{	
				Node lastNode= this.frontierNodes.peek();
				return this.successRoutine(lastNode);
			}			
		}
		//If no solution exists and we've exhaustively explored the search space, then return null.
		return null;
	}
	
}
