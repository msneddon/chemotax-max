package edu.yale.max.display;

import edu.yale.max.cell.AbstractCell;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import java.text.DecimalFormat;


public class MessagePanel extends JPanel {

	
	JLabel ypConcLabel;
	JLabel cwBiasLabel;
	JLabel stateLabel;
	JLabel timerLabel;
	AbstractCell cell;
	DecimalFormat numberFormatter;
	
	public MessagePanel(AbstractCell cell, int width)
	{
		this.cell = cell;
		//this.setPreferredSize(new Dimension(width,100));
		this.setBackground(Color.LIGHT_GRAY);
		this.setLayout(new GridLayout(3,1));
		
		numberFormatter = new DecimalFormat("0.00");
		
		ypConcLabel = new JLabel("   [CheYp]: "+numberFormatter.format(cell.getYpConc())+" uM");
		ypConcLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
		this.add(ypConcLabel);
		
		cwBiasLabel = new JLabel("   CW Bias: "+numberFormatter.format(cell.getCWbias()));
		cwBiasLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
		this.add(cwBiasLabel);
		
		String state = "";
		if(cell.getFlagellaState()==AbstractCell.COUNTERCLOCKWISE)
			state="Running! ";
		else
			state="Tumbling!";
		
			
		stateLabel = new JLabel("   State: "+state);
		stateLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
		this.add(stateLabel);
	}
	
	
	
	public void updateMessages()
	{
		ypConcLabel.setText("   [CheYp]: "+numberFormatter.format(cell.getYpConc()));
		cwBiasLabel.setText("   CW Bias: "+numberFormatter.format(cell.getCWbias()));
		String state = "";
		if(cell.getFlagellaState()==AbstractCell.COUNTERCLOCKWISE)
			state="Running! ";
		else
			state="Tumbling!";
		
			
		stateLabel.setText("   State: "+state);
	}
	
	
	
	
	
	
}
