package edu.yale.max.cell;



import java.awt.Color;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.util.Vector;
import java.awt.Point;

/**
 * 
 * 
 * 
 * 
 * @author Michael Sneddon
 */
public abstract class AbstractCell {

	//X and Y position in the environment
	protected float xPosition;
	protected float yPosition;
	
	protected float lastX;
	protected float lastY;
	
	//Orientation vector (should be normalized) giving
	//the direction of the cell
	protected double xOrientation;
	protected double yOrientation;
	
	//Basic cell display properties           
	protected float cellHeight;
	protected float cellWidth;
	protected float flagellaLength;
	protected int flagellaLoopLength;
	protected int flagellaLoopHeight;
	protected Color flagellaColor;
	protected Color cytoplasmColor;
	protected Color cellWallColor;
	protected Stroke cellWallStroke;
	protected Stroke flagellaStroke;
	
	//Basic cell behavior properties
	static public int CLOCKWISE = 0;
	static public int COUNTERCLOCKWISE = 1;
	protected int flagellaState;
	protected float swimSpeed;
	

	protected float Yp;
	protected float YpRateOfChange;
	
	/**
	 * Create a cell with basic default properties
	 */
	public AbstractCell(float startXpos, float startYpos)
	{
		//Cell position and orientation
		xPosition = startXpos;
		yPosition = startYpos;
		
		lastX = xPosition;
		lastY = yPosition;
		
		xOrientation = 1.0;
		yOrientation = 0.0;
		
		//Cell
		float size = 1f;
		cellHeight = 8*size;
		cellWidth = 18*size;
		flagellaLength = 40*size;
		flagellaState = AbstractCell.CLOCKWISE; //COUNTERCLOCKWISE;
		swimSpeed = 75;
		
		flagellaLength = 40;
		flagellaLoopLength = 6;
		flagellaLoopHeight = 6;
		flagellaColor = new Color(0f, 0f, 0f);
		flagellaStroke = new BasicStroke(1.3f);

		cytoplasmColor = new Color(0.4f, 0.4f, 0.4f);
		cellWallColor = new Color(0f, 0f, 0f);
		cellWallStroke = new BasicStroke();
		
		Yp = 2.8f;
		YpRateOfChange = 0f;
		
	}
	
	
	
	//Basic functions to get the properties of the cell
	public final float getXpos() { return xPosition; }
	public final float getYpos() { return yPosition; }
	public final float getLastXpos() { return lastX; }
	public final float getLastYpos() { return lastY; }
	
	public final void resetX() { xPosition = lastX; }
	public final void resetY() { yPosition = lastY; }
	
	public final float getYpConc() { return this.Yp; }
	
	public final float getXorientation() { return (float)xOrientation; }
	public final float getYorientation() { return (float)yOrientation; }
	public final float getCellHeight() { return cellHeight; }
	public final float getCellWidth() { return cellWidth; }
	public final float getFlagellaLength() { return flagellaLength; }
	public final int getFlagellaState() { return flagellaState; }
	
	public final Color getCytoplasmColor() { return this.cytoplasmColor; }
	public final Color getCellWallColor() { return this.cellWallColor; }
	public final Stroke getCellWallStroke() { return this.cellWallStroke; }
	
	
	public final int getFlagellaLoopLength() { return flagellaLoopLength; }
	public final int getFlagellaLoopHeight() { return flagellaLoopHeight; }
	public final Color getFlagellaColor() { return flagellaColor; }
	public final Stroke getFlagellaStroke() { return flagellaStroke; }
	
	
	public final void setFlagellaState(int newState) { flagellaState = newState; }
	
	/**
	 * This function gets overridden 
	 * @param dt
	 */
	abstract public void updatePosition(long currentTime);
	
	abstract public float getCWbias();
	
	public final void updateYpConcentration(float newYp) { this.Yp = newYp; }
	
	
	abstract public Vector <Vector<Float>> getMotorCurve(float minYp, float maxYp, int points);
	
	public final void setYpRateOfChange(float newRateOfChange) { this.YpRateOfChange=newRateOfChange; }
	
	
	
	
	
}
