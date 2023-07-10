package com.board;

import com.individual.Individual;
import com.individual.state.Healthy;
import com.individual.state.InfectedWithSymptoms;
import com.individual.state.State;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SimulationBoard
{
    //INDIVIDUALS QUANTITY
    //init number of individuals
    public static final int IND_NUMBER = 350;
    //[n]
    public static final int MAX_ADDED_IND = 50;
    //[n]
    public static int NEW_IND_PER_SECOND = 5;
    //[n]
    public static int MAXIMUM_NUMBER_OF_INDIV = 400;

    //INDIVIDUALS ATTRIBUTES
    //[n]
    public static double IND_CHANGE_DIRECTION_PROP = 0.3;
    //[n]
    public static double IND_CHANGE_SPEED_PROP = 0.5;
    //[n]
    public static final double PROPABILITY_OF_CREATING_SEEK_IND = 0.1;
    //[m/s]
    public static final double MAXIMUM_SPEED = 2.5;
    //[m]
    public static final double MAXIMUM_MOVE_VECTOR_LENGTH = SimulationBoard.MAXIMUM_SPEED / SimulationBoard.FRAME_PER_SECOND;
    //[s]
    public static final double TIME_TO_GET_SEEK = 3;
    //[s]
    public static final int MAX_SEEK_TIME = 30;
    //[s]
    public static final int MIN_SEEK_TIME = 20;
    //[m]
    public static double INFECTION_DISTANCE = 2.0;
    //[n]
    public static double EXITING_BOARD_PROP = 0.5;
    //[n]
    public static int CHANGE_DIRECTION_FREQ = 25;
    //[n]
    public static int CHANGE_SPEED_FREQ = 25;
    //[n]
    public static final double GET_INFECTED_FROM_NO_SYMPT_IND = 0.5;
    //[n]
    public static final double GETTING_SEEK_WITH_SYMPTOMS = 1.0;

    //SYMULATION ATTRIBUTES
    //[n]
    public static final int UNDO_STEP_FREQ = 100;
    //[n]
    public static final int FRAME_PER_SECOND = 25;
    //[m]
    public static final double WIDTH = 50;
    //[m]
    public static final double HEIGTH = 50;
    //[s]
    public static double time;
    //[n]
    public static int step = 0;
    //[n]
    private int  addedInd = 0;

    private List<Individual> individuals;

    public SimulationBoard()
    {
        individuals = new LinkedList<>();
        for(int i = 0; i < IND_NUMBER; i++)
        {
            Individual individual = new Individual(i, randomX(), randomY(), new Healthy());
            individuals.add(individual);
        }
    }

    private double randomX()
    {
        return Math.random() * WIDTH;
    }

    private double randomY()
    {
        return Math.random() * HEIGTH;
    }

    public void next()
    {
        if(step % FRAME_PER_SECOND == 0)
            addNewIndividuals(SimulationBoard.NEW_IND_PER_SECOND);

        individuals.removeIf(individual ->  ! isInsideBoard(individual));
        for(Individual ind : individuals)
        {
            ind.setTrackedIndividuals(getTrackedIndividuals(ind));
            ind.preparMove();
            ind.move();
        }

        step++;
        time += (1.0 / FRAME_PER_SECOND);
    }

    public static boolean isInsideBoard(Individual ind)
    {
        if(ind.getX() < 0 || ind.getY() < 0)
            return false;
        if(ind.getX() > WIDTH || ind.getY() > HEIGTH)
            return false;
        return true;
    }

    private void addNewIndividuals(int num)
    {
        int i = 0;
        while(individuals.size() < MAXIMUM_NUMBER_OF_INDIV && i < num && addedInd < MAX_ADDED_IND)
        {
            double seek = Math.random();

            State state;
            if(seek < SimulationBoard.PROPABILITY_OF_CREATING_SEEK_IND)
                state = new InfectedWithSymptoms(SimulationBoard.time);
            else
                state = new Healthy();
            Point2D point = getInitCoord();
            Individual individual = new Individual(individuals.size() - 1, point.getX(), point.getY(), state);
            individuals.add(individual);
            addedInd++;
        }
    }

    private List<Individual> getTrackedIndividuals(Individual ind)
    {
        List<Individual> list  = new LinkedList<Individual>();
        for(Individual tmp: individuals)
        {
            if(tmp != ind && ind.distance(tmp) <= SimulationBoard.INFECTION_DISTANCE)
                list.add(tmp);

        }
        return list;
    }

    public List<Individual> getIndividuals()
    {
        return individuals;
    }

    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        for(Individual individual : individuals)
            builder.append(individual.toString());
        return builder.toString();
    }

    public Point2D.Double getInitCoord()
    {
        double x = 0;
        double y = 0;
        Random random = new Random();
        switch (random.nextInt(4)) {
            case 0 -> {
                x = randomX();
                y = 0;
            }
            case 1 -> {
                x = WIDTH;
                y = randomY();
            }
            case 2 -> {
                x = randomX();
                y = HEIGTH;
            }
            case 3 -> {
                x = 0;
                y = randomY();
            }
        }

        return new Point2D.Double(x, y);
    }

    public Snaphshot save()
    {
        return new Snaphshot(individuals, time, step);
    }

    public void restore(Snaphshot snaphshot)
    {
        this.individuals = snaphshot.getIdividuals();
        step = snaphshot.getStep();
        time = snaphshot.getTime();
    }

    public static class Snaphshot
    {
        private final List<Individual> individualsM;
        private final double time;
        private final int step;

        private Snaphshot(List<Individual> individuals, double time, int step)
        {
            this.individualsM = new LinkedList<>();
            for(Individual ind : individuals)
                this.individualsM.add(ind.copy());
            this.time = time;
            this.step = step;
        }

        private List<Individual> getIdividuals()
        {
            return individualsM;
        }

        public double getTime()
        {
            return time;
        }

        public int getStep()
        {
            return step;
        }
    }
}
