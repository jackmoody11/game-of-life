package game;

import javax.swing.SwingUtilities;

public class Simulation extends Thread {
    private boolean _done;
    private GameOfLifeModel _model;

    Simulation(GameOfLifeModel model) {
        _model = model;
        _done = false;
    }

    public void halt() {
        _done = true;
    }

    public void run() {
        while (!_done) {
            try {
                Thread.sleep(_model.getSimulationSpeed());
            } catch (InterruptedException ignored) {
            }
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    _model.setNextGeneration(_model.getDieLessThanThresh(),
                                             _model.getDieGreaterThanThresh(),
                                             _model.getLiveLessThanThresh(),
                                             _model.getLiveGreaterThanThresh());
                }
            });
        }
    }
}
