package game;

public abstract class GameOfLifeViewEvent {

    public boolean isDimensionEvent() {
        return false;
    }

    public boolean isThresholdEvent() {
        return false;
    }

    public boolean isRandomFillEvent() {
        return false;
    }

    public boolean isSimulationSpeedEvent() {
        return false;
    }

    public boolean isSimulationEvent() {
        return false;
    }

    public boolean isNextIterationEvent() {
        return false;
    }

    public boolean isRestartEvent() {
        return false;
    }
}

class DimensionEvent extends GameOfLifeViewEvent {
    private int _width, _height;

    DimensionEvent(int width, int height) {
        _width = width;
        _height = height;
    }

    public int getWidth() {
        return _width;
    }

    public int getHeight() {
        return _height;
    }

    public boolean isDimensionEvent() {
        return true;
    }

}

class ThresholdEvent extends GameOfLifeViewEvent {
    private boolean _isDie, _isLessThan;
    private int _value;

    ThresholdEvent(int value, boolean isDie, boolean isLessThan) {
        _value = value;
        _isDie = isDie;
        _isLessThan = isLessThan;
    }

    public int getValue() {
        return _value;
    }

    public boolean isDie() {
        return _isDie;
    }

    public boolean isLessThan() {
        return _isLessThan;
    }

    public boolean isThresholdEvent() {
        return true;
    }
}

class RandomFillEvent extends GameOfLifeViewEvent {
    public boolean isRandomFillEvent() {
        return true;
    }

}
class SimulationSpeedEvent extends GameOfLifeViewEvent {
    private int _speed;

    SimulationSpeedEvent(int speed) {
        _speed = speed;
    }

    public int getSpeed() {
        return _speed;
    }

    public boolean isSimulationSpeedEvent() {
        return true;
    }

}
class SimulationEvent extends GameOfLifeViewEvent {
    public boolean isSimulationEvent() {
        return true;
    }

}

class NextIterationEvent extends GameOfLifeViewEvent {
    public boolean isNextIterationEvent() {
        return true;
    }
}

class RestartEvent extends GameOfLifeViewEvent {
    public boolean isRestartEvent() {
        return true;
    }
}

