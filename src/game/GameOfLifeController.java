package game;

public class GameOfLifeController implements GameOfLifeObserver, GameOfLifeViewListener {

    private GameOfLifeModel _model;
    private GameOfLifeView _view;

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
            handleRestartEvent(e);
        } else if (e.isSimulationSpeedEvent()) {
            handleSimulationSpeedEvent(e);
        } else if (e.isThresholdEvent()) {
            handleThresholdEvent(e);
        } else {
            throw new RuntimeException("Event not recognized.");
        }
    }

    @Override
    public void handleDimensionEvent(GameOfLifeViewEvent e) {
        DimensionEvent dimension = (DimensionEvent) e;
        int board_width = _view.getBoard().getSpotWidth();
        int board_height = _view.getBoard().getSpotHeight();
        int width = dimension.getWidth();
        int height = dimension.getHeight();
        _view.setBoard(new JSpotBoard(width, height));
        _view.revalidate();
        _view.repaint();
    }

    @Override
    public void handleThresholdEvent(GameOfLifeViewEvent e) {
        ThresholdEvent threshEvent = (ThresholdEvent) e;
        int value = threshEvent.getValue();
        if (threshEvent.isDie() && threshEvent.isLessThan()) {
            _model.setDieLessThanThresh(value);
        } else if (threshEvent.isDie()) {
            _model.setDieGreaterThanThresh(value);
        } else if (threshEvent.isLessThan()) {
            _model.setLiveLessThanThresh(value);
        } else {
            _model.setLiveGreaterThanThresh(value);
        }

    }

    @Override
    public void handleRandomFillEvent(GameOfLifeViewEvent e) {
        SpotBoardIterator iterator = new SpotBoardIterator(_view.getBoard());
        while (iterator.hasNext()) {
            Spot s = iterator.next();
            if (Math.random() >= 0.7) {
                s.toggleSpot();
            }
        }
    }

    @Override
    public void handleSimulationSpeedEvent(GameOfLifeViewEvent e) {
        _model.setSimulationSpeed(((SimulationSpeedEvent) e).getSpeed());
    }

    @Override
    public void handleSimulationEvent(GameOfLifeViewEvent e) {

    }

    @Override
    public void handleNextIterationEvent(GameOfLifeViewEvent e) {
        _model.setNextGeneration(_view.getDieLessThanThresh(),
                                 _view.getDieGreaterThanThresh(),
                                 _view.getLiveLessThanThresh(),
                                 _view.getLiveGreaterThanThresh());
        _view.getBoard().update();
    }

    @Override
    public void handleRestartEvent(GameOfLifeViewEvent e) {
        _model.reset();
        _view.setBoard(_model.getBoard());
    }


    @Override
    public void update(GameOfLifeModel model, GameOfLifeViewEvent e) {

    }
}
