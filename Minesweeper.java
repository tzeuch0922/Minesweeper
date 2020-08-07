import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

public class Minesweeper extends JFrame
{
	private JMenuBar menu;
	private JMenu file;
	private JMenuItem newButton = new JMenuItem("New");
	private JMenuItem save = new JMenuItem("Save");
	private JMenuItem load = new JMenuItem("Load");
	private JMenuItem quit = new JMenuItem("Quit");
	private Minesweeper test;
	private MinesweeperPanel panel;
	
	public static void main(String[] args)
	{
		Minesweeper test = new Minesweeper();
	}
	public Minesweeper()
	{
		panel = new MinesweeperPanel();
		setTitle("Minesweeper");
		setSize(new Dimension(30*panel.getWidthVariable()+7,30*panel.getHeightVariable()+159));
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
		add(panel,BorderLayout.CENTER);
		menu = new JMenuBar();
		file = new JMenu("File");
		file.add(newButton);
		newButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				reset();
			}
		});
		file.add(save);
		save.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				panel.save();
			}
		});
		file.add(load);
		load.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				panel.load();
				setSize(new Dimension(30*panel.getWidthVariable()+7,30*panel.getHeightVariable()+159));
			}
		});
		file.add(quit);
		quit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				panel.quit();
			}
		});
		menu.add(file);
		add(menu, BorderLayout.NORTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	public void reset()
	{
		dispose();
		test = new Minesweeper();
	}
}