
package landRegistry;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.util.Comparator;

public final class RegViewGUI extends JFrame{
//transplants from original regview:
	private static RegControl rc = new RegControl();
	public RegViewGUI() {};
	public final static String PROPERTIES_FILE="LandRegistry.prop" , REGISTRANTS_FILE="LandRegistry.reg";
	private static RegControl getRegControl(){
    	return rc;
    }
	public static int selected=0;
	
private static String BasicInputCheck(String s) {
	if(getRegControl().testnull(s)) throw new BadLandRegistryException("Null value entered","Canceled!"); // modified to make more sense in wording
	if(getRegControl().testBlank(s)) throw new BadLandRegistryException("Missing value","Missing an input value");
	return (s);
}

private static String NameFormatCheck(String s) {
	if(!s.matches("(.*) (.*)")) throw new BadLandRegistryException("Bad Input","Check name input format!");
	return s;
}
	
	
	
// gridx    gridy                  gridwidth  gridheight  weightx weighty anchor                       fill                           insets                      ipadx  ipady 
   private static final GridBagConstraints registrantConstraints = new GridBagConstraints(
   0,       0,                     1,         1,          0.5,     0,     GridBagConstraints.NORTH,    GridBagConstraints.VERTICAL,   new Insets(10, 10, 10, 10), 0,     0);
   private static final GridBagConstraints btnConstraints = new GridBagConstraints(
   1,       0,                     1,         2,          0.5,     0.5,   GridBagConstraints.NORTH,    GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5),   0,     0);
   private static final GridBagConstraints propertyConstraints = new GridBagConstraints(
   0,       1,                     1,         1,          0.5,     1,	  GridBagConstraints.NORTH,    GridBagConstraints.VERTICAL,   new Insets(5, 5, 5, 5),   0,     0);
   private static final GridBagConstraints southBtnConstraints = new GridBagConstraints(
   0,       2,                     1,         1,          0.5,     1,     GridBagConstraints.SOUTH,    GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5),   0,     0);
   private static final GridBagConstraints exitBtnConstraints = new GridBagConstraints(   
   1,       2,                     1,         1,          0.5,     1,	  GridBagConstraints.SOUTH,    GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5),   0,     0);
	
    private static final int     SPACE = 12, BORDER = 24;
    private static final boolean ON = true,  OFF = false;
    
    // eastBtnPanel values
    private static final int ADD_NEW_REGISTRANT = 1, DELETE_REGISTRANT = 2, ADD_PROPERTY = 3, 
    		                 DELETE_CURRENT_PROPERTY = 4, CHANGE_REGISTRATION_NUMBER = 5;
    // southBtn Panel values
    private static final int RELOAD_LAND_REGISTRY = 1, BACKUP_LAND_REGISTRY = 2;
    
    private static final Font      defaultFont         = new Font("SansSerif", Font.PLAIN, 20);	
    private static final Dimension defaultDimension    = new Dimension(540, 40);
    private static final Dimension mainDimension       = new Dimension(1024, 540);
    
    private static       JFrame    f                   = new JFrame("");  
    private static final JPanel    mainPanel           = new JPanel(new GridBagLayout());
    private static       JPanel    registrantsPanel    = new JPanel(); 
    private static       JPanel    eastBtnPanel        = new JPanel();
    private static       JPanel    propertiesPanel     = new JPanel(); 
    private static       JPanel    southBtnPanel       = new JPanel();
	
	public static void launchDialog(){
		
    	//------------------------------------------------------------------------------------------------------//
		     f.setTitle("Land Registry");                                                              // - O X //
		//-----------------------------------------------------------------------------------------------------//
		//                                                                            //                        //
mainPanel.add(loadRegistrantsPanel(getRegControl().listOfRegistrants()), registrantConstraints);         //                        //
		                                                                                   mainPanel.add(       //
                                                                                           loadEastBtnPanel(),  //
                                                                                           btnConstraints);     //
		//                                                                            //                        //
		//----------------------------------------------------------------------------//                        //
		//                                                                            //                        //
mainPanel.add(loadPropertyPanel(getRegControl().listOfAllProperties()), propertyConstraints);              //                        //
		//                                                                            //                        //		
    	//------------------------------------------------------------------------------------------------------//		
		//                                                                                                      //
                          mainPanel.add(loadSouthBtnPanel(), southBtnConstraints);    //        EXIT            // 	                      
		//                                                                                                      //
		//------------------------------------------------------------------------------------------------------//
 
        mainPanel.setBorder(BorderFactory.createEmptyBorder(BORDER, BORDER, BORDER, BORDER));
        mainPanel.setPreferredSize(mainDimension);
        
	    f.add(mainPanel);
	    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);	
	}
	
