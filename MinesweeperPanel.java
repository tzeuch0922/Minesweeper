import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class MinesweeperPanel extends JPanel
{
	private String[] possibleValues = {"Beginner", "Intermediate", "Expert"};
	private Object choice = JOptionPane.showInputDialog(null, "Choose the Dificulty", "Input", JOptionPane.INFORMATION_MESSAGE,  null, possibleValues, possibleValues[0]);
	private String difficultyChoice = (""+choice);
	private MinesweeperGame gm = new MinesweeperGame(difficultyChoice);
	private int[][] board = gm.getBoard();
	private boolean[][] revealed = gm.getRevealed();
	private boolean[][] flagged = gm.getFlagged();
	private String[] messages = gm.getMessages();

	public MinesweeperPanel()
	{
		repaint();
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(new Color(0,0,0));
		setBackground(Color.white);
		revealed = gm.getRevealed();
		flagged = gm.getFlagged();
		System.out.println(gm.getWidth()+", "+gm.getHeight());
		gm.printBoard();
		gm.printTF(gm.getRevealed());
		int minesLeft = gm.getMinesLeft();
		for(int i = 0; i<gm.getWidth(); i++)
		{
			for(int j = 0; j<gm.getHeight(); j++)
			{
				g.drawRect(30*i,30*j+100,30,30);
				g.drawRect(30*i+4,30*j+104,22,22);
				g.drawLine(30*i,30*j+100,30*i+4,30*j+104);
				g.drawLine(30*i+30,30*j+130,30*i+26,30*j+126);
				g.drawLine(30*i,30*j+130,30*i+4,30*j+126);
				g.drawLine(30*i+30,30*j+100,30*i+26,30*j+104);
			}
		}
		for(int i = 0; i<gm.getHeight(); i++)
		{
			for(int j = 0; j<gm.getWidth(); j++)
			{
				if(revealed[i][j])
				{
					if(board[i][j]==0)
					{
						g.setColor(new Color(230,230,230));
						g.fillRect(j*30,i*30+100,30,30);
						g.setColor(Color.black);
						g.drawRect(j*30,i*30+100,30,30);
					}
					else if(board[i][j]==9)
					{
						g.setColor(Color.white);
						g.fillRect(j*30+4,i*30+104,22,22);
						g.setColor(Color.black);
						g.drawRect(j*30+4,i*30+104,22,22);
						g.setFont(new Font(Font.SERIF, Font.PLAIN, 12));
						g.drawString("*",30*j+14,30*i+118);
					}
					else
					{
						g.setColor(new Color(230,230,230));
						g.fillRect(j*30,i*30+100,30,30);
						g.setColor(Color.black);
						g.drawRect(j*30,i*30+100,30,30);
						g.setFont(new Font(Font.SERIF, Font.PLAIN, 12));
						g.drawString(""+board[i][j],30*j+14,30*i+120);
					}
				}
				else
				{
					if(flagged[i][j])
					{
						g.setFont(new Font(Font.SERIF, Font.PLAIN, 12));
						g.setColor(Color.black);
						g.drawString("F",30*j+14,30*i+118);
					}
				}
			}
		}
		g.setColor(Color.lightGray);
		g.fillRect(0,0,900,100);
		g.setColor(Color.white);
		g.fillRect(400,0,100,100);
		g.setColor(Color.black);
		g.drawRect(400,0,100,100);
		g.setFont(new Font(Font.SERIF, Font.PLAIN, 12));
		g.drawString("Mines Left:", 425, 15);
		g.setColor(Color.black);
		g.setFont(new Font(Font.SERIF, Font.BOLD, 100));
		g.drawString(""+minesLeft,400, 90);
	}
	public void leftClick(int x,int y)
	{
		mineCheck(gm.getBoard(),gm.getRevealed(),gm.getFlagged(),y,x);
	}
	public void rightClick(int x,int y)
	{
		flag(y,x,gm.getRevealed(),gm.getFlagged());
	}
	public void mineCheck(int[][] board, boolean[][] revealed, boolean[][] flagged, int x, int y)
	{
		if(revealed[x][y]||flagged[x][y])
		{
			return;
		}
		else
		{
			if(board[x][y]==9)
			{
				for(int i = 0; i<gm.getHeight(); i++)
				{
					for(int j = 0; j<gm.getWidth(); j++)
					{
						revealed[i][j]=true;
					}
				}
				repaint();
				gameLoss();
			}
			else if(board[x][y]==0)
			{
				revealed[x][y]=!revealed[x][y];
				if(x>0&&y>0)
				{
					mineCheck(board,revealed,flagged,x-1,y-1);
				}
				if(x>0)
				{
					mineCheck(board,revealed,flagged,x-1,y);
				}
				if(x>0&&y<gm.getWidth()-1)
				{
					mineCheck(board,revealed,flagged,x-1,y+1);
				}
				if(x<gm.getHeight()-1&&y>0)
				{
					mineCheck(board,revealed,flagged,x+1,y-1);
				}
				if(x<gm.getHeight()-1)
				{
					mineCheck(board,revealed,flagged,x+1,y);
				}
				if(x<gm.getHeight()-1&&y<gm.getWidth()-1)
				{
					mineCheck(board,revealed,flagged,x+1,y+1);
				}
				if(y>0)
				{
					mineCheck(board,revealed,flagged,x,y-1);
				}
				if(y<gm.getWidth()-1)
				{
					mineCheck(board,revealed,flagged,x,y+1);
				}
				repaint();
			}
			else
			{
				revealed[x][y]=!revealed[x][y];
				repaint();
			}
			
		}
		int k = 0;
		for(int i = 0; i<gm.getHeight(); i++)
		{
			for(int j = 0; j<gm.getWidth(); j++)
			{
				if(revealed[x][y])
				{
					k++;
				}
			}
		}
		if(k==gm.getWidth()*gm.getHeight()-gm.getMinesLeft())
		{
			gameWin();
		}
	}
	public void flag(int x, int y, boolean[][] revealed, boolean[][] flagged)
	{
		if(revealed[x][y])
		{
			return;
		}
		else
		{
			if(flagged[x][y])
			{
				flagged[x][y]=!flagged[x][y];
				gm.mineCount(1);
				repaint();
			}
			else
			{
				flagged[x][y]=!flagged[x][y];
				gm.mineCount(-1);
				repaint();
			}
		}
	}
	public void gameWin()
	{
		int z = (int)(Math.random()*2);
		JOptionPane.showMessageDialog(null,messages[z]);
		System.exit(0);
	}
	public void gameLoss()
	{
		int z = (int)(Math.random()*3)+2;
		JOptionPane.showMessageDialog(null,messages[z]);
		System.exit(0);
	}
	public int getWidth()
	{
		return gm.getWidth();
	}
	public int getHeight()
	{
		return gm.getHeight();
	}
}