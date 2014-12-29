/**
 * Author: Ian Foertsch
 * Date: 12/15/14
 * Project: Heuristic Search
 */
package searchTools;
import java.util.ArrayList;

/**
 * The Aggregate Heuristic class is a packaging class which accepts multiple heuristic functions and makes simulataneous 
 * heuristic evaluation of search nodes more convenient.
 * @author Ian
 *
 */
public class AggregateHeuristic  implements HeuristicFunction{

	//The path cost function (Not actually a heuristic function, but it implements the heuristic function interface
	//in order to make packaging more convenient
	private HeuristicFunction pathCost;
	//An arrayList of functions
	private ArrayList<HeuristicFunction> functions;
	//A multiplier figure which is used to implement sub-optimal search algorithms.
	private double multiplier;
	
	/**
	 * Constructor accepts a multiplier, the pathcost heuristic, and a list of heuristic functions
	 * @param multiplier
	 * @param pathCost
	 * @param initialFunctions
	 */
	public AggregateHeuristic(double multiplier, HeuristicFunction pathCost, HeuristicFunction... initialFunctions)
	{
		this.pathCost = pathCost;
		this.functions = new ArrayList<HeuristicFunction>(initialFunctions.length);
		this.multiplier = multiplier;
		
		for(HeuristicFunction function: initialFunctions)
		{
			this.functions.add(function);
		}
	}

	/**
	 * the 'h' method is the heuristic node evaluation function.
	 */
	@Override
	public double h(Node node) {
		double score = 0.0;
		
		for(HeuristicFunction function: this.functions)
		{
			score += function.h(node);
		}
		
		return (score* this.multiplier) + pathCost.h(node);
	}
	
	
}
