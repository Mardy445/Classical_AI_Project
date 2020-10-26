package Model;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Represents A star search.
 */
public class AStarSearch extends Search {
    public AStarSearch(HashMap<String, Point> goalStateBlockLocations) {
        super(goalStateBlockLocations);
    }

    //Attempts to find the solution using A start search.
    @Override
    public boolean doSearch(Point agentStartLocation, Grid startGrid) {
        //Creates a priority queue that orders elements based on their evaluation function result
        Comparator<AStarSearchState> comparator = Comparator.comparingInt(AStarSearchState::getEvaluationFunction);
        PriorityQueue<AStarSearchState> currentStates = new PriorityQueue<>(comparator);

        currentStates.add(new AStarSearchState(startGrid, agentStartLocation, null, calculateHeuristic(startGrid), 0,0));
        AStarSearchState holdBestState;

        //Loops while there is a state in the queue
        while (currentStates.size() > 0) {
            holdBestState = currentStates.poll();

            //Checks if the state is the solution
            if (checkState(holdBestState.getGrid())) {
                storeDirectionsToSolution(holdBestState);
                writer.close();
                return true;
            }

            currentStates.addAll(getNextStatesToExpand(holdBestState));
        }
        writer.close();
        return false;
    }

    //This method returns all possible expansions off a given state
    private List<AStarSearchState> getNextStatesToExpand(AStarSearchState stateToExpand) {
        List<AStarSearchState> newStates = new ArrayList<>();
        Point holdPoint;
        Grid grid = stateToExpand.getGrid();
        Grid holdNewGrid;
        Point agentLocation = stateToExpand.getAgentLocation();
        AStarSearchState holdState;


        //Iterates over every direction
        for (Point direction : DEFAULT_MOVEMENTS) {
            holdPoint = new Point(agentLocation.x + direction.x, agentLocation.y + direction.y);
            //If the new point is inside the grid
            if (isNewPositionPossible(holdPoint.x, holdPoint.y)) {
                //Adds a state after calculating its heuristic
                holdNewGrid = grid.swapGridElements(agentLocation, holdPoint);
                if (stateToExpand.getPreviousState() != null) {
                    if (compareGrids(holdNewGrid, stateToExpand.getPreviousState().getGrid()) && comparePoints(holdPoint, stateToExpand.getPreviousState().getAgentLocation())) {
                        continue;
                    }
                }
                nodesGenerated++;
                holdState = new AStarSearchState(holdNewGrid, MainGame.getPoint(holdPoint.x, holdPoint.y), stateToExpand, calculateHeuristic(holdNewGrid), stateToExpand.getPreviousHeuristicSum() + calculatePointsToMove(holdPoint, direction), nodesGenerated);
                newStates.add(holdState);
                writeToFile(stateToExpand, holdState);
            }
        }
        return newStates;
    }


    //Calculates the heuristic for a given grid (Manhattan Distance).
    private int calculateHeuristic(Grid grid) {
        int h = 0;
        Point point;
        HashMap<String, Point> uniqueSquareLocations = new HashMap<>();

        for (String tile : new String[]{"A", "B", "C"}) {
            uniqueSquareLocations.put(tile, grid.get(tile));
        }
        for (String id : uniqueSquareLocations.keySet()) {
            point = goalStateBlockLocations.get(id);
            h += calculateManhattanDistance(point, uniqueSquareLocations.get(id));
        }
        return h;
    }

    //Calculates Manhattan distance between 2 points
    private int calculateManhattanDistance(Point p1, Point p2) {
        return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
    }

    //Calculate the score it takes to move to the point in the direction
    //This method is simply here to be overridden in the ModifiedAStarSearch
    protected int calculatePointsToMove(Point point, Point direction) {
        return 1;
    }
}
