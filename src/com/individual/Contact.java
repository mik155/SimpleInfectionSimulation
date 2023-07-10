package com.individual;

public class Contact
{
    private Individual individual;
    private double time;
    private boolean toDelete = false;

    public Contact(Individual individual)
    {
        this.individual = individual;
        time = 0;
    }

    public void addTime(double time)
    {
        this.time += time;
    }

    public double getTime()
    {
        return time;
    }

    public boolean represents(Individual individual)
    {
        return this.individual.equals(individual);
    }

    public boolean delete()
    {
        return toDelete;
    }

    public void setToDelete()
    {
        toDelete = true;
    }

    public void setToSave()
    {
        toDelete = false;
    }

    public Individual getIndividual()
    {
        return individual;
    }
}
