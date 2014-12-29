package nPuzzle;

import java.util.LinkedList;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import searchTools.*;

public class NPuzzleDriver_test {
	
	final int PUZZLE_DIMENSION = 6;
	final int WINDOW_DIMENSION = 900;
	private Search search;
	private NPuzzleGraph graph;
	private int GRAPH_BORDER = 300;
	private int GRAPH_DIMENSION = this.WINDOW_DIMENSION - GRAPH_BORDER;
	private int INITIAL_GRAPH_POINT = this.GRAPH_BORDER / 2;
	private int multiplier = 2;
			
	
	public NPuzzleDriver_test()
	{
		
		this.buildNPuzzleObjects();
		this.buildGraphicalObjects();
		this.mainLoop();
	}
	
	public void buildNPuzzleObjects()
	{
		/*
		Problem problem = new NPuzzleProblem(PUZZLE_DIMENSION);
		HeuristicFunction tileHeuristic = new IndividualTileHeuristic(problem.getGoalNode(), 1, multiplier);
		HeuristicFunction heuristic = new AggregateHeuristic(this.multiplier, new PathCostHeuristic(), tileHeuristic);
		//HeuristicFunction heuristic = new ManhattanDistanceHeuristic(problem.getGoalNode());
		this.search = new IndividualTileHeuristicSearch(problem, tileHeuristic, heuristic);
		

		this.graph = new NPuzzleGraph((NPuzzleNode)problem.getOriginNode(), this.GRAPH_DIMENSION, this.INITIAL_GRAPH_POINT, this.INITIAL_GRAPH_POINT, this.search.search());
		*/

		
		LinkedList<Action> path = new LinkedList<Action>();
		Problem problem = new NPuzzleProblem(this.PUZZLE_DIMENSION);
		
		
		
		for(int i = 1; i < 2; i++)
		{
			HeuristicFunction decomposedHeuristic = new DecomposedManhattanHeuristic(problem.getGoalNode(), i, this.multiplier);
			HeuristicFunction heuristic = new AggregateHeuristic(this.multiplier, new PathCostHeuristic(), decomposedHeuristic);
		    this.search = new DecomposedHeuristicSearch(problem, decomposedHeuristic, heuristic);
			
			path.addAll(search.search());
			Node node = search.getGoalNode();
			problem = new NPuzzleProblem(this.PUZZLE_DIMENSION, node);
			System.out.println("Loop " + i + " Executed");
		}
		
		this.graph = new NPuzzleGraph((NPuzzleNode)problem.getOriginNode(), this.GRAPH_DIMENSION, this.INITIAL_GRAPH_POINT, this.INITIAL_GRAPH_POINT, path);
		
		
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
		
	public static void main(String[] args)
	{
		
		NPuzzleDriver_test driver = new NPuzzleDriver_test();
		
	}
}

