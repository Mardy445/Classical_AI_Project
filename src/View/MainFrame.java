package View;

import Controller.ProblemDifficultyChangedListener;
import Controller.StartSearchListener;
import Model.Grid;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents the main frame for the GUI element of the application.
 */
public class MainFrame extends JFrame {

    private GridPanel gridPanel;
    private OptionsPanel optionsPanel;

    public MainFrame(){
        initFrame();
    }

    //Initialises the frames attributes
    private void initFrame(){
        this.setVisible(true);
        this.setSize(1000,800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        BorderLayout layout = new BorderLayout();
        layout.setVgap(10);
        mainPanel.setLayout(layout);
        this.add(mainPanel);

        gridPanel = new GridPanel();
        optionsPanel = new OptionsPanel();

        mainPanel.add(gridPanel,BorderLayout.CENTER);
        mainPanel.add(optionsPanel,BorderLayout.EAST);
        this.validate();
    }

    //Sets the action listener for the problem difficulty spinner
    public void setProblemDifficultyChangedListener(ProblemDifficultyChangedListener listener){
        optionsPanel.setProblemDifficultyChangedListener(listener);
    }

    //Sets the action listener for the start search button
    public void setStartSearchListener(StartSearchListener listener){
        optionsPanel.setStartSearchListener(listener);
    }

    //Gets the currently selected search
    public String getSelectedSearch(){
        return optionsPanel.getSelectedSearch();
    }

    //Gets the current value of the problem difficulty spinner
    public int getSpinnerValue(){
        return optionsPanel.getProblemDifficulty();
    }

    //Sets the text output to modify the option panels text box
    public void setTextOutput(String string){
        optionsPanel.setTextOutput(string);
    }

    //Updates the grid panel with the new grid for the current state.
    public void updateGridPanel(Grid grid, Point agentLocation){
        gridPanel.updateCurrentGrid(grid, agentLocation);
    }

    //Requests the options panel to enable or disable all functionality
    public void setIsOptionsPanelEnabled(boolean isOptionsPanelEnabled){
        optionsPanel.setEnableOptionsPanel(isOptionsPanelEnabled);
    }

}
