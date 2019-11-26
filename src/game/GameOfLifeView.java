package game;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.ArrayList;

public class GameOfLifeView extends JPanel implements ActionListener, SpotListener, ChangeListener {

    private JSpotBoard _board;
    private JPanel _simpleSettingsPanel;
    private JPanel _advancedSettingsPanel;
    private List<GameOfLifeViewListener> listeners;

    // Height and width text fields
    private JSpinner _heightSpinner;
    private JSpinner _widthSpinner;

    // Add random fill button
    private JButton _randomFillButton;

    // Add next iteration button
    private JButton _nextButton;

    // Add restart button
    private JButton _restartButton;

    // Threshold text fields
    private JSpinner _dieGreaterThanThresh;
    private JSpinner _dieLessThanThresh;
    private JSpinner _liveGreaterThanThresh;
    private JSpinner _liveLessThanThresh;

    // Add simulation speed field
    private JSpinner _simulationSpeed;

    // Add simulation button
    private JButton _simulationButton;



    GameOfLifeView(int width, int height) {
        setLayout(new BorderLayout());

        _board = new JSpotBoard(width, height);
        _board.addSpotListener(this);
        add(_board, BorderLayout.NORTH);


        /*
         * Set up simple settings panel
         */
        _simpleSettingsPanel = new JPanel();
        _simpleSettingsPanel.setLayout(new GridLayout());

        // Add width and height text fields
        SpinnerModel dimensionModel = new SpinnerNumberModel(10, 10, 500, 1);
        JLabel heightTextLabel = new JLabel("Height: ");
        _heightSpinner = new JSpinner(dimensionModel);
        JLabel widthTextLabel = new JLabel("Width: ");
        _widthSpinner = new JSpinner(dimensionModel);
        _simpleSettingsPanel.add(heightTextLabel);
        _simpleSettingsPanel.add(_heightSpinner);
        _simpleSettingsPanel.add(widthTextLabel);
        _simpleSettingsPanel.add(_widthSpinner);


        // Add random fill button
        _randomFillButton = new JButton();
        _randomFillButton.setText("Randomly Fill");
        _simpleSettingsPanel.add(_randomFillButton);

        // Add next iteration button
        _nextButton = new JButton();
        _nextButton.setText("Next Iteration");
        _simpleSettingsPanel.add(_nextButton);

        // Add restart button
        _restartButton = new JButton();
        _restartButton.setText("Restart");
        _simpleSettingsPanel.add(_restartButton);

        // Add listeners to simple settings panel
        add(_simpleSettingsPanel, BorderLayout.CENTER);
        for (Component c : _simpleSettingsPanel.getComponents()) {
            if (c instanceof JButton) {
                ((JButton) c).addActionListener(this);
            } else if (c instanceof JSpinner) {
                ((JSpinner) c).addChangeListener(this);
            }
        }

        /*
         * Set up advanced settings panel
         */
        _advancedSettingsPanel = new JPanel();
        _advancedSettingsPanel.setLayout(new GridLayout());

        // Add threshold text fields
        // TODO: Will need to add some sort of model logic to only allow certain numbers
        //       based on less than/greater than value
        SpinnerModel dieGreaterModel = new SpinnerNumberModel(2, 0, 8, 1);
        SpinnerModel dieLessModel = new SpinnerNumberModel(4, 0, 8, 1);
        SpinnerModel liveGreaterModel = new SpinnerNumberModel(1, 0, 8, 1);
        SpinnerModel liveLessModel = new SpinnerNumberModel(4, 0, 8, 1);
        _dieGreaterThanThresh = new JSpinner(dieGreaterModel);
        _dieLessThanThresh = new JSpinner(dieLessModel);
        _liveGreaterThanThresh = new JSpinner(liveGreaterModel);
        _liveLessThanThresh = new JSpinner(liveLessModel);
        JLabel dieGreaterThanLabel = new JLabel("Die Thresh >");
        JLabel dieLessThanLabel = new JLabel("Die Thresh <");
        JLabel liveGreaterThanLabel = new JLabel("Live Thresh >");
        JLabel liveLessThanLabel = new JLabel("Live Thresh <");

        _advancedSettingsPanel.add(dieGreaterThanLabel);
        _advancedSettingsPanel.add(_dieGreaterThanThresh);
        _advancedSettingsPanel.add(dieLessThanLabel);
        _advancedSettingsPanel.add(_dieLessThanThresh);
        _advancedSettingsPanel.add(liveGreaterThanLabel);
        _advancedSettingsPanel.add(_liveGreaterThanThresh);
        _advancedSettingsPanel.add(liveLessThanLabel);
        _advancedSettingsPanel.add(_liveLessThanThresh);

        // Add simulation speed text field
        SpinnerModel simulationModel = new SpinnerNumberModel(10, 10, 1000, 1);
        _simulationSpeed = new JSpinner(simulationModel);
        JLabel simulationSpeedLabel = new JLabel("Simulation Speed");
        _advancedSettingsPanel.add(simulationSpeedLabel);
        _advancedSettingsPanel.add(_simulationSpeed);

        // Add simulation button
        _simulationButton = new JButton("Run Simulation");
        _advancedSettingsPanel.add(_simulationButton);

        add(_advancedSettingsPanel, BorderLayout.SOUTH);

        // Add listeners to advanced settings panel
        for (Component c : _advancedSettingsPanel.getComponents()) {
            if (c instanceof JSpinner) {
                ((JSpinner) c).addChangeListener(this);
            } else if (c instanceof JButton) {
                ((JButton) c).addActionListener(this);
            }
        }

        listeners = new ArrayList<>();

        this.setFocusable(true);
        this.grabFocus();
    }

