package com.gui;

import com.board.SnapshotManager;
import com.board.SimulationBoard;
import com.individual.Individual;

import javax.swing.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class BoardView extends JPanel implements ActionListener
{
    public static final int WIDTH = 500;
    public static final int HEIGTH = 500;
    private static final int DELAY = 40;
    private final SimulationBoard board;
    private final Timer timer = new Timer(DELAY, this);
    private final SnapshotManager snapshotManager;

    public BoardView(SimulationBoard board)
    {
        this.board = board;
        this.snapshotManager = new SnapshotManager(board);
    }

    public void start()
    {
        timer.start();
    }
    public void stop()
    {
        timer.stop();
    }

    public void undo()
    {
        if(SimulationBoard.step > SimulationBoard.UNDO_STEP_FREQ)
            snapshotManager.undo();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        List<Individual> list = board.getIndividuals();
        for(Individual ind : list)
        {
            double xScale = WIDTH / SimulationBoard.WIDTH;
            double x = ind.getX() * xScale;
            double yScale = HEIGTH / SimulationBoard.HEIGTH;
            double y = ind.getY() * yScale;
            if(ind.getActualState().isHealthy())
                g.setColor(Color.GREEN);
            else if(ind.getActualState().isInfected())
                g.setColor(Color.RED);
            else if(ind.getActualState().isResistance())
                g.setColor(Color.BLUE);

            g.fillOval((int) x, (int) y, (int) (1 * xScale), (int) (1 * yScale));
        }

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        board.next();

        if(SimulationBoard.step % SimulationBoard.UNDO_STEP_FREQ == 0)
            snapshotManager.save(board.save());

        repaint();
    }

}