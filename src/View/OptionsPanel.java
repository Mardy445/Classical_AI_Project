package View;

import Controller.ProblemDifficultyChangedListener;
import Controller.StartSearchListener;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * This class represents the options panel on the right of the GUI where a user can provide inputs and run the algorithms.
 */
public class OptionsPanel extends JPanel {

    private JComboBox searchSelection;
    private JSpinner problemDifficultySelection;
    private JButton beginButton;
    private JLabel textOutput;

    public OptionsPanel(){
        initOptionsPanel();
    }

    /*
    This method initialises the GUI elements of the options panel
     */
    private void initOptionsPanel(){
        //Set attributes for the panel itself
        setBackground(Color.gray);
        setPreferredSize(new Dimension(200,200));
        setLayout(new GridBagLayout());

        //Set the base grid bag constraints for elements of the panel
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 0.03;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(10,10,10,10);

        //Init the combo box for search method selection
        String[] searches = {"Depth First Search","Breadth First Search","Iterative Deepening Search","A* Search","Modified A* Search (EXTRA)"};
        searchSelection = new JComboBox<>(searches);

        //Init the spinner for problem difficulty selection
        SpinnerModel model = new SpinnerNumberModel(0, 0, 18, 1);
        problemDifficultySelection = new JSpinner(model);

        //Init the begin button to start the searches
        beginButton = new JButton("Begin Search");

        //Init the text output to display information to the user
        textOutput = new JLabel("");
        textOutput.setVerticalAlignment(JLabel.TOP);
        textOutput.setOpaque(true);
        textOutput.setBackground(Color.white);

        add(searchSelection, c);

        c.gridy = 1;
        add(problemDifficultySelection, c);

        c.gridy = 2;
        add(beginButton, c);

        c.gridy = 3;
        c.weighty = 0.91;
        add(textOutput, c);
    }

    //Sets the listener for the spinner
    public void setProblemDifficultyChangedListener(ProblemDifficultyChangedListener listener){
        problemDifficultySelection.addChangeListener(listener);
    }

    //Sets the listener for the start button
    public void setStartSearchListener(StartSearchListener listener){
        beginButton.addActionListener(listener);
    }

    //Gets the search currently selected by the drop down box
    public String getSelectedSearch(){
        return Objects.requireNonNull(searchSelection.getSelectedItem()).toString();
    }

    //Gets the problem difficulty currently set
    public int getProblemDifficulty(){
        return (Integer) problemDifficultySelection.getValue();
    }

    //Sets the text output
    public void setTextOutput(String output){
        textOutput.setText(output);
    }

    //Enables or disables all option panel functionality
    public void setEnableOptionsPanel(boolean enabled){
        beginButton.setEnabled(enabled);
        searchSelection.setEnabled(enabled);
        problemDifficultySelection.setEnabled(enabled);
    }

}
