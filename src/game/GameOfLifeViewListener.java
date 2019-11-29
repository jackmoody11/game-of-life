package game;

public interface GameOfLifeViewListener {
    void handleGameOfLifeViewEvent(GameOfLifeViewEvent e);

    void handleDimensionEvent(GameOfLifeViewEvent e);
    void handleThresholdEvent(GameOfLifeViewEvent e);
    void handleRandomFillEvent(GameOfLifeViewEvent e);
    void handleSimulationSpeedEvent(GameOfLifeViewEvent e);
    void handleSimulationEvent(GameOfLifeViewEvent e);
    void handleNextIterationEvent(GameOfLifeViewEvent e);
    void handleRestartEvent(GameOfLifeViewEvent e);
    void handleTorusToggleEvent(GameOfLifeViewEvent e);

}
