package Controller;

import Model.MainGame;
import Model.Search;
import View.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 * This class is responsible for carrying out a specified search when the start search button is pressed.
 */
public class StartSearchListener implements ActionListener {
    private MainGame model;
    private MainFrame view;

    private int index;

    public StartSearchListener(MainGame model, MainFrame view){
        this.model = model;
        this.view = view;
    }

    //Carrys out a search and updates the view accordingly
    @Override
    public void actionPerformed(ActionEvent e) {
        String textOutput;
        view.setIsOptionsPanelEnabled(false);
        //Sets a timer to refresh every 200 ms upon activation
        Timer timer = new Timer(200 ,null);

        //Requests for the model to perform the selected search.
        //Updates the view's text panel once this search is complete
        try {
            textOutput = model.doSelectedSearch(view.getSelectedSearch());
        }
        catch (OutOfMemoryError error){
            view.setTextOutput("Error: Out of Memory");
            view.setIsOptionsPanelEnabled(true);
            return;
        }
        view.setTextOutput(textOutput);

        //If the selected search is DFS, then dont show the user the output
        //This is because DFS can produce solutions that take a very long time to show
        if(view.getSelectedSearch().equals("Depth First Search")){
            view.setIsOptionsPanelEnabled(true);
            return;
        }

        //Continually updates the view to show the solution found step by step
        //This will start when the timer starts
        ActionListener taskPerformer = evt -> {
            Search.SolutionState solutionState = model.getDirectionsToCurrentlyStoredSolution().get(index);
            view.updateGridPanel(solutionState.getGrid(), solutionState.getAgentLocation());
            index++;
            if(index == model.getDirectionsToCurrentlyStoredSolution().size()){
                timer.stop();
                view.setIsOptionsPanelEnabled(true);
            }
        };

        //Starts the timer
        timer.addActionListener(taskPerformer);
        timer.setRepeats(true);
        timer.start();


        index = 0;
        view.updateGridPanel(model.getGrid(), model.getAgentLocation());
    }
}
