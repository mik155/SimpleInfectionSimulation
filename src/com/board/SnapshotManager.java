package com.board;

import java.util.Stack;

public class SnapshotManager
{
    private final SimulationBoard simulationBoard;
    private final Stack<SimulationBoard.Snaphshot> history;

    public SnapshotManager(SimulationBoard simulationBoard)
    {
        this.simulationBoard = simulationBoard;
        history = new Stack<>();
    }

    public void undo()
    {
        if(!history.isEmpty())
            simulationBoard.restore(history.pop());
    }

    public void save(SimulationBoard.Snaphshot memento)
    {
        history.push(memento);
    }
}