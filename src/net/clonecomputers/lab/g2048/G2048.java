package net.clonecomputers.lab.g2048;

import java.awt.*;
import java.awt.event.*;

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
		for(int i = 0; i < numbers.length; i++) for(int j= 0; j < numbers[i].length; j++) {
			numbers[i][j] = 0;
			labels[i][j] = new JLabel(Integer.toString(numbers[i][j]));
			labels[i][j].setMinimumSize(new Dimension(40,40));
			labels[i][j].setPreferredSize(new Dimension(40,40));
			labels[i][j].setAlignmentX(JLabel.CENTER_ALIGNMENT);
			labels[i][j].setAlignmentY(JLabel.CENTER_ALIGNMENT);
			Font f = new Font("Monospaced",Font.PLAIN,24);
			// make font centered
			labels[i][j].setFont(f);
			labels[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
			this.add(labels[i][j]);
		}
		
		KeyListener kl = new KeyAdapter() {
			@Override public void keyTyped(KeyEvent e) {
				System.out.println("Got a keypress");
				System.out.println("it was "+e.getKeyChar());
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
	}

}
