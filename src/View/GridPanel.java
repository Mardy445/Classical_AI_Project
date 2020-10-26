package View;

import Model.Grid;
import Model.MainGame;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents the GUI element that displays the grid.
 */
public class GridPanel extends JPanel {
    private final static Color EMPTY_SQUARE_COLOUR = new Color(156, 180, 255);
    private final static Color LETTER_SQUARE_COLOUR = new Color(84, 114, 255);
    private final static Color AGENT_SQUARE_COLOUR = new Color(48, 80, 255);

    private Grid currentGrid;
    private Point agentLocation;
    private boolean ready = false;

    //Updates the view with the specified grid and agent location
    public void updateCurrentGrid(Grid currentGrid, Point agentLocation){
        this.ready = true;
        this.currentGrid = currentGrid;
        this.agentLocation = agentLocation;
        repaint();
    }

    //This method will utilise Graphics2D to draw out the currently stored grid and agent
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if(!ready){
            return;
        }

        int gridSquareWidth = this.getWidth()/MainGame.GRID_SIZE;
        int gridSquareHeight = this.getHeight()/MainGame.GRID_SIZE;

        g2d.setFont(new Font("TimesRoman", Font.BOLD, 15));

        //Iterates over every grid element, and sets their colour and text accordingly
        for(int x = 0; x < MainGame.GRID_SIZE; x++){
            for(int y = 0; y < MainGame.GRID_SIZE; y++){
                g2d.setColor(agentLocation.x == x && agentLocation.y == y ? AGENT_SQUARE_COLOUR : (currentGrid.getElementOfGrid(x,y).equals("NONE") ? EMPTY_SQUARE_COLOUR : LETTER_SQUARE_COLOUR));
                g2d.fillRect(x*gridSquareWidth,y*gridSquareHeight,gridSquareWidth,gridSquareHeight);
                g2d.setColor(Color.BLACK);
                g2d.drawString(agentLocation.x == x && agentLocation.y == y ? "AGENT" : (currentGrid.getElementOfGrid(x,y).equals("NONE") ? "" : currentGrid.getElementOfGrid(x,y)), (x*gridSquareWidth)+(gridSquareWidth/2), (y*gridSquareHeight)+(gridSquareHeight/2));
            }
        }
    }
}
