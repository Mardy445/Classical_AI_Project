package Model;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

/**
 * This is the main class for the model. It calls methods in the search classes to find solutions to the problem.
 */
public class MainGame {
    public static final int GRID_SIZE = 4;
    private static final Point[][] POINTS = generatePoints();

    private Grid grid;
    private Point agentLocation;
    private HashMap<String, Point> goalStateBlockLocations;
    private List<Search.SolutionState> directionsToCurrentlyStoredSolution = null;

    public MainGame(){
        generateGoalStateBlockLocations();
        generateDefaultGrid();
    }

    //Generates the point objects for each potential grid location. I use points since they are easy to work with
    public static Point[][] generatePoints(){
        Point[][] points = new Point[GRID_SIZE][GRID_SIZE];
        for(int x = 0; x < GRID_SIZE; x++){
            for(int y = 0; y < GRID_SIZE; y++){
                points[x][y] = new Point(x,y);
            }
        }
        return points;
    }

    //Gets the point object for a specific x and y point. This method exists to avoid duplicate objects being created
    public static Point getPoint(int x, int y){
        return POINTS[x][y];
    }

    //Sets the goal state
    private void generateGoalStateBlockLocations(){
        goalStateBlockLocations = new HashMap<>();
        goalStateBlockLocations.put("A",POINTS[1][1]);
        goalStateBlockLocations.put("B",POINTS[1][2]);
        goalStateBlockLocations.put("C",POINTS[1][3]);
    }

    //Generates a starting grid (IE the grid of problem size 0)
    private void generateDefaultGrid(){
        grid = new Grid();
        grid.put("A",POINTS[1][1]);
        grid.put("B",POINTS[1][2]);
        grid.put("C",POINTS[1][3]);
        agentLocation = getPoint(2,2);
    }

    //Attempts to generate a grid with a specific depth from the default grid.
    public void generateGridWithSpecifiedProblemDifficulty(int problemDifficulty){
        generateDefaultGrid();
        Point invalidDirection = new Point(0,0);
        String currentSquareObserved;
        String lastSquareChecked = "";
        Point potentialEmptySquare = null;

        //Iterates n times where n is the problem difficulty
        for(int i = 0; i < problemDifficulty; i++) {
            //Iterates each direction
            for (Point direction : Search.shuffleDirections()) {
                //If the new point is not off the grid...
                if(agentLocation.x + direction.x < GRID_SIZE && agentLocation.x + direction.x >= 0){
                    if (agentLocation.y + direction.y < GRID_SIZE && agentLocation.y + direction.y >= 0){
                        //Sets the current square observed to the new position
                        currentSquareObserved = grid.getElementOfGrid(agentLocation.x + direction.x,agentLocation.y + direction.y);
                    }
                    else {
                        continue;
                    }
                }
                else{
                    continue;
                }

                //If the current square observed is "A", "B" or "C"
                if("ABC".contains(currentSquareObserved)){
                    //If the direction is not the opposite direction to the last move AND
                    //If the currentSquare observed is not equal to the last square observed
                    if(!(direction.x == invalidDirection.x && direction.y == invalidDirection.y) && !lastSquareChecked.equals(currentSquareObserved)){
                        //Moves to the next state accordingly
                        invalidDirection = new Point(-direction.x,-direction.y);
                        lastSquareChecked = currentSquareObserved;
                        grid.setElementOfGrid(agentLocation.x, agentLocation.y, currentSquareObserved);
                        moveAgent(direction);
                        potentialEmptySquare = null;
                        break;
                    }
                }
                //Else the new point is set to be potentially empty
                else {
                    potentialEmptySquare = direction;
                }
            }
            //If no movement to an ABC square is possible, move to an empty square
            if(potentialEmptySquare != null){
                invalidDirection = new Point(-potentialEmptySquare.x,-potentialEmptySquare.y);
                moveAgent(potentialEmptySquare);
            }
        }

        //If the grid generated does not have the problem difficulty specified, try again
        if(doAStarSearch().getDirectionsToSolution().size()-1 != problemDifficulty){
            generateGridWithSpecifiedProblemDifficulty(problemDifficulty);
        }
    }

    //Moves the agent in a specified direction (used for grid generation)
    private void moveAgent(Point directionToMove){
        //grid.setElementOfGrid(agentLocation.x + directionToMove.x,agentLocation.y + directionToMove.y,"AGENT");
        agentLocation = getPoint(agentLocation.x + directionToMove.x,agentLocation.y + directionToMove.y);
    }

    //Uses the input to decide which search to do.
    public String doSelectedSearch(String selection){
        Search search = null;
        switch (selection){
            case "Depth First Search":
                search = doDepthFirstSearch();
                break;
            case "Breadth First Search":
                search = doBreadthFirstSearch();
                break;
            case "Iterative Deepening Search":
                search = doIterativeDeepeningSearch();
                break;
            case "A* Search":
                search = doAStarSearch();
                break;
            case "Modified A* Search (EXTRA)":
                search = doModifiedAStarSearch();
                break;
        }

        return search.getTextInformation();
    }

    //Carries out DFS
    public Search doDepthFirstSearch(){
        DepthFirstSearch dfs = new DepthFirstSearch(goalStateBlockLocations);

        dfs.doSearch(new Point(agentLocation), copyGrid(grid));

        directionsToCurrentlyStoredSolution = dfs.getDirectionsToSolution();

        return dfs;
    }

    //Carries out BFS
    public Search doBreadthFirstSearch(){
        BreadthFirstSearch bfs = new BreadthFirstSearch(goalStateBlockLocations);

        bfs.doSearch(new Point(agentLocation), copyGrid(grid));

        directionsToCurrentlyStoredSolution = bfs.getDirectionsToSolution();

        return bfs;
    }

    //Carries out IDS
    public Search doIterativeDeepeningSearch(){
        IterativeDeepeningSearch ids = new IterativeDeepeningSearch(goalStateBlockLocations);

        ids.doSearch(new Point(agentLocation), copyGrid(grid));

        directionsToCurrentlyStoredSolution = ids.getDirectionsToSolution();

        return ids;
    }

    //Carries out A Star Search
    public Search doAStarSearch(){
        AStarSearch aStarSearch = new AStarSearch(goalStateBlockLocations);

        aStarSearch.doSearch(new Point(agentLocation), copyGrid(grid));

        directionsToCurrentlyStoredSolution = aStarSearch.getDirectionsToSolution();

        return aStarSearch;
    }

    //Carries out MODIFIED A Star Search (EXTRA)
    public Search doModifiedAStarSearch(){
        ModifiedAStarSearch modifiedAStarSearch = new ModifiedAStarSearch(goalStateBlockLocations);

        modifiedAStarSearch.doSearch(new Point(agentLocation), copyGrid(grid));

        directionsToCurrentlyStoredSolution = modifiedAStarSearch.getDirectionsToSolution();

        return modifiedAStarSearch;
    }

    //GETTER
    public List<Search.SolutionState> getDirectionsToCurrentlyStoredSolution(){
        return directionsToCurrentlyStoredSolution;
    }

    //GETTER
    public Grid getGrid(){
        return grid;
    }

    //GETTER
    public Point getAgentLocation(){
        return agentLocation;
    }

    //Creates a copy of a grid that uses a new memory location
    private static Grid copyGrid(Grid grid){
        return (Grid) grid.clone();
    }


}
