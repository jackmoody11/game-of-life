package game;

import java.util.ArrayList;
import java.util.List;

public class GameOfLifeModel {
    private JSpotBoard _board;
    private List<GameOfLifeObserver> _observers;
    private int _simulationSpeed;
    private int _dieGreaterThanThresh;
    private int _dieLessThanThresh;
    private int _liveGreaterThanThresh;
    private int _liveLessThanThresh;

    GameOfLifeModel() {
        _observers = new ArrayList<>();

        // Set default values for model
        _dieGreaterThanThresh = 2;
        _dieLessThanThresh = 4;
        _liveGreaterThanThresh = 1;
        _liveLessThanThresh = 4;
        _simulationSpeed = 100;
    }

    void setSimulationSpeed(int simulationSpeed) {
        _simulationSpeed = simulationSpeed;
    }

    void setDieGreaterThanThresh(int dieGreaterThanThresh) {
        _dieGreaterThanThresh = dieGreaterThanThresh;
    }

    void setDieLessThanThresh(int dieLessThanThresh) {
        _dieLessThanThresh = dieLessThanThresh;
    }

    void setLiveGreaterThanThresh(int liveGreaterThanThresh) {
        _liveGreaterThanThresh = liveGreaterThanThresh;
    }

    void setLiveLessThanThresh(int liveLessThanThresh) {
        _liveLessThanThresh = liveLessThanThresh;
    }

    int getDieGreaterThanThresh() {
        return _dieGreaterThanThresh;
    }

    int getDieLessThanThresh() {
        return _dieLessThanThresh;
    }

    int getLiveGreaterThanThresh() {
        return _liveGreaterThanThresh;
    }

    int getLiveLessThanThresh() {
        return _liveLessThanThresh;
    }

    void setBoard(JSpotBoard board) {
        _board = board;
        notifyObservers(board);
    }

    JSpotBoard getBoard() {
        return _board;
    }

    void addObserver(GameOfLifeObserver o) {
        _observers.add(o);
    }

    public void removeObserver(GameOfLifeObserver o) {
        _observers.remove(o);
    }

    private void notifyObservers(JSpotBoard board) {
        for (GameOfLifeObserver o : _observers) {
            o.update(this, board);
        }
    }

    /*
     * Clear all spots on board
     */
    void reset() {
        SpotBoardIterator iterator = new SpotBoardIterator(_board);
        while (iterator.hasNext()) {
            Spot s = iterator.next();
            s.clearSpot();
        }
        notifyObservers(_board);
    }

    void randomlyFill() {
        SpotBoardIterator iterator = new SpotBoardIterator(_board);
        while (iterator.hasNext()) {
            Spot s = iterator.next();
            if (Math.random() >= 0.7) {
                s.toggleSpot();
            }
        }
        notifyObservers(_board);
    }

    void setNextGeneration(int dieLessThanThresh,
                                  int dieGreaterThanThresh,
                                  int liveLessThanThresh,
                                  int liveGreaterThanThresh) {
        JSpotBoard nextBoard = new JSpotBoard(_board.getSpotWidth(), _board.getSpotHeight());
        for (int i=0; i< _board.getSpotWidth(); i++) {
            for (int j=0; j < _board.getSpotHeight(); j++) {
                Spot s = _board.getSpotAt(i, j);
                int liveCount = s.getNumberOfLiveNeighbors();
                if (s.isEmpty()) {
                    if (liveCount < dieLessThanThresh && liveCount > dieGreaterThanThresh) {
                        nextBoard.getSpotAt(i, j).toggleSpot();
                    }
                } else if (liveCount > liveGreaterThanThresh && liveCount < liveLessThanThresh) {
                    // nextBoard initialized as empty, so only toggle if live spot stays alive
                    nextBoard.getSpotAt(i, j).toggleSpot();
                }
            }
        }
        for (int i=0; i < _board.getSpotWidth(); i++) {
            for (int j=0; j < _board.getSpotHeight(); j++) {
                if (nextBoard.getSpotAt(i, j).isEmpty() && !_board.getSpotAt(i, j).isEmpty()) {
                    _board.getSpotAt(i, j).toggleSpot();
                }
            }
        }
        notifyObservers(_board);
    }
}
