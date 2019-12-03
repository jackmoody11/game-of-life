package game.controller;

import game.board.JBoard;
import game.model.GameOfLifeModel;

public interface GameOfLifeObserver {

    // May need to change parameters
    public void update(GameOfLifeModel model, JBoard board);
}