//.revalidate() .repainted syntax were taken reference from: https://stackoverflow.com/questions/7628121/how-can-i-refresh-or-reload-the-jframe
	public static void refresh() {
		loadPropertyPanel(getRegControl().listOfAllProperties()).removeAll();
		loadRegistrantsPanel(getRegControl().listOfRegistrants()).removeAll();
		loadSouthBtnPanel().removeAll();
		loadPropertyPanel(getRegControl().listOfAllProperties());
		loadRegistrantsPanel(getRegControl().listOfRegistrants());
		loadSouthBtnPanel();
		mainPanel.revalidate();
		mainPanel.repaint();
	}
	
	public static void refreshCbox(ArrayList<Registrant> reg, ArrayList<Property> prop ) {
		loadRegistrantsPanel(getRegControl().listOfRegistrants()).removeAll();
		loadRegistrantsPanel(reg);
		loadPropertyPanel(getRegControl().listOfAllProperties()).removeAll();
		loadPropertyPanel(prop);
		loadEastBtnPanel().removeAll();
		turnAllBtnsIn(loadEastBtnPanel(),ON, new Integer[] {DELETE_CURRENT_PROPERTY,6,7});
		mainPanel.revalidate();		
		mainPanel.repaint();
	}
	
	public static void refreshBtnToDefault() {
		loadEastBtnPanel().removeAll();
		turnAllBtnsIn(loadEastBtnPanel(),OFF,ADD_NEW_REGISTRANT);
		mainPanel.revalidate();		
		mainPanel.repaint();
	}
	
	public static void refreshSorted(ArrayList <Property> props){
		loadPropertyPanel(getRegControl().listOfProperties(selected)).removeAll();
		loadPropertyPanel(props);
		mainPanel.revalidate();
		mainPanel.repaint();
	}
	
