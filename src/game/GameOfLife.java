package game;

import javax.swing.*;
import java.awt.*;

public class GameOfLife {
    public static void main(String[] args) {

        GameOfLifeModel model = new GameOfLifeModel();
        GameOfLifeView view = new GameOfLifeView(10, 10);
        GameOfLifeController controller = new GameOfLifeController(model, view);

        JFrame mainFrame = new JFrame();
        mainFrame.setTitle("Welcome to the Game of Life");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainFrame.setContentPane(view);

        mainFrame.pack();
        mainFrame.setVisible(true);
    }
}
