package edu.yale.max.display;


import edu.yale.max.cell.AbstractCell;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JButton;






public class CheYcontrolPanel extends JPanel implements MouseListener {

	JButton decreaseYpButton;
	JButton increaseYpButton;
	AbstractCell cell;
	
	
	
	public CheYcontrolPanel(AbstractCell cell, int width)
	{
		this.setPreferredSize(new Dimension(width,100));
		this.setBackground(Color.WHITE);
		this.cell = cell;
		
		
		decreaseYpButton = new JButton("Lower Yp");
		decreaseYpButton.setActionCommand("decreaseYp");
		decreaseYpButton.setFocusable(false);
		decreaseYpButton.setPreferredSize(new Dimension(100,100));
		decreaseYpButton.addMouseListener(this);
		this.add(decreaseYpButton);
		
		increaseYpButton = new JButton("Raise Yp");
		increaseYpButton.setActionCommand("decreaseYp");
		increaseYpButton.setFocusable(false);
		increaseYpButton.setPreferredSize(new Dimension(100,100));
		increaseYpButton.addMouseListener(this);
		this.add(increaseYpButton);
		
	}
	
	public void mousePressed(MouseEvent e) {
		if(decreaseYpButton.equals(e.getComponent()) )
		{
			cell.setYpRateOfChange(-0.05f);
		}
		else if(increaseYpButton.equals(e.getComponent()) )
		{
			cell.setYpRateOfChange(0.05f);
		}
	}

	public void mouseReleased(MouseEvent e) {
		cell.setYpRateOfChange(0f);
	}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {
		cell.setYpRateOfChange(0f);
	}

	public void mouseClicked(MouseEvent e) {}
	


	
	
}
