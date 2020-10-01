
package landRegistry;
/**
 * This is the registry launcher class that consists of the mian method and it simply starts the program
 * @author SamLiu
 * @version 3.0
*/
public class RegLauncher {
/**
 * main method of the program
 * 
 * @param arg arg parameter
 */
	
	public static void main(String[] args) {
	javax.swing.SwingUtilities.invokeLater(new Runnable() {public void run() 
	{
		RegViewGUI.launchDialog();
		
		
	}});
}
}