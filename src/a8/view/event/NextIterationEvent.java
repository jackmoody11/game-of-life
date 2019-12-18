package a8.view.event;

public class NextIterationEvent extends GameOfLifeViewEvent {
    @Override
    public boolean isNextIterationEvent() {
        return true;
    }
}
