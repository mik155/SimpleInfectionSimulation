package com.individual;

import com.board.SimulationBoard;
import com.individual.state.*;
import com.vector.Vector;
import com.vector.VectorI;
import com.vector.adapter.VectorAdapter;
import java.util.LinkedList;
import java.util.List;

public class Individual
{
    private int id;
    private VectorI actualMoveVector;
    private VectorI nextMoveVector;
    private double xPosition;
    private double yPosition;

    private State actualState;
    private State nextState;

    private List<Individual> trackedIndividuals = new LinkedList<Individual>();

    public Individual(int id, double x, double y, State state)
    {
        this.id = id;
        this.xPosition = x;
        this.yPosition = y;

        actualState = state;
        actualState.setContext(this);
        nextState = state;
        nextState.setContext(this);

        nextMoveVector = new VectorAdapter(new Vector(0,0));
        actualMoveVector = new VectorAdapter(new Vector(0,0));

        state.setContext(this);
    }

    public void setTrackedIndividuals(List trackedIndividuals)
    {
        this.trackedIndividuals = trackedIndividuals;
    }

    public void setNextState(State actualState)
    {
        this.nextState = actualState;
        this.nextState.setContext(this);
    }

    public void setActualState(State actualState)
    {
        this.actualState = actualState;
        this.actualState.setContext(this);
    }

    public State getActualState()
    {
        return actualState;
    }

    public State getNextState()
    {
        return nextState;
    }

    public void move()
    {
        actualState.move();
    }

    public void preparMove()
    {
        actualState.prepearMove();
    }

    public List getTracktedIndividuals()
    {
        return trackedIndividuals;
    }

    public double distance(Individual individual)
    {
        double v1 = xPosition - individual.xPosition;
        v1 = v1 * v1;
        double v2 = yPosition - individual.yPosition;
        v2 = v2 * v2;
        return Math.sqrt(v1 + v2);
    }

    public boolean equals(Object obj)
    {
        if( !(obj instanceof Individual))
            return false;
        Individual other = (Individual) obj;
        return other.id == id;
    }

    public VectorI getActualMoveVector()
    {
        return actualMoveVector;
    }

    public VectorI getNextMoveVector()
    {
        return nextMoveVector;
    }

    public void setActualMoveVector(VectorI v)
    {
        this.actualMoveVector = v;
    }

    public void setNextMoveVector(VectorI v)
    {
        this.nextMoveVector = v;
    }

    public void setX(double x)
    {
        this.xPosition = x;
    }

    public void setY(double y)
    {
        this.yPosition = y;
    }

    public double getX()
    {
        return xPosition;
    }

    public double getY()
    {
        return yPosition;
    }

    public String toString()
    {
        return "(x, y): (" + getX() + ", " + getY() + ")";
    }

    public Individual copy()
    {
        State state = null;
        if(actualState.isHealthy())
            state = new Healthy();
        else if(actualState.isResistance())
            state = new Resistance();
        else if(actualState.isInfected())
        {
            if(actualState instanceof InfectedWithoutSymptoms)
                state = new InfectedWithoutSymptoms(SimulationBoard.time);
            else
                state = new InfectedWithSymptoms(SimulationBoard.time);
        }
        return new Individual(id, xPosition, yPosition, state);
    }
}
