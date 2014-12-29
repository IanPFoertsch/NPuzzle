package nPuzzle;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import searchTools.*;

public class ManhattanDistanceHeuristicTest {

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
	public void test() {
		int[][] sample = {{1,2,3},{4,5,6},{7,0,8}};
		int[][] solvedPuzzle = {{1,2,3},{4,5,6},{7,8,0}};
		Node newNode = new NPuzzleNode(new NPuzzleState(sample), null, null, 0.0);
		
		HeuristicFunction heuristic = new ManhattanDistanceHeuristic(new NPuzzleNode(new NPuzzleState(solvedPuzzle), null, null, 0.0));
		
		assertEquals("Error: expecting 1, returned: " + heuristic.h(newNode),heuristic.h(newNode), 1.0, .0001);
		
		
		
	}

}
