package nPuzzle;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import searchTools.*;


public class NPuzzleProblemTest {

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
	public void testGoalFormulation() {
		//Initialize new problem of some dimension
		NPuzzleProblem problem = new NPuzzleProblem(3);
		//get the state of that problem and compare it to a known value
		int[][] knownGoalValue = {{1,2,3},{4,5,6},{7,8,0}};
		
		Node testGoalNode = new NPuzzleNode(new NPuzzleState(knownGoalValue), null, null, 0.0);
		
		
		
		assertEquals(problem.getGoalNode(), testGoalNode);
	}
	

	
	@Test
	public void testLoadToSingleDimensionArray()
	{
		NPuzzleProblem problem = new NPuzzleProblem(3);
		
		int[][] sampleArray = {{1,2,3},{4,5,6},{7,8,0}};
		
		int[] knownResult = {1,2,3,4,5,6,7,8,0};
		
		assertArrayEquals(problem.loadToSingleDimensionArray(sampleArray), knownResult);
	}
	
	@Test
	public void testInversionsCounter()
	{
		NPuzzleProblem problem = new NPuzzleProblem(3);
		
		int[] testThis = {1,2,3,4,6,7,8,5,0};
		
		assertEquals(problem.countInversions(testThis), 3);
	}
	
	@Test
	public void testGetIndexOfZero()
	{
		NPuzzleProblem problem = new NPuzzleProblem(3);
		
		int[][] testThis = {{1,2,3},{4,5,6},{7,8,0}};
		
		assertEquals(problem.getRowOfZero(testThis), 2);
	}
	
	@Test
	public void testIsSolveable()
	{
		NPuzzleProblem problem = new NPuzzleProblem(3);
		
		int[][] testThis = {{1,3,2},{5,4,7},{6,8,0}};
		
		assertFalse(problem.isSolvable(testThis));
		
		NPuzzleProblem problem2 = new NPuzzleProblem(4);
		
		int[][] testEvenDimension = {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,0}};
		
		assertTrue(problem2.isSolvable(testEvenDimension));
	}

	

	
}
