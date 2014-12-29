package nPuzzle;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import searchTools.Node;

public class DecomposedManhattanHeuristicTest {

	DecomposedManhattanHeuristic heuristic;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		int[][] puzzle = {{1,2,3,4}, {5,6,7,8},{9,10,11,12},{13,14,15,0}};
		NPuzzleState goalState = new NPuzzleState(puzzle);
		Node goalNode = new NPuzzleNode(goalState, null, null, 0.0);
		
		heuristic = new DecomposedManhattanHeuristic(goalNode, 1, 1);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIncludedValues() {
		
		ArrayList<Integer> includedTiles = heuristic.getIncludedTiles();
		//The included values are the values of the tiles in the topmost row and leftmost column.
		int[] values = {1,2,3,4,5,9,13};
		
		
		for(int i = 0; i < values.length; i++)
		{
			if(!includedTiles.contains(new Integer(values[i])))
			{
				fail("Error: The included tiles heuristic "
						+ "does not contain the expected tile value: " + values[i]);
			}
		}
	}
	
	@Test
	public void testHeuristicScoreIgnoresExcludedValues()
	{
		int[][] puzzle = new int[][] {{1,2,3,4},{5,7,7,7},{9,11,10,14},{13,12,15,0}};
		NPuzzleState state = new NPuzzleState(puzzle);
		Node exampleNode = new NPuzzleNode(state, null, null, 0.0);
		
		double score = heuristic.h(exampleNode);
	
		if(score != 0.0)
		{
			fail("Decomposition heuristic score expected to equal 0.0, instead equaled " + score);
		}
	}
	
	@Test
	public void testHeuristicScoreFunction()
	{
		int[][] puzzle = new int[][] {{1,2,3,12},{5,6,7,8},{10,11,9,4},{13,14,15,0}};
		NPuzzleState state = new NPuzzleState(puzzle);
		Node exampleNode = new NPuzzleNode(state, null, null, 0.0);
		
		assertEquals(heuristic.h(exampleNode), 4.0, .001);
		
	}

}
