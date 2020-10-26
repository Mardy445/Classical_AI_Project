package Model;

import java.awt.*;
import java.util.HashMap;

/**
 * This class is used to carry out IDS. Extension of DFS since IDS is very similar to DFS.
 */
public class IterativeDeepeningSearch extends DepthFirstSearch {

    private int currentLimit;

    public IterativeDeepeningSearch(HashMap<String, Point> goalStateBlockLocations){
        super(goalStateBlockLocations);
        currentLimit = 1;
    }

    //Simply uses DFS while incrementing the currentLimit each time DFS returns false and trying again.
    @Override
    public boolean doSearch(Point agentStartLocation, Grid startGrid) {
        while (!super.doSearch(agentStartLocation,startGrid)){
            currentLimit++;
            writer.println("Restarting tree with new limit: " + currentLimit);
        }
        return true;
    }

    //Override DFS method to not return a shuffled array as this is not needed for IDS
    @Override
    protected Point[] getPossibleDirections(){
        return DEFAULT_MOVEMENTS;
    }

    //Override DFS method so that if the depth hits the limit, returns true and stops going any deeper down a path.
    @Override
    protected boolean shouldNotAttemptToExpand(int depth){
        return depth == currentLimit;
    }
}
