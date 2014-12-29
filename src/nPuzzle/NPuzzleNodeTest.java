package nPuzzle;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import searchTools.*;

import static org.junit.Assert.*;
import junit.framework.TestCase;


public class NPuzzleNodeTest extends TestCase {
	
	private NPuzzleNode node;
	private NPuzzleAction action;
	
	
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		int[][] puzzle = {{1,2,3},{4,5,0},{7,8,6}};
		
		int[] position1 = {2,2};
		int[] position2 = {1,2};
		
		NPuzzleState state = new NPuzzleState(puzzle);
		
		this.action = new NPuzzleAction(position1, position2);
		
		this.node = new NPuzzleNode(action.performAction(state), action, null, 0.0);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	
	@Test
	public void testSwapOperationInNPuzzleNode()
	{
		
		int[][] expectedValue = {{1,2,3},{4,5,6},{7,8,0}};
		assertArrayEquals(((NPuzzleState)this.node.getCurrentState()).getPuzzle(), expectedValue);
	}
	
	
	
	/**
	 * testActionListSize() tests whether the possible action list is of the correct size for an 
	 * empty node located in the center of the array. This empty node will have 4 possible move positions.
	 */
	@Test
	public void testActionListSize()
	{
		int[][] puzzle = {{1,2,3}, {4,0,6},{7,8,5}};
		NPuzzleState state = new NPuzzleState(puzzle);
		NPuzzleNode newNode = new NPuzzleNode(state, null, null, 0.0);
		
		LinkedList<Node> expandedNodes = newNode.expand(0.0);
		
		
		assertEquals("Expected list size: 4. Actual list size: " + expandedNodes.size(), expandedNodes.size(), 4);
	}
	
	
	
	@Test
	public void testExpand()
	{
		int[][] puzzle = {{1,2,3}, {4,0,5},{6,7,8}};
		NPuzzleState originState = new NPuzzleState(puzzle);
		NPuzzleNode newNode = new NPuzzleNode(originState, null, null, 0.0);
		
		int[][] northMove = {{1,0,3},{4,2,5},{6,7,8}};
		int[][] southMove = {{1,2,3},{4,7,5},{6,0,8}};
		int[][] eastMove = {{1,2,3},{4,5,0}, {6,7,8}};
		int[][] westMove = {{1,2,3},{0,4,5},{6,7,8}};
		
		LinkedList<Node> successorNodes = newNode.expand(0.0);
		
		boolean nodeFound = false;
		
		LinkedList<NPuzzleState> knownStates= new LinkedList<NPuzzleState>();
		
		knownStates.add(new NPuzzleState(northMove));
		knownStates.add(new NPuzzleState(southMove));
		knownStates.add(new NPuzzleState(eastMove));
		knownStates.add(new NPuzzleState(westMove));
		
		for(Node node: successorNodes)
		{
			nodeFound = false;
			for(State state: knownStates)
			{
				if(state.equals(node.getCurrentState()))
				{
					nodeFound = true;			
				}
			}
			
			if(nodeFound == false)
			{
				fail("Error: unable to locate known successor state when expanding Node.");
			}	
		}
		
	}
	
	
	@Test 
	public void testParentActionTracing()
	{
		LinkedList<Node> newNodes = this.node.expand(0.0);
		
		assertEquals("Expected expanded nodes list to have size of 1. Size: " + newNodes.size(), 1, newNodes.size());
	}

	@Test
	public void testEqualsMethod()
	{
		int[][] puzzle = {{1,2,3}, {4,0,5},{6,7,8}};
		NPuzzleState originState = new NPuzzleState(puzzle);
		NPuzzleNode newNode = new NPuzzleNode(originState, null, null, 0.0);
		
		NPuzzleNode secondNode = new NPuzzleNode(originState.getDeepCopy(), null, null, 0.0);
		
		assertEquals(newNode, secondNode);
		
		int[][] puzzle3 = {{1,2,4}, {4,0,5},{6,7,8}};
		
		NPuzzleState thirdState = new NPuzzleState(puzzle3);
		NPuzzleNode thirdNode = new NPuzzleNode(thirdState, null, null, 0.0);
		assertFalse(thirdNode.equals(secondNode));
	}
	
	
}




