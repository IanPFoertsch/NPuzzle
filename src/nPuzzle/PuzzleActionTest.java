package nPuzzle;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import searchTools.State;


public class PuzzleActionTest {

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
	public void testEqualsPosition() {
		int[] position1 = {1,1,1};
		int[] position2 = {2,1,1};
		int[] position3 = {2,1,1};
		
		NPuzzleAction action = new NPuzzleAction(position1, position2);
		
		Assert.assertTrue("Equals Position Method failed to execute as expected.", action.equalsEitherPosition(position3));
	}
	
	@Test
	public void testNPuzzleActionPerformsAsExpected() {
		int[][] puzzle1 = {{0,1},{0,0}};
		State testState1 = new NPuzzleState(puzzle1);
		int[][] puzzle2 = {{0,0},{0,1}};
		State testState2 = new NPuzzleState(puzzle2);
		
		int[] position1 = {0,1};
		int[] position2 = {1,1};
		
		NPuzzleAction action = new NPuzzleAction(position1, position2);
		
		State testMe = action.performAction(testState1);
		assertArrayEquals("Swap Operation did not perform as expected", ((NPuzzleState)testState2).getPuzzle(), ((NPuzzleState)testMe).getPuzzle());
	}
	@Test
	public void testParentActionEquality()
	{
		int[] position1 = {0,1};
		int[] position2 = {1,1};
		
		NPuzzleAction action1 = new NPuzzleAction(position1, position2);
		NPuzzleAction action2 = new NPuzzleAction(position1, position2);
		
		assertEquals(action1, action2);
	}
	
	
	

}
