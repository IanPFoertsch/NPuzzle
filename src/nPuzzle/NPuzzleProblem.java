/**
 * Author: Ian Foertsch
 * Date: 12/14/14
 * Project: N-Puzzle Heuristic Search
 */

package nPuzzle;
import java.util.ArrayList;
import java.util.Collections;

import searchTools.*;


/**
 * N-Puzzle problem.  Implements the problem interface.
 * @author Ian
 *
 */
public class NPuzzleProblem implements Problem{
		
	private Node originNode;
	private Node goalNode;
	
	/**
	 * Upon initialization, nPuzzleProblem initializes to a random n-Puzzle arrangement to the size of the specified dimension argument.
	 */
	public NPuzzleProblem(int dimension)
	{
		//Generate an ordered N-Puzzle state and assign a node containing that state to the goalNode field.
		this.goalNode = new NPuzzleNode(this.generateOrderedNPuzzleState(dimension), null, null, 0.0); 
		//Then scramble that state to create a random instance of the N-puzzle, and create a node containing that state, set the originNode field equal to 
		//the random puzzle state.
		this.originNode =new NPuzzleNode(new NPuzzleState(this.shuffleNPuzzleState(this.generateOrderedNPuzzleState(dimension).getPuzzle())), null, null, 0.0);
	}
	
	/**
	 * Additional constructor accepts and origin node as well as a dimension arguement. This is primarily used in chaining problems 
	 * together during problem decomposition. 
	 * The origin node becomes 
	 * @param dimension
	 * @param originNode
	 */
	public NPuzzleProblem(int dimension, Node originNode)
	{
		this.originNode = originNode;
		this.goalNode = new NPuzzleNode(this.generateOrderedNPuzzleState(dimension), null, null, 0.0);
	}
	
	/**
	 * generateRandomNPuzzleState generates a random n-Puzzle of a specified dimension 
	 */
	private NPuzzleState generateOrderedNPuzzleState(int dimension)
	{
		int[][] puzzle = new int[dimension][dimension];
		
		//starting at 1,
		int number = 1;
		//For each row,
		for(int row = 0; row < dimension; row++)
		{	
			//for each column within the row
			for(int column = 0; column < dimension; column++)
			{
				//Assign the contents of the puzzle array at the given coordinates the value of "number"
				puzzle[row][column] =  number;
				number++;
			}
		}
		//initialize the coordinate in the puzzle to 0
		puzzle[dimension -1][dimension -1] = 0;
		return new NPuzzleState(puzzle);
	}
	
	public Node getOriginNode()
	{
		return this.originNode.deepCopy();
	}
	
	public Node getGoalNode()
	{
		return this.goalNode.deepCopy();
	}
	
	/**
	 * SuffleNPuzzleState accepts an existing two dimensional int[][] array, copies it, and returns a randomized copy.
	 * @param puzzle
	 * @return
	 */
	public int[][] shuffleNPuzzleState(int[][] puzzle)
	{
		int dimension = puzzle[0].length;
		ArrayList<Integer> values = new ArrayList<Integer>();
		//Load the contents of the puzzle array to an arrayList of integers
		for(int row = 0; row < dimension; row++)
		{
			for(int column = 0; column < dimension; column++)
			{
				values.add(puzzle[row][column]);
			}
		}
		//Shuffle that collection to randomize it.
		Collections.shuffle(values);
		
		//create a new 2d array to hold the return values.
		int[][] newPuzzle = new int[dimension][dimension];
				
		//Load those randomized values back into a new array and return it.
		for(int row = 0; row < dimension; row++)
		{
			for(int column = 0; column < dimension; column++)
			{
				newPuzzle[row][column] = values.remove(0);
			}
		}
		//check if the newly created puzzle is solveable 
		if(this.isSolvable(newPuzzle))
		{
			return newPuzzle;
		}
		//if the new puzzle is not solveable, call the shuffle function again on the old puzzle array.
		else
		{
			return this.shuffleNPuzzleState(puzzle);
		}
	
	}
	
	/**
	 * LoadToSingleDimensionArray accepts a two dimensional array and returns a single dimensional array with the 
	 * contents arrayed in order.
	 * @param puzzle
	 * @return
	 */
	protected int[] loadToSingleDimensionArray(int[][] puzzle)
	{
		int dimension = puzzle[0].length;
		int index = 0;
		int[] array = new int[dimension*dimension];
		
		
		for(int row = 0; row < dimension; row++)
		{
			for(int column = 0; column < dimension; column++)
			{
				array[index] = puzzle[row][column];
				index++;
			}
		}
		
		return array;
	}
	
	/**
	 * Count inversions counts the number of position inversions present within a single-dimensional array.
	 * @param testArray
	 * @return
	 */
	protected int countInversions(int[] testArray)
	{
		int length = testArray.length;
		int inversions = 0;
		//For each position within the array,
		for(int index = 0; index < length; index++)
		{	
			//load the comparison value from the test array
			int compareMe = testArray[index];
			for(int testValue = index + 1; testValue < length; testValue++)
			{
				//Count the number of times the compareMe value is greater than the value contained
				//within the testArray in all the positions prior to the position of the compareMe value.
				if(compareMe > testArray[testValue] && testArray[testValue] != 0)
				{
					inversions++;
				}
			}
		}
		//Return the number of inversions
		return inversions;
	}
	
	/**
	 * goalTest runs the .equals method on the arguement node and the problem's goalnode field, returning the result of the logical test. 
	 */
	public boolean goalTest(Node testMe)
	{
		if(testMe.equals(this.goalNode))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Is solvable returns true or false depending on whether the input array is solveable.
	 * @param testArray
	 * @return
	 */
	public boolean isSolvable(int[][] testArray)
	{
		int dimension = testArray.length;
		int inversions = this.countInversions(this.loadToSingleDimensionArray(testArray));
		/*
		 * First if the array is of odd dimensions, then a simple inversion count will tell us if it is solveable.
		 */
		if(dimension % 2 != 0)
		{
			//If the inversions are odd, then the puzzle is not solveable and we should return false;
			if(inversions % 2 != 0)
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		//Otherwise we have to combine the inversions and the row index of the blank tile (In our case represented by "0"
		else
		{
			int result = inversions + this.getRowOfZero(testArray);
			
			if(result % 2 != 0)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	}

	/**
	 * Returns the row containing the zero, or "blank space" in the N-puzzle.
	 * @param testArray
	 * @return
	 */
	protected int getRowOfZero(int[][] testArray)
	{
		int length = testArray.length;
		for(int row = 0; row < length; row++)
		{
			for(int column = 0; column < length; column++)
			{
				if(testArray[row][column] == 0)
				{
					return row;
				}
			}
		}
		
		return 0;
	}
}
