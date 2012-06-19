package edu.yale.max.display;


import edu.yale.max.cell.AbstractCell;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Point;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Font;

import java.util.Vector;

public class MotorCurvePanel extends JPanel {

	AbstractCell cell;
	int [] motorCurveX;
	int [] motorCurveY;
	int numOfPoints;
	
	public MotorCurvePanel(AbstractCell cell, int width)
	{
		
		this.cell = cell;
		this.numOfPoints = 80;
		
		
		this.setPreferredSize(new Dimension(width,200));
		
		this.setPlotParameters(30, 30, 200, 130, 0f, -0.1f, 6f, 1.1f);
		
		getMotorCurveValues();
		
	}
	
	
	protected void getMotorCurveValues()
	{
		Vector <Vector<Float>> curve = cell.getMotorCurve(0, 6, numOfPoints);
		Vector <Float> x = curve.get(0);
		Vector <Float> y = curve.get(1);
		motorCurveX = new int [numOfPoints];
		motorCurveY = new int [numOfPoints];
		for(int i=0; i<x.size(); i++)
		{
			Point p = this.getAbsoluteCoordinates(x.get(i).floatValue(), y.get(i).floatValue());
			motorCurveX[i] = p.x;
			motorCurveY[i] = p.y;
			System.out.println(x.get(i) + "  " + y.get(i));
		}
	}
	
	protected void paintComponent( Graphics graphics )
	{
		super.paintComponent(graphics);
		drawPanel(graphics);
	}
	
	
	private boolean drawPanel(Graphics graphics)
	{
	    Graphics2D g2 = (Graphics2D) graphics;
	    g2.clearRect(0, 0, this.getWidth(), this.getHeight());
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        drawResponseCurve(g2);
        drawCurrentYp(g2);
        drawResponseCurvePlotArea(g2);
        
	    return true;
	}
	
	
	
	
	private void drawResponseCurve(Graphics2D g2)
	{
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(1f));
		g2.drawPolyline(this.motorCurveX, this.motorCurveY, numOfPoints);
	}
	
	
	private void drawCurrentYp(Graphics2D g2)
	{
		//Set the color and get the basic information
		g2.setColor(Color.RED);
		float xVal = this.cell.getYpConc();
		float yVal = this.cell.getCWbias();
		Point p = this.getAbsoluteCoordinates(xVal, yVal);
		Point p0 = this.getAbsoluteCoordinates(minXval, minYval);
		
		//Draw the point
		int pointSize = 10;
		g2.setStroke(new BasicStroke(2.0f));
		g2.fillOval(p.x-(pointSize/2), p.y-(pointSize/2), pointSize, pointSize);
		
		//Draw the lines
		float dash[] = { 5.0f };
		BasicStroke s = new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
				10.0f, dash, 0.0f);
		g2.setStroke(s);
		g2.drawLine(p.x, p0.y, p.x, p.y);
		g2.drawLine(p0.x, p.y, p.x, p.y);
	}
	
	private void drawResponseCurvePlotArea(Graphics2D g2)
	{
		int labelHeightOffset = 6;
		
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(2.0f));
		g2.drawRoundRect(xOffset, yOffset, plotWidth, plotHeight, 15, 15);
		
		Point py0 = this.getAbsoluteCoordinates(0f, 0f);
		Point py05 = this.getAbsoluteCoordinates(0f, 0.5f);
		Point py1 = this.getAbsoluteCoordinates(0f, 1f);
		
		int yLabelXval = 5;
		
		g2.setFont(new Font("SansSerif", Font.BOLD, 16));
		g2.drawString("   0", yLabelXval, py0.y+labelHeightOffset);
		g2.drawString("0.5", yLabelXval, py05.y+labelHeightOffset);
		g2.drawString("   1", yLabelXval, py1.y+labelHeightOffset);
		
		int xTitle = 40;
		int yTitle = 22;
		
		g2.drawString("Motor Response Curve", xTitle, yTitle);
		
		Point px1 = this.getAbsoluteCoordinates(1f,0f);
		Point px2 = this.getAbsoluteCoordinates(2f,0f);
		Point px3 = this.getAbsoluteCoordinates(3f,0f);
		Point px4 = this.getAbsoluteCoordinates(4f,0f);
		Point px5 = this.getAbsoluteCoordinates(5f,0f);
		
		int xLabelYval = this.xOffset+this.plotHeight + 18;
		int xLabelOffset = 3;
		
		g2.drawString("1",  px1.x-xLabelOffset, xLabelYval);
		g2.drawString("2",  px2.x-xLabelOffset, xLabelYval);
		g2.drawString("3",  px3.x-xLabelOffset, xLabelYval);
		g2.drawString("4",  px4.x-xLabelOffset, xLabelYval);
		g2.drawString("5",  px5.x-xLabelOffset, xLabelYval);
		
	}
	
	
	
	private void setPlotParameters(int xOffset, int yOffset, int width, int height, float minX, float minY, float maxX, float maxY)
	{
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.plotWidth = width;
		this.plotHeight = height;
		this.minXval = minX;
		this.minYval = minY;
		this.maxXval = maxX;
		this.maxYval = maxY;
	}
	
	protected int xOffset;
	protected int yOffset;
	protected int plotWidth;
	protected int plotHeight;
	protected float minXval;
	protected float minYval;
	protected float maxXval;
	protected float maxYval;
	
	
	private Point getAbsoluteCoordinates(float x, float y)
	{
		if(x>maxXval || x<minXval) System.out.println("Uh OhX!!");
		if(y>maxYval || y<minYval) System.out.println("Uh OhY!!");
		
		float xFraction = (x-(minXval))/(maxXval - minXval);
		float yFraction = 1-((y-(minYval))/(maxYval-minYval));
		
		int xPixel = Math.round((plotWidth*xFraction))+xOffset;
		int yPixel = Math.round((plotHeight*yFraction))+yOffset;
		
		return new Point(xPixel,yPixel);
	}
	
	
	
	
}
