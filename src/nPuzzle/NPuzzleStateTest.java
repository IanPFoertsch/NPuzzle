package nPuzzle;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class NPuzzleStateTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testEquals() {
		
		int[][] puzzle1 = {{0,1},{0,1}};
		NPuzzleState testState1 = new NPuzzleState(puzzle1);
		int[][] puzzle2 = {{0,1},{0,1}};
		NPuzzleState testState2 = new NPuzzleState(puzzle2);
		
		assertTrue(testState1.equals(testState2));	
	}
	
	
	@Test
	public void testZeroIndexTool()
	{
		int[][] puzzle = {{1,2,3},{4,5,6},{7,8,0}};
		NPuzzleState testState = new NPuzzleState(puzzle);
		
		int[] index = {2,2};
		
		assertArrayEquals(index, testState.getIndexOfZero());
	}
	
	
	@Test
	public void testValidAddressDetermination()
	{
		int[][] puzzle = {{1,2,3},{4,5,0},{7,8,6}};
		NPuzzleState puzzleState = new NPuzzleState(puzzle);
		
		int[] beyondColumnBounds = {2,3};
		assertFalse("Expected Test array coordinates 2,3 to be beyond the bounds of the node's valid address determination. NPuzzleNode dimensions: " + 
				puzzleState.getDimension() + ".", puzzleState.determineValidAddress(beyondColumnBounds));
		
		int[] beyondRowBounds = {3,2};
		assertFalse("Expected Test array coordinates 3,2 to be beyond the bounds of the node's valid address determination.",
				puzzleState.determineValidAddress(beyondRowBounds));
		
		int[] belowZeroRowBounds = {-1,2};
		int[] belowZeroColumnBounds = {2,-1};
		
		assertFalse(puzzleState.determineValidAddress(belowZeroRowBounds));
		assertFalse(puzzleState.determineValidAddress(belowZeroColumnBounds));
	}
	
	@Test
	public void testValidActionListSize()
	{
		int[][] puzzle = {{1,2,3},{4,5,0},{7,8,6}};
		NPuzzleState puzzleState = new NPuzzleState(puzzle);
		
		assertEquals(puzzleState.getLegalActions().size(), 3);
		
		int[][] puzzle2 = {{1,2,3},{4,5,6},{7,8,0}};
		NPuzzleState puzzleState2 = new NPuzzleState(puzzle2);
		
		assertEquals(puzzleState2.getLegalActions().size(), 2);
		
		int[][] puzzle3 = {{0,2,3},{4,5,6},{7,8,1}};
		NPuzzleState puzzleState3 = new NPuzzleState(puzzle3);
		
		assertEquals(puzzleState3.getLegalActions().size(), 2);
		
		
		int[][] puzzle4 = {{1,2,3},{4,0,5},{6,7,8}};
		NPuzzleState puzzleState4 = new NPuzzleState(puzzle4);
	
		assertEquals(puzzleState4.getLegalActions().size(), 4);
		
	}
	
	
}