    SpotBoard getBoard() {
        return _board;
    }

    public void setBoard(JSpotBoard board) {
        _board = board;
    }

    void addGameOfLifeViewListener(GameOfLifeViewListener l) {
        listeners.add(l);
    }

    public void removeGameOfLifeViewListener(GameOfLifeViewListener l) {
        listeners.remove(l);
    }

    private void fireEvent(GameOfLifeViewEvent e) {
        for (GameOfLifeViewListener l : listeners) {
            l.handleGameOfLifeViewEvent(e);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if (button == _randomFillButton) {
            fireEvent(new RandomFillEvent());
        } else if (button == _nextButton) {
            fireEvent(new NextIterationEvent());
        } else if (button == _restartButton) {
            fireEvent(new RestartEvent());
        } else if (button == _simulationButton) {
            fireEvent(new SimulationEvent());
        }
    }

    // Credit : https://www.baeldung.com/java-check-string-number
    private static boolean isNumeric(String string) {
        if (string == null) {
            return false;
        }
        try {
            int i = Integer.parseInt(string);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public int getDieGreaterThanThresh() {
        return (Integer) _dieGreaterThanThresh.getValue();
    }

    public int getDieLessThanThresh() {
        return (Integer) _dieLessThanThresh.getValue();
    }

    public int getLiveGreaterThanThresh() {
        return (Integer) _liveGreaterThanThresh.getValue();
    }

    public int getLiveLessThanThresh() {
        return (Integer) _liveLessThanThresh.getValue();
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

    @Override
    public void stateChanged(ChangeEvent e) {
        JSpinner source = (JSpinner) e.getSource();
        int value = (Integer) source.getValue();
        if (_heightSpinner.equals(source)) {
            int height = (value >= 10 && value <= 500) ? value : 10;
            int width = (Integer) _widthSpinner.getValue();
            fireEvent(new DimensionEvent(width, height));
            System.out.println("Height changed");
        } else if (_widthSpinner.equals(source)) {
            int width = (value >= 10 && value <= 500) ? value : 10;
            int height = (Integer) _heightSpinner.getValue();
            fireEvent(new DimensionEvent(width, height));
            System.out.println("Width changed");
        } else if (_dieGreaterThanThresh.equals(source)) {
            fireEvent(new ThresholdEvent(value, true, false));
        } else if (_dieLessThanThresh.equals(source)) {
            fireEvent(new ThresholdEvent(value, true, true));
        } else if (_liveGreaterThanThresh.equals(source)) {
            fireEvent(new ThresholdEvent(value, false, false));
        } else if (_liveLessThanThresh.equals(source)) {
            fireEvent(new ThresholdEvent(value, false, false));
        } else if (_simulationSpeed.equals(source)) {
            fireEvent(new SimulationSpeedEvent(value));
        }
    }
}
