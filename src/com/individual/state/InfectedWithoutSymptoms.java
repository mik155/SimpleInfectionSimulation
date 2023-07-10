package com.individual.state;

import com.board.SimulationBoard;

public class InfectedWithoutSymptoms extends BasicState implements State
{

    private final double timeOfGettingSeek;
    private final double timeOfRecover;

    public InfectedWithoutSymptoms(double time)
    {
        this.timeOfGettingSeek = time;
        double deltaT = Math.random() * (SimulationBoard.MAX_SEEK_TIME - SimulationBoard.MIN_SEEK_TIME);
        deltaT += SimulationBoard.MIN_SEEK_TIME;
        this.timeOfRecover = this.timeOfGettingSeek + deltaT;
    }

    @Override
    public void prepearMove()
    {
        if(SimulationBoard.time >= timeOfRecover)
            individual.setNextState(new Resistance());
        else
            individual.setNextState(individual.getActualState());
    }

    public boolean isInfected()
    {
        return true;
    }
}
