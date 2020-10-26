package Model;

import java.awt.*;
import java.util.HashMap;

/**
 * This class represents BFS.
 */
public class BreadthFirstSearch extends Search {
    public BreadthFirstSearch(HashMap<String, Point> goalStateBlockLocations){
        super(goalStateBlockLocations);
    }

    //Attempts to find a solution using BFS.
    @Override
    public boolean doSearch(Point agentStartLocation, Grid startGrid){
        //Holds the location of the next square to check based on the direction
        Point holdPoint;

        SearchState holdCurrentState;
        SearchState holdNewState;

        Grid grid; //A variable to hold the grid for the state on top of the stack
        Point agentLocation; //A variable to hold the agentLocation for the state on top of the stack

        Queue<SearchState> stateQueue = new Model.Queue<>(); //The stack to hold the different depths of the tree

        //Pushes the initial stack element
        stateQueue.enqueue(new SearchState(0,startGrid,agentStartLocation,null, 0));
        if (checkState(stateQueue.peek().getGrid())) {
            storeDirectionsToSolution(stateQueue.peek());
            writer.close();
            return true;
        }


        while(!stateQueue.empty()) {
            holdCurrentState = stateQueue.peek();
            grid = holdCurrentState.getGrid();
            agentLocation = holdCurrentState.getAgentLocation();

            //While there are still directions to check in movements for this particular depth of the tree
            for (Point direction : DEFAULT_MOVEMENTS){

                holdPoint = new Point(agentLocation.x + direction.x, agentLocation.y + direction.y);
                //If the next square to check is in the grid (does not go over the edge of the grid) then...
                if (isNewPositionPossible(holdPoint.x, holdPoint.y)) {
                    nodesGenerated++;

                    holdNewState = new SearchState(holdCurrentState.getStateDepth() + 1, grid.swapGridElements(agentLocation, holdPoint), MainGame.getPoint(holdPoint.x, holdPoint.y), holdCurrentState, nodesGenerated);
                    stateQueue.enqueue(holdNewState);

                    if (checkState(holdNewState.getGrid())) {
                        storeDirectionsToSolution(holdNewState);
                        writer.close();
                        return true;
                    }

                    writeToFile(holdCurrentState, holdNewState);
                }
            }
            stateQueue.poll();
        }
        return false;
    }

}
