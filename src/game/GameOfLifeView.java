package game;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class GameOfLifeView extends JPanel implements ActionListener, SpotListener, ChangeListener {

    private JSpotBoard _board;
    private List<GameOfLifeViewListener> listeners;

    private JSpinner _heightSpinner;
    private JSpinner _widthSpinner;

    private JButton _randomFillButton;

    // Next iteration button
    private JButton _nextButton;

    private JButton _restartButton;

    private JSpinner _dieGreaterThanThresh;
    private JSpinner _dieLessThanThresh;
    private JSpinner _liveGreaterThanThresh;
    private JSpinner _liveLessThanThresh;

    private JSpinner _simulationSpeed;
    private JButton _simulationButton;


    GameOfLifeView(GameOfLifeModel model) {
        setLayout(new BorderLayout());

        _board = new JSpotBoard(10,10);
        _board.addSpotListener(this);
        add(_board, BorderLayout.NORTH);


        /*
         * Set up simple settings panel
         */
        JPanel simpleSettingsPanel = new JPanel();
        simpleSettingsPanel.setLayout(new GridLayout());

        // Add width and height text fields
        _heightSpinner = new JSpinner(new SpinnerNumberModel(10, 10, 500, 1));
        _widthSpinner = new JSpinner(new SpinnerNumberModel(10, 10, 500, 1));
        simpleSettingsPanel.add(new JLabel("Height: "));
        simpleSettingsPanel.add(_heightSpinner);
        simpleSettingsPanel.add(new JLabel("Width: "));
        simpleSettingsPanel.add(_widthSpinner);


        // Add random fill button
        _randomFillButton = new JButton();
        _randomFillButton.setText("Randomly Fill");
        simpleSettingsPanel.add(_randomFillButton);

        // Add next iteration button
        _nextButton = new JButton();
        _nextButton.setText("Next Iteration");
        simpleSettingsPanel.add(_nextButton);

        // Add restart button
        _restartButton = new JButton();
        _restartButton.setText("Restart");
        simpleSettingsPanel.add(_restartButton);

        // Add torus toggle button
        // Add torus toggle
        JToggleButton torusToggle = new JToggleButton("Torus Mode", false);
        simpleSettingsPanel.add(torusToggle);

        // Add listeners to simple settings panel
        add(simpleSettingsPanel, BorderLayout.CENTER);
        for (Component c : simpleSettingsPanel.getComponents()) {
            if (c instanceof JButton) {
                ((JButton) c).addActionListener(this);
            } else if (c instanceof JSpinner) {
                ((JSpinner) c).addChangeListener(this);
            } else if (c instanceof JToggleButton) {
                ((JToggleButton) c).addActionListener(this);
            }
        }

        /*
         * Set up advanced settings panel
         */
        JPanel advancedSettingsPanel = new JPanel();
        advancedSettingsPanel.setLayout(new GridLayout());

        // Add threshold text fields
        // TODO: Will need to add some sort of model logic to only allow certain numbers
        //       based on less than/greater than value -- Actually not sure if this is necessary
        //       since user is explicitly stating that they want this to apply
        SpinnerModel dieGreaterModel = new SpinnerNumberModel(2, -1, 9, 1);
        SpinnerModel dieLessModel = new SpinnerNumberModel(4, -1, 9, 1);
        SpinnerModel liveGreaterModel = new SpinnerNumberModel(1, -1, 9, 1);
        SpinnerModel liveLessModel = new SpinnerNumberModel(4, -1, 9, 1);
        _dieGreaterThanThresh = new JSpinner(dieGreaterModel);
        _dieLessThanThresh = new JSpinner(dieLessModel);
        _liveGreaterThanThresh = new JSpinner(liveGreaterModel);
        _liveLessThanThresh = new JSpinner(liveLessModel);

        advancedSettingsPanel.add(new JLabel("Die Thresh >"));
        advancedSettingsPanel.add(_dieGreaterThanThresh);
        advancedSettingsPanel.add(new JLabel("Die Thresh <"));
        advancedSettingsPanel.add(_dieLessThanThresh);
        advancedSettingsPanel.add(new JLabel("Live Thresh >"));
        advancedSettingsPanel.add(_liveGreaterThanThresh);
        advancedSettingsPanel.add(new JLabel("Live Thresh <"));
        advancedSettingsPanel.add(_liveLessThanThresh);

        // Add simulation speed text field
        _simulationSpeed = new JSpinner(new SpinnerNumberModel(10, 10, 1000, 1));
        advancedSettingsPanel.add(new JLabel("Simulation Speed"));
        advancedSettingsPanel.add(_simulationSpeed);

        // Add simulation button
        _simulationButton = new JButton("Run Simulation");
        _simulationButton.setActionCommand("start");
        advancedSettingsPanel.add(_simulationButton);

        add(advancedSettingsPanel, BorderLayout.SOUTH);

        // Add listeners to advanced settings panel
        for (Component c : advancedSettingsPanel.getComponents()) {
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

    JSpotBoard getBoard() {
        return _board;
    }

    void setBoard(JSpotBoard board) {
        this.getLayout().removeLayoutComponent(_board);
        _board = board;
        _board.addSpotListener(this);
        add(_board, BorderLayout.NORTH);
    }

    JButton getSimulationButton() {
        return _simulationButton;
    }

    void addGameOfLifeViewListener(GameOfLifeViewListener l) {
        listeners.add(l);
    }

    public void removeGameOfLifeViewListener(GameOfLifeViewListener l) {
        listeners.remove(l);
    }

    // Same thing as notifyObservers
    private void fireEvent(GameOfLifeViewEvent e) {
        for (GameOfLifeViewListener l : listeners) {
            l.handleGameOfLifeViewEvent(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            if (button == _randomFillButton) {
                fireEvent(new RandomFillEvent());
            } else if (button == _nextButton) {
                fireEvent(new NextIterationEvent());
            } else if (button == _restartButton) {
                fireEvent(new RestartEvent());
            } else if (button == _simulationButton) {
                fireEvent(new SimulationEvent(e.getActionCommand()));
            }
        } else if (e.getSource() instanceof JToggleButton) {
            fireEvent(new TorusToggleEvent(((JToggleButton) e.getSource()).isSelected()));
        }
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
        if (_heightSpinner.equals(source) || _widthSpinner.equals(source)) {
            int height = (Integer) _heightSpinner.getValue();
            int width = (Integer) _widthSpinner.getValue();
            fireEvent(new DimensionEvent(width, height));
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
