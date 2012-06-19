package edu.yale.max.cell;

import java.util.Vector;

public class LargeDisplayCell extends AbstractCell {

	public LargeDisplayCell()
	{
		super(150f,150f);
		
	}
	
	
	
	public void updatePosition(long dt) {
		// TODO Auto-generated method stub

		
		
		
	}

	
	public float getCWbias()
	{
		return 0.5f;
	}
	
	public Vector <Vector<Float>>getMotorCurve(float minYp, float maxYp, int points)
	{
		
		
		return new Vector<Vector<Float>>();
	}
}
