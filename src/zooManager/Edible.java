/* Zoo Manager Application v.03
 *     Author:	Michael Provan
 *       Date: 	Oct 13, 2017
 *      Class: 	COP2552.0M1
 * Assignment:	Project 2
 * 
 *   Includes:	n/a
 * 
 * Java SE 1.8
 * 
 * == Notes:
 * Just a data storage class with a few get/set methods.
 */

package zooManager;


//This class describes all edible objects in the application. Edibles include Animals.
public class Edible {
	
	//variables that describe the edible
	private String name, desc;
	
	static int edibleCount = 0;	//goes up when an edible is made
	int id;	//Each edible created gets a unique id
	
	//=====Constructors and Overloaded alternatives=====//
	Edible(String n, String d){
		id = edibleCount++;
		
		name = n;
		desc = d;
	}
	Edible(){
		
	}
	
	//===== Methods =====//
	
	//Get and set for name
	public String getName() {
		return name;
	}
	public void setName(String newName) {
		name = newName;
	}
	
	//get/set for description
	public String getDesc() {
		return desc;
	}
	public void setDesc(String newDesc) {
		desc = newDesc;
	}
	
	//get id
	public int getID() {
		return id;
	}
	
}
