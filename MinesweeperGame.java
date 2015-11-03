public class MinesweeperGame
{
	public int[][] board;
	public boolean[][] revealed;
	public boolean[][] flagged;
	public int minesLeft;
	public String[] messages;
	public int height;
	public int width;
	public String difficulty;
	
	/*public static void main(String[] args)
	{
		MinesweeperGame test = new MinesweeperGame();
		printBoard(test.board);
		printTF(test.revealed);
		printTF(test.flagged);
	}
	/*public MinesweeperGame()
	{
		board = constructBoard();
		revealed = new boolean[16][30];
		flagged = new boolean[16][30];
		messages = new String[5];
		messages[0] = "Congrats, you didn't blow up your foot this time!";
		messages[1] = "Good thing you made it this time, that wasn't a simulation.";
		messages[2] = "Would you like some ice for that third-degree burn?";
		messages[3] = "How does it feel to get a free amputation?";
		messages[4] = "That looks like it hurts, just walk it off... Oh, sorry.";
		for(int i = 0; i<16; i++)
		{
			for(int j = 0; j<30; j++)
			{
				revealed[i][j]=false;
				flagged[i][j]=false;
			}
		}
		minesLeft = 99;
	}*/
	public MinesweeperGame(String difficultyChoice)
	{
		difficulty = difficultyChoice;
		if(difficulty.equals("Beginner"))
		{
			height = 8;
			width = 8;
			minesLeft = 10;
		}
		else if(difficulty.equals("Intermediate"))
		{
			height = 16;
			width = 16;
			minesLeft = 40;
		}
		else if(difficulty.equals("Expert"))
		{
			height = 16;
			width = 30;
			minesLeft = 99;
		}
		System.out.println(difficulty+", "+height+", "+width);
		revealed = new boolean[height][width];
		flagged = new boolean[height][width];
		messages = new String[5];
		messages[0] = "Congrats, you didn't blow up your foot this time!";
		messages[1] = "Good thing you made it this time, that wasn't a simulation.";
		messages[2] = "Would you like some ice for that third-degree burn?";
		messages[3] = "How does it feel to get a free amputation?";
		messages[4] = "That looks like it hurts, just walk it off... Oh, sorry.";
		for(int i = 0; i<height; i++)
		{
			for(int j = 0; j<width; j++)
			{
				revealed[i][j]=false;
				flagged[i][j]=false;
			}
		}
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
	public String[] getMessages()
	{
		return messages;
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
	//Below method only for testing purposes.
	public void printBoard()
	{
		for(int i = 0; i<height; i++)
		{
			System.out.print("{ ");
			for(int j = 0; j<width; j++)
			{
				System.out.print(board[i][j] + " ");
			}
			System.out.print("}\n");
		}
	}
	public void printTF(boolean[][] tF)
	{
		for(int i = 0; i<height; i++)
		{
			System.out.print("{ ");
			for(int j = 0; j<width; j++)
			{
				if(tF[i][j])
				{
					System.out.print("t ");
				}
				else
				{
					System.out.print("f ");
				}
			}
			System.out.print("}\n");
		}
	}
}