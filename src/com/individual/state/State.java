package com.individual.state;

import com.individual.Individual;

public interface State
{
    void move();
    void prepearMove();
    void setContext(Individual individual);
    boolean isInfected();
    boolean isResistance();
    boolean isHealthy();
}
