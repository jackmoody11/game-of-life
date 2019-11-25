package game;

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

    public boolean isSpotEvent() {
        return false;
    }
}

class DimensionEvent extends GameOfLifeViewEvent {
    public boolean isDimensionEvent() {
        return true;
    }
}

class ThresholdEvent extends GameOfLifeViewEvent {
    public boolean isThresholdEvent() {
        return true;
    }
}

class RandomFillEvent extends GameOfLifeViewEvent {
    public boolean isRandomFillEvent() {
        return true;
    }

}
class SimulationSpeedEvent extends GameOfLifeViewEvent {
    public boolean isSimulationSpeedEvent() {
        return true;
    }

}
class SimulationEvent extends GameOfLifeViewEvent {
    public boolean isSimulationEvent() {
        return true;
    }

}

class NextIterationEvent extends GameOfLifeViewEvent {
    public boolean isNextIterationEvent() {
        return true;
    }

}

class RestartEvent extends GameOfLifeViewEvent {
    public boolean isRestartEvent() {
        return true;
    }

}

class SpotEvent extends GameOfLifeViewEvent {
    public boolean isSpotEvent() {
        return true;
    }

}

