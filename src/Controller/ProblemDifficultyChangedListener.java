package Controller;

import Model.MainGame;
import View.MainFrame;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This class is responsible for updating the grid if the problem difficulty slider is changed.
 */
public class ProblemDifficultyChangedListener implements ChangeListener {
    private MainGame model;
    private MainFrame view;

    public ProblemDifficultyChangedListener(MainGame model, MainFrame view){
        this.model = model;
        this.view = view;
    }

    //Requests the model to generate a new grid of problem difficulty specified by the user and updates the view accordingly
    @Override
    public void stateChanged(ChangeEvent e) {
        model.generateGridWithSpecifiedProblemDifficulty(view.getSpinnerValue());
        view.updateGridPanel(model.getGrid(), model.getAgentLocation());
    }
}
