/* Zoo Manager Application v.03
 *     Author:	Michael Provan
 *       Date: 	Oct 13, 2017
 *      Class: 	COP2552.0M1
 * Assignment:	Project 2
 * 
 *   Includes:	Player.java
 *   			Edible.java
 *   			Animal.java
 * 
 * Java SE 1.8
 * 
 * 
 * == Notes:
 * This is the heart of the whole application.
 * The Data Manager has a local class SaveData for storing Save Data
 * that it manages. Ideally, only the DataManager would have access
 * to SaveData directly. However, that would require more infrastructure
 * work that I did not have time to implement. That'll be something TODO
 * 
 * Initialize sets up filenames and such, then calls the load method to
 * store data from an external file into SaveData.
 * 
 * Save simply translates the data in SaveData into strings that are
 * then printed into an external file with a PrintWriter. It has
 * exception handling, but since PrintWriter creates files that it
 * can't find, it isn't likely to happen. I even tested this by 
 * deleting save.dat while the app was running. It was able to create
 * a new save.dat and save successfully without presenting a single error.
 * 
 * Load takes the strings in save.dat, splits them up by the commas and
 * passes the appropriate data through the methods required to create
 * Edible/Animal objects from it. It stores the objects into arrays, then
 * also maps them to a hashmap to be stored in SaveData.
 * TODO The arrays aren't ever used after being loaded. The app only uses the
 * 	hashmaps. So removal of the arrays may be a good idea.
 * 
 * searchAnimals takes in a string then checks that against the keys in the
 * animal hashmap in SaveData. Since all animals must have a unique name, if 
 * there is a perfect match, the object can be returned immediately.
 * It also checks for partial matches while looping, if it finds one, it stores
 * the first partial match in a temp container, then stops looking for partial matches.
 * If no perfect match is found, it returns that partial match.
 * TODO due to the animals hashmap storing values as Edibles, this method requires
 * 	the results to be cast as Animals before being returned. Changing the hashmap
 * 	value type to Animal may be more elegant.
 * 
 * addNewAnimal simply takes the passed in values and constructs a new Animal from
 * them and puts it into both edibles and animals hashmaps in SaveData. All validation
 * should be taken care of prior to calling this method.
 */

