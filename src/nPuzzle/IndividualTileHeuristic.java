package nPuzzle;

import searchTools.HeuristicFunction;
import searchTools.Node;

public class IndividualTileHeuristic implements HeuristicFunction
{
	//BLANKTILEVALUE holds the integer value of the empty space in the n-puzzle.
	private final static int BLANKTILEVALUE = 0;
	private int tileValue;
	private int[] goalCoordinate;
	//ONETILE is the one tile distance used in checking if the blank tile is more than one space away from the
	//target tile. it is set to 1.1 in order to prevent floating point arithmatic error risk in comparing two
	//very similar values.
	private final static double ONETILE = 1.1;
	private double multiplier; 
	

	
	//Constructor method: Get a goal state and lookup the coordinates of the tile in question.
	//No need to store the goal state locally, because this is a heuristic function for only part of the 
	//goal state. 
	public IndividualTileHeuristic(Node goalNode, int tileValue, double multiplier)
	{	
		this.tileValue = tileValue;
		this.multiplier = multiplier;
		this.goalCoordinate = ((NPuzzleState) goalNode.getCurrentState()).getCoordinates(this.tileValue);
	}
	
	
	
	public double h(Node node) {
		int[] targetCoordinate = ((NPuzzleState) node.getCurrentState()).getCoordinates(tileValue);
		int[] blankCoordinate = ((NPuzzleState) node.getCurrentState()).getCoordinates(BLANKTILEVALUE);
		
		double tileDistance = this.coordinateDistance(targetCoordinate, this.goalCoordinate); 
		if(tileDistance < 1)
		{
			return 0.0;
		}
		else
		{
			double blankDistance = this.blankDistanceToTile(targetCoordinate, blankCoordinate);
			return (tileDistance + blankDistance) * multiplier;
		}
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
		double difference = 0.0;
		for(int i = 0; i < length; i++)
		{
			difference += this.absoluteDistance(coordinate1[i], coordinate2[i]);
		}
		return difference;
	}
	
	private double absoluteDistance(int a, int b)
	{
		return Math.abs(a - b);
	}
	
	/**
	 * The blankDistanceToTile measures the distance from the blank tile to the heuristic's target tile. 
	 * this portion of the heuristic is designed to guide the algorithm in moving the blank space towards the target
	 * tile. This is useful in large instances of the N-puzzle where the algorithm would ahve to search the space
	 * exhaustively if the blank tile were a significant distance from the target tile, because only changes in the 
	 * target tile's location are reflected in the state's heuristic score.
	 * 
	 * The distance from the blank tile to the target tile is added to heuristic score only if the blank space is 
	 * more than 1 tile away from the target tile (diagonally adjacent tiles are counted as a single tile away from
	 * the target.
	 * @param tileCoordinate
	 * @param blankCoordinate
	 * @return
	 */
	public double blankDistanceToTile(int[] tileCoordinate, int[] blankCoordinate)
	{
		//The coordinates of the blank tile are looked up in the parent method.
		//First, determine if the blank coordinate is within 1 tile (including diagonal adjacency) of the target tile
		//if so, return 0;
		int dimension = tileCoordinate.length;
		double distances = 0.0;
		boolean withinOne = true;
		double singleDistance = 0.0;
		
		for(int i = 0; i < dimension; i++)
		{
			//access both coordinate arrays and calculate their distance
			singleDistance = this.absoluteDistance(tileCoordinate[i], blankCoordinate[i]);
			if(singleDistance > ONETILE )
			{
				withinOne = false;
			}
			distances += singleDistance;
		}
		
		if(withinOne == true)
		{
			return 0;
		}
		else 
		{
			return distances;
		}
	}
}
