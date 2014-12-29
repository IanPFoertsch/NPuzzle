/**
 * Author: Ian Foertsch
 * Date: 12/18/14
 * Project: N-Puzzle Problem.
 */
import java.util.LinkedList;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import searchTools.*;
import nPuzzle.*;

/**
 * The NPuzzleDriver class packages the puzzle creation, solving and graphical display operations into one class. The class accepts puzzle dimension and 
 * heuristic multiplier constructor arguments, which determine the size of the puzzle and heuristic multiplier to use during heuristic evaluation of search nodes.
 * @author Ian
 *
 */
public class NPuzzleDriver {
	
	private int PUZZLE_DIMENSION;
	final int WINDOW_DIMENSION = 900;
	private Search search;
	private NPuzzleGraph graph;
	private int GRAPH_BORDER = 300;
	private int GRAPH_DIMENSION = this.WINDOW_DIMENSION - GRAPH_BORDER;
	private int INITIAL_GRAPH_POINT = this.GRAPH_BORDER / 2;
	private int multiplier;
			
	
	public NPuzzleDriver(int dimension, int multiplier)
	{
		this.PUZZLE_DIMENSION = dimension;
		this.multiplier = multiplier; 
		this.buildNPuzzleObjects();
		this.buildGraphicalObjects();
		this.mainLoop();
	}
	
	/**
	 * Creates the N-Puzzle objects required to graph and animate the search.
	 */
	public void buildNPuzzleObjects()
	{
		
		Problem problem = new NPuzzleProblem(PUZZLE_DIMENSION);
		HeuristicFunction heuristic = new AggregateHeuristic(multiplier, new PathCostHeuristic(), new ManhattanDistanceHeuristic(problem.getGoalNode()));
		//HeuristicFunction heuristic = new ManhattanDistanceHeuristic(problem.getGoalNode());
		this.search = SearchFactory.buildSearch(problem, heuristic);
		//This step involves the actual search operation which consumes the bulk of the computational resources of this class.
		//the this.search.search() operation performs the search, returning the linked list of action objects needed to animate the graph.
		this.graph = new NPuzzleGraph((NPuzzleNode)problem.getOriginNode(), this.GRAPH_DIMENSION, this.INITIAL_GRAPH_POINT, this.INITIAL_GRAPH_POINT, this.search.search());
	}
	
	public void mainLoop()
	{
		while(!Display.isCloseRequested()) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
			{
				Display.destroy();
				System.exit(0);
			}
			/////////////////////////////////////////////////
			//Place all the graphical drawing methods here
			///////////////////////////////////////////////
			this.graph.draw();
			this.graph.executeAction();
			
			
			Display.update();
			Display.sync(60);
		}
		
		Display.destroy();
	}
	
	/**
	 * buildGraphicalObjects creates the window in which the graph animation is drawn. 
	 */
	public void buildGraphicalObjects()
	{
		try
		{
			Display.setDisplayMode(new DisplayMode(this.WINDOW_DIMENSION, this.WINDOW_DIMENSION));
			Display.create();
		}
		catch(LWJGLException e)
		{
			e.printStackTrace();
			Display.destroy();
		}
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		//Not sure what this does but apparently it's "Not important"
		GL11.glLoadIdentity();
		//Initialize the orthoprojection so upper left corner is 0x0y, 
		//Param 1 : zero X coordinate: 0."Left"
		//Param 2 : max X coordinate: "width" constant "Right:
		//Param 3 : zero Y coordinate: 0 "Bottom"
		//Param 4 : max Y coordinate: "height" constant "top
		//Param 5&6 are used for 3d, so set to +/- 1 for now.
		GL11.glOrtho(0,this.WINDOW_DIMENSION, this.WINDOW_DIMENSION, 0, 1,-1);
		//No explanation here. I guess it defines how openGL renders the objects
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
	}
		
}
