/**
 * Author: Ian Foertsch
 * Date: 12/15/14
 * Project: N Puzzle Solver
 */

package nPuzzle;
import searchTools.HeuristicFunction;
import searchTools.Node;



/**
 * The manhattanDistanceHeuristic Implements the HeuristicFunction interface and is used to estimate the number of moves required
 * to solve instances of the N-puzzle.   
 * @author Ian Foertsch
 *
 */
public class ManhattanDistanceHeuristic implements HeuristicFunction{
	

	//The goalPuzzle serves as a lookup table for the correct tile position in a solved puzzle.
	private int[][] goalPuzzle;
	private int dimension;
	
	/**
	 * Constructor accepting a goal node containing a solved (ordered) instance of the puzzle.
	 * @param goalNode
	 */
	public ManhattanDistanceHeuristic(Node goalNode)
	{
		
		this.goalPuzzle = ((NPuzzleState)goalNode.getCurrentState()).getPuzzle();
		this.dimension = this.goalPuzzle[0].length;
	}
	
	/**
	 * The misplaced Tile heuristic evaluates the distance to the goal by counting the number of cells
	 * in the puzzle array which are out of position compared to the goal Node.
	 */
	public double h(Node node)
	{	//Get the current state from the node in question
		int[][] currentState = ((NPuzzleState) node.getCurrentState()).getPuzzle();
		
		//initialize the score to 0
		double score = 0.0;
		
		//for each row in the state smaller 
		for(int row = 0; row < this.dimension; row++)
		{
			//For each column in the state
			for(int column = 0; column < this.dimension; column++)
			{
				//do not add to the heuristic score if the tile is == 0(representing "blank")
				if(currentState[row][column] != 0)
				{
					//lookup the proper location of the tile, calculate the distance 
					//using the manhattan distance heuristic and add it to the current 
					//heuristic score
					try {
						score += this.coordinateDistance(new int[] {row, column}, this.lookupValueCoordinateInGoal(currentState[row][column]));
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}
		return score;
	}
	
	/**
	 * The coordiateDistance function returns the "manhattan" distance between two integer arrays,
	 * the contents of which represent dimensional coordinates.
	 * @param coordinate1
	 * @param coordinate2
	 * @return
	 */
	private double coordinateDistance(int[] coordinate1, int[]coordinate2)
	{
		int length = coordinate1.length;
		
		double difference = 0;
		
		for(int i = 0; i < length; i++)
		{
			difference += Math.abs(coordinate1[i] - coordinate2[i]);
		}
		
		return difference;
	}
	
	/**
	 * Locates the location of a specified integer arguement (representing a tile) within the N-puzzle 2d array.
	 * @param value
	 * @return
	 * @throws Exception
	 */
	private int[] lookupValueCoordinateInGoal(int value) throws Exception
	{
		int length = this.goalPuzzle.length;
		for(int row = 0; row < length; row ++)
		{
			for(int column = 0; column < length; column++)
			{
				if(this.goalPuzzle[row][column] == value)
				{
					return new int[] {row, column};
				}
			}
		}
		throw new Exception("ERROR: Specified value " + value + " not within puzzle bounds.");
	}
}