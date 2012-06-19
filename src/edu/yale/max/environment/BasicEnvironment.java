package edu.yale.max.environment;

import java.awt.Graphics2D;

import edu.yale.max.cell.AbstractCell;

import java.awt.Color;
import java.awt.BasicStroke;

public class BasicEnvironment extends AbstractEnvironment{

	
	protected int minX;
	protected int maxX;
	protected int minY;
	protected int maxY;
	
	protected int minBottleX;
	protected int maxBottleX;
	protected int minBottleY;
	protected int maxBottleY;
	
	protected int totalWidth;
	protected int totalHeight;
	
	protected int margin;
	protected int cellWidth;
	
	protected float borderStrokeWidth;
	
	public BasicEnvironment(int width, int height, float cellWidth)
	{
		this.cellWidth = Math.round(cellWidth/2);
		margin = 20;
		borderStrokeWidth = 6f;
		totalWidth = width;
		totalHeight = height;
		
		minX = margin+this.cellWidth;
		minBottleX = margin-(int)(borderStrokeWidth/2);
		maxX = width-margin-this.cellWidth;
		maxBottleX = width-margin+(int)(borderStrokeWidth/2);
		minY = margin+this.cellWidth;
		minBottleY = margin-(int)(borderStrokeWidth/2);
		maxY = height-margin-this.cellWidth;
		maxBottleY = height-margin+(int)(borderStrokeWidth/2);
	}
	
	public void movingCell(AbstractCell cell)
	{
		
		if(cell.getXpos()>=maxX || cell.getXpos()<=minX) cell.resetX();
		if(cell.getYpos()>=maxY || cell.getYpos()<=minY) cell.resetY();
	}
	
	public void isInBounds(int x, int y)
	{
		
		
	}
	
	
	public void drawBackground(Graphics2D g2)
	{
		
		
		
	}
	
	public void drawForeground(Graphics2D g2)
	{
		g2.setColor(new Color(0.3f, 0.3f, 0.3f));
		g2.setStroke(new BasicStroke(borderStrokeWidth));
		g2.fillRect(0, 0, totalWidth, minBottleY);
		g2.fillRect(0, 0, minBottleX, totalHeight);
		
		g2.fillRect(0, maxBottleY, totalWidth, minBottleY);
		g2.fillRect(maxBottleX, 0, minBottleX, totalHeight);
		
		
		g2.setColor(Color.WHITE);
		g2.setStroke(new BasicStroke(5f));
		g2.drawRoundRect(minBottleX, minBottleY, maxBottleX-minBottleX, maxBottleY-minBottleY, 15, 15);
		
	}
	
	
	
	
	
}
