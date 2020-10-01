
package landRegistry;
import java.util.ArrayList;
import java.io.EOFException;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
/**
 * This is the RegControl class that provides all data handling function including file back up, reading
 * and store objects in ArrayList
 * @author SamLiu
 * @version 3.0
*/
public class RegControl {
/**
 * Data field of RegControl class, which inludes two ArrayList that contains registrants and properties objects
*/
public RegControl() {};
private ArrayList<Registrant> registrants= new ArrayList<>();
private ArrayList<Property> properties = new ArrayList<>(); 
/**
 * Getter for registrants arraylist
 * 
 * @return registrants which is private data member: arraylist of registrants
 */
private ArrayList<Registrant> getRegistrants() {
	return registrants;
}
/**
 * Getter for properties arraylist
 * 
 * @return properties which is private data member: arraylist of properties
 */
private ArrayList<Property> getProperties() {
	return properties;
}

/**
 * Adding new registrant with fist, last name entered by user
 * 
 * @param reg is a registrant object that was entered by user
 * @return Registrant object of what's entered by user
 */
public Registrant addNewRegistrant(Registrant reg) {
	getRegistrants().add(reg);
	return getRegistrants().get(getRegistrants().size()-1);
}
/**
 * Finds the registrant fist and last name by registrant's number
 * 
 * @param regNum is the registrant's number that user wish to look for
 * @return Registrant object that matches the registrant's number that user wish to look for
 */
public Registrant findRegistrant(int regNum) {
	for(Registrant temp:getRegistrants()) {
		if(temp.getRegNum()==regNum) {
			return temp;
		}
	}
	return null;
}
/**
 * Shows all objects stored in registrant arraylist
 * 
 * @return Registrant ArrayList that shows all objects stored in registrant arraylist
 */
public ArrayList<Registrant> listOfRegistrants() {
	    return getRegistrants();
	    
}
/**
 * Delete registrant object from registrant arraylist
 * 
 * @param regNum is the registrant's number that user wishes to delete
 * @return Registrant object that is going to be deleted from registrant arraylist
 */
public Registrant deleteRegistrant(int regNum) {
		getRegistrants().remove(findRegistrant(regNum));
		return findRegistrant(regNum);
}
/**
 * Add Property object into properties arraylist
 * 
 * @param prop is the Property object that is going to be added into properties arraylist
 * @return Property object that is going to be added into properties arraylist
 */
public Property addNewProperty(Property prop) {
		getProperties().add(prop);
		return prop;
}
/**
 * Taking an arraylist that contains none or one or more property objects and delete these objects from properties arraylist from
 * this instance, if deletion is successful, return yes, otherwise return false
 * 
 * @param list is an arraylist that contains none or one or more property objects 
 * @return if delete action is successful
 */
//Syntax: removeAll() were taken reference from Oracle[2020] webpage retrived from: https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html
public boolean deleteProperties(ArrayList<Property> list) {//6 no loop
	if(list.isEmpty()) {
		return false;
	}
	else {
		getProperties().removeAll(list);
		return true;
	}
}
/**
 * Change all the properties with the same registrant's number to a new registrant's number and both registrant must exist
 * in the program
 * 
 * @param oldRegNumPropertyArrayList is an arraylist of all the same registrant's number entered by the user
 * @param newRegNum is the new registrant's nnumber
 * @return Arraylist that contains the properties after new registrant's number
 */
public ArrayList<Property> changePropertyRegistrant(ArrayList<Property> oldRegNumPropertyArrayList, int newRegNum) {//##
	ArrayList<Property> temp = new ArrayList<>();
	for(Property prop:oldRegNumPropertyArrayList) {
		temp.add(new Property (prop, newRegNum));
		getProperties().remove(prop);
		getProperties().add(new Property (prop, newRegNum));
	}
	return temp;
}
/**
 * Finding properties with user entered registrant's number
 * 
 * @param regNum is the properties registrant's number that user wishes to get 
 * @return Arraylist that contains the properties with registrant number enterd by users
 */
public ArrayList<Property> listOfProperties(int regNum) {
	if(regNum==0) {return listOfAllProperties();}
	else {ArrayList<Property> temp=new ArrayList<>();
	for(Property prop:getProperties()) {
		if(prop.getRegNum()==regNum)
		    temp.add(prop);
		}
				return temp;
	    }
}
/**
 * List all the registered properties 
 * 
 * @return Arraylist of properties of this instance i.e. all registered properties
 */
public ArrayList<Property> listOfAllProperties() {
		    return getProperties();    
}
/**
 * Return the property object that overlaps with the property that gets passed into this method
 * 
 * @param prop is the property object that user newly entered
 * @return property object if there is any, overlaps with the one that user entered
 */
public Property propertyOverlaps(Property prop) {//not a main regcontrol fuction
//MODED for exception hadling!! privae->public in order for RegView to have access 
	for(Property temp:listOfAllProperties()) {
	    if(temp.overlaps(prop))
	    	return temp;
	}
	return null;
}

/**
 * This method saves arraylist of genneric type which can be both properties or registrants and save to file accordingly
 * Here we implements I/O stream and try catch blocks to catch any exception during the process
 * 
 * @param <T> arraylist of generic type, which can take both properties or registrants
 * @param source name of the arraylist of generic type
 * @param fileName is the filename that user wish to save to
 * @return if saving to file is successful or not
 */

//All the exceptions and code flow were taken reference from by Dave Houtman [2020] CST8284_Hybrid6_FileIO_Employee class EmployeeFileUtils
public <T> boolean saveToFile(ArrayList<T> source, String fileName) {
	File file = new File(fileName);
	if (file.exists()) {file.delete();}
	try (FileOutputStream fout = new FileOutputStream(fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fout); )//this is try..with..resource structure
	{
		if(source==null) {
			return false;
					}
		else {
		for(T temp:source) {
		oos.writeObject(temp);
		}
		return true;
		}
	} 
	catch(Exception e) {
		return false;
		}
	//deleting following code is taken reference from by Dave Houtman [2020] CST8284_20S_Assignment3_Starter_Code class RegControl
	/*	catch (IOException ex){
		return false;
	}*///no need for a specific exception when a general exception would do
}
/**
 * Loads data from file that user previously saved to, return either a properties arraylist or a registrant arraylist
 * 
 * @param <T> arraylist of generic type, which can take both properties or registrants
 * @param fileName is the filename that user wish to load from
 * @return arraylist of generic type that consists either a properties arraylist or a registrant arraylist that user previously saved to 
 */
//All the exceptions and code flow were taken reference from by Dave Houtman [2020] CST8284_Hybrid6_FileIO_Employee class EmployeeFileUtils
public <T> ArrayList<T> loadFromFile(String fileName){ //##
	
	ArrayList<T> temp=new ArrayList<>();
	Object obj=null;
	boolean couting=true , reset=false;
	try (
			FileInputStream fin = new FileInputStream(fileName);
			 ObjectInputStream ois = new ObjectInputStream(fin);
			)
	{
		while(couting) {
		obj=ois.readObject();
		if(obj instanceof Property && !reset) {
			getProperties().clear();
			reset=true;
		}
		if(obj instanceof Registrant && !reset) {
			getRegistrants().clear();
			reset=true;
		}
		if(obj!=null) {
			temp.add((T)obj);
			}
		else {couting=false;}
		}
		return temp;
	}
	
	catch (EOFException ex){
		  return temp;
	}
	catch (Exception ex)
	{
		return null;
	}
	//deleting following code is taken reference from by Dave Houtman [2020] CST8284_20S_Assignment3_Starter_Code class RegControl
	/*catch (FileNotFoundException ex){return null;}
	catch (ClassNotFoundException ex){return null;}			
	catch (IOException ex){return null;}*/
	//no need for a specific exception when a general exception does the job
}
/**
 * Performs loading from file, clears this properties arraylist and overwrites by what's from the file
 */
	public void refreshProperties() {
		if ((new File("LandRegistry.prop")).exists()) {
			ArrayList<Property> propList=loadFromFile("LandRegistry.prop");
			getProperties().clear();
			for(Property prop:propList) {getProperties().add(prop);}
		}
	}
/**
 * Performs loading from file, clears this registrants arraylist and overwrites by what's from the file
 */
	public void refreshRegistrants() {
		if ((new File("LandRegistry.reg")).exists()) {
			ArrayList<Registrant> regList=loadFromFile("LandRegistry.reg");
			getRegistrants().clear();
			for(Registrant reg:regList) {getRegistrants().add(reg);}
		}
	}
	//general input testing methods
	/**
	 * test if input String "in" is empty
	 * @param in is the String entered by user
	 * @return if input is empty
	 */
	 public boolean testBlank(String in) {return in.equals("");}
	 /**
	  * test if input String "in" is null
	  * @param in is the String entered by user
	  * @return if input is null
	  */
	 public boolean testnull(String in) {return in==null;}
	//regnum testing methods
	 /**
	  * test if registrant number is less than 0
	  * @param regnum is the registrant number
	  * @return if registrant number is less than 0
	  */
	 public boolean testNegReg(int regnum) {return regnum<0;}
	 /**
	  * test if there is no registrant loaded
	  * @return if there is no registrant loaded
	  */
	 public boolean testNoReg() {return getRegistrants().isEmpty();}
	 /**
	  * test if registrant number is bigger than 1016 or smaller than 1000
	  * @param regnum is registrant number
	  * @return if registrant number is bigger than 1016 or smaller than 1000
	  */
	 public boolean testUnReg(int regnum) {return (regnum>1016||regnum<1000);}
	//property testing methods
	 /**
	  * test if the property sits over the boundary
	  * @param x X coordinate or Y coordinate of the property
	  * @param y length of width of the property
	  * @return if the property sits over the boundary
	  */ 
	 public boolean testPropExc(int x,int y) {return x+y>1000;}
	 /**
	  * test if property's length is smaller than minimum requirement
	  * @param length is the length of the property
	  * @return if length is smaller than minimum requirement
	  */
	 public boolean testPropL(int length){return length<20;}
	 /**
	  * test if property's width is smaller than minimum requirement
	  * @param width is the width of the property
	  * @return if property's width is smaller than minimum requirement
	  */
	 public boolean testPropW(int width){return width<10;}
	 
	 /*public static boolean testSChar(String in){
    	 String schar="!#$%&'()*+,-./:;<=>?@[]^_`{|}~";
    	 String[] strcharlist = new String[in.length()];
   	  	 for(int k=0;k<in.length();k++) {strcharlist[k] = Character.toString(in.charAt(k));}
   	     for (int j = 0; j <  strcharlist.length; j++) {
   	        if (schar.contains(strcharlist[j])) {
   	        	return true;
   	        }
   	     }
   	   return false;
     }*///This is for later assignment when parseInt() is not in use (address assignment manual Version1.02 first bullet point)
	 //note:couldn't figure out how to test regular alphabet character yet but i will for next assignment
     //note 2:probably not the most elegent way of testing special character but it works (tested), will think of better ways later!
}//class

