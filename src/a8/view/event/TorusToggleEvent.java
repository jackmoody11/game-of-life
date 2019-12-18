package a8.view.event;

public class TorusToggleEvent extends GameOfLifeViewEvent {
    private boolean _selected;

    public TorusToggleEvent(boolean selected) {
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
