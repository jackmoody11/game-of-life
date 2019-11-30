package game;

import java.awt.*;

public class GameOfLifeController implements GameOfLifeObserver, GameOfLifeViewListener {

    private GameOfLifeModel _model;
    private GameOfLifeView _view;
    private Simulation _simulation;

    GameOfLifeController(GameOfLifeModel model, GameOfLifeView view) {
        _model = model;
        _view = view;
        _model.setBoard(view.getBoard());

        view.addGameOfLifeViewListener(this);
        model.addObserver(this);
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
            handleSimulationEvent(e);
        } else if (e.isSimulationSpeedEvent()) {
            handleSimulationSpeedEvent(e);
        } else if (e.isThresholdEvent()) {
            handleThresholdEvent(e);
        } else if (e.isTorusToggleEvent()) {
            handleTorusToggleEvent(e);
        } else {
            throw new RuntimeException("Event not recognized.");
        }
    }

    /*
     * Changes dimensions of board
     */
    @Override
    public void handleDimensionEvent(GameOfLifeViewEvent e) {
        DimensionEvent dimension = (DimensionEvent) e;
        int width = dimension.getWidth();
        int height = dimension.getHeight();

        _view.remove(_view.getBoard());
        JSpotBoard board = new JSpotBoard(width, height);
        _model.setBoard(board);
        _view.add(board, BorderLayout.NORTH);
        _view.revalidate();
    }

    /*
     * Changes model threshold values
     */
    @Override
    public void handleThresholdEvent(GameOfLifeViewEvent e) {
        ThresholdEvent threshEvent = (ThresholdEvent) e;
        int value = threshEvent.getValue();
        if (threshEvent.isDie()) {
            if (threshEvent.isLessThan()) {
                _model.setDieLessThanThresh(value);
            } else {
                _model.setDieGreaterThanThresh(value);
            }
        } else {
            if (threshEvent.isLessThan()) {
                _model.setLiveLessThanThresh(value);
            } else {
                _model.setLiveGreaterThanThresh(value);
            }
        }
    }

    @Override
    public void handleRandomFillEvent(GameOfLifeViewEvent e) {
        _model.randomlyFill();
    }

    @Override
    public void handleSimulationSpeedEvent(GameOfLifeViewEvent e) {
        _model.setSimulationSpeed(((SimulationSpeedEvent) e).getSpeed());
    }

    @Override
    public void handleSimulationEvent(GameOfLifeViewEvent e) {
        String cmd = ((SimulationEvent) e).getSimulationCommand();
        if (cmd.equals("start")) {
            _simulation = new Simulation(_model);
            _simulation.start();
            _view.getSimulationButton().setText("Stop Simulation");
            _view.getSimulationButton().setActionCommand("stop");
        } else if (cmd.equals("stop")) {
            _simulation.halt();
            try {
                _simulation.join();
            } catch (InterruptedException ignored) {
            }
            _view.getSimulationButton().setText("Start Simulation");
            _view.getSimulationButton().setActionCommand("start");
        }
    }

    @Override
    public void handleNextIterationEvent(GameOfLifeViewEvent e) {
        _model.setNextGeneration();
    }

    @Override
    public void handleRestartEvent(GameOfLifeViewEvent e) {
        _model.reset();
    }

    @Override
    public void handleTorusToggleEvent(GameOfLifeViewEvent e) {
        TorusToggleEvent event = (TorusToggleEvent) e;
        _model.setIsTorusMode(event.isSelected());
    }


    @Override
    public void update(GameOfLifeModel model, JSpotBoard board) {
        _view.setBoard(_model.getBoard());
        _view.repaint();
    }
}
