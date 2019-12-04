package a8.view;

public class NextIterationEvent extends GameOfLifeViewEvent {
    @Override
    public boolean isNextIterationEvent() {
        return true;
    }
}
