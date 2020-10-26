package Model;

import java.awt.*;
import java.util.HashMap;
import java.util.Stack;

/**
 * This class represents DFS.
 */
public class DepthFirstSearch extends Search{

    public DepthFirstSearch(HashMap<String, Point> goalStateBlockLocations){
        super(goalStateBlockLocations);
    }

    //Attempts to find a solution using DFS.
    @Override
    public boolean doSearch(Point agentStartLocation, Grid startGrid) {
        //Holds the location of the next square to check based on the direction
        Point holdPoint;
        SearchState holdState;
        int counter = 0;

        Grid grid; //A variable to hold the grid for the state on top of the stack
        Point agentLocation; //A variable to hold the agentLocation for the state on top of the stack
        Point[] movements; //A variable to hold the movements list (due to order) for the state on top of the stack
        int currentDepth;

        Stack<SearchState> stateStack = new Stack<>(); //The stack to hold the different depths of the tree

        //Pushes the initial stack element
        stateStack.push(new SearchState(0, startGrid,agentStartLocation, null, counter));

        if (checkState(stateStack.peek().getGrid())) {
            storeDirectionsToSolution(stateStack.peek());
            writer.close();
            return true;
        }

        //Iterates until every state has been checked. Since depth is infinite for this problem however, it will only stop when a solution is found
        while(!stateStack.empty()) {
            holdState = stateStack.peek();
            stateStack.pop();
            grid = holdState.getGrid();
            agentLocation = holdState.getAgentLocation();
            movements = getPossibleDirections();
            currentDepth = holdState.getStateDepth();

            //Checks if it should attempt to continue node expansion. This is used to allow iterative deepening search to work.
            if (shouldNotAttemptToExpand(currentDepth)){
                continue;
            }

            //While there are still directions to check in movements for this particular depth of the tree
            for(Point direction : movements){

                holdPoint = new Point(agentLocation.x + direction.x, agentLocation.y + direction.y);
                //If the next square to check is in the grid (does not go over the edge of the grid) then...
                if (isNewPositionPossible(holdPoint.x, holdPoint.y)) {
                    //Increment the counter for the nodes expanded and expand the node by pushing a new state for the next depth
                    nodesGenerated++;
                    counter++;
                    stateStack.push(new SearchState(currentDepth + 1, grid.swapGridElements(agentLocation, holdPoint), MainGame.getPoint(holdPoint.x, holdPoint.y), holdState, counter));
                    writeToFile(holdState, stateStack.peek());
                    //Checks if the current state is the goal state
                    if (checkState(stateStack.peek().getGrid())) {
                        storeDirectionsToSolution(stateStack.peek());
                        writer.close();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //Returns the possible directions to take in the order they should be taken at each node
    protected Point[] getPossibleDirections(){
        return shuffleDirections();
    }

    //This method is overridden for use in IDS. In the case of depth first search, it only returns false
    protected boolean shouldNotAttemptToExpand(int depth){
        return false;
    }
}
