package game.view;

public abstract class GameOfLifeViewEvent {

    public boolean isDimensionEvent() {
        return false;
    }

    public boolean isThresholdEvent() {
        return false;
    }

    public boolean isRandomFillEvent() {
        return false;
    }

    public boolean isSimulationSpeedEvent() {
        return false;
    }

    public boolean isSimulationEvent() {
        return false;
    }

    public boolean isNextIterationEvent() {
        return false;
    }

    public boolean isRestartEvent() {
        return false;
    }

    public boolean isTorusToggleEvent() {
        return false;
    }
}

