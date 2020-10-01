
package landRegistry;
/**
 * This is the bad land registry class that consists of self-constructed exception for RegControl class and RegView class
 * @author SamLiu
 * @version 3.0
*/
public class BadLandRegistryException extends java.lang.RuntimeException{
	private static final long serialVersionUID = 1L;
	private String header;
/**
 * No-arg constructor for class BadLandRegistryException, which passes the default message to two-arg constructor
 */
	public BadLandRegistryException() {
		this("Bad land registry data entered","Please try again");
	}
/**
 * Two-arg constructor which takes two strings: header message and error message, then pass only the error message 
 * to the superclass: RuntimeException 
 * 
 * @param header displays the error type
 * @param msg displays detailed descreption of the error
 */
	public BadLandRegistryException(String header, String msg) {
		super(msg);
		setHeader(header);
	}
/**
 * Getter for private class data String header
 * 
 * @return header is a String of the header message 
 */
	public String getHeader() {
		return header;
	}
/**
 * Setter for private class data String header
 * 
 * @param header is the message String that gets passed into the setter 
 */
	public void setHeader(String header) {
		this.header = header;
	}
	
}
