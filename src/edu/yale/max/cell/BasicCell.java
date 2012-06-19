package edu.yale.max.cell;

import java.lang.Math;
import java.util.Random;
import java.util.Vector;
import java.awt.Point;

public class BasicCell extends AbstractCell {

	protected long lastTime;
	protected Random r;
	protected double rotationalDiffusion;
	
	public BasicCell()
	{
		super(150f,150f);
		lastTime = 0;
		rotationalDiffusion = 0.1;
		Yp = 3.1f;
		this.swimSpeed = 150;
		initMotorParameters();
		r=new Random();
		randomRotateOrientation();
	}
	
	
	
	public void updatePosition(long currentTime) {
		
		this.Yp+=this.YpRateOfChange;
		if(Yp<=0) Yp = 0;
		if(Yp>=6) Yp = 6;
		updateMotor((currentTime-lastTime)*(0.0005f), Yp);
		
		//If we are tumbling...
		if(flagellaState==AbstractCell.CLOCKWISE)
		{
			//all we do is choose a random new rotation
			randomRotateOrientation();
		} 
		//Otherwise we are swimming
		else if(flagellaState==AbstractCell.COUNTERCLOCKWISE)
		{
			if(lastTime==0) move(0);
			else move(currentTime - lastTime);
		}
		lastTime = currentTime;
	}

	
	protected void randomRotateOrientation()
	{
		double theta = r.nextDouble()*2.0*Math.PI;
		double sinTheta = Math.sin(theta);
		double cosTheta = Math.cos(theta);
		double newXori = (cosTheta*xOrientation - sinTheta*yOrientation);
		double newYori = (sinTheta*xOrientation + cosTheta*yOrientation);
		xOrientation = newXori;
		yOrientation = newYori;
		
		//Normalize just in case
		double mag = (xOrientation*xOrientation) + (yOrientation*yOrientation);
		xOrientation/=mag;
		yOrientation/=mag;
	}
	
	protected void move(double dt)
	{
		lastX = xPosition;
		lastY = yPosition;
		
		dt = dt*0.001;
		
		double displacementFactor = swimSpeed*dt;
		xPosition = xPosition+ (float)(displacementFactor*xOrientation);
		yPosition = yPosition+ (float)(displacementFactor*yOrientation);
		
		
		double sqrtDt2rotationalDiffusion = Math.sqrt(dt * 2 * rotationalDiffusion);
		double theta = sqrtDt2rotationalDiffusion * r.nextGaussian();
		double sinTheta = Math.sin(theta);
		double cosTheta = Math.cos(theta);
		double newXori = (cosTheta*xOrientation - sinTheta*yOrientation);
		double newYori = (sinTheta*xOrientation + cosTheta*yOrientation);
		xOrientation = newXori;
		yOrientation = newYori;
	}
	
	
	protected float Kd;
	protected float g0;
	protected float g1;
	protected float theta;
	protected double w;
	protected double kPlus;
	protected double kMinus;
	
	
	
	protected void initMotorParameters()
	{
		Kd = 3.1f;
		g0 = 6.7f;
		g1 = 80f;
		theta = 400f;
		kPlus = 0;
		kMinus = 0;
		w = (float)((theta*g0*Math.sqrt(32)) / (2.0*Math.PI)) * Math.exp(-g0);
	}
	
	protected void updateMotor(float dt, float YpConcentration)
	{
		calculateRates(YpConcentration);
		
		int newState = -1;
		double rNum = r.nextDouble();
		if(this.flagellaState==AbstractCell.CLOCKWISE)
			if(rNum<(kPlus*dt)) {
				newState = AbstractCell.COUNTERCLOCKWISE;
			} else {
				newState = AbstractCell.CLOCKWISE;
			}
		else if(this.flagellaState==AbstractCell.COUNTERCLOCKWISE)
			if(rNum<(kMinus*dt)) {
				newState = AbstractCell.CLOCKWISE;
			} else {
				newState = AbstractCell.COUNTERCLOCKWISE;
			}
		this.flagellaState = newState;
	}
	
	protected void calculateRates(float YpConcentration)
	{
		double deltaG = (g1/2f) * ((1f/2f) - (YpConcentration / (Kd + YpConcentration)));
		kPlus = w*Math.exp((deltaG));
		kMinus = w*Math.exp((-deltaG));
	}
	
	public float getCWbias()
	{
		
		return (float)(kMinus/(kPlus+kMinus));
	}
	
	

	public Vector <Vector<Float>> getMotorCurve(float minYp, float maxYp, int points)
	{
		Vector <Vector<Float>> motorCurve = new Vector<Vector<Float>>();
		motorCurve.add(new Vector<Float>());
		motorCurve.add(new Vector<Float>());
		
		float step = (maxYp-minYp)/(points-1);
		float y = minYp;
		for(int i=0; i<points; i++, y+=step)
		{
			calculateRates(y);
			motorCurve.get(0).add(new Float(y));
			motorCurve.get(1).add(new Float(getCWbias()));
		}
		
		return motorCurve;
	}
	
}
