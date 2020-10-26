package Model;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * This class represents the superclass for all search methods.
 */
public class Search {
    protected PrintWriter writer;

    protected HashMap<String,Point> goalStateBlockLocations;
    protected int nodesGenerated;
    protected List<SolutionState> directionsToSolution;

    protected static final Point UP = new Point(0,-1);
    protected static final Point DOWN = new Point(0,1);
    protected static final Point LEFT = new Point(-1,0);
    protected static final Point RIGHT = new Point(1,0);
    protected static final Point[] DEFAULT_MOVEMENTS = {UP, DOWN, LEFT, RIGHT};

    public Search(HashMap<String,Point> goalStateBlockLocations){
        this.goalStateBlockLocations = goalStateBlockLocations;
        nodesGenerated = 0;
        directionsToSolution = new ArrayList<>();
        setUpFileSavingSystem();
    }

    //Creates a PrintWriter that will create a new file to write to
    private void setUpFileSavingSystem(){
        try {
            writer = new PrintWriter(this.getClass().getSimpleName() + ".txt", StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Writes the information about the generation of a state to the file
    protected void writeToFile(SearchState state1, SearchState state2){
        writer.println(state1.getId() + "(" + state1.getStateDepth() + ") -> " + state2.getId() + "(" + state2.getStateDepth() + ")");
    }

    //Writes the information about the generation of a state in A* search to the file
    protected void writeToFile(AStarSearchState state1, AStarSearchState state2){
        writer.println(state1.getId() + "(h=" + state1.getHeuristic() + ",g=" + state1.getPreviousHeuristicSum() + ") -> " + state2.getId()+ "(h=" + state2.getHeuristic() + ",g=" + state2.getPreviousHeuristicSum()+")");
    }

    //Overridable doSearch method. Each search will utilise this method differently. Returns True if solution found
    public boolean doSearch(Point agentLocation, Grid grid){
        return false;
    }

    //Checks whether a given grid state is the solution
    protected boolean checkState(Grid grid){
        Point holdPoint;
        for(String id : goalStateBlockLocations.keySet()){
            holdPoint = goalStateBlockLocations.get(id);
            if(!grid.getElementOfGrid(holdPoint.x,holdPoint.y).equals(id)){
                return false;
            }
        }
        return true;
    }

    //Compares 2 grid objects. Returns true if they are equal
    protected boolean compareGrids(Grid a1, Grid a2){
        return a1.equals(a2);
    }

    //Compares 2 point objects. Returns true if they are equal
    protected boolean comparePoints(Point p1, Point p2){
        return p1.x == p2.x && p1.y == p2.y;
    }

    //Checks that a state with the specified agent position is possible
    protected boolean isNewPositionPossible(int x, int y){

        if (x >= 0 && x < MainGame.GRID_SIZE) {
            if (y >= 0 && y < MainGame.GRID_SIZE) {
                return true;
            }
        }
        return false;
    }

    //GETTER
    public int getNodesGenerated(){
        return nodesGenerated;
    }

    //GETTER
    public List<SolutionState> getDirectionsToSolution(){
        return directionsToSolution;
    }

    //Shuffles a given array of points. This is used to shuffle the directions for DFS to prevent an infinite loop
    public static Point[] shuffleDirections()
    {
        Point holdPoint;
        int holdRandomInt;
        Random random = new Random();
        Point[] directions = DEFAULT_MOVEMENTS.clone();

        for (int i = directions.length - 1; i > 0; i--)
        {
            holdRandomInt = random.nextInt(i + 1);
            holdPoint = directions[holdRandomInt];
            directions[holdRandomInt] = directions[i];
            directions[i] = holdPoint;
        }
        return directions;
    }

    //Returns information about the last solution found in a format that the GUI text box can use
    public String getTextInformation(){
        return "<html><body>Nodes Generated: " + getNodesGenerated()
                + "<br>Depth of Solution: " + (getDirectionsToSolution().size()-1)
                 +"</body></html>";
    }

    //Stores the path from the start state to the goal state
    protected void storeDirectionsToSolution(SearchState startState){
        SearchState state = startState;
        directionsToSolution.add(0,new SolutionState(startState.getGrid(),startState.getAgentLocation()));
        while(state.getPreviousState() != null){
            state = state.getPreviousState();
            directionsToSolution.add(0, new SolutionState(state.getGrid(),state.getAgentLocation()));
        }
    }

    /**
     * Inner class that represents all the information required to construct a state of the problem
     */
    public class SolutionState{
        private Grid grid;
        private Point agentLocation;
        public SolutionState(Grid grid, Point agentLocation){
            this.grid = grid;
            this.agentLocation = agentLocation;
        }

        public Grid getGrid(){
            return grid;
        }

        public Point getAgentLocation(){
            return agentLocation;
        }
    }
}
