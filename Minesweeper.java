import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

public class Minesweeper extends JFrame
{
	public static void main(String[] args)
	{
		Minesweeper test = new Minesweeper();
	}
	public Minesweeper()
	{
		MinesweeperPanel panel = new MinesweeperPanel();
		setTitle("Minesweeper");
		setSize(new Dimension(30*panel.getWidth()+7,30*panel.getHeight()+129));
		setResizable(false);
		panel.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				if(e.getY()>=100)
				{
					int x = e.getX()/30;
					int y = (e.getY()-100)/30;
					if(e.getButton()==1)
					{
						panel.leftClick(x,y);
					}
					else if(e.getButton()==3)
					{
						panel.rightClick(x,y);
					}
				}
			}
		});
		add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}