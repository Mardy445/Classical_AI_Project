package Controller;

import Model.MainGame;
import View.MainFrame;

/**
 * This class is responsible for assigning action listeners to certain GUI elements and giving the view start data.
 */
public class MainController {
    private MainGame model;
    private MainFrame view;

    public MainController(MainGame game, MainFrame frame){
        this.model = game;
        this.view = frame;
        setDefault();
        setListeners();
    }

    //Sets the grid of the view to its default state
    private void setDefault(){
        view.updateGridPanel(model.getGrid(), model.getAgentLocation());
    }

    //Sets the listeners for all GUI elements that need them
    private void setListeners(){
        view.setProblemDifficultyChangedListener(new ProblemDifficultyChangedListener(model,view));
        view.setStartSearchListener(new StartSearchListener(model,view));
    }
}
