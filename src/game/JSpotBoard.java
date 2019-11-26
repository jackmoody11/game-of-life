package game;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
    private static final Color DEFAULT_BACKGROUND_DARK = new Color(0.5f, 0.5f, 0.5f);
    private static final Color DEFAULT_SPOT_COLOR = Color.BLACK;
    private static final Color DEFAULT_HIGHLIGHT_COLOR = Color.YELLOW;

    private Spot[][] _spots;

    public JSpotBoard(int width, int height) {
        if (width < 1 || height < 1 || width > 500 || height > 500) {
            throw new IllegalArgumentException("Illegal spot board geometry");
        }
        setLayout(new GridLayout(height, width));
        _spots = new Spot[width][height];
        initializeBoard(_spots);

    }

    private void initializeBoard(Spot[][] spots) {
        Dimension preferred_size = new Dimension(DEFAULT_SCREEN_WIDTH/getSpotWidth(), DEFAULT_SCREEN_HEIGHT/getSpotHeight());

        for (int y=0; y<getSpotHeight(); y++) {
            for (int x=0; x<getSpotHeight(); x++) {
                spots[x][y] = new JSpot(DEFAULT_BACKGROUND_LIGHT, DEFAULT_SPOT_COLOR, DEFAULT_HIGHLIGHT_COLOR, this, x, y);
                ((JSpot)spots[x][y]).setPreferredSize(preferred_size);
                add(((JSpot) spots[x][y]));
            }
        }
    }

    public void setNextGeneration(int dieLessThanThresh,
                                  int dieGreaterThanThresh,
                                  int liveLessThanThresh,
                                  int liveGreaterThanThresh) {
        Spot[][] nextBoard = new Spot[getSpotWidth()][getSpotHeight()];
        initializeBoard(nextBoard);
        for (int i=0; i< getSpotWidth(); i++) {
            for (int j=0; j < getSpotHeight(); j++) {
                Spot s = getSpotAt(i, j);
                int liveCount = s.getNumberOfLiveNeighbors();
                if (s.isEmpty()) {
                    if (liveCount < dieLessThanThresh && liveCount > dieGreaterThanThresh) {
                        nextBoard[i][j].toggleSpot();
                    }
                } else if (liveCount > liveGreaterThanThresh && liveCount < liveLessThanThresh) {
                    // nextBoard initialized as empty, so only toggle if live spot stays alive
                    nextBoard[i][j].toggleSpot();
                }
            }
        }
        for (int i=0; i < getSpotWidth(); i++) {
            for (int j=0; j < getSpotHeight(); j++) {
                if (nextBoard[i][j].getSpotColor() != _spots[i][j].getSpotColor()) {
                    nextBoard[i][j].toggleSpot();
                }
            }
        }
        _spots = nextBoard;
        update();
    }

    public void update() {
        trigger_update();
    }

    private void trigger_update() {
        repaint();

        // Not sure why, but need to schedule a call
        // to repaint for a little bit into the future
        // as well as the one we just did above
        // in order to make sure that we don't end up
        // with visual artifacts due to race conditions.

        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                }
                repaint();
            }
        }).start();

    }

    // Getters for SpotWidth and SpotHeight properties

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
}