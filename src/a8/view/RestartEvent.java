package a8.view;

public class RestartEvent extends GameOfLifeViewEvent {
    @Override
    public boolean isRestartEvent() {
        return true;
    }
}
