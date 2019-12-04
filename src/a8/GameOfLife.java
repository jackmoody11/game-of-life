package a8;

import a8.controller.GameOfLifeController;
import a8.model.GameOfLifeModel;
import a8.view.GameOfLifeView;

import javax.swing.*;

public class GameOfLife {
    public static void main(String[] args) {

        GameOfLifeModel model = new GameOfLifeModel();
        GameOfLifeView view = new GameOfLifeView(model);
        GameOfLifeController controller = new GameOfLifeController(model, view);

        JFrame mainFrame = new JFrame();
        mainFrame.setTitle("Conway's Game of Life");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainFrame.setContentPane(view);

        mainFrame.pack();
        mainFrame.setVisible(true);
    }
}
