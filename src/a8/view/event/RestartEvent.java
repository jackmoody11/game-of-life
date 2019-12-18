package a8.view.event;

public class RestartEvent extends GameOfLifeViewEvent {
    @Override
    public boolean isRestartEvent() {
        return true;
    }
}
