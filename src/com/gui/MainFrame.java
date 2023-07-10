package com.gui;

import com.board.SimulationBoard;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame
{
    private final BoardView board;
    private final JButton undoButton = new JButton("⎌");
    private final JButton pauseButton = new JButton("⏸");
    private final JButton playButton = new JButton("⏵");

    public MainFrame(SimulationBoard simulationBoard)
    {
        board = new BoardView(simulationBoard);

        setMinimumSize( new Dimension(BoardView.WIDTH + 20, BoardView.HEIGTH + 150));
        setMaximumSize(new Dimension(BoardView.WIDTH + 100, BoardView.HEIGTH + 200));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(getUpperMenuPanel(), BorderLayout.NORTH);
        add(board, BorderLayout.CENTER);
        setVisible(true);
    }

    public static void main(String[] args)
    {
        SimulationBoard board = new SimulationBoard();
        MainFrame mainFrame = new MainFrame(board);
    }

    public JPanel getUpperMenuPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        undoButton.setFont(new Font(undoButton.getFont().getFontName(), Font.PLAIN, 30));
        pauseButton.setFont(new Font(undoButton.getFont().getFontName(), Font.PLAIN, 30));
        playButton.setFont(new Font(undoButton.getFont().getFontName(), Font.PLAIN, 30));

        undoButton.setOpaque(false);
        pauseButton.setOpaque(false);
        playButton.setOpaque(false);

        undoButton.setBackground(Color.gray);
        pauseButton.setBackground(Color.gray);
        playButton.setBackground(Color.gray);

        undoButton.setPreferredSize(new Dimension(100, 60));
        undoButton.setMinimumSize(new Dimension(100, 60));
        pauseButton.setPreferredSize(new Dimension(100, 60));
        pauseButton.setMinimumSize(new Dimension(100, 60));
        playButton.setPreferredSize(new Dimension(100, 60));
        playButton.setMinimumSize(new Dimension(100, 60));

        panel.add(undoButton);
        panel.add(Box.createRigidArea(new Dimension(30, 0)));
        panel.add(pauseButton);
        panel.add(Box.createRigidArea(new Dimension(30, 0)));
        panel.add(playButton);

        playButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                board.start();
            }
        });

        pauseButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                board.stop();
            }
        });

        undoButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                board.stop();
                board.undo();
                board.start();
            }
        });

        return panel;
    }
}
