package edu.yale.max.environment;

import edu.yale.max.cell.AbstractCell;

import java.awt.Graphics2D;

public abstract class AbstractEnvironment {

	
	public AbstractEnvironment()
	{
		
		
	}
	
	abstract public void movingCell(AbstractCell cell);
	
	abstract public void isInBounds(int x, int y);
	
	
	abstract public void drawBackground(Graphics2D g2);
	abstract public void drawForeground(Graphics2D g2);
}
