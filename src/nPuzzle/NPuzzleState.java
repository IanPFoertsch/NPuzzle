/**
 * Author: Ian Foertsch
 * Date: 12/14/14
 * Project: N Puzzle Problem Domain
 */
package nPuzzle;
import java.util.LinkedList;

import searchTools.*;

/**
 * The NPuzzle State class contains the two dimensional n-puzzle array.
 * @author Ian
 *
 */
public class NPuzzleState implements State {
	
	int[][] puzzle;
	
	/**
	 * Constructor accepts a two-dimensional array, and clones the arguement, assigning the cloned array to the class's puzzle field.
	 * @param puzzle
	 */
	public NPuzzleState(int[][] puzzle)
	{
		this.puzzle = puzzle.clone();
	}
	
	/**
	 * getPuzzle returns a two-dimensional array deep copy of the this state's puzzle.
	 * @return
	 */
	public int[][] getPuzzle()
	{
		int dimension = puzzle[0].length;
		int[][] copyOfPuzzle = new int[dimension][dimension];
		for(int row = 0; row < dimension; row++)
		{
			for(int column = 0; column < dimension; column++)
			{
				copyOfPuzzle[row][column] = puzzle[row][column];
			}
		}
		return copyOfPuzzle;
	}
	
	/**
	 * The equals method accepts an "Object" class object in order to override the implementation of .equals for the Object class.
	 * this necessessitates some awkward casting within the method to access the underlying state data.  
	 */
	@Override
	public boolean equals(Object compareMe)
	{
		int dimension = this.puzzle[0].length ;
		
		for(int row = 0; row < dimension; row++)
		{
			for(int column = 0; column < dimension; column++)
			{
				if(this.puzzle[row][column] != ((NPuzzleState) compareMe).getElement(row,column))
				{
					return false;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * getElement returns the integer value contained at the specified coordinates of the State's puzzle.
	 * @param row
	 * @param column
	 * @return
	 */
	public int getElement(int row, int column)
	{
		return this.puzzle[row][column];
	}
	
	/**
	 * Looks up the index of the array element containing the "0" within the array.
	 * @return
	 */
	protected int[] getIndexOfZero()
	{
		int dimension = puzzle[0].length;
		for(int row = 0; row < dimension; row++)
		{
			for(int column = 0; column < dimension; column++)
			{
				if(puzzle[row][column] == 0)
				{
					int[] index = {row, column};
					return index;
				}
			}
		}
		
		return null;
	}

	

	
	public State getDeepCopy() {
		return new NPuzzleState(this.getPuzzle());
	}
	
	/**
	 * getPossibleActions returns a list of legal puzzle actions, trimming any puzzle actions which would move tiles outside the bounds of the two-dimensional array.
	 * @return
	 */
	public LinkedList<Action> getLegalActions()
	{
		//Get the zero index
		int[] zeroIndex = this.getIndexOfZero();
		//Next, calculate the indices of all value addresses within one step of the zero index. 
		int[] north =  {zeroIndex[0] -1, zeroIndex[1]};
		int[] south = {zeroIndex[0] + 1, zeroIndex[1]};
		int[] east = {zeroIndex[0], zeroIndex[1] - 1};
		int[] west = {zeroIndex[0], zeroIndex[1] + 1};
		
		//create an array of those positions
		int[][] positions = {north, south, east, west};
		//create a linked list of action Objects
		LinkedList<Action> possibleActions = new LinkedList<Action>();
		
		//For each position array within the positions 2d array,
		for(int i = 0; i < positions.length; i++)
		{
			//run the determineValidAddress method to determine if the specified position is outside the bounds of the puzzle.
			if(determineValidAddress(positions[i]))
			{
				possibleActions.add(new NPuzzleAction(zeroIndex, positions[i]));
			}
		}
		return possibleActions;
	}
	
	/**
	 * determineValidAddress determines if the generated array address is valid(not either less than zero or more than the dimension of the n-puzzle.
	 * Also, this method checks the parent action to ensure that we are not undoing the action which led to the creation of this node, thus ensuring
	 * that infinite search trees are avoided. 
	 * @param possibleAddress
	 * @return
	 */
	public boolean determineValidAddress(int[] possibleAddress)
	{
		int dimension = this.puzzle[0].length;
			
		if(possibleAddress[0] < 0 || possibleAddress[0] > dimension -1 || possibleAddress[1] < 0 || possibleAddress[1] > dimension - 1)
		{
			return false;
		}
		return true;		
	}
	
	/**
	 * GetDimension returns the dimension of the puzzle along one side (Puzzles are square, so this dimension can be used as either width or
	 * height interchangably.
	 * @return
	 */
	public int getDimension()
	{
		return this.puzzle[0].length;
	}
	
	/**
	 * The getCoordinates(int) method returns a single-dimensional integer array describing the 
	 * location of a tile with the value specified by the parameter.
	 * @return
	 */
	public int[] getCoordinates(int value)
	{
		int dimension = this.puzzle[0].length;
		for(int row = 0; row < dimension; row++)
		{
			for(int column = 0; column < dimension; column++)
			{
				if(this.puzzle[row][column] == value)
				{
					return new int[] {row, column};
				}
			}
		}
		
		return null;
	}
	
	
	
	
}
