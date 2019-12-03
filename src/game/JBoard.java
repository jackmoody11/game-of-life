package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class JBoard extends JPanel implements Board, MouseListener {

    private boolean[][] _board;
    private List<BoardListener> _listeners;

    private final static int DEFAULT_SCREEN_HEIGHT = 500;
    private final static int DEFAULT_SCREEN_WIDTH = 500;
    private final static Color BACKGROUND_COLOR = Color.WHITE;
    private final static Color BORDER_COLOR = Color.LIGHT_GRAY;
    private final static Color SPOT_COLOR = Color.BLACK;


    JBoard(int width, int height) {
        _board = new boolean[height][width];
        _listeners = new ArrayList<>();
        addMouseListener(this);

        setBackground(BACKGROUND_COLOR);
        setPreferredSize(new Dimension(DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT));
    }

    public void addBoardListener(BoardListener l) {
        _listeners.add(l);
    }

    public void removeBoardListener(BoardListener l) {
        _listeners.remove(l);
    }

    public int getSpotWidth() {
        return _board[0].length;
    }

    public int getSpotHeight() {
        return _board.length;
    }

    public void changeDimensions(int width, int height) {
        _board = new boolean[height][width];
        trigger_update();
    }

    public void toggleSpotAt(int x, int y) {
        _board[y][x] = !_board[y][x];
        trigger_update();
    }

    public void randomlyFill() {
        reset();
        for (int x = 0; x < getSpotWidth(); x++) {
            for (int y = 0; y < getSpotHeight(); y++) {
                if (Math.random() >= 0.7) {
                    _board[y][x] = true;
                }
            }
        }
    }

    public void reset() {
        for (int x = 0; x < getSpotWidth(); x++) {
            for (int y = 0; y < getSpotHeight(); y++) {
                _board[y][x] = false;
            }
        }
        trigger_update();
    }

    public boolean getSpotAt(int x, int y) {
        return _board[y][x];
    }

    public int getNumberOfLiveNeighbors(boolean torusMode, int x, int y) {
        int liveCount = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (!(i == 0 && j == 0)) {
                    int currSpotX = x + i;
                    int currSpotY = y + j;
                    try {
                        if (getSpotAt(x + i, y + j)) {
                            liveCount++;
                        }
                    } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
                        if (torusMode) {
                            int width = getSpotWidth();
                            int height = getSpotHeight();
                            if (currSpotX < 0) {
                                currSpotX = width - 1;
                            } else if (currSpotX > width - 1) {
                                currSpotX = 0;
                            }
                            if (currSpotY < 0) {
                                currSpotY = height - 1;
                            } else if (currSpotY > height - 1) {
                                currSpotY = 0;
                            }
                            if (getSpotAt(currSpotX, currSpotY)) {
                                liveCount++;
                            }
                        }
                    }
                }
            }
        }
        return liveCount;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX() * getSpotWidth() / getWidth();
        int y = e.getY() * getSpotHeight() / getHeight();
        System.out.println("(" + x + ", " + y + ")");
        for (BoardListener l : _listeners) {
            l.boardClicked(this, x, y);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        int widthDelta = getWidth() / getSpotWidth();
        int heightDelta = getHeight() / getSpotHeight();

        // Clear board


        // Set border for each rectangle
        g2d.setColor(BORDER_COLOR);
        for (int x = 0; x < getSpotWidth(); x++) {
            for (int y = 0; y < getSpotHeight(); y++) {
                g2d.drawRect(x * widthDelta, y * heightDelta, widthDelta, heightDelta);
            }
        }

        // Fill in each live spot
        g2d.setColor(SPOT_COLOR);
        for (int x = 0; x < getSpotWidth(); x++) {
            for (int y = 0; y < getSpotHeight(); y++) {
                if (_board[y][x]) {
                    g2d.fillRect(x * widthDelta, y * heightDelta, widthDelta, heightDelta);
                }
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

        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ignored) {
                }
                repaint();
            }
        }).start();

    }
}
