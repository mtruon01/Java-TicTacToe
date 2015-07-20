//Thong Truong - 0356860
//CS 391 - HW 3 Tic Tac Toe
package cs391hw3;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class cs391_hw3
{
    public static void main(String[] args)
    {
        //initialize the board
        Board game = new Board();

        //play the game
        game.play();
    }
}

class Board
{
    private JFrame game;
    private JPanel board;
    private JButton[][] buttons;
    private String previousMove = "O";
    private int turn = 0;

    public Board()
    {
        //initialize the window
        game = new JFrame("Tic Tac Toe");
        game.setSize(240,240);
        game.setResizable(false);
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //initialize the board
        board = new JPanel();
        board.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        board.setLayout(new GridLayout(3,3,0,0));

        //create listener
        ActionListener al = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                for(int i=0; i<buttons.length;i++)
                {
                    for(int j=0; j<buttons[i].length;j++)
                    {
                        if(buttons[i][j] == e.getSource())
                        {
                            turn++;
                            
                            //check previous move to decide X or O
                            if(previousMove.equals("X"))
                            {
                                buttons[i][j].setText("O");
                                previousMove = "O";
                                if(isWin(i,j,previousMove)) //if win
                                {
                                    setDisable();
                                    displayWinner(previousMove, false);
                                }
                                else if(turn == 9) //if tie
                                {
                                    displayWinner(previousMove, true);
                                }
                            }
                            else
                            {
                                buttons[i][j].setText("X");
                                previousMove = "X";
                                if(isWin(i,j,previousMove)) //if win
                                {
                                    setDisable();
                                    displayWinner(previousMove, false);
                                }
                                else if(turn == 9) //if tie
                                {
                                    displayWinner(previousMove, true);
                                }
                            }

                            //disable the clicked button
                            buttons[i][j].setEnabled(false);
                        }
                    }
                }
            }
        };

        //initialize buttons
        buttons = new JButton[3][3];
        
        for(int i=0; i<buttons.length;i++)
        {
            for(int j=0; j<buttons[i].length;j++)
            {
                buttons[i][j] = new JButton();
                buttons[i][j].setBackground(Color.white);
                buttons[i][j].setEnabled(true);
                buttons[i][j].setText("");
                buttons[i][j].addActionListener(al);
                board.add(buttons[i][j]);
            }
        }

        game.add(board);
    }

    public boolean isWin(int row, int column, String current)
    {
        //check for columns
        if(row==buttons.length-1) //if at the bottom
        {
            if(buttons[row-1][column].getText().equals(current) && buttons[row-2][column].getText().equals(current))
            {
                return true;
            }
        }

        if(row == 0) //if at the top
        {
            if(buttons[row+1][column].getText().equals(current) && buttons[row+2][column].getText().equals(current))
            {
                return true;
            }
        }

        if(row != 0 && row != buttons.length-1) //if in the middle
        {
            if(buttons[row - 1][column].getText().equals(current) && buttons[row + 1][column].getText().equals(current))
            {
                return true;
            }
        }

        //check for rows
        if(column == buttons[row].length-1) //if at the end edge
        {
            if(buttons[row][column-1].getText().equals(current) && buttons[row][column-2].getText().equals(current))
            {
                return true;
            }
        }

        if(column == 0) //if at the start edge
        {
            if(buttons[row][column+1].getText().equals(current) && buttons[row][column+2].getText().equals(current))
            {
                return true;
            }
        }

        if(column != 0 && column != buttons[row].length-1) //if in the middle
        {
            if(buttons[row][column-1].getText().equals(current) && buttons[row][column+1].getText().equals(current))
            {
                return true;
            }
        }

        //check diagonals
        if(row == buttons.length-1 && column == buttons[row].length-1) //if at the bottom right corner
        {
            if(buttons[row-1][column-1].getText().equals(current) && buttons[row-2][column-2].getText().equals(current))
            {
                return true;
            }
        }

        if(row == 0 && column == 0) //if at the top left corner
        {
            if(buttons[row+1][column+1].getText().equals(current) && buttons[row+2][column+2].getText().equals(current))
            {
                return true;
            }
        }

        if(row == buttons.length-1 && column == 0) //if at the bottom left corner
        {
            if(buttons[row-1][column+1].getText().equals(current) && buttons[row-2][column+2].getText().equals(current))
            {
                return true;
            }
        }

        if(row == 0 && column == buttons[row].length-1) //if at the top right corner
        {
            if(buttons[row+1][column-1].getText().equals(current) && buttons[row+2][column-2].getText().equals(current))
            {
                return true;
            }
        }

        else if(row!=0 && column!=0 && row!=buttons.length-1 && column!=buttons[row].length-1) //if in the middle
        {
            if(buttons[row - 1][column - 1].getText().equals(current) && buttons[row + 1][column + 1].getText().equals(current))
            {
                return true;
            }

            if(buttons[row - 1][column + 1].getText().equals(current) && buttons[row + 1][column - 1].getText().equals(current))
            {
                return true;
            }
        }

        return false;
        
    }

    public void displayWinner(String current, boolean tie)
    {
        JFrame jf = new JFrame("Tic Tac Toe");
        jf.setSize(300,100);
        jf.setResizable(false);
        jf.setVisible(true);

        JPanel jp = new JPanel();
        jp.setLayout(new BorderLayout());

        JLabel jl;
        if(!tie) //if there is a winner
        {
            jl = new JLabel("Congratulations! Player " + current + " is the winner!");
        }
        else //if the game is tie
        {
            jl = new JLabel("Both players are tie!");
        }

        jl.setHorizontalAlignment(JLabel.CENTER);
        jp.add(jl, BorderLayout.CENTER);
        jf.add(jp);
        jf.setAlwaysOnTop(true);        
    }

    public void setDisable() //disable the whole board when there is a winner
    {
        for(int i=0;i<buttons.length;i++)
        {
            for(int j=0;j<buttons[i].length;j++)
            {
                buttons[i][j].setEnabled(false);
            }
        }
    }

    public void play()
    {
        game.setVisible(true);
    }
}