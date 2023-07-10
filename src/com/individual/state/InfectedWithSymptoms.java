package com.individual.state;

import com.individual.Individual;

public class InfectedWithSymptoms extends InfectedWithoutSymptoms implements State
{
    public InfectedWithSymptoms(double time)
    {
        super(time);
    }

    public boolean isInfected()
    {
        return true;
    }
}
