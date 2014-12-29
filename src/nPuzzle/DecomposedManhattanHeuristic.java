package nPuzzle;
import java.util.ArrayList;

import searchTools.HeuristicFunction;
import searchTools.Node;


/**
 * The DecomposedManhattanHeurstic is an implementation
 * of the manhattan distance heuristic with decomposed 
 * puzzle states. The heuristic identifies which tiles are
 * included in the heuristic evaluation and which to ignore 
 * based on a depth arguement specified as a parameter. 
 * @author Ian
 *
 */
public class DecomposedManhattanHeuristic implements HeuristicFunction{
	
	
	
	private int[][] goalPuzzle;
	private int dimension;
	private ArrayList<Integer> includedTiles;
	private double multiplier;
	
	/**
	 * The decomposed Heuristic accepts an integer arguement specifying the depth to which tiles
	 * should be included in the heuristic evaluation. Admissible values are determined as tiles 
	 * with a row OR column coordinate less than the specified depth. For example, if depth was specified as 1,
	 * any tile with a row coordinate == 0 OR a tile coordinate == 0 would be included in the heuristic
	 * evaluation score. 
	 * @param goalNode
	 */
	public DecomposedManhattanHeuristic(Node goalNode, int depth, double multiplier)
	{
		this.goalPuzzle = ((NPuzzleState)goalNode.getCurrentState()).getPuzzle();
		this.dimension = this.goalPuzzle[0].length;
		this.buildIncludedTiles(depth);
		this.multiplier = multiplier;
	}
	
	public void buildIncludedTiles(int depth)
	{
		this.includedTiles = new ArrayList<Integer>();
		for(int row = 0; row < this.dimension; row++)
		{
			for(int column = 0; column < this.dimension; column++)
			{
				if(row < depth || column < depth)
				{
					this.includedTiles.add(new Integer(this.goalPuzzle[row][column]));
				}
			}
		}
		
		
	}
	
	public ArrayList<Integer> getIncludedTiles()
	{
		ArrayList<Integer> copy = new ArrayList<Integer>(this.includedTiles.size());
		
		for(int i = 0; i < this.includedTiles.size(); i++)
		{
			copy.add(new Integer(this.includedTiles.get(i).intValue()));
		}
		
		
		return copy;
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
				Integer value = new Integer(currentState[row][column]); 
				if((value.intValue() != 0) && (this.includedTiles.contains(value)))
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
		if(score == 0.0)
		{
			System.out.println("Completely Sorted subproblem identified");
		}
		return score * multiplier;
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
