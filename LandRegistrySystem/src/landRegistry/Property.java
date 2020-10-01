
package landRegistry;
import java.io.Serializable;
/**
 * This is the Property class that constructs property objects
 * @author SamLiu
 * @version 3.0
*/
public class Property implements Serializable{
	/**Data field of Property class, which inludes property size, property coordinate, property area, 
	 * property registration number, and property tax
	 */
	public static final long serialVersionUID = 1L;
	private final static double TAX_RATE_PER_M2 = 12.50;
	private final static int DEFAULT_REGNUM = 999;
	private int xLeft;
	private int yTop;
	private int xLength;
	private int yWidth;
	private int regNum;
	private int area;
	private double taxes;
	/**
	 * No-arg constructor that passes all zero to the next chaining constructor
	 */
	public Property() {
		this(0,0,0,0);
	}
	/**
	 * Four-arg constructor that takes coordinate and size data and then passed 
	 * these data to its chaining constructor with default registrant number
	 * 
	 * @param xLength is the length of the property
	 * @param yWidth is the width of the property
	 * @param xLeft is the X value for a regular two dimensional cartesian coordinate system
	 * @param yTop is the Y value for a regular two dimensional cartesian coordinate system
	 */
	public Property(int xLength, int yWidth, int xLeft, int yTop) {
		this(xLength, yWidth, xLeft, yTop, DEFAULT_REGNUM);
	}
	/**
	 * Two-arg constructor that takes a property object and an entried registration number
	 * then passed to its chaining constructor
	 * 
	 * @param prop is a property object that is often made by users after data entry
	 * @param regNum is the registration number of the property that is entered by users
	 */
	public Property(Property prop, int regNum) {
		this(prop.getXLength(),prop.getYWidth(),prop.getXLeft(),prop.getYTop(),regNum);
	}
	/**
	 * Five-arg constructor that is the ultimate constructor of the class, it takes all the data:
	 * property size, property coordinate, property registration number and then set each data into the 
	 * data field of this class
	 * 
	 * @param xLength is the length of the property
	 * @param yWidth is the width of the property
	 * @param xLeft is the X value for a regular two dimensional cartesian coordinate system
	 * @param yTop is the Y value for a regular two dimensional cartesian coordinate system
	 * @param regNum is the registration number of the property that is entered by users or by default
	 */
	public Property(int xLength, int yWidth, int xLeft, int yTop, int regNum) {
		setXLength(xLength);
		setYWidth(yWidth);
		setXLeft(xLeft);
		setYTop(yTop);
		setRegNum(regNum);
	}
	/**
	 * Getter for private class data xLeft
	 * 
	 * @return xLeft is an integer of X value for a regular two dimensional cartesian coordinate system
	 */
	public int getXLeft() {
		return xLeft;
	}
	/**
	 * Setter for private class data xLeft
	 * 
	 * @param left is equals to xLeft which is the X value for a regular two dimensional cartesian coordinate system
	 */
	public void setXLeft(int left) {
			this.xLeft=left;
	}
	/**
	 * Getter for the X value for the bottom right point's coordinate of the property 
	 * 
	 * @return xLeft+xLength is an integer of X value for the bottom right point's cooridate of the property 
	 */
	public int getXRight() {
		return xLeft+xLength;
	}
	/**
	 * Getter for the Y value for the top left point's coordinate of the property
	 * 
	 * @return yTop is an integer of Y value for the top left point's coordinate of the property
	 */
	public int getYTop() {
		return yTop;
	}
	/**
	 * Setter for the Y value for the top left point's coordinate of the property
	 * 
	 * @param top is an integer that equals to Y value for the top left point's coordinate of the property
	 */
	public void setYTop(int top) {
			this.yTop=top;
	}
	/**
	 * Getter for Y value for the bottom right point's cooridate of the property
	 * 
	 * @return yTop+yWidth is an integer of Y value for the bottom right point's cooridate of the property
	 */
	public int getYBottom() {
		return (yTop+yWidth);
	}
	/**
	 * Getter for property's width
	 * 
	 * @return yWidth is an interger of property's width
	 */
	public int getYWidth() {
		return yWidth;
	}
	/**
	 * Setter of the property's width
	 * 
	 * @param width is equal to property's width
	 */
	public void setYWidth(int width) {
			this.yWidth=width;
	}
	/**
	 * Getter for property's length
	 * 
	 * @return xLength is an integer of property's length
	 */
	public int getXLength() {
		return xLength;
	}
	/**
	 * Setter for property's length
	 * 
	 * @param length is equal to property's length
	 */
	public void setXLength(int length) {
			this.xLength=length;
	}
	/**
	 * Getter for property's registration number
	 * 
	 * @return regNum is an integer of property's registration number
	 */
	public int getRegNum() {
		return regNum;
	}
	/**
	 * Setter for property's registration number
	 * 
	 * @param regNum is equal to property's registration number
	 */
	private void setRegNum(int regNum) {
		this.regNum=regNum;
	}
	/**
	 * Getter/setter for property's area, within this method it first calculates property's area then set it
	 * 
	 * @return area is an interger of property's area
	 */
	public int getArea() {
		this.area=(getXLength()*getYWidth());
		return area;
	}
	/**
	 * Getter/setter for property tax, within this method it first calculates property tax then set it
	 * 
	 * @return taxes is an integer of property tax
	 */
	public double getTaxes() {
		this.taxes=(getArea()*TAX_RATE_PER_M2);
		return taxes;
	}
	/**
	 * Overriden toString method that return a String of property infomation
	 * 
	 * @return propertyinfo String that contains property information
	 */
	public String toString() {
		return ("Coordinates:"+getXLeft()+", "+getYTop()+"\n"+
 		"Length: "+getXLength()+" m  "+"Width: "+getYWidth()+" m"+"\n"+
 		"Registrant: #"+getRegNum()+"\n"+
 		"Area: "+getArea()+" m2"+"\n"+
 		"Property Taxes: $"+getTaxes()+"\n");
	}
	
	/**
	 * overriden method equals() that Compare if two property objects is exactly the same
	 * 
	 * @param obj is the object being compared with this instance
	 * @return if the object that gets passed into is equal to this instance
	 */
	//This code provided by Dave Houtman [2020] CST8284_20S_Assignment2_Starter_Code class Property
	public boolean equals(Object obj) {
		if (!(obj instanceof Property)) return false;
		Property temp = (Property)obj;
		return (temp.getYTop()==this.getYTop() && temp.getXLeft()==this.getXLeft() && hasSameSides(temp));
	}
	
	/**
	 * Compares if two properties have exact same size
	 * 
	 * @param obj is the object being compared with this instance
	 * @return if two properties have exact same size
	 */
	public boolean hasSameSides(Property obj) {
			return ((this.getXLength()==obj.getXLength()) && (this.getYWidth()==obj.getYWidth()));
	}
	/**
	 * Check if two properties overlap, return the "not" condition when two property don't overlap which
	 * true means overlaping and false means no overlaping
	 * 
	 * @param prop is the object being compared with this instance
	 * @return if two properties overlap
	 */
	public boolean overlaps(Property prop) {
	int ax1=this.getXLeft(),ay1=this.getYTop(),ax2=this.getXRight(),ay2=this.getYBottom(); 
	int bx1=prop.getXLeft(),by1=prop.getYTop(),bx2=prop.getXRight(),by2=prop.getYBottom(); 
	return (!((bx1>=ax2) || (bx2<=ax1) || (by2<=ay1) || (by1>=ay2))); 
	}
}

