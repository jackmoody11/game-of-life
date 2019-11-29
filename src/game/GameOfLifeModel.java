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
    private boolean _isTorusMode;

    GameOfLifeModel() {
        _observers = new ArrayList<>();

        // Set default values for model
        _dieGreaterThanThresh = 2;
        _dieLessThanThresh = 4;
        _liveGreaterThanThresh = 1;
        _liveLessThanThresh = 4;
        _simulationSpeed = 10;
        _isTorusMode = false;
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

    void setIsTorusMode(boolean torusMode) {
        _isTorusMode = torusMode;
    }

    int getSimulationSpeed() {
        return _simulationSpeed;
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

    boolean isTorusMode() {
        return _isTorusMode;
    }

    void setBoard(JSpotBoard board) {
        _board = board;
        notifyObservers(getBoard());
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
        SpotBoardIterator iterator = new SpotBoardIterator(getBoard());
        while (iterator.hasNext()) {
            Spot s = iterator.next();
            s.clearSpot();
        }
        notifyObservers(getBoard());
    }

    void randomlyFill() {
        SpotBoardIterator iterator = new SpotBoardIterator(getBoard());
        while (iterator.hasNext()) {
            Spot s = iterator.next();
            if (Math.random() >= 0.7) {
                s.toggleSpot();
            }
        }
        notifyObservers(getBoard());
    }

    void resizeBoard(int width, int height) {
        _board.resizeBoard(width, height);
        reset();
//        notifyObservers(getBoard());
    }

    synchronized void setNextGeneration(int dieLessThanThresh,
                           int dieGreaterThanThresh,
                           int liveLessThanThresh,
                           int liveGreaterThanThresh) {
        JSpotBoard nextBoard = new JSpotBoard(getBoard().getSpotWidth(), getBoard().getSpotHeight());
        for (int i=0; i< getBoard().getSpotWidth(); i++) {
            for (int j=0; j < getBoard().getSpotHeight(); j++) {
                Spot s = getBoard().getSpotAt(i, j);
                int liveCount;
                // TODO : Add torus mode
                if (_isTorusMode) {
                    liveCount = s.getNumberOfLiveNeighborsTorus();
                } else {
                    liveCount = s.getNumberOfLiveNeighbors();
                }

                // When to set dead to alive
                if (s.isEmpty()) {
                    if (liveCount < dieLessThanThresh && liveCount > dieGreaterThanThresh) {
                        nextBoard.getSpotAt(i, j).toggleSpot();
                    }
                } else if (liveCount > liveGreaterThanThresh && liveCount < liveLessThanThresh) {
                    // live spot dies if live count outside of threshold
                    nextBoard.getSpotAt(i, j).toggleSpot();
                }
            }
        }
        for (int i=0; i < getBoard().getSpotWidth(); i++) {
            for (int j=0; j < getBoard().getSpotHeight(); j++) {
                if (nextBoard.getSpotAt(i, j).isEmpty() != getBoard().getSpotAt(i, j).isEmpty()) {
                    getBoard().getSpotAt(i, j).toggleSpot();
                }
            }
        }
        notifyObservers(getBoard());
    }
}
