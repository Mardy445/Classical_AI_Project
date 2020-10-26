import Controller.MainController;
import Model.MainGame;
import View.MainFrame;
import javax.swing.*;

/**
 * This class is used to launch the program and initialise the Model, View and Controller elements.
 */
public class Launcher {
    public static void main(String[] args){

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }


        MainGame game = new MainGame();

        MainFrame frame = new MainFrame();

        new MainController(game,frame);
    }
}
