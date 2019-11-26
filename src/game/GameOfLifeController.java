package game;

import org.w3c.dom.events.EventException;

import java.awt.event.ActionEvent;

public class GameOfLifeController implements GameOfLifeObserver, GameOfLifeViewListener {

    private GameOfLifeModel _model;
    private GameOfLifeView _view;

    GameOfLifeController(GameOfLifeModel model, GameOfLifeView view) {
        _model = model;
        _view = view;

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
        if (width >= 10 && width <= 500) {
            board_width = width;
        }
        if (height >= 10 && height <= 500) {
            board_height = height;
        }
        System.out.println("Width: " + width + " Height: " + height);
        _view.setBoard(new JSpotBoard(width, height));
        _view.repaint();
        System.out.println("New Width: " + _view.getBoard().getSpotWidth() + " New Height: " + _view.getBoard().getSpotHeight());
        System.out.println("Dimensions changed.");
    }

    @Override
    public void handleThresholdEvent(GameOfLifeViewEvent e) {

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

    }

    @Override
    public void handleSimulationEvent(GameOfLifeViewEvent e) {

    }

    @Override
    public void handleNextIterationEvent(GameOfLifeViewEvent e) {

            _view.getBoard().setNextGeneration(_view.getDieLessThanThresh(), _view.getDieGreaterThanThresh(),
                                               _view.getLiveLessThanThresh(), _view.getLiveGreaterThanThresh());
            int liveCount = 0;
            for (Spot s : _view.getBoard()) {
                if (!s.isEmpty()) {
                    liveCount++;
                }
            }
            System.out.println("Live spots: " + liveCount);
    }

    @Override
    public void handleRestartEvent(GameOfLifeViewEvent e) {
        SpotBoardIterator iterator = new SpotBoardIterator(_view.getBoard());
        while (iterator.hasNext()) {
            Spot s = iterator.next();
            s.clearSpot();
        }
    }


    @Override
    public void update(GameOfLifeModel model, GameOfLifeViewEvent e) {

    }
}
