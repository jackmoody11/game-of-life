package game.view;

public class SimulationSpeedEvent extends GameOfLifeViewEvent {
    private int _speed;

    SimulationSpeedEvent(int speed) {
        _speed = speed;
    }

    public int getSpeed() {
        return _speed;
    }

    @Override
    public boolean isSimulationSpeedEvent() {
        return true;
    }

}
