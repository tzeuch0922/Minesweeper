public class MinesweeperGame
{
	private int[][] board;
	private boolean[][] revealed;
	private boolean[][] flagged;
	private int minesLeft;
	public String winMessage;
	public String lossMessage;
	private int height;
	private int width;
	public String difficulty;
	private int mines;
	
	public MinesweeperGame(String difficultyChoice)
	{
		difficulty = difficultyChoice;
		if(difficulty.equals("Beginner"))
		{
			height = 8;
			width = 8;
			minesLeft = 10;
			mines = 10;
		}
		else if(difficulty.equals("Intermediate"))
		{
			height = 16;
			width = 16;
			minesLeft = 40;
			mines = 40;
		}
		else if(difficulty.equals("Expert"))
		{
			height = 16;
			width = 30;
			minesLeft = 99;
			mines = 99;
		}
		else
		{
			System.exit(0);
		}
		revealed = new boolean[height][width];
		flagged = new boolean[height][width];
		winMessage = "Congratulation, you win!";
		lossMessage = "Better luck next time.";
		for(int i = 0; i<height; i++)
		{
			for(int j = 0; j<width; j++)
			{
				revealed[i][j]=false;
				flagged[i][j]=false;
			}
		}
		board = constructBoard();
	}
	public String getDifficulty()
	{
		return difficulty;
	}
	public int[][] getBoard()
	{
		return board;
	}
	public boolean[][] getRevealed()
	{
		return revealed;
	}
	public boolean[][] getFlagged()
	{
		return flagged;
	}
	public int getMinesLeft()
	{
		return minesLeft;
	}
	public int getMines()
	{
		return mines;
	}
	public int[][] constructBoard()
	{
		int[][] temp = new int[height][width];
		int x;
		int y;
		for(int i = 0; i<minesLeft; i++)
		{
			x=(int)(Math.random()*height);
			y=(int)(Math.random()*width);
			while(temp[x][y]==9)
			{
				x=(int)(Math.random()*height);
				y=(int)(Math.random()*width);
			}
			temp[x][y]=9;
		}
		boardValues(temp);
		return temp;
	}
	public int getHeight()
	{
		return height;
	}
	public int getWidth()
	{
		return width;
	}
	public String getWinMessage()
	{
		return winMessage;
	}
	public String getLossMessage()
	{
		return lossMessage;
	}
	public void boardValues(int[][] temp)
	{
		for(int i=0; i<height; i++)
		{
			for(int j=0; j<width; j++)
			{
				if(temp[i][j]!=9)
				{
					if(i>0&&j>0&&temp[i-1][j-1]==9)
					{
						temp[i][j]++;
					}
					if(i>0&&temp[i-1][j]==9)
					{
						temp[i][j]++;
					}
					if(i>0&&j<width-1&&temp[i-1][j+1]==9)
					{
						temp[i][j]++;
					}
					if(i<height-1&&j>0&&temp[i+1][j-1]==9)
					{
						temp[i][j]++;
					}
					if(i<height-1&&temp[i+1][j]==9)
					{
						temp[i][j]++;
					}
					if(i<height-1&&j<width-1&&temp[i+1][j+1]==9)
					{
						temp[i][j]++;
					}
					if(j>0&&temp[i][j-1]==9)
					{
						temp[i][j]++;
					}
					if(j<width-1&&temp[i][j+1]==9)
					{
						temp[i][j]++;
					}
				}
			}
		}
	}
	public void mineCount(int mineNum)
	{
		minesLeft+=mineNum;
	}
	public void setMineNum(int mines)
	{
		minesLeft = mines;
	}
	public void setDifficulty(String setting)
	{
		difficulty = setting;
		if(difficulty.equals("Beginner"))
		{
			height = 8;
			width = 8;
		}
		else if(difficulty.equals("Intermediate"))
		{
			height = 16;
			width = 16;
		}
		else if(difficulty.equals("Expert"))
		{
			height = 16;
			width = 30;
		}
	}
	public void setBoard(int[][] array)
	{
		board = array;
	}
	public void setFlagged(boolean[][] array)
	{
		flagged = array;
	}
	public void setRevealed(boolean[][] array)
	{
		revealed = array;
	}
}