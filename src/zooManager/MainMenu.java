/* Zoo Manager Application v.03
 *     Author:	Michael Provan
 *       Date: 	Oct 13, 2017
 *      Class: 	COP2552.0M1
 * Assignment:	Project 2
 * 
 *   Includes:	DataManager.java
 *   			ZooManager.java
 *   			Animal.java
 * 
 * Java SE 1.8
 * 
 * 
 * == Notes:
 * Not a lot of infrastructure effort went into this class.
 * Replacing the entire menu system with a GUI is on the TODO
 * since the next project involves using JavaFX for a GUI.
 * 
 * display() just displays a basic main menu
 * 
 * viewMenu(int) takes in an integer, passes it through a switch
 * and presents the resulting menu option.
 * 
 * It doesn't do anything special other than light input validation
 * and formatting data returned from methods it calls.
 */

package zooManager;

import java.util.Map;

public class MainMenu {
	
	
	
	public static void display(){
		System.out.println("======================");
		System.out.println("|   MAIN MENU        |");
		System.out.println("======================");
		System.out.println("| 1. Zoo Info        |");
		System.out.println("| 2. Display Data    |");
		System.out.println("| 3. Search Data     |");
		System.out.println("| 4. Enter Data      |");
		System.out.println("|                    |");
		System.out.println("| 0. Save & Exit     |");
		System.out.println("======================");
	}
	
	
	public static void viewMenu(int menuID) {
		String userOption, newName, newDesc;
		String[] newDiet;
		Animal a;
		
		switch(menuID) {
			case 1:
				System.out.println("======================");
				System.out.println("|   ZOO INFO         |");
				System.out.println("======================");
				System.out.println("| Name: " + SaveData.player.getBusinessName());
				System.out.println("| Owner: " + SaveData.player.getFullName());
				System.out.println("| Animals: " + Animal.animalCount);
				System.out.println("======================");
				
				display();
				break;
			case 2:
				System.out.println("======================");
				System.out.println("|   ALL ANIMALS      |");
				System.out.println("======================");
				for(Map.Entry<String,Edible> m : SaveData.animalsMap.entrySet()) {
					System.out.println("| " + m.getValue().getName());
				}
				System.out.println("======================");
				
				display();
				break;
			case 3:
				System.out.println("======================");
				System.out.println("|   SEARCH DATA      |");
				System.out.println("======================");
				System.out.print("|Search: ");
				
				if(ZooManager.userInput.hasNextLine())
					ZooManager.userInput.nextLine();
				
				userOption = ZooManager.userInput.nextLine();
				System.out.println("| ");
				
				a = DataManager.searchAnimals(userOption);
				
				if(a!=null) {
					System.out.println("| Name: " + a.getName());
					System.out.println("|     " + a.getDesc());
					System.out.println("| ");
					System.out.println("| Diet: " + a.getDietAsString(", "));
				}
				else
					System.out.println("| No Results found for " + userOption);
				
				System.out.println("======================");
				
				display();
				break;
			case 4:
				System.out.println("======================");
				System.out.println("|   ENTER DATA       |");
				System.out.println("======================");
				System.out.println("| Add new Animal entry:");
				System.out.println("| (Do not use commas,");
				System.out.println("|  except to separate");
				System.out.println("|    foods in diet)  ");
				System.out.println("| ");
				
				if(ZooManager.userInput.hasNextLine())
					ZooManager.userInput.nextLine();
				
				System.out.print("| Name: ");
				userOption = ZooManager.userInput.nextLine();
				newName = userOption.split(",")[0];		//This should ignore any commas and what comes after it if the user puts them in
				
				System.out.print("| Description: ");
				userOption = ZooManager.userInput.nextLine();
				newDesc = userOption.split(",")[0];
				
				System.out.print("| Diet: ");
				userOption = ZooManager.userInput.nextLine();
				newDiet = userOption.split(",");
				
				DataManager.addNewAnimal(newName, newDesc, newDiet);

				System.out.println("| ");
				
				a = DataManager.searchAnimals(newName);
				
				if(a!=null) {
					System.out.println("| Added the following: ");
					System.out.println("| Name: " + a.getName());
					System.out.println("|     " + a.getDesc());
					System.out.println("| Diet: " + a.getDietAsString(", "));
				}
				else
					System.out.println("| Failed to create '" + newName + "'");
				
				System.out.println("======================");
				break;
			default:
				System.out.println(menuID + " is not an available option. Try again!");
				display();
				break;
		}
	}
	

}

class Menu {
	
	String title;
	
}