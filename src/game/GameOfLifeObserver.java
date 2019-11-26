package game;

import java.awt.event.ActionEvent;

public interface GameOfLifeObserver {

    // May need to change parameters
    public void update(GameOfLifeModel model, GameOfLifeViewEvent e);
}
