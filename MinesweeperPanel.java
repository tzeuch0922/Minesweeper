import javax.swing.*;
import java.awt.*;
import java.io.*;

public class MinesweeperPanel extends JPanel
{
	private String[] possibleValues = { "Beginner", "Intermediate", "Expert" };
	private Object choice = JOptionPane.showInputDialog(null, "Choose the Dificulty", "Input", JOptionPane.INFORMATION_MESSAGE,  null, possibleValues, possibleValues[0]);
	private String difficultyChoice = (""+choice);
	private MinesweeperGame gm = new MinesweeperGame(difficultyChoice);
	private int[][] board = gm.getBoard();
	private boolean[][] revealed = gm.getRevealed();
	private boolean[][] flagged = gm.getFlagged();
	private String winMessage = gm.getWinMessage();
	private String lossMessage = gm.getLossMessage();
	private int minesLeft = gm.getMinesLeft();
	private String difficulty = gm.getDifficulty();
	
	public MinesweeperPanel()
	{
		repaint();
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		revealed = gm.getRevealed();
		flagged = gm.getFlagged();
		g.setColor(new Color(0,0,0));
		minesLeft = gm.getMinesLeft();
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
		g.fillRect(0,0,(30*gm.getWidth()),100);
		g.setColor(Color.white);
		g.fillRect((30*gm.getWidth())/2-50,0,100,100);
		g.setColor(Color.black);
		g.drawRect((30*gm.getWidth())/2-50,0,100,100);
		g.setFont(new Font(Font.SERIF, Font.PLAIN, 12));
		g.drawString("Mines Left:", (30*gm.getWidth())/2-25, 15);
		g.setColor(Color.black);
		g.setFont(new Font(Font.SERIF, Font.BOLD, 100));
		g.drawString(""+minesLeft,(30*gm.getWidth())/2-50, 90);
		
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
				if(revealed[i][j])
				{
					k++;
				}
			}
		}
		if(k==gm.getWidth()*gm.getHeight()-gm.getMines())
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
		JOptionPane.showMessageDialog(null,winMessage);
		System.exit(0);
	}
	public void gameLoss()
	{
		JOptionPane.showMessageDialog(null,lossMessage);
		System.exit(0);
	}
	public int getWidthVariable()
	{
		return gm.getWidth();
	}
	public int getHeightVariable()
	{
		return gm.getHeight();
	}
	public void save()
	{
		String boardPlaces = "";
		for(int i = 0; i<gm.getHeight(); i++)
		{
			for(int j = 0; j<gm.getWidth(); j++)
			{
				boardPlaces += board[i][j] + "";
			}
		}
		String revealedPlaces = "";
		for(int i = 0; i<gm.getHeight(); i++)
		{
			for(int j = 0; j<gm.getWidth(); j++)
			{
				if(revealed[i][j])
				{
					revealedPlaces += "t";
				}
				else
				{
					revealedPlaces += "f";
				}
			}
		}
		String flaggedPlaces = "";
		for(int i = 0; i<gm.getHeight(); i++)
		{
			for(int j = 0; j<gm.getWidth(); j++)
			{
				if(flagged[i][j])
				{
					flaggedPlaces += "t";
				}
				else
				{
					flaggedPlaces += "f";
				}
			}
		}
		String outputFileName = JOptionPane.showInputDialog("Name the save file. (If you want to save it somewhere besides the folder where this game is stored, then type in the filepath.)");
		if(outputFileName.indexOf(".txt")!=outputFileName.length()-3)
		{
			outputFileName = outputFileName + ".txt";
		}
		try
		{
			FileWriter fileWriter = new FileWriter(outputFileName);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(difficulty);
			bufferedWriter.newLine();
			bufferedWriter.write("" + gm.getMinesLeft());
			bufferedWriter.newLine();
			bufferedWriter.write(boardPlaces);
			bufferedWriter.newLine();
			bufferedWriter.write(revealedPlaces);
			bufferedWriter.newLine();
			bufferedWriter.write(flaggedPlaces);
			bufferedWriter.close();
			fileWriter.close();
		}
		catch(IOException ex)
		{
			System.out.println("Could not write to " + outputFileName + ".txt");
		}
	}
	public void load()
	{
		try
		{
			String inputFileName = JOptionPane.showInputDialog("What is the name of the file do you want to load?");
			FileReader fileReader = new FileReader(inputFileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line = bufferedReader.readLine();
			gm.setDifficulty(line);
			line = bufferedReader.readLine();
			gm.setMineNum(Integer.parseInt(line));
			int index = 0;
			line = bufferedReader.readLine();
			for(int i = 0; i<gm.getHeight();i++)
			{
				for(int j = 0; j<gm.getWidth();j++)
				{
					board[i][j] = Integer.parseInt(line.substring(index, index+1));
					index++;
				}
			}
			line = bufferedReader.readLine();
			index = 0;
			for(int i = 0; i<gm.getHeight();i++)
			{
				for(int j = 0; j<gm.getWidth();j++)
				{
					if(line.substring(index,index+1).equals("t"))
					{
						revealed[i][j]=true;
					}
					else
					{
						revealed[i][j]=false;
					}
					index++;
				}
			}
			line = bufferedReader.readLine();
			index = 0;
			for(int i = 0; i<gm.getHeight();i++)
			{
				for(int j = 0; j<gm.getWidth();j++)
				{
					if(line.substring(index,index+1).equals("t"))
					{
						flagged[i][j]=true;
					}
					else
					{
						flagged[i][j]=false;
					}
					index++;
				}
			}
			bufferedReader.close();
			repaint();
		}
		catch(FileNotFoundException e)
		{
			JOptionPane.showMessageDialog(null, "File not found.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		catch(IOException e)
		{
			JOptionPane.showMessageDialog(null, "This is not a Minesweeper File.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	public void quit()
	{
		System.exit(0);
	}
}