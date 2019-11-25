package game;

import org.w3c.dom.events.EventException;

import java.awt.event.ActionEvent;

public class GameOfLifeController implements GameOfLifeObserver, GameOfLifeViewListener {

    private GameOfLifeModel _model;
    private GameOfLifeView _view;

    GameOfLifeController(GameOfLifeModel model, GameOfLifeView view) {
        _model = model;
        _view = view;
    }

    @Override
    public void update(GameOfLifeModel model, ActionEvent e) {

    }

    @Override
    public void handleGameOfLifeViewEvent(GameOfLifeViewEvent e) {
        if (e.isDimensionEvent()) {
            handleDimensionEvent(e);
        } else if (e.isNextIterationEvent()) {
            handleNextIterationEvent(e);
        } else if (e.isRandomFillEvent()) {
            handleRandomFillEvent(e);
        } else if (e.isRestartEvent()) {
            handleRestartEvent(e);
        } else if (e.isSimulationEvent()) {
            handleRestartEvent(e);
        } else if (e.isSimulationSpeedEvent()) {
            handleSimulationSpeedEvent(e);
        } else if (e.isSpotEvent()) {
            handleSpotEvent(e);
        } else if (e.isThresholdEvent()) {
            handleThresholdEvent(e);
        } else {
            throw new RuntimeException("Event not recognized.");
        }
    }

    @Override
    public void handleDimensionEvent(GameOfLifeViewEvent e) {

    }

    @Override
    public void handleThresholdEvent(GameOfLifeViewEvent e) {

    }

    @Override
    public void handleRandomFillEvent(GameOfLifeViewEvent e) {

    }

    @Override
    public void handleSimulationSpeedEvent(GameOfLifeViewEvent e) {

    }

    @Override
    public void handleSimulationEvent(GameOfLifeViewEvent e) {

    }

    @Override
    public void handleNextIterationEvent(GameOfLifeViewEvent e) {

    }

    @Override
    public void handleRestartEvent(GameOfLifeViewEvent e) {

    }

    @Override
    public void handleSpotEvent(GameOfLifeViewEvent e) {

    }
}
