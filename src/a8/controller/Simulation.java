package a8.controller;

import a8.model.GameOfLifeModel;

import javax.swing.SwingUtilities;

public class Simulation extends Thread {
    private boolean _done;
    private GameOfLifeModel _model;
    private int _simulationSpeed;

    Simulation(GameOfLifeModel model) {
        _model = model;
        _simulationSpeed = _model.getSimulationSpeed();
        _done = false;
    }

    void halt() {
        _done = true;
    }

    public void run() {
        while (!_done) {
            try {
                Thread.sleep(_simulationSpeed);
            } catch (InterruptedException e) {
                throw new RuntimeException("Unexpected interrupt", e);
            }
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    // Adjust simulation speed to what the computer can handle
                    final long start = System.currentTimeMillis();
                    _model.setNextGeneration();
                    final long end = System.currentTimeMillis();
                    _simulationSpeed = Math.max((int) (end - start), _model.getSimulationSpeed());
                }
            });
        }
    }
}
