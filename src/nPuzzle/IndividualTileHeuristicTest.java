package nPuzzle;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import searchTools.HeuristicFunction;
import searchTools.Node;

public class IndividualTileHeuristicTest {

	HeuristicFunction heuristic;
	
	
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
		
		this.heuristic = new IndividualTileHeuristic(goalNode, 1, 1);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSingleDistanceFunction() {
		
		int[][] nonGoalPuzzle = {{15,2,3,4}, {5,6,7,8},{9,10,11,12},{13,14,0,1}};
		NPuzzleState nonGoalState = new NPuzzleState(nonGoalPuzzle);
		Node nonGoalNode = new NPuzzleNode(nonGoalState, null, null, 0.0);
				
		assertEquals(heuristic.h(nonGoalNode), 6, .001 );	
	}
	
	@Test
	public void testBlankTileDistanceFunction()
	{
		int[][] nonGoalPuzzle = {{2,1,3,4}, {5,6,7,8},{9,10,11,0},{13,14,15,9}};
		NPuzzleState nonGoalState = new NPuzzleState(nonGoalPuzzle);
		Node nonGoalNode = new NPuzzleNode(nonGoalState, null, null, 0.0);
				
		assertEquals(heuristic.h(nonGoalNode), 5, .001 );
	}
	
	@Test
	public void testCorrectTilePosition()
	{
		int[][] nonGoalPuzzle = {{1,2,3,4}, {5,6,7,8},{9,10,11,0},{13,14,15,9}};
		NPuzzleState nonGoalState = new NPuzzleState(nonGoalPuzzle);
		Node nonGoalNode = new NPuzzleNode(nonGoalState, null, null, 0.0);
				
		assertEquals(heuristic.h(nonGoalNode), 0, .001 );
	}
}
