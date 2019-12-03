package game.view;

public class RestartEvent extends GameOfLifeViewEvent {
    @Override
    public boolean isRestartEvent() {
        return true;
    }
}
