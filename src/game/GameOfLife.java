package game;

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
