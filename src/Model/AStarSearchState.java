package Model;

import java.awt.*;

/**
 * Represents one state of an A start search tree. Extension of BFS State since A star search state is very similar, but needs a few more attributes.
 */
public class AStarSearchState extends SearchState{

    private int heuristic;
    private int previousHeuristicSum;

    public AStarSearchState(Grid grid, Point agentLocation, AStarSearchState previousState, int heuristic, int previousHeuristicSum, int id){
        super(previousHeuristicSum,grid,agentLocation,previousState,id);
        this.heuristic = heuristic;
        this.previousHeuristicSum = previousHeuristicSum;
    }

    public int getHeuristic(){
        return heuristic;
    }

    public int getPreviousHeuristicSum(){
        return previousHeuristicSum;
    }

    public int getEvaluationFunction(){
        return getHeuristic() + previousHeuristicSum;
    }
}
