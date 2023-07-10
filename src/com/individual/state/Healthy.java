package com.individual.state;

import com.board.SimulationBoard;
import com.individual.Contact;
import com.individual.Individual;
import java.util.LinkedList;
import java.util.List;

public class Healthy extends BasicState implements State
{
    private List<Contact> contactedInd;

    public Healthy()
    {
        contactedInd = new LinkedList<Contact>();
    }

    @Override
    public void prepearMove()
    {
        updateConntactStat();
        if(isGettingSeek())
        {
            if(Math.random() < SimulationBoard.GETTING_SEEK_WITH_SYMPTOMS)
                individual.setNextState(new InfectedWithSymptoms(SimulationBoard.time));
            else
                individual.setNextState(new InfectedWithoutSymptoms(SimulationBoard.time));

        }
        else
            individual.setNextState(individual.getActualState());

    }

    private void updateConntactStat()
    {
        List<Individual> trackedInd = individual.getTracktedIndividuals();
        for(Contact c : contactedInd)
            c.setToDelete();

        for(Individual individual : trackedInd)
        {
            Contact conntact = notifiedContact(individual);
            if(conntact != null)
            {
                conntact.addTime(1.0 / SimulationBoard.FRAME_PER_SECOND);
                conntact.setToSave();
            }
            else
            {
                conntact = new Contact(individual);
                contactedInd.add(conntact);
                conntact.setToSave();
            }
        }
        contactedInd.removeIf(Contact::delete);
    }

    private Contact notifiedContact(Individual individual)
    {
        for(Contact c : contactedInd)
            if(c.represents(individual))
                return c;

        return null;
    }

    private boolean isGettingSeek()
    {
        for(Contact c : contactedInd)
            if(c.getTime() >= SimulationBoard.TIME_TO_GET_SEEK)
            {
                Individual ind = c.getIndividual();
                if(ind.getActualState() instanceof InfectedWithoutSymptoms)
                {
                    if(Math.random() < SimulationBoard.GET_INFECTED_FROM_NO_SYMPT_IND)
                        return true;
                }
                else if(ind.getActualState() instanceof  InfectedWithSymptoms)
                    return true;
            }

        return false;
    }

    public boolean isHealthy()
    {
        return true;
    }
}
