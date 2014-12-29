package nPuzzle;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import searchTools.HeuristicFunction;
import searchTools.Node;


public class MisplacedTileHeuristicTest {

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
	public void testResults() {
		int[][] puzzle = {{1,2,3}, {4,0,5},{6,7,8}};
		NPuzzleState testState = new NPuzzleState(puzzle);
		NPuzzleNode testNode = new NPuzzleNode(testState, null, null, 0.0);
		
		int[][] orderedState = {{1,2,3}, {4,5,6},{7,8,0}};
		NPuzzleState goalState = new NPuzzleState(orderedState);
		Node goalNode = new NPuzzleNode(goalState, null, null, 0.0);
		
		HeuristicFunction heuristic = new MisplacedTileHeuristic(goalNode);
		assertEquals(heuristic.h(testNode), 5.0, .0001);
		
		int[][] allwrong = {{10,10,10},{10,10,10},{10,10,10}};
		NPuzzleState wrongState = new NPuzzleState(allwrong);
		NPuzzleNode wrongNode = new NPuzzleNode(wrongState, null, null, 0.0);
		
		assertEquals(heuristic.h(wrongNode), 9, .0001);
		
	}

}
