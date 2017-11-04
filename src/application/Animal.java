/* Zoo Manager Application v.03
 *     Author:	Michael Provan
 *       Date: 	Oct 13, 2017
 *      Class: 	COP2552.0M1
 * Assignment:	Project 2
 * 
 *   Includes:	Edible.java
 * 
 * Java SE 1.8
 * 
 * == Notes:
 * Just a Data storage class. Subclass of Edible.
 * TODO Learn more about inheritance/overriding/polymorphism/etc
 * 	I ran into many complications with things not responding as
 * 	I expected between Edible and Animal objects.
 * 
 * getDietAsString takes a delimiter and returns the diet array as 
 * a single String separated by the delimiter
 */

package application;

//All animals are edible, have the addition of a list of other Edibles they themselves can consume
public class Animal extends Edible {
	
	public String[] diet; //TODO make this an array of Edible objects
	
	static int animalCount = 0;
	
	
	//=====Constructors=====//
	
	//For turning an Edible into an Animal
	Animal(Edible e, String[] d){
		++animalCount;
		
		setName(e.getName());
		setDesc(e.getDesc());
		id = e.getID();
		diet = d;
		
		//Effectively delete the original Edible object
		e = null;
	}
	
	
	//Methods//
	public String getDietAsString(String delimiter) {
		String d = "";
		
		for(String s : diet) {
			d += s;
			if(s != diet[diet.length-1])
				d+= delimiter;
		}
		
		return d;
	}
	
	
}