package application;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class DataManager {
	
	public static boolean isInitialized = false;
	public static String status;
	
	//Filename for save file. Using .dat so that average user has harder time opening it
	public static final String SAVE_FILE_NAME = "save.dat";
	
	//Static containers
	private static String fileName;
	private static File file;
	
	//Method to initialize SaveManager. Loads file, as well.
	public static void initialize(){
		if(!isInitialized) {
			setFileName(SAVE_FILE_NAME);
			setFile(fileName);
			load();
			System.out.println("Profile for " + SaveData.player.getFullName() + " loaded.");
			status = "Successfully loaded " + Edible.edibleCount + " edible objects, including " + Animal.animalCount + " animal objects.";
			System.out.println(status);
			isInitialized = true;
		}
	}
	
	
	//Sets
	private static void setFileName(String newFileName) {
		fileName = newFileName;
	}
	private static void setFile(String newFileName) {
		file = new File(newFileName);
	}
	
	//Data Management Methods
	
	//Save existing objects in SaveData to external file
	public static void save() {
		Player p = SaveData.player;
		java.io.PrintWriter pw = null;
		
		try {
			pw = new java.io.PrintWriter(file);
			
			pw.print(p.getFirstName() + "," + p.getLastName() + "," + p.getBusinessName());
			
			for(Map.Entry<String, Edible> m : SaveData.ediblesMap.entrySet()) {
				pw.println();
				pw.print(m.getValue().getName());
				pw.print(",");
				pw.print(m.getValue().getDesc());
				
				if(SaveData.animalsMap.containsKey(m.getKey())) {
					pw.print(",");
					Animal temp = (Animal)SaveData.animalsMap.get(m.getKey());
					pw.print(temp.getDietAsString(","));
				}
			}
		} catch(FileNotFoundException e) {
			try {
				status = "No Save File Found. Creating New Save File...";
				System.out.println(status);
				file.createNewFile();
			} catch (IOException x) {
				status = "Error: IO Exception occured while creating new save file.";
				System.out.println(status);
			} catch (SecurityException x) {
				status = "Error: Security Access to manipulate files denied.";
				System.out.println(status);
			} catch (Exception x) {
				status = "Error: Something unexpected happened...";
				System.out.println(status);
			} finally {
				if(file.exists()) {
					status = "Save File created successfully.";
					System.out.println(status);
					save();
				}
				else
					status = "Failed to create Save File";
					System.out.println(status);
			}
		} catch (Exception e) {
			status = "Error: Something unexpected happened...";
			System.out.println(status);
		} finally {
			if(pw != null)
				pw.close();
			if(file.exists())
				status = "Data Saved.";
				System.out.println(status);
		}
	}
	
	//Load objects into SaveData from external file
	public static void load() {
		
		//Check if a save file exists already. If not, try to create it, and throw exceptions if an error occurs.
		int inputLines = 0, numAnimals = 0;
		String rawData = "";
		String[][] rawArray;
		
		System.out.println("Loading Save Data...");
		//Try/Catch around File accessing
		try {
			Scanner input = new Scanner(file);
			while(input.hasNextLine()) {
				rawData += input.nextLine() + "\n";
				inputLines++;
			}
			input.close();
		} catch (FileNotFoundException e) {
			try {
				System.out.println("No Save File Found. Creating New Save File...");
				file.createNewFile();
			} catch (IOException x) {
				System.out.println("Error: IO Exception occured while creating new save file.");
			} catch (SecurityException x) {
				System.out.println("Error: Security Access to manipulate files denied.");
			} catch (Exception x) {
				System.out.println("Error: Something unexpected happened...");
			} finally {
				if(file.exists()) {
					System.out.println("Save File created successfully.");
					load();
				}
				else
					System.out.println("Failed to create Save File");
			}
		} catch (Exception e) {
			System.out.println("Error: Something unexpected happened...");
		} 
		
		rawArray = new String[inputLines][];
		
		for(int i = 0 ; i < rawArray.length ; i++) {
			rawArray[i] = rawData.split("\n")[i].split(",");
			if(i!= 0 && rawData.split("\n")[i].split(",").length > 2)
				numAnimals++;
		}
		
		SaveData.edibles = new Edible[(rawArray.length>0)?rawArray.length-1:0];
		SaveData.animals = new Animal[numAnimals];
		SaveData.ediblesMap = new HashMap<String,Edible>();
		SaveData.animalsMap = new HashMap<String,Edible>();
		int k = 0, j = 0;
		for(String[] s : rawArray) {
			if(s == rawArray[0]) {
				SaveData.player = new Player(s[0],s[1],s[2]);
			}
			else {
				SaveData.edibles[k] = new Edible(s[0],s[1]);
				SaveData.ediblesMap.put(SaveData.edibles[k].getName().toUpperCase(), SaveData.edibles[k]);
				k++;
			}
			
			if(s.length > 2 && s!= rawArray[0]) {
				SaveData.edibles[k-1] = SaveData.animals[j] = new Animal(SaveData.edibles[k-1],Arrays.copyOfRange(s,2,s.length));
				SaveData.animalsMap.put(
						SaveData.animals[j].getName().toUpperCase(), 
						SaveData.animals[j]);
				j++;
			}
		}
		
	}
	
	//Search Data for a string/substring, return the id of the object. returns null if no object found.
	public static Animal searchAnimals(String searchQuery) {
		String s = searchQuery.toUpperCase();
		boolean isPartial = false;
		String partialKey = null;
		
		for(Map.Entry<String, Edible> m : SaveData.animalsMap.entrySet()) {
			if(m.getKey().equals(s))
				return (Animal) m.getValue();
			
			if(m.getKey().contains(s) && !isPartial) {
				isPartial = true;
				partialKey = m.getKey();
			}
		}
		
		if(isPartial && partialKey != null)
			return (Animal) SaveData.animalsMap.get(partialKey);
		
		return null;
	}
	
	//Add new Animal to SaveData
	public static void addNewAnimal(String name, String description, String[] diet) {
		SaveData.ediblesMap.put(name.toUpperCase(), new Animal(new Edible(name,description), diet));
		SaveData.animalsMap.put(name.toUpperCase(), SaveData.ediblesMap.get(name.toUpperCase()));
	}
		
}

class SaveData{
	
	//Static containers
	static Player player;
	static Edible[] edibles;
	static Animal[] animals;
	static Map<String, Edible> ediblesMap, animalsMap;
	
}