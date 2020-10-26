package Model;

import java.awt.*;

/**
 * This class is the superclass for all search states, where a search state holds the information needed for each node of the tree
 */
public class SearchState {
    private int depth;
    private Grid grid;
    private Point agentLocation;
    private SearchState previousState;
    private int id;

    public SearchState(int depth, Grid grid, Point agentLocation, SearchState previousState, int id){

        this.depth = depth;
        this.grid = grid;
        this.agentLocation = agentLocation;
        this.previousState = previousState;
        this.id = id;
    }

    public Grid getGrid(){
        return grid;
    }

    public int getStateDepth(){
        return depth;
    }

    public Point getAgentLocation(){return agentLocation;}

    public SearchState getPreviousState(){
        return previousState;
    }

    public int getId() {return id;}
}
