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
 * Data storage class for Player Data.
 * Get/Set methods for things that may be needed outside of it
 */

package application;

public class Player {
	
	private static String firstName, lastName, businessName;
	
	//Constructor
	Player(String f, String l, String b){
		setFirstName(f);
		setLastName(l);
		setBusinessName(b);
	}
	
	//Get Methods
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getFullName() {
		return firstName + " " + lastName;
	}
	public String getBusinessName() {
		return businessName;
	}
	
	//Set Methods
	public void setFirstName(String s) {
		firstName = s;
	}
	public void setLastName(String s) {
		lastName = s;
	}
	public void setBusinessName(String s) {
		businessName = s;
	}
}
