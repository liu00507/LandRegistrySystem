
package landRegistry;
import java.io.Serializable;
/**
 * This is the Property class that constructs registrant objects
 * @author SamLiu
 * @version 3.0
*/
public class Registrant implements Serializable{
	/**
	 * Data field of registrant class, which inludes registrant firstname, lastname, registration number
	 */
	public static final long serialVersionUID = 1L;
	private final static int REGNUM_START=1000;
	private static int currentRegNum = REGNUM_START;
	private int REGNUM = currentRegNum;//This line provived by Dave Houtman's personal instruction during office hour on June 19th 3:40pm, 2020
	private String firstName;
	private String lastName;
	/**
	 * No-arg constructor that passes default first,last name to its chaining constructor
	 * 
	 */
	public Registrant() {
		this("unknown unknown");
	}
	/**
	 * One-arg constructor that splits first and last name into two seperate strings and set them
	 * registration number is automatically incremented starting from #1000
	 * 
	 * @param firstLastName is one String that contains both first and lastname with a space in between
	 */
	public Registrant(String firstLastName) {
		String name[]=firstLastName.split(" ");
		setFirstName(name[0]);
		setLastName(name[1]);
		incrToNextRegNum();
	}
	/**
	 * Getter for registrant's registration number
	 * 
	 * @return REGNUM is an integer of registrant's registration number
	 */
	public int getRegNum() {
	return REGNUM;
	}
	/**
	 * Increment default registrant's registration number 
	 */
	private static void incrToNextRegNum() {
		currentRegNum++;
	}
	/**
	 * Getter for registrant's first name
	 * 
	 * @return firstName is the String that contains registrant's first name
	 */
	public String getFirstName() {
	return firstName;
	}
	/**
	 * Setter for registrant's first name
	 * 
	 * @param firstName is equal to registrant's first name
	 */
	public void setFirstName(String firstName) {
		this.firstName=firstName;
	}
	/**
	 * Getter for registrant's last name
	 * 
	 * @return lastName is the String that contains registrant's last name
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * Setter for registrant's last name
	 * 
	 * @param lastName is equal to registrant's last name
	 */
	public void setLastName(String lastName) {
		this.lastName=lastName;
	}
	/**
	 * Overriden equals() method that compares if two registrant objects are exactly the same
	 * 
	 * @param obj is the registrant object being compared with this instance
	 * @return if two registrant objects are exactly the same
	 */
	//This code provided by Dave Houtman [2020] CST8284_20S_Assignment2_Starter_Code class Registrant
	public boolean equals(Object obj) { 
		if (!(obj instanceof Registrant)) return false;
		Registrant reg = (Registrant)obj;
		return ((reg.getFirstName().equals(this.getFirstName())) &&
		   (reg.getLastName().equals(this.getLastName())) &&
		   (reg.getRegNum()==(this.getRegNum())));
	}
	/**
	 * Overriden toString method that return a String of registrant infomation
	 * 
	 * @return registrantinfo String that contains registrant information
	 */
	public String toString() {
	return ("Name:"+getFirstName()+" "+getLastName()+"\n"+"Registration Number: #"+getRegNum());
	}
	
}
