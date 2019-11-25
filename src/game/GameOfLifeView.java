package game;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.ArrayList;

public class GameOfLifeView extends JPanel implements ActionListener, KeyListener, SpotListener {

    private JSpotBoard _board;
    private JPanel _simpleSettingsPanel;
    private JPanel _advancedSettingsPanel;
    private List<GameOfLifeViewListener> listeners;

    GameOfLifeView() {
        setLayout(new BorderLayout());

        _board = new JSpotBoard(10,10);
        _board.addSpotListener(this);
        add(_board, BorderLayout.NORTH);


        /*
         * Set up simple settings panel
         */
        _simpleSettingsPanel = new JPanel();
        _simpleSettingsPanel.setLayout(new GridLayout());

        // Add width and height text fields
        JLabel heightTextLabel = new JLabel("Height: ");
        JTextField heightTextField = new JTextField();
        JLabel widthTextLabel = new JLabel("Width: ");
        JTextField widthTextField = new JTextField();
        _simpleSettingsPanel.add(heightTextLabel);
        _simpleSettingsPanel.add(heightTextField);
        _simpleSettingsPanel.add(widthTextLabel);
        _simpleSettingsPanel.add(widthTextField);

        // Add random fill button
        JButton randomFillButton = new JButton();
        randomFillButton.setText("Randomly Fill");
        _simpleSettingsPanel.add(randomFillButton);

        // Add next iteration button
        JButton nextButton = new JButton();
        nextButton.setText("Next Iteration");
        _simpleSettingsPanel.add(nextButton);

        // Add restart button
        JButton restartButton = new JButton();
        restartButton.setText("Restart");
        _simpleSettingsPanel.add(restartButton);

        add(_simpleSettingsPanel, BorderLayout.CENTER);


        /*
         * Set up advanced settings panel
         */
        _advancedSettingsPanel = new JPanel();
        _advancedSettingsPanel.setLayout(new GridLayout());

        // Add threshold text fields
        JLabel dieGreaterThanLabel = new JLabel("Die Thresh >=");
        JTextField dieGreaterThanThresh = new JTextField();
        JLabel dieLessThanLabel = new JLabel("Die Thresh <=");
        JTextField dieLessThanThresh = new JTextField();
        JLabel liveGreaterThanLabel = new JLabel("Live Thresh >=");
        JTextField liveGreaterThanThresh = new JTextField();
        JLabel liveLessThanLabel = new JLabel("Live Thresh <=");
        JTextField liveLessThanThresh = new JTextField();
        _advancedSettingsPanel.add(dieGreaterThanLabel);
        _advancedSettingsPanel.add(dieGreaterThanThresh);
        _advancedSettingsPanel.add(dieLessThanLabel);
        _advancedSettingsPanel.add(dieLessThanThresh);
        _advancedSettingsPanel.add(liveGreaterThanLabel);
        _advancedSettingsPanel.add(liveGreaterThanThresh);
        _advancedSettingsPanel.add(liveLessThanLabel);
        _advancedSettingsPanel.add(liveLessThanThresh);

        // Add simulation speed text field
        JTextField simulationSpeed = new JTextField();
        JLabel simulationSpeedLabel = new JLabel("Simulation Speed (Enter number between 10 and 1000 ms)");
        _advancedSettingsPanel.add(simulationSpeedLabel);
        _advancedSettingsPanel.add(simulationSpeed);

        // Add simulation button
        JButton simulationButton = new JButton("Run Simulation");
        _advancedSettingsPanel.add(simulationButton);

        add(_advancedSettingsPanel, BorderLayout.SOUTH);

    }

    public void addGameOfLifeViewListener(GameOfLifeViewListener l) {
        listeners.add(l);
    }

    public void removeGameOfLifeViewListener(GameOfLifeViewListener l) {
        listeners.remove(l);
    }

    public void fireEvent(GameOfLifeViewEvent e) {
        for (GameOfLifeViewListener l : listeners) {
            l.handleGameOfLifeViewEvent(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void spotClicked(Spot spot) {
        spot.toggleSpot();
    }

    @Override
    public void spotEntered(Spot spot) {
        spot.highlightSpot();
    }

    @Override
    public void spotExited(Spot spot) {
        spot.unhighlightSpot();
    }
}
