package a8.view;

public class ThresholdEvent extends GameOfLifeViewEvent {
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
