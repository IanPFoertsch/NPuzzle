package nPuzzle;
import searchTools.HeuristicFunction;
import searchTools.Node;



public class MisplacedTileHeuristic implements HeuristicFunction{
	
	private final int MISPLACED_PENALTY = 1;
	
	private int[][] goalPuzzle;
	private int dimension;
	
	public MisplacedTileHeuristic(Node goalNode)
	{
		
		this.goalPuzzle = ((NPuzzleState)goalNode.getCurrentState()).getPuzzle();
		this.dimension = this.goalPuzzle[0].length;
	}
	
	/**
	 * The misplaced Tile heuristic evaluates the distance to the goal by counting the number of cells
	 * in the puzzle array which are out of position compared to the goal Node.
	 */
	public double h(Node node)
	{
		int[][] currentState = ((NPuzzleState) node.getCurrentState()).getPuzzle();
		
		double score = 0.0;
		
		for(int row = 0; row < this.dimension; row++)
		{
			for(int column = 0; column < this.dimension; column++)
			{
				score += this.evaluate(currentState[row][column], this.goalPuzzle[row][column]);
			}
		}
		
		return score;
		
	}
	
	private double evaluate(int value1, int value2)
	{
		if(value1 == value2)
		{
			return 0.0;
		}
		else
		{
			return this.MISPLACED_PENALTY;
		}
	}

}
