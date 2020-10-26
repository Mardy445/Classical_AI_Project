package Model;

import java.awt.*;
import java.util.HashMap;

/**
 * A slightly modified A* Search created for one of my extras.
 * This A* Search exists to show that the optimal solution to a problem is not always the shallowest depth solution.
 */
public class ModifiedAStarSearch extends AStarSearch {
    public ModifiedAStarSearch(HashMap<String, Point > goalStateBlockLocations){
        super(goalStateBlockLocations);
    }

    //If the transition was a downwards movement to point [2,2] then have the cost be 10 points, otherwise 1 point for any other move
    @Override
    protected int calculatePointsToMove(Point point, Point direction){
        int x = point.x;
        int y = point.y;
        return x == 2 && y == 2 && direction.equals(DOWN) ? 10 : 1;
    }
}
