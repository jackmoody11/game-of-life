package a8.view.event;

public class RandomFillEvent extends GameOfLifeViewEvent {
    @Override
    public boolean isRandomFillEvent() {
        return true;
    }

}
