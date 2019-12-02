package game;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Iterator;

import javax.swing.JPanel;

/*
 * JSpotBoard is a user interface component that implements SpotBoard.
 *
 * Spot width and spot height are specified to the constructor.
 *
 * By default, the spots on the spot board are set up with a checker board pattern
 * for background colors and yellow highlighting.
 *
 * Uses SpotBoardIterator to implement Iterable<Spot>
 *
 */

public class JSpotBoard extends JPanel implements SpotBoard {

    private static final int DEFAULT_SCREEN_WIDTH = 500;
    private static final int DEFAULT_SCREEN_HEIGHT = 500;
    private static final Color DEFAULT_BACKGROUND_LIGHT = new Color(0.8f, 0.8f, 0.8f);
    private static final Color DEFAULT_SPOT_COLOR = Color.BLACK;
    private static final Color DEFAULT_HIGHLIGHT_COLOR = Color.GREEN;

    private Spot[][] _spots;

    JSpotBoard(int width, int height) {
        if (width < 1 || height < 1 || width > 500 || height > 500) {
            throw new IllegalArgumentException("Illegal spot board geometry");
        }
        initializeBoard(width, height);

    }

    private void initializeBoard(int width, int height) {
        setLayout(new GridLayout(height, width));
        _spots = new Spot[width][height];
        Dimension preferred_size = new Dimension(DEFAULT_SCREEN_WIDTH/width, DEFAULT_SCREEN_HEIGHT/height);
        for (int y=0; y<height; y++) {
            for (int x=0; x<width; x++) {
                _spots[x][y] = new JSpot(DEFAULT_BACKGROUND_LIGHT, DEFAULT_SPOT_COLOR, DEFAULT_HIGHLIGHT_COLOR, this, x, y);
                ((JSpot) _spots[x][y]).setPreferredSize(preferred_size);
                add(((JSpot) _spots[x][y]));
            }
        }
    }

    private void trigger_update() {
        repaint();

        // Not sure why, but need to schedule a call
        // to repaint for a little bit into the future
        // as well as the one we just did above
        // in order to make sure that we don't end up
        // with visual artifacts due to race conditions.

        new Thread(() -> {
            try {
                Thread.sleep(50);
            } catch (InterruptedException ignored) {
            }
            repaint();
        }).start();

    }

    @Override
    public void setSpots(Spot[][] spots) {
        _spots = spots;
    }

    @Override
    public void update() {
        trigger_update();
    }

    @Override
    public int getSpotWidth() {
        return _spots.length;
    }

    @Override
    public int getSpotHeight() {
        return _spots[0].length;
    }

    // Lookup method for Spot at position (x,y)

    @Override
    public Spot getSpotAt(int x, int y) {
        if (x < 0 || x >= getSpotWidth() || y < 0 || y >= getSpotHeight()) {
            throw new IllegalArgumentException("Illegal spot coordinates");
        }

        return _spots[x][y];
    }

    // Convenience methods for (de)registering spot listeners.

    @Override
    public void addSpotListener(SpotListener spot_listener) {
        for (int x=0; x<getSpotWidth(); x++) {
            for (int y=0; y<getSpotHeight(); y++) {
                _spots[x][y].addSpotListener(spot_listener);
            }
        }
    }

    @Override
    public void removeSpotListener(SpotListener spot_listener) {
        for (int x=0; x<getSpotWidth(); x++) {
            for (int y=0; y<getSpotHeight(); y++) {
                _spots[x][y].removeSpotListener(spot_listener);
            }
        }
    }

    @Override
    public Iterator<Spot> iterator() {
        return new SpotBoardIterator(this);
    }

    public void changeDimensions(int width, int height) {
        removeAll();
        revalidate();
        repaint();
        initializeBoard(width, height);
    }
}