package nPuzzle;

import java.util.LinkedList;

import searchTools.AggregateHeuristic;
import searchTools.HeuristicFunction;
import searchTools.HeuristicSearch;
import searchTools.IndividualTileHeuristicSearch;
import searchTools.Node;
import searchTools.PathCostHeuristic;
import searchTools.Problem;
import searchTools.Search;
import searchTools.SearchFactory;
import searchTools.Action;

public class test {
	
	
	
	public static void main(String[] args)
	{
		int dimension = 3;
		int multiplier = 1;
		
		LinkedList<LinkedList<Action>> path = new LinkedList<LinkedList<Action>>();
		NPuzzleProblem problem = new NPuzzleProblem(dimension);
		
		
		
		for(int i = 1; i < (dimension*dimension); i++)
		{
			HeuristicFunction tileHeuristic = new IndividualTileHeuristic(problem.getGoalNode(), i, multiplier);
			HeuristicFunction heuristic = new AggregateHeuristic(multiplier, new PathCostHeuristic(), tileHeuristic);
			IndividualTileHeuristicSearch search = new IndividualTileHeuristicSearch(problem, tileHeuristic, heuristic);
			
			path.add(search.search());
			Node node = search.getGoalNode();
			problem = new NPuzzleProblem(dimension, node);
			System.out.println("Loop " + i + " Executed");
		}
		
		
		
		
		
	}
	
	public static void test2()
	{
		/*
		System.out.println("Building N Puzzle Objects");
		LinkedList<Action> path = new LinkedList<Action>();
		NPuzzleProblem problem = new NPuzzleProblem(this.GRAPH_DIMENSION);
		
		
		System.out.println("Loop entered");
		for(int i = 1; i < (this.GRAPH_DIMENSION*this.GRAPH_DIMENSION); i++)
		{
			System.out.println("Entering solving loop");
			HeuristicFunction tileHeuristic = new IndividualTileHeuristic(problem.getGoalNode(), i, this.multiplier);
			HeuristicFunction heuristic = new AggregateHeuristic(this.multiplier, new PathCostHeuristic(), tileHeuristic);
		    this.search = new IndividualTileHeuristicSearch(problem, tileHeuristic, heuristic);
			
			path.addAll(search.search());
			Node node = search.getGoalNode();
			problem = new NPuzzleProblem(this.GRAPH_DIMENSION, node);
			System.out.println("Loop " + i + " Executed");
		}
		
		this.graph = new NPuzzleGraph((NPuzzleNode)problem.getOriginNode(), this.GRAPH_DIMENSION, this.INITIAL_GRAPH_POINT, this.INITIAL_GRAPH_POINT, path);
		System.out.println("diag");
		*/
	}
}
