package Model;

import java.awt.*;
import java.util.HashMap;

/**
 * This class represents the grid of the problem using a hashmap.
 */
public class Grid extends HashMap<String,Point> {
    //Returns the tile at point [x,y]. If no tile exists there, then return "NONE"
    public String getElementOfGrid(int x, int y){
        for(String id : new String[]{"A","B","C"}){
            if(get(id).x == x && get(id).y == y){
                return id;
            }
        }
        return "NONE";
    }

    //Sets a location for a unique ABC element at point [x,y]
    public void setElementOfGrid(int x, int y, String element){
        if(!element.equals("NONE") && !element.equals("AGENT")){
            put(element,MainGame.getPoint(x,y));
        }
    }

    //Swaps 2 points contents in the grid
    //If the 2 points are both "NONE" then the grid remains the same and simply returns this grid object
    //This is done to avoid wasting memory on duplicate grids
    public Grid swapGridElements(Point p1, Point p2){
        Grid gridCopy = (Grid) this.clone();
        String e1 = "NONE";
        String e2 = "NONE";
        for(String id : new String[]{"A","B","C"}){
            if(get(id).x == p1.x && get(id).y == p1.y){
                e1 = id;
            }
            else if(get(id).x == p2.x && get(id).y == p2.y){
                e2 = id;
            }
        }
        if(e1.equals("NONE") && e2.equals("NONE")){
            return this;
        }
        else if(e1.equals("NONE")){
            gridCopy.put(e2, p1);
        }
        else if(e2.equals("NONE")){
            gridCopy.put(e1,p2);
        }
        else {
            gridCopy.put(e2,p1);
            gridCopy.put(e1,p2);
        }
        return gridCopy;
    }
}
