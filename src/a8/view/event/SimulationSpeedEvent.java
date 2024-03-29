package a8.view.event;

public class SimulationSpeedEvent extends GameOfLifeViewEvent {
    private int _speed;

    public SimulationSpeedEvent(int speed) {
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
