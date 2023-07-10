package com.individual.state;

import com.board.SimulationBoard;
import com.individual.Individual;
import com.vector.VectorI;
import com.vector.adapter.VectorAdapter;

public class BasicState implements State
{
    protected Individual individual;
    public void setContext(Individual individual)
    {
        this.individual = individual;
    }

    @Override
    public boolean isInfected()
    {
        return false;
    }

    @Override
    public boolean isResistance()
    {
        return false;
    }

    @Override
    public boolean isHealthy()
    {
        return false;
    }

    public void move()
    {
        double phi = individual.getActualMoveVector().getPhi();

        if(Math.random() < SimulationBoard.IND_CHANGE_DIRECTION_PROP &&
                (SimulationBoard.step %  SimulationBoard.CHANGE_DIRECTION_FREQ) == 0)
            phi = Math.random() * 360.0;

        double length = individual.getActualMoveVector().length();
        if(Math.random() < SimulationBoard.IND_CHANGE_SPEED_PROP &&
                (SimulationBoard.step  % SimulationBoard.CHANGE_SPEED_FREQ) == 0 )
            length = Math.random() * SimulationBoard.MAXIMUM_MOVE_VECTOR_LENGTH;

        VectorI nextMoveVector = individual.getNextMoveVector();
        nextMoveVector.setVector(phi, length);
        individual.setNextMoveVector(nextMoveVector);

        VectorI vectorI = individual.getActualMoveVector();
        individual.setX(individual.getX() + vectorI.getX());
        individual.setY(individual.getY() + vectorI.getY());

        if(!SimulationBoard.isInsideBoard(individual) && Math.random() >= SimulationBoard.EXITING_BOARD_PROP)
        {
            individual.setX(individual.getX() - 2 * vectorI.getX());
            individual.setY(individual.getY() - 2 * vectorI.getY());
            double phiTmp = individual.getActualMoveVector().getPhi();
            individual.getNextMoveVector().setVector((phiTmp + 180) % 360, SimulationBoard.MAXIMUM_MOVE_VECTOR_LENGTH);
        }

        individual.setActualState(individual.getNextState());
        individual.setActualMoveVector(individual.getNextMoveVector());
    }



    @Override
    public void prepearMove()
    {

    }
}
