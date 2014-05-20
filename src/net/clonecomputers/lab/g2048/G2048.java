package net.clonecomputers.lab.g2048;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

public class G2048 extends JPanel implements Runnable {
	int[][] numbers = new int[4][4];
	JLabel[][] labels = new JLabel[numbers.length][numbers[0].length];
	public static void main(String[] args) {
		G2048 g2048 = new G2048();
		JFrame window = new JFrame("2048");
		window.setContentPane(g2048);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setVisible(true);
		g2048.run();
	}
	
	@Override
	public void run() {
		this.setLayout(new GridLayout(numbers.length, numbers[0].length));
		for(int x = 0; x < numbers.length; x++) for(int y = 0; y < numbers[x].length; y++) {
			numbers[x][y] = 0;
			labels[x][y] = new JLabel(Integer.toString(numbers[x][y]));
			labels[x][y].setMinimumSize(new Dimension(40,40));
			labels[x][y].setPreferredSize(new Dimension(40,40));
			labels[x][y].setAlignmentX(JLabel.CENTER_ALIGNMENT);
			labels[x][y].setAlignmentY(JLabel.CENTER_ALIGNMENT);
			Font f = new Font("Monospaced",Font.PLAIN,24);
			// make font centered
			labels[x][y].setFont(f);
			labels[x][y].setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}
		
		for(int y = numbers[0].length-1; y >= 0; y--) for(int x = 0; x < numbers.length; x++) this.add(labels[x][y]);
		
		KeyListener kl = new KeyAdapter() {
			@Override public void keyTyped(KeyEvent e) {
				switch(e.getKeyChar()) {
				case 'w': move(Dir.UP); break;
				case 'a': move(Dir.LEFT); break;
				case 's': move(Dir.DOWN); break;
				case 'd': move(Dir.RIGHT); break;
				}
			}
		};
		this.getParent().getParent().getParent().addKeyListener(kl);
		((JFrame)this.getParent().getParent().getParent()).pack();
		
		numbers[rand()][rand()] = 2;
		updateDisplay();
	}
	
	private int rand() {
		return (int)(Math.random()*numbers.length);
	}
	
	private void updateDisplay() {
		for(int i = 0; i < numbers.length; i++) for(int j= 0; j < numbers[i].length; j++) {
			labels[i][j].setText(Integer.toString(numbers[i][j]));
			labels[i][j].setBackground(new Color(0f,0f,1 - 1f/(numbers[i][j]+1)));
			labels[i][j].setForeground(new Color(0f,0f,1 - 1f/(numbers[i][j]+1)));
			labels[i][j].repaint();
		}
	}
	
	private void move(Dir d) {
		System.out.println("moving "+d);
		
		boolean moved = false;
		List<Integer> movedPoints = new ArrayList<Integer>();
		for(int x = 0; x < numbers.length; x++) for(int y = 0; y < numbers[x].length; y++) {
			if(movedPoints.contains(x + y*numbers.length)) continue;
			if(numbers[x][y] == 0) continue;
			if(numbers[x][y] == d.inDir(x,y,numbers)) {
				numbers[x+d.dx][y+d.dy] += numbers[x][y];
				numbers[x][y] = 0;
				movedPoints.add((x+d.dx) + (y+d.dy)*numbers.length);
				moved = true;
			} else if(d.inDir(x,y,numbers) == 0) {
				numbers[x+d.dx][y+d.dy] = numbers[x][y];
				numbers[x][y] = 0;
				movedPoints.add((x+d.dx) + (y+d.dy)*numbers.length);
				moved = true;
			}
		}
		if(moved) {
			int x,y;
			do {
				x = rand(); y = rand();
			} while(numbers[x][y] != 0);
			numbers[x][y] = Math.random() > .1? 2: 4;
		} else {
			JOptionPane.showMessageDialog(this, "You lose!");
		}
		
		updateDisplay();
	}
	
	private enum Dir {
		UP(0,1),
		DOWN(0,-1),
		LEFT(-1,0),
		RIGHT(1,0);
		
		public final int dx, dy;
		private Dir(int dx, int dy) {
			this.dx = dx;
			this.dy = dy;
		}
		
		private int inDir(int x, int y, int[][] numbers) {
			x += dx;
			y += dy;
			if(x < 0 || y < 0 || x >= numbers.length || y >= numbers[0].length) {
				return -1;
			} else {
				return numbers[x][y];
			}
		}
	}

}
