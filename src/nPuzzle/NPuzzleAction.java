/**
 * Author: Ian Foertsch
 * Date: 12/14/14
 * Project: N Puzzle Problem
 */

package nPuzzle;
import searchTools.*;

/**
 * The NPuzzleAction class represents a "slide" action within the N-puzzle problem domain. The class implements the Action interface
 * @author Ian
 *
 */
public class NPuzzleAction implements searchTools.Action {
	
	private int[] position1, position2;
	
	/**
	 * NPuzzleAction is initialized with two single dimension arrays, position1 and position2, describing the origin and destination of the 
	 * "slide" action within the puzzle respectively.
	 * @param position1
	 * @param position2
	 */
	public NPuzzleAction(int[] position1, int[] position2)
	{
		this.position1 = position1;
		this.position2 = position2;
	}
	
	
	/**
	 * performAction operates on the current state, and returns a new, altered state. In this n-puzzle version, the action to 
	 * be performed involves swapping two values within the state arrays. 
	 */
	public State performAction(State currentState)
	{
		//create method in current state which will return puzzleState
		int[][] puzzle = ((NPuzzleState)currentState).getPuzzle();
		
		//First load the value of the "origin" position1 to a temp value
		int temp = puzzle[position1[0]][position1[1]];
		//Assign the value in position2 to position1,
		puzzle[position1[0]][position1[1]] = puzzle[position2[0]][position2[1]];
		//Then load the temp value into position2
		puzzle[position2[0]][position2[1]] = temp;
		
		//create and return a new NPuzzleState object based on the altered puzzle array
		return new NPuzzleState(puzzle);
	}
	
	
	/**
	 * The equalsEitherPosition accepts an int[] array representing slide action coordinates
	 * @param testMe
	 * @return
	 */
	public boolean equalsEitherPosition(int[] testMe)
	{
		if(this.equalsPosition(testMe, this.position1)||this.equalsPosition(testMe, this.position2))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	/**
	 * Returns a deep copy of this action, containing duplicates of the Origin and Destination Coordinates of the 
	 * slide action.
	 */
	public Action deepCopy()
	{
		int length = this.position1.length;
		
		int[] duplicatePosition1 = new int[length];
		int[] duplicatePosition2 = new int[length];
		
		for(int i = 0; i < length; i++)
		{
			duplicatePosition1[i] = this.position1[i];
			duplicatePosition2[i] = this.position2[i];
		}
		
		return new NPuzzleAction(duplicatePosition1, duplicatePosition2);
		
	}
	
	/**
	 * Equals Position evaluates the equality of two integer arrays, representing coordinates within the n-puzzle.
	 * @param array1
	 * @param array2
	 * @return
	 */
	private boolean equalsPosition(int[] array1, int[] array2)
	{
		int dimension = this.position1.length;
		
		for(int i = 0; i < dimension; i++)
		{
			if(array1[i] != array2[i])
			{
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * EqualsBosthPositions accepts two integer arrays, returning true or false depending upon whether the arguement arrays are 
	 * equal to the origin and destination coordinates of this Action's fields.
	 * @param test1
	 * @param test2
	 * @return
	 */
	public boolean equalsBothPositions(int[] test1, int[] test2)
	{
		if((this.equalsPosition(test1, this.position1) && this.equalsPosition(test2, this.position2)) || 
				(this.equalsPosition(test1, this.position2) && this.equalsPosition(test2, this.position1)))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Overrides the object .equals method.
	 */
	@Override
	public boolean equals(Object action)
	{
		if(((NPuzzleAction)action).equalsBothPositions(this.position1, this.position2))
		{
			return true;
		}
		return false;
	}


	public int[] getPosition1() {
		return position1;
	}


	public int[] getPosition2() {
		return position2;
	}



}
