package searchTools;

public class SearchFactory {
	
	
	public static Search buildSearch(Problem problem, HeuristicFunction heuristic)
	{
		Search search = new HeuristicSearch(problem, heuristic);
		return search;
	}
}
