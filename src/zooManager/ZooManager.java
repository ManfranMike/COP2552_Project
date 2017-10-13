/* Zoo Manager Application v.03
 *     Author:	Michael Provan
 *       Date: 	Oct 13, 2017
 *      Class: 	COP2552.0M1
 * Assignment:	Project 2
 * 
 *   Includes:	DataManager.java
 *   			MainMenu.java
 * 
 * Java SE 1.8
 * 
 * 
 * == Notes:
 * This class contains the Main method which initializes
 * the DataManager (thus loading from a save file)
 * 
 * TODO User should be prompted for Name and Zoo Name
 * 
 * Main then displays the MainMenu and starts a menu loop
 * that takes user input for navigating menus.
 * 
 * If the user enters a non-integer, it tells them to try again
 * If the user inputs 0 twice, the app saves and exits.
 * If the user enters nothing, nothing happens
 * 
 * HINT: If you rename/delete save.dat, the app will function properly
 * but will not display any animals until the user inputs new ones
 * via the Enter Data menu.
 * 
 */

package zooManager;

import java.util.Scanner;

public class ZooManager {
	
	public static Scanner userInput = new Scanner(System.in);
	
	public static void main(String[] args) {
		DataManager.initialize();	//Gets SaveManager ready and loads/creates save.dat
		boolean menuLoop = true;
		int userOption;
		
		
		// Console Menu Stuff. A bit sloppy, but will be replaced by GUI soon anyway
		// TODO Replace Menus with GUI
		MainMenu.display();
		
		while(menuLoop) {
			try { userOption = userInput.nextInt(); }
			catch(java.util.InputMismatchException e) {
				System.out.print("Error: Invalid Input.\n Please Enter an integer to select an option: ");
				if(userInput.hasNextLine())
					userInput.nextLine();
				continue;
			}
			
			if(userOption == 0) {
				System.out.print("Type 0 again if you'd like to exit: ");
				userOption = userInput.nextInt();
				if(userOption == 0) {
					menuLoop = false;
					DataManager.save();
				}
				continue;
			}
			
			MainMenu.viewMenu(userOption);
			
		}
		
		System.out.println("Exiting Application...");
		userInput.close();
	}

}
