package edu.yale.max.display;

import edu.yale.max.cell.*;
import edu.yale.max.environment.*;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import java.awt.color.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class MainDisplayPanel extends JPanel {

	AbstractCell cell;
	int animationState;  //The state of the animation, goes from 0 to maxAnimationState
	int animationPath;   //The animation path, if 1, animation state will increase by 1
	                     //next iteration, if 0 then animation state will decrease
	int maxAnimationState;
	int flagellaState; //Either 1 if we are in the first half of rotation, 0 on the other half
	
	
	AbstractEnvironment currentEnvironment;
	
	
	// The object we will use to write with instead of the standard screen graphics
    //Graphics2D bufferGraphics;
    // The image that will contain everything that has been drawn on
    // bufferGraphics.
    //Image offscreen; 
	
	
	
	public void initialize(AbstractCell cell, AbstractEnvironment e)
	{
		this.cell = cell;
		this.currentEnvironment = e;
		
		this.setBackground(Color.LIGHT_GRAY);
		
		animationState = 0;
		maxAnimationState = 3;
		animationPath=1;
		
		
		//offscreen = this.createImage(this.getWidth(),this.getHeight()); 
		//offscreen.getGraphics(); 
	}
	
	public void setNewEnvironment(AbstractEnvironment e)
	{
		this.currentEnvironment = e;
	}
	
	/**
	 * Paint the graphics of the panel.
	 */
	protected void paintComponent( Graphics graphics )
	{
		super.paintComponent(graphics);
		drawPanel(graphics);
	}
	
	
	
	
	private boolean drawPanel(Graphics graphics)
	{
	    Graphics2D g2 = (Graphics2D) graphics;
	    //g2.clearRect(0, 0, this.getWidth(), this.getHeight());
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        
        // First paint the environment backdrop
        this.currentEnvironment.drawBackground(g2);
        
        
        
        ////////////////////////////////
        //Draw Max!!
        ///////////////////////////////
        paintCell(g2, this.cell);
       
        
        
        // Now paint the environment foreground
        this.currentEnvironment.drawForeground(g2);
        

        
        //Update the animation state
        if(animationPath==1)
        	this.animationState++;
        else
        	this.animationState--;
        if(animationState>=maxAnimationState)
        	animationPath=0;
        if(animationState<=0)
        {
        	animationPath=1;
        	if(flagellaState==0)flagellaState=1;
        	else if(flagellaState==1)flagellaState=0;
        }
        
        
	    return true;
	}
	
	
	protected void paintCell(Graphics2D g2, AbstractCell cellToPaint)
	{
		 //Remember the previous transformation
        AffineTransform AT_beforeMax = g2.getTransform();
        
        //First get position and the graphic properties of the cell
        int xPos = Math.round(cellToPaint.getXpos()-(cellToPaint.getCellWidth()/2));
        int yPos = Math.round(cellToPaint.getYpos()-(cellToPaint.getCellHeight()/2));
        int height = Math.round(cellToPaint.getCellHeight());
        int width = Math.round(cellToPaint.getCellWidth());
        int arcHeight = Math.round(cellToPaint.getCellWidth()/2.7f);
        int arcWidth = width;
        
        //Get the rotation
        double rotAngle = Math.atan2(cellToPaint.getYorientation(),cellToPaint.getXorientation()) - Math.atan2(0,1);
    	g2.rotate(rotAngle, cellToPaint.getXpos(), cellToPaint.getYpos());
    	
        if(cellToPaint.getFlagellaState()==AbstractCell.COUNTERCLOCKWISE)
        {
        	animateSwimmingFlagella(g2, xPos, cellToPaint);
        } else if(cellToPaint.getFlagellaState()==AbstractCell.CLOCKWISE) {
        	animateFlailingFlagella(g2, xPos,yPos, cellToPaint);
        }
        
        //Shade in the cytoplasm
        g2.setColor(cellToPaint.getCytoplasmColor());
        g2.fillRoundRect(xPos, yPos, width, height, arcWidth, arcHeight);
        
        //Draw the cell wall
        g2.setStroke(cellToPaint.getCellWallStroke());
        g2.setColor(cellToPaint.getCellWallColor());
        g2.drawRoundRect(xPos, yPos, width, height, arcWidth, arcHeight);
        
        //Finally, reset the transformation
        g2.setTransform(AT_beforeMax);
	}
	
	
	protected void animateSwimmingFlagella(Graphics2D g2, int xPos, AbstractCell cellToPaint)
	{
		g2.setColor(cellToPaint.getFlagellaColor());
		g2.setStroke(cellToPaint.getFlagellaStroke());
		int flagellaX = xPos;
		int flagellaY = Math.round(cellToPaint.getYpos());


		int flagellaMaxHeight = Math.abs(Math.round(cellToPaint.getFlagellaLoopHeight() * ((float)animationState) / (float) maxAnimationState));
		int flagellaLoopWidth = cellToPaint.getFlagellaLoopLength();

		int i=0;
		for(;;i++)
		{
			if(i%2==flagellaState){
				g2.drawArc(flagellaX-flagellaLoopWidth, Math.round(flagellaY-(flagellaMaxHeight/2f)), 
						flagellaLoopWidth, flagellaMaxHeight, 0, 180);

			} else {
				g2.drawArc(flagellaX-flagellaLoopWidth, Math.round(flagellaY-(flagellaMaxHeight/2f)), 
						flagellaLoopWidth, flagellaMaxHeight, 0, -180);
			}
			flagellaX-=flagellaLoopWidth;
			if(Math.abs(xPos-flagellaX)>(1.2*cellToPaint.getFlagellaLength())) break;
		}
		if(i%2==flagellaState){
			g2.drawArc(flagellaX-flagellaLoopWidth, Math.round(flagellaY-(flagellaMaxHeight/2f)), 
					flagellaLoopWidth, flagellaMaxHeight, 0, -90);

		} else {
			g2.drawArc(flagellaX-flagellaLoopWidth, Math.round(flagellaY-(flagellaMaxHeight/2f)), 
					flagellaLoopWidth, flagellaMaxHeight, 0, 90);
		}
	}
	
	
	protected void animateFlailingFlagella(Graphics2D g2, int xPos, int yPos, AbstractCell cellToPaint)
	{
		g2.setColor(cellToPaint.getFlagellaColor());
		g2.setStroke(cellToPaint.getFlagellaStroke());
		int flagellaX = xPos;
		int flagellaY = Math.round(cellToPaint.getYpos());

		float randValue = ((float)Math.random()*2f)+cellToPaint.getFlagellaLoopLength();
		int flagellaMaxHeight = Math.round(cellToPaint.getFlagellaLoopHeight()*randValue);
		int flagellaLoopWidth = Math.round(cellToPaint.getFlagellaLoopLength()*randValue);

		int i=0;
		int spinState = Math.round((float)Math.random());
		for(;i<20;i++)
		{
			if(i%2==spinState){
				g2.drawArc(flagellaX-flagellaLoopWidth, Math.round(flagellaY-(flagellaMaxHeight/2f)), 
						flagellaLoopWidth, flagellaMaxHeight, 0, 180);

			} else {
				g2.drawArc(flagellaX-flagellaLoopWidth, Math.round(flagellaY-(flagellaMaxHeight/2f)), 
						flagellaLoopWidth, flagellaMaxHeight, 0, -180);
			}
			flagellaX-=flagellaLoopWidth;
			if(Math.abs(xPos-flagellaX)+flagellaLoopWidth>(cellToPaint.getFlagellaLength())) break;
		}
		if(i%2==spinState){
			g2.drawArc(flagellaX-flagellaLoopWidth, Math.round(flagellaY-(flagellaMaxHeight/2f)), 
					flagellaLoopWidth, flagellaMaxHeight, 0, -90);
		} else {
			g2.drawArc(flagellaX-flagellaLoopWidth, Math.round(flagellaY-(flagellaMaxHeight/2f)), 
					flagellaLoopWidth, flagellaMaxHeight, 0, 90);
		}
		
		randValue = ((float)Math.random()*2f)+cellToPaint.getFlagellaLoopLength();
		flagellaMaxHeight = Math.round(cellToPaint.getFlagellaLoopHeight()*randValue);
		flagellaLoopWidth = Math.round(cellToPaint.getFlagellaLoopLength()*randValue);
		spinState = Math.round((float)Math.random());
		flagellaY = yPos;
		flagellaX = Math.round(cellToPaint.getXpos());
		for(i=0;i<20;i++)
		{
			if(i%2==spinState){
				g2.drawArc(Math.round(flagellaX-(flagellaMaxHeight/2f)), flagellaY-flagellaLoopWidth,
						flagellaMaxHeight, flagellaLoopWidth, 90, -180);
			} else {
				g2.drawArc(Math.round(flagellaX-(flagellaMaxHeight/2f)), flagellaY-flagellaLoopWidth,
						flagellaMaxHeight, flagellaLoopWidth, 90, 180);
			}
			flagellaY-=flagellaLoopWidth;
			if(Math.abs(yPos-flagellaY)+flagellaLoopWidth>(cellToPaint.getFlagellaLength())) break;
		}
		if(i%2==spinState){
			g2.drawArc(Math.round(flagellaX-(flagellaMaxHeight/2f)), flagellaY-flagellaLoopWidth,
					flagellaMaxHeight, flagellaLoopWidth, 180, 90);
		} else {
			g2.drawArc(Math.round(flagellaX-(flagellaMaxHeight/2f)), flagellaY-flagellaLoopWidth,
					flagellaMaxHeight, flagellaLoopWidth, 0, -90);
		}
		
		randValue = ((float)Math.random()*2f)+cellToPaint.getFlagellaLoopLength();
		flagellaMaxHeight = Math.round(cellToPaint.getFlagellaLoopHeight()*randValue);
		flagellaLoopWidth = Math.round(cellToPaint.getFlagellaLoopLength()*randValue);
		spinState = Math.round((float)Math.random());
		flagellaY = Math.round(yPos+cellToPaint.getCellHeight());
		
		for(i=0;i<20;i++)
		{
			if(i%2==spinState){
				g2.drawArc(Math.round(flagellaX-(flagellaMaxHeight/2f)), flagellaY,
						flagellaMaxHeight, flagellaLoopWidth, 90, -180);
			} else {
				g2.drawArc(Math.round(flagellaX-(flagellaMaxHeight/2f)), flagellaY,
						flagellaMaxHeight, flagellaLoopWidth, 90, 180);
			}
			flagellaY+=flagellaLoopWidth;
			if(Math.abs(yPos-flagellaY)+flagellaLoopWidth>(cellToPaint.getFlagellaLength())) break;
		}
		if(i%2==spinState){
			g2.drawArc(Math.round(flagellaX-(flagellaMaxHeight/2f)), flagellaY,
					flagellaMaxHeight, flagellaLoopWidth, 90, 90);
		} else {
			g2.drawArc(Math.round(flagellaX-(flagellaMaxHeight/2f)), flagellaY,
					flagellaMaxHeight, flagellaLoopWidth, 90, -90);
		}
		
		
		
		
	}
	

}