//Hovercraft Full Of EelsAdapted[2011]. Multiple input in JOptionPane.showInputDialog. 
//Adapted from: https://stackoverflow.com/questions/6555040/multiple-input-in-joptionpane-showinputdialog/6555051
	public static String propertyDialog() {
		 JTextField xField = new JTextField(5);
	      JTextField yField = new JTextField(5);
	      JTextField lField = new JTextField(5);
	      JTextField wField = new JTextField(5);
	      JPanel myPanel = new JPanel();
	      myPanel.add(new JLabel("Top(X):"));
	      myPanel.add(xField);
	      myPanel.add(Box.createHorizontalStrut(15)); // a spacer
	      myPanel.add(new JLabel("Left(Y):"));
	      myPanel.add(yField);
	      myPanel.add(Box.createHorizontalStrut(15));
	      myPanel.add(new JLabel("lenth:"));
	      myPanel.add(lField);
	      myPanel.add(Box.createHorizontalStrut(15));
	      myPanel.add(new JLabel("width:"));
	      myPanel.add(wField);
	      int result = JOptionPane.showConfirmDialog(null, myPanel, 
	               "Please Enter X and Y Coordinates and Property's Size for Registrant #"+selected, JOptionPane.OK_CANCEL_OPTION);
	      String x=lField.getText()+"-"+wField.getText()+"-"+xField.getText()+"-"+yField.getText();
	      if (result == JOptionPane.OK_OPTION) {
Boolean test= (x.matches("---")||x.matches("(.*)---")||x.matches("-(.*)--")||x.matches("--(.*)-")||x.matches("---(.*)")
||x.matches("(.*)-(.*)--")||x.matches("--(.*)-(.*)")||x.matches("-(.*)-(.*)-")||x.matches("(.*)-(.*)-(.*)-")||x.matches("-(.*)-(.*)-(.*)"));
//custom regex 
	    	  if(test) 
	    		  return "";
	    	  else
	    		  return x;
	      }
	      else {
	      	return null;
	      	}
	}
	
	//modified original regview function
	 private static Property makeNewPropertyFromUserInput(String s) {
		String a = BasicInputCheck(s);
		String[] coord=new String[2];String[] size=new String[2];size[0]=a.split("-")[0];size[1]=a.split("-")[1];coord[0]=a.split("-")[2];coord[1]=a.split("-")[3];
		if(getRegControl().testPropL(Integer.parseInt(size[0]))) throw new BadLandRegistryException("Property below minimum size","The minimum property size entered must have a length of at least 20 m and a width of 10 m");
		if(getRegControl().testPropW(Integer.parseInt(size[1]))) throw new BadLandRegistryException("Property below minimum size","The minimum property size entered must have a length of at least 20 m and a width of 10 m");
		if(getRegControl().testPropExc(Integer.parseInt(coord[0]),Integer.parseInt(size[0]))) throw new BadLandRegistryException("Property exceeds available size","The property requested extends beyond the boundary of the available land");
		if(getRegControl().testPropExc(Integer.parseInt(coord[1]),Integer.parseInt(size[1]))) throw new BadLandRegistryException("Property exceeds available size","The property requested extends beyond the boundary of the available land");
		Property temp=getRegControl().propertyOverlaps(new Property(Integer.parseInt(size[0]),Integer.parseInt(size[1]),Integer.parseInt(coord[0]),Integer.parseInt(coord[1]),selected));
		if(temp!=null) 
		throw new BadLandRegistryException("Property overlap","The property entered overlaps with an existing property with coordinates "+temp.getXLeft()+"m, "+temp.getYTop()+"m and size "+temp.getXLength()+"m, "+temp.getYWidth()+"m");
		return (new Property(Integer.parseInt(size[0]),Integer.parseInt(size[1]),Integer.parseInt(coord[0]),Integer.parseInt(coord[1]),selected));
	 }
	
	
	private static JPanel loadRegistrantsPanel(ArrayList<Registrant> regs) {
		
		registrantsPanel.setPreferredSize(getStandardDimension(10, 192));
		registrantsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		JComboBox<String> cBox = new JComboBox<String>();
		cBox.setFont(defaultFont);
		cBox.setPreferredSize(getStandardDimension(-5,10));   
		if (regs != null && regs.size() == 1 && selected>=1000) {
			cBox.addItem("All Registration Numbers");
			for (Registrant reg: regs) {
			cBox.addItem(Integer.toString(reg.getRegNum()));}
			cBox.setSelectedIndex(1);
		}
		else if (regs != null && regs.size() >0) {
			cBox.addItem("All Registration Numbers");
			for (Registrant reg: regs) {cBox.addItem(Integer.toString(reg.getRegNum()));}
			cBox.setSelectedIndex(0);
		}//Modification of the starter code to adapt which SelectedIndex to display issue
		
		JPanel cBoxPanel = new JPanel();
		cBoxPanel.add(cBox);
		registrantsPanel.add(cBoxPanel);
		
		//Andy Wicks[2013]. Java - Simple ComboBox. Adapted from: Boxhttps://www.youtube.com/watch?v=iOV_oaJhABQ&t=214s
		cBox.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
			       	JComboBox cb = (JComboBox)e.getSource();
			    ArrayList<Registrant> temp =new ArrayList<>();
			    ArrayList<Property> prop=new ArrayList<>();
			    if(cb.getSelectedItem() == "All Registration Numbers") {
			    	selected=0;
			    	refreshCbox(getRegControl().listOfRegistrants(),null);
			    	refreshBtnToDefault();
			    }
			    else {
			    	selected=Integer.parseInt((String) cb.getSelectedItem());
			    	temp.add(getRegControl().findRegistrant(selected));
			    	prop.addAll(getRegControl().listOfProperties(selected));
			    	refreshCbox(temp,prop);
			    }
			     }});
		JScrollPane regScrollPanel = new JScrollPane(getRestrantsScrollPanel(regs));
		regScrollPanel.setPreferredSize(getStandardDimension(10,120));
		registrantsPanel.add(regScrollPanel);		
		return registrantsPanel;
	}
		
	private static JPanel loadEastBtnPanel() {
	eastBtnPanel.setLayout(new GridLayout(7, 1, SPACE, SPACE));/////////////////5->7
		
	eastBtnPanel.add(loadBtn("    Add New Registrant    ", 'l', true, (ActionEvent E)-> {
		try {getRegControl().addNewRegistrant(new Registrant(NameFormatCheck(BasicInputCheck(JOptionPane.showInputDialog("Enter registrant's name as (FirstName LastName): ")))));}
		catch(BadLandRegistryException e) 
		{JOptionPane.showMessageDialog(f,e.getMessage(),e.getHeader(),JOptionPane.WARNING_MESSAGE);}
		refresh();
		}));  
		
	eastBtnPanel.add(loadBtn("    Delete Registrant     ", 's', false, (ActionEvent E)-> {
			JOptionPane.showMessageDialog(f,"You will delete registrant #"+selected+" and its associated properties","Warning!",JOptionPane.WARNING_MESSAGE);
			getRegControl().deleteRegistrant(selected);
			getRegControl().deleteProperties(getRegControl().listOfProperties(selected));
			selected=0;
			refreshCbox(getRegControl().listOfRegistrants(),null);
			refreshBtnToDefault();
	    }));  
	
	
	eastBtnPanel.add(loadBtn("       Add Property       ", 's', false, (ActionEvent E)-> {
	try {getRegControl().addNewProperty(makeNewPropertyFromUserInput(propertyDialog()));
		ArrayList<Registrant> reg=new ArrayList<>();
		reg.add(getRegControl().findRegistrant(selected));
		refreshCbox(reg,getRegControl().listOfProperties(selected));}
	catch(BadLandRegistryException e) {JOptionPane.showMessageDialog(f,e.getMessage(),e.getHeader(),JOptionPane.WARNING_MESSAGE);}
	catch(NumberFormatException e) {JOptionPane.showMessageDialog(f,"Property data must be digits only!","Invalid input",JOptionPane.WARNING_MESSAGE);}
        })); 
	
	
	eastBtnPanel.add(loadBtn("  Delete Current Property ", 's', !getRegControl().listOfProperties(selected).isEmpty(), (ActionEvent E)-> {
		getRegControl().deleteProperties(getRegControl().listOfProperties(selected));
		refreshCbox(getRegControl().listOfRegistrants(),null);
		refreshBtnToDefault();
	    })); 
	
	eastBtnPanel.add(loadBtn("Change Registration Number", 's', false, (ActionEvent E)-> {
		try {
		int newRegNum=Integer.parseInt(BasicInputCheck(JOptionPane.showInputDialog("Enter new registrant's registration number: ")));
		getRegControl().changePropertyRegistrant(getRegControl().listOfProperties(selected), newRegNum);
		refresh();
		refreshCbox(getRegControl().listOfRegistrants(),getRegControl().listOfProperties(selected));
		refreshBtnToDefault();}
		catch(NumberFormatException e) {JOptionPane.showMessageDialog(f,"Registration number must contain digits only!","Invalid Registration number",JOptionPane.WARNING_MESSAGE);}
		catch(BadLandRegistryException e){JOptionPane.showMessageDialog(f,e.getMessage(),e.getHeader(),JOptionPane.WARNING_MESSAGE);}
        })); 
	
	eastBtnPanel.add(loadBtn("Sort Properties by Size", 's', getRegControl().listOfProperties(selected).size()>1, (ActionEvent E)-> {
		ArrayList <Property> props= new ArrayList <>();
		props.addAll(getRegControl().listOfProperties(selected));
		Collections.sort(props, new Comparator<Property>() {
		public int compare(Property A, Property B) {return A.getArea()-B.getArea();}});
		refreshSorted(props);
		}));
	
	eastBtnPanel.add(loadBtn("Sort Properties by Tax", 's', getRegControl().listOfProperties(selected).size()>1, (ActionEvent E)-> {
		ArrayList <Property> props= new ArrayList <>();
		props.addAll(getRegControl().listOfProperties(selected));
		Collections.sort(props, new Comparator<Property>() {
		public int compare(Property A, Property B) {return (int) (A.getTaxes() - B.getTaxes());}});
		refreshSorted(props);
		}));
	
		return eastBtnPanel;													
	}

	private static JPanel loadPropertyPanel(ArrayList<Property> props) {
		propertiesPanel.setPreferredSize(getStandardDimension(10, 140));
		JScrollPane propertyScrollPanel = new JScrollPane(getPropertiesScrollPanel(props)) ;
		propertyScrollPanel.setPreferredSize(getStandardDimension(5,120));
		propertiesPanel.add(propertyScrollPanel);
		return propertiesPanel;
	}
	
	private static JPanel loadSouthBtnPanel() {
		southBtnPanel.setLayout(new GridLayout(1, 2, SPACE, SPACE));
		
	southBtnPanel.add(loadBtn("Load Land Registry", 'r', true, (ActionEvent E)->{
			JOptionPane.showMessageDialog(f,"You will override existing record!","Warning!",JOptionPane.WARNING_MESSAGE);
			getRegControl().refreshProperties();
			getRegControl().refreshRegistrants();
			refreshCbox(getRegControl().listOfRegistrants(),null);
			//refresh();
			refreshBtnToDefault();
		}));    
		// TODO: replace null with ActionListener to load ArrayLists from Backup files
		
	southBtnPanel.add(loadBtn("Backup Land Registry", 'b', (!getRegControl().listOfRegistrants().isEmpty()), (ActionEvent e)->{
		boolean regsaved=getRegControl().saveToFile(getRegControl().listOfRegistrants(), REGISTRANTS_FILE);
		 boolean propsaved=getRegControl().saveToFile(getRegControl().listOfAllProperties(), PROPERTIES_FILE);
		if(regsaved||propsaved) 
		JOptionPane.showMessageDialog(f,"Land Registry has been backed up to file","Operation Success",JOptionPane.WARNING_MESSAGE);
	   })); // TODO: replace null with ActionListener to backup ArrayLists to files
		
	southBtnPanel.setAlignmentX(RIGHT_ALIGNMENT);
		mainPanel.add(loadBtn("       Exit        ", 'x', true, (ActionEvent e)->f.dispose()), exitBtnConstraints);	  
		return southBtnPanel;
	}
	
	private static JPanel getRestrantsScrollPanel(ArrayList<Registrant> regs) {
	   JPanel regsScrollPanel = new JPanel(new GridLayout(regs==null?1:regs.size(), 1));
	   if (regs != null && !regs.isEmpty()) 
	      for (Registrant reg: regs) {
	         JPanel thisRegsNames = new JPanel(new GridLayout(3,1));
	         thisRegsNames.setPreferredSize(getStandardDimension(-20,120));
	         thisRegsNames.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
	         thisRegsNames.add(displayInformation("Registrant's #: ", Integer.toString(reg.getRegNum())));
	         thisRegsNames.add(displayInformation("First Name:     ", reg.getFirstName()));
	         thisRegsNames.add(displayInformation("Last Name:      ", reg.getLastName()));
	         regsScrollPanel.add(thisRegsNames);
	      }  
	   return regsScrollPanel;
	}
	
	private static JPanel getPropertiesScrollPanel(ArrayList<Property> props) {
		   JPanel propsScrollPane = new JPanel(new GridLayout(props==null?1:props.size(), 1));
		   propsScrollPane.setPreferredSize(getStandardDimension(-20, 580));
		   
		   if (props != null && !props.isEmpty()) 
		      for (Property prop: props) {
		         JPanel thisPropertyInfo = new JPanel(new GridLayout(3,2));
		         thisPropertyInfo.setPreferredSize(getStandardDimension(-20,240));
		         thisPropertyInfo.setBorder(BorderFactory.createLineBorder(Color.black));
		         thisPropertyInfo.add(displayInformation("Left  ", Integer.toString(prop.getXLeft())));
		         thisPropertyInfo.add(displayInformation("Top   ", Integer.toString(prop.getYTop())));
		         thisPropertyInfo.add(displayInformation("Length", Integer.toString(prop.getXLength())));
		         thisPropertyInfo.add(displayInformation("Width ", Integer.toString(prop.getYWidth())));
		         thisPropertyInfo.add(displayInformation("Area  ", Integer.toString(prop.getArea())));
		         thisPropertyInfo.add(displayInformation("Taxes ", Double.toString(prop.getTaxes())));
		         propsScrollPane.add(thisPropertyInfo);
		      }  
		   return propsScrollPane;
		}
	
	// Utility to set a button in a JPanel ON/OFF.  Use constants above for component number, and ON/OFF values for boolean parameter
	private static boolean setBtnIn(JPanel p, int componentNumber, boolean onOff) {  
		if (p.getComponentCount() < componentNumber) return false;
		JButton b = (JButton)p.getComponent(componentNumber - 1);
		b.setEnabled(onOff);
		return true;
	}
	
	// Utility to set a group of buttons in a JPanel ON/OFF.  Use ON/OFF for boolean parameter, and set the value of exceptFor with the buttons to be excluded
	private static void turnAllBtnsIn(JPanel p, boolean b, Integer... exceptFor) {
		List<Integer> arInt = Arrays.asList(exceptFor);
		for (int n = 1; n <= p.getComponentCount(); n++ ) {
			if (!(arInt.contains(n)))
				setBtnIn(p, n, b);
		}
	}
	
	// Gets standard dimension information and resizes its length and width by deltaX and deltaY
	private static Dimension getStandardDimension(int deltaX, int deltaY) {
		 Dimension d = (Dimension)defaultDimension.clone();
		 d.setSize(d.getWidth() + deltaX, d.getHeight() + deltaY);
		 return d;
	 }
	
	private static JPanel displayInformation(String label, String name) {
		JLabel l = new JLabel(label); 
		l.setFont(defaultFont);
		
		JTextField t = new JTextField(); 
	    t.setFont(defaultFont);   t.setPreferredSize(getStandardDimension(-360,0));
	    t.setEditable(false);     t.setEnabled(false);    
	    t.setText(name); 
	    
	    JPanel p = new JPanel();
	    p.add(l); p.add(t);
	    return p;
	}
	
	private static JButton loadBtn(String label, char keyboardShortcut, boolean onOff, ActionListener act) {
		JButton btn = new JButton(label);
		btn.addActionListener(act);
		btn.setFont(defaultFont);
		btn.setEnabled(onOff);
		btn.setMnemonic(keyboardShortcut);
		return btn;
	} 
}
