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
        getBoard().resizeBoard(width, height);
    }

    synchronized void setNextGeneration() {

        JSpotBoard nextBoard = new JSpotBoard(getBoard().getSpotWidth(), getBoard().getSpotHeight());
        for (int i=0; i< getBoard().getSpotWidth(); i++) {
            for (int j=0; j < getBoard().getSpotHeight(); j++) {
                Spot s = getBoard().getSpotAt(i, j);
                int liveCount;
                if (_isTorusMode) {
                    liveCount = s.getNumberOfLiveNeighborsTorus();
                } else {
                    liveCount = s.getNumberOfLiveNeighbors();
                }

                // Handle cases when spot is toggled
                if (s.isEmpty()) {
                    if (liveCount < _dieLessThanThresh && liveCount > _dieGreaterThanThresh) {
                        nextBoard.getSpotAt(i, j).toggleSpot();
                    }
                } else if (liveCount > _liveGreaterThanThresh && liveCount < _liveLessThanThresh) {
                    nextBoard.getSpotAt(i, j).toggleSpot();
                }
            }
        }

        // Toggle spots on board to match nextBoard
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
