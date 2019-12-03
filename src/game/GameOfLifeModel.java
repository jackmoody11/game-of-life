package game;

import java.util.ArrayList;
import java.util.List;

public class GameOfLifeModel {
    private JBoard _board;
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

    void setBoard(JBoard board) {
        _board = board;
        notifyObservers(getBoard());
    }

    JBoard getBoard() {
        return _board;
    }

    void addObserver(GameOfLifeObserver o) {
        _observers.add(o);
    }

    public void removeObserver(GameOfLifeObserver o) {
        _observers.remove(o);
    }

    private void notifyObservers(JBoard board) {
        for (GameOfLifeObserver o : _observers) {
            o.update(this, board);
        }
    }

    /*
     * Clear all spots on board
     */
    void reset() {
        getBoard().reset();
        notifyObservers(getBoard());
    }

    /*
     * Randomly fill board
     */
    void randomlyFill() {
        getBoard().randomlyFill();
        notifyObservers(getBoard());
    }

    /*
     * Calculate which cells change in next generation and change board to match
     */
    synchronized void setNextGeneration() {

        JBoard nextBoard = new JBoard(getBoard().getSpotWidth(), getBoard().getSpotHeight());

        // Find which spots to change in current board
        for (int x = 0; x < getBoard().getSpotWidth(); x++) {
            for (int y = 0; y < getBoard().getSpotHeight(); y++) {
                boolean s = getBoard().getSpotAt(x, y);
                int liveCount;
                liveCount = getBoard().getNumberOfLiveNeighbors(_isTorusMode, x, y);
                System.out.println(liveCount);
                // Handle cases when spot is toggled
                if (s) {
                    if (liveCount < _dieLessThanThresh && liveCount > _dieGreaterThanThresh) {
                        nextBoard.toggleSpotAt(x, y);
                    }
                } else if (liveCount > _liveGreaterThanThresh && liveCount < _liveLessThanThresh) {
                    nextBoard.toggleSpotAt(x, y);
                }
            }
        }

        // Toggle spots on board to match nextBoard
        for (int x = 0; x < getBoard().getSpotWidth(); x++) {
            for (int y = 0; y < getBoard().getSpotHeight(); y++) {
                if (nextBoard.getSpotAt(x, y) != getBoard().getSpotAt(x, y)) {
                    getBoard().toggleSpotAt(x, y);
                }
            }
        }
        notifyObservers(getBoard());
    }
}
