package searchTools;
import java.util.Comparator;

/**
 * The NodeComparator returns values to the priority queue which will prioritize nodes with low heuristic values.
 * @author Ian
 *
 */
public class NodeComparator implements Comparator<Node> {

	@Override
	public int compare(Node x, Node y) {
		if(x.getHeuristicScore() > y.getHeuristicScore())
		{
			return 1;
		}
		if(x.getHeuristicScore() < y.getHeuristicScore())
		{
			return -1;
		}
		
		return 0;
	}
	
}
