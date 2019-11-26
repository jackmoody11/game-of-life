package game;

import java.util.ArrayList;
import java.util.List;

public class GameOfLifeModel {
    private Spot[][] _board;
    private List<GameOfLifeObserver> _observers;

    GameOfLifeModel() {
        _observers = new ArrayList<>();
    }

    public void addObserver(GameOfLifeObserver o) {
        _observers.add(o);
    }

    public void removeObserver(GameOfLifeObserver o) {
        _observers.remove(o);
    }

    private void notifyObservers(GameOfLifeViewEvent e) {
        for (GameOfLifeObserver o : _observers) {
            o.update(this, e);
        }
    }

    public void reset() {
        for (Spot[] row : _board) {
            for (Spot s : row) {
                s.clearSpot();
            }
        }
    }
}
