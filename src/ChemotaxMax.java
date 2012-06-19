/**
 * CHEMOTAX MAX, v1.0 !!!!
 * The definitive online chemotaxis game where you attempt 
 * to navigate Max, a lowly single E. coli cell, towards 
 * the food he so desperately craves.
 * 
 * 
 * 
 * Created and written by
 * Michael Sneddon
 * email: michael.sneddon@yale.edu
 * 12/28/2007
 * 
 * 
 * 
 */





import edu.yale.max.cell.*;
import edu.yale.max.display.*;
import edu.yale.max.environment.*;
import edu.yale.max.ui.*;


import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;


public class ChemotaxMax extends Applet implements Runnable{

	JPanel globalContainer;
	MainDisplayPanel mainDisplayPanel;
	SideControlPanel sideControlPanel;
	AbstractCell cell;
	AbstractEnvironment environment;
	
	Thread mainGameLoop;
	/**
	 * 
	 */
	public void init(){
		System.out.println("Applet initializing.");
		int xDim = 850, yDim = 600;
		
		this.setMinimumSize(new Dimension(xDim,yDim));
		this.setMaximumSize(new Dimension(xDim,yDim));
		this.setPreferredSize(new Dimension(xDim,yDim));
		this.setSize(new Dimension(xDim,yDim));
		setLayout(new BorderLayout());
//		
//
		globalContainer = new JPanel();
		globalContainer.setLayout(new BorderLayout());
		globalContainer.setMaximumSize(new Dimension(xDim,yDim));
		globalContainer.setMinimumSize(new Dimension(xDim, yDim));
		globalContainer.setPreferredSize(new Dimension(xDim, yDim));
		
		mainDisplayPanel = new MainDisplayPanel();
//		
		cell = new BasicCell();
		environment = new BasicEnvironment(600,600, cell.getCellWidth());
		mainDisplayPanel.initialize(cell, environment);
		globalContainer.add(mainDisplayPanel, BorderLayout.CENTER);
//		
		sideControlPanel = new SideControlPanel(cell, xDim-600, yDim);
		globalContainer.add(sideControlPanel, BorderLayout.EAST);
//		
		this.add(globalContainer);
//		
	//    mainGameLoop = new Thread(this);
	    
		this.validate();
	}

	/**
	 * 
	 */
	public void start(){
		System.out.println("Applet starting.");
		mainGameLoop = new Thread(this);
		mainGameLoop.start();
	}

	/**
	 * 
	 */
	public void stop(){
		System.out.println("Applet stopping.");
		mainGameLoop = null;
	}

	/**
	 * 
	 */
	public void destroy(){
		System.out.println("Destroy method called.");
		mainGameLoop = null;
	}


	
	
	public void run() {
		
	    // positions once each 83 
	    // milliseconds
		long animationDelay = 83;
	    long time = System.currentTimeMillis();
	    
	    int i=0;
	    while (true) {//infinite loop
	    	i++;
	    	
	      cell.updatePosition(System.currentTimeMillis());
	      environment.movingCell(cell);
	      mainDisplayPanel.repaint();
	      sideControlPanel.updateMessages();
	      try {
	        time += animationDelay;
	        Thread.sleep(Math.max(0,time - 
	          System.currentTimeMillis()));
	      }catch (InterruptedException e) {
	        System.out.println(e);
	      }
	    }
        
        
    }
	
}
