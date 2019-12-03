package game.view;

public class NextIterationEvent extends GameOfLifeViewEvent {
    @Override
    public boolean isNextIterationEvent() {
        return true;
    }
}
