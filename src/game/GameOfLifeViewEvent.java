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

    public boolean isTorusToggleEvent() {
        return false;
    }
}

class DimensionEvent extends GameOfLifeViewEvent {
    private int _width, _height;

    DimensionEvent(int width, int height) {
        if (width < 10 || height < 10 || width > 500 || height > 500) {
            throw new RuntimeException("Width and height must be between 10 and 500");
        }
        _width = width;
        _height = height;
    }

    public int getWidth() {
        return _width;
    }

    public int getHeight() {
        return _height;
    }

    @Override
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

    @Override
    public boolean isThresholdEvent() {
        return true;
    }
}

class RandomFillEvent extends GameOfLifeViewEvent {
    @Override
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

    @Override
    public boolean isSimulationSpeedEvent() {
        return true;
    }

}
class SimulationEvent extends GameOfLifeViewEvent {
    @Override
    public boolean isSimulationEvent() {
        return true;
    }

}

class NextIterationEvent extends GameOfLifeViewEvent {
    @Override
    public boolean isNextIterationEvent() {
        return true;
    }
}

class RestartEvent extends GameOfLifeViewEvent {
    @Override
    public boolean isRestartEvent() {
        return true;
    }
}

class TorusToggleEvent extends GameOfLifeViewEvent {
    private boolean _selected;
    TorusToggleEvent(boolean selected) {
        _selected = selected;
    }

    public boolean isSelected() {
        return _selected;
    }

    @Override
    public boolean isTorusToggleEvent() {
        return true;
    }
}

