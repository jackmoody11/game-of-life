package a8.view.event;

public class SimulationEvent extends GameOfLifeViewEvent {
    private String _simulationCommand;

    SimulationEvent(String cmd) {
        _simulationCommand = cmd;
    }

    @Override
    public boolean isSimulationEvent() {
        return true;
    }

    public void setSimulationCommand(String cmd) {
        _simulationCommand = cmd;
    }

    public String getSimulationCommand() {
        return _simulationCommand;
    }
}
