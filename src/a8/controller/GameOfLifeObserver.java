package a8.controller;

import a8.board.JBoard;
import a8.model.GameOfLifeModel;

public interface GameOfLifeObserver {

    // May need to change parameters
    public void update(GameOfLifeModel model, JBoard board);
}
