package a8.view.event;

public class DimensionEvent extends GameOfLifeViewEvent {
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
