/**
 * Author: Ian Foertsch
 * Date: 12/18/14
 * Project: N-Puzzle Problem.
 */

package nPuzzle;

import graphicalTools.GraphicalObject;
import java.util.LinkedList;
import org.lwjgl.opengl.GL11;
import searchTools.Action;
import searchTools.State;
import nPuzzle.*;

/**
 * The NPuzzleGraph class handles the graphical representation of the N-Puzzle solving process. 
 * @author Ian
 *
 */
public class NPuzzleGraph implements GraphicalObject{

	private NPuzzleNode currentNode;
	private int puzzleDimension;
	private int graphDimension;
	private int tileDimension;
	private LinkedList<Tile> tiles;
	private LinkedList<Action> actionList;
	
	private int startX, startY;
	
	/**
	 * Constructor method. The graph accepts an origin node (to describe the initial state of the graph),
	 * an integer determining the pixel dimensions of the graph, a starting X and Y coordinate, and a linked 
	 * list of action objects describing the actions required to solve the graph.
	 * @param newNode
	 * @param graphDimension
	 * @param startX
	 * @param startY
	 * @param actions
	 */
	public NPuzzleGraph(NPuzzleNode newNode, int graphDimension, int startX, int startY, LinkedList<Action> actions)
	{
		//Initialize the parameters of the class, setting the current node to the "new Node"
		this.currentNode = newNode;
		this.graphDimension = graphDimension;
		this.startX = startX;
		this.startY = startY;
		this.actionList = actions;
		//Determine the tile dimension of the puzzle.
		this.puzzleDimension = ((NPuzzleState)currentNode.getCurrentState()).getDimension();
		//Determine the dimensions of the individual tiles by dividing the dimensions of the 
		//graph by the numbers of puzzles on a side.
		this.tileDimension = this.graphDimension/this.puzzleDimension;
		
		//Create a new list to hold the tiles in, then populate it with tile objects, loading their colors and 
		//positions from the puzzle array.
		this.tiles = new LinkedList<Tile>();
		this.populateTiles();
		this.updateTileColor();
	}
	
	/**
	 * populate tiles adds tiles to the tiles list when called, updating their colors and positions
	 */
	private void populateTiles()
	{
		for(int row = 0; row < this.puzzleDimension; row++)
		{
			for(int column = 0; column< this.puzzleDimension; column++)
			{
				tiles.add(new Tile((this.graphDimension/this.puzzleDimension), this.determineStartCoordinate(row, this.startX), 
						this.determineStartCoordinate(column, this.startY), 1.0,1.0,1.0, column, row));
			}
		}
	}
	
	/**
	 * determineStartCoordinate is used to move from the different graphical systems used in the graphical display and the puzzle. It updates coordinates 
	 * so that the tiles in the puzzle are displayed correctly. 
	 * @param puzzleCoordinate
	 */
	private int determineStartCoordinate(int startingPuzzleCoordinate, int startingGraphCoordinate)
	{
		int tileStart = startingGraphCoordinate + (startingPuzzleCoordinate*this.tileDimension);
		return tileStart;
	}
	
	/**
	 * The execute action method executes a single action from the class's Action list (if the list is empty, no operation is performed).
	 */
	public void executeAction()
	{	//If the action list has an action object in it,
		if(!this.actionList.isEmpty())
		{
			Action action = this.actionList.poll();
			//perform the action operation, creating a new state object
			State result = action.performAction(this.currentNode.getCurrentState());
			//bundle that state into a new node
			NPuzzleNode newNode = new NPuzzleNode(result, action, this.currentNode, this.currentNode.getPathCost()+1);
			//set the current node equal to the new Node.
			this.currentNode = newNode;
		}
		//Call the update tile color operation to access the current position of each tile and update the color. 
		this.updateTileColor();
	}
	
	/**
	 * The updateTileColor method accesses the current state of "currentNode" field within the class, calculates the appropriate color,
	 * and updates the entries in the this.tiles list to reflect the changes. 
	 */
	private void updateTileColor()
	{
		//Access the current state information in currentNode.
		int[][] puzzle = ((NPuzzleState)this.currentNode.getCurrentState()).getPuzzle();
		
		//Calculate the maximum value.
		int max = this.puzzleDimension*this.puzzleDimension;
		
		for(Tile tile: this.tiles)
		{
			//Assign the tile color by on a greyscale normalized by the maximum value.
			double value = ((double) puzzle[tile.row][tile.column])/max;
			
			tile.setColor(value, value, value);
		
		}
	}
	
	
	public void setCurrentNode(NPuzzleNode newNode)
	{
		this.currentNode = newNode;
	}
	
	/**
	 * Draw method executes the draw method on all tile objects held in the class's tiles list.
	 */
	@Override
	public void draw() {
		
		for(Tile tile: this.tiles)
		{
			tile.draw();
		}
		
	}
	
	/**
	 * The Tile class holds the graphical information and lwjgl methods required to create the graphical representation of the tile objects 
	 * during the puzzle solving process. 
	 * @author Ian Foertsch
	 *
	 */
	private class Tile implements GraphicalObject
	{
		int startX, startY, row, column;
		double red, green, blue;
		int graphicalDimension;
		
		/**
		 * constructor method: Each tile has a graphicalDimensions value(Describing how large the tile is in pixels(all tiles are square)), 
		 * as well as starting x and y coordinates.
		 * @param graphicalDimension
		 * @param x
		 * @param y
		 */
		public Tile(int graphicalDimension, int x, int y)
		{
			this.startX = x;
			this.startY = y;
			this.graphicalDimension = graphicalDimension;
		}
		/**
		 * Additional constructor method
		 * @param graphicalDimension : how large the tile is in pixels(all tiles are square).
		 * @param x : starting x coordinate on the display window.
		 * @param y : starting x coordinate on the display window.
		 * @param red : red value
		 * @param green : green value
		 * @param blue : blue value
		 * @param row : row within the puzzle
		 * @param column : column within the puzzle.
		 */
		public Tile(int graphicalDimension, int x, int y, double red, double green, double blue, int row, int column)
		{
			this.startX = x;
			this.startY = y;
			this.graphicalDimension = graphicalDimension;
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.row = row;
			this.column = column;
		}

		/**
		 * The setColor method updates the red/green/blue values for the tile object.
		 * @param red
		 * @param green
		 * @param blue
		 */
		public void setColor(double red, double green, double blue)
		{
			this.red = red;
			this.green = green;
			this.blue = blue;
		}
		
		/**
		 * The draw method accesses the lwjgl methods required to physically draw the tile object on the display. 
		 */
		public void draw()
		{
			GL11.glColor3d(this.red, this.green, this.blue);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2d(this.startX, this.startY);
			GL11.glVertex2d(this.startX + this.graphicalDimension, this.startY);
			GL11.glVertex2d(this.startX + this.graphicalDimension, this.startY + this.graphicalDimension);
			GL11.glVertex2d(this.startX, this.startY + this.graphicalDimension);
			GL11.glEnd();
		}
	}

}
