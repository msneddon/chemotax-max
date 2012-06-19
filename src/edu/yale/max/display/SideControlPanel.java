package edu.yale.max.display;



import edu.yale.max.cell.AbstractCell;
import edu.yale.max.environment.AbstractEnvironment;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BoxLayout;

import java.awt.Toolkit;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Graphics;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class SideControlPanel extends JPanel{
	
	LogoPanel logoPane;
	
	CheYcontrolPanel cheYcontrolPanel;
	MotorCurvePanel motorCurvePanel;
	MessagePanel messagePanel;
	
	
	public SideControlPanel(AbstractCell cell, int width, int height)
	{
		
		this.setBackground(Color.WHITE);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setPreferredSize(new Dimension(width,height));
		
//		logoPane = new LogoPanel();
//		logoPane.load("maxLogo.png");
//		add(logoPane);
//		logoPane.repaint();
//		
		JPanel topFiller = new JPanel();
		topFiller.setBackground(Color.WHITE);
		topFiller.setPreferredSize(new Dimension(width,50));
		add(topFiller);
		
		
		cheYcontrolPanel = new CheYcontrolPanel(cell,width);
		add(cheYcontrolPanel);
//		
		motorCurvePanel = new MotorCurvePanel(cell,width);
		add(motorCurvePanel);
//		
		messagePanel = new MessagePanel(cell, width);
		add(messagePanel);
		
		JPanel bottomFiller = new JPanel();
		bottomFiller.setBackground(Color.WHITE);
		bottomFiller.setPreferredSize(new Dimension(width,100));
		add(bottomFiller);
	}
	
	
	public void updateMessages()
	{
		motorCurvePanel.repaint();
		messagePanel.updateMessages();
		
	}
	
	
	

	
	
	public void initialize()
	{
		
		
		
		
	}

	
	class LogoPanel extends JPanel
	{
		private Image image;
		protected void load(String logoFilename)
		{
			try {
				image = ImageIO.read(new File(logoFilename));
			} catch (IOException e) {
				System.err.println(e);
			}
			logoPane.setPreferredSize(new Dimension(250,130));
		}
		public void paint(Graphics graphics) {
			if(image!=null) graphics.drawImage(image, 0, 0, this);
		}
	}
	
}
