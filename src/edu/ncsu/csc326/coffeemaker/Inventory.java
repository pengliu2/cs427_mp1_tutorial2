package edu.ncsu.csc326.coffeemaker;

import edu.ncsu.csc326.coffeemaker.db.InventoryDB;
import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;

/**
 * @author Sarah Heckman
 *
 * Inventory for the coffee maker
 */
public class Inventory {
    
    /**
     * Creates a coffee maker inventory object and
     * fills each item in the inventory with 15 units.
     */
    public Inventory() {
    	setCoffee(15);
    	setMilk(15);
    	setSugar(15);
    	setChocolate(15);
    }
    
    private void storeInventory(String name, int amt) {
    	int curr = 0;
    	curr = InventoryDB.checkInventory(name);
    	if(amt >= 0) {
    		amt -= curr;
    		InventoryDB.addInventory(name, amt);
    	}
    }

    /**
     * Returns the current number of chocolate units in 
     * the inventory.
     * @return int
     */
    public int getChocolate() {
    	int chocolate = -1;
    	chocolate = InventoryDB.checkInventory("chocolate");
        return chocolate;
    }
    
    /**
     * Sets the number of chocolate units in the inventory
     * to the specified amount.
     * @param chocolate
     */
    public synchronized void setChocolate(int chocolate) {
    	storeInventory("chocolate", chocolate);
    }
    
    /**
     * Add the number of chocolate units in the inventory 
     * to the current amount of chocolate units.
     * @param chocolate
     * @throws InventoryException
     */
    public synchronized void addChocolate(String chocolate) throws InventoryException {
    	int amtChocolate = 0;
    	try {
    		amtChocolate = Integer.parseInt(chocolate);
    	} catch (NumberFormatException e) {
    		throw new InventoryException("Units of chocolate must be a positive integer");
    	}
		if (amtChocolate >= 0) {
			InventoryDB.addInventory("chocolate", amtChocolate);
		} else {
			throw new InventoryException("Units of chocolate must be a positive integer");
		}
    }
    
    /**
     * Returns the current number of coffee units in
     * the inventory.
     * @return int
     */
    public int getCoffee() {
        return InventoryDB.checkInventory("coffee");
    }
    
    /**
     * Sets the number of coffee units in the inventory 
     * to the specified amount.
     * @param coffee
     */
    public synchronized void setCoffee(int coffee) {
    	storeInventory("coffee", coffee);
    }
    
    /**
     * Add the number of coffee units in the inventory 
     * to the current amount of coffee units.
     * @param coffee
     * @throws InventoryException
     */
    public synchronized void addCoffee(String coffee) throws InventoryException {
    	int amtCoffee = 0;
    	try {
    		amtCoffee = Integer.parseInt(coffee);
    	} catch (NumberFormatException e) {
    		throw new InventoryException("Units of coffee must be a positive integer");
    	}
		if (amtCoffee >= 0) {
			InventoryDB.addInventory("coffee", amtCoffee);
		} else {
			throw new InventoryException("Units of coffee must be a positive integer");
		}
    }
    
    /**
     * Returns the current number of milk units in
     * the inventory.
     * @return int
     */
    public int getMilk() {
    	int amt = -1;
    	amt = InventoryDB.checkInventory("milk");
        return amt;
    }
    
    /**
     * Sets the number of milk units in the inventory
     * to the specified amount.
     * @param milk
     */
    public synchronized void setMilk(int milk) {
    	storeInventory("milk", milk);
    }
    
    /**
     * Add the number of milk units in the inventory 
     * to the current amount of milk units.
     * @param milk
     * @throws InventoryException
     */
    public synchronized void addMilk(String milk) throws InventoryException {
    	int amtMilk = 0;
    	try {
    		amtMilk = Integer.parseInt(milk);
    	} catch (NumberFormatException e) {
    		throw new InventoryException("Units of milk must be a positive integer");
    	}
		if (amtMilk >= 0) {
			InventoryDB.addInventory("milk", amtMilk);
		} else {
			throw new InventoryException("Units of milk must be a positive integer");
		}
    }
    
    /**
     * Returns the current number of sugar units in 
     * the inventory.
     * @return int
     */
    public int getSugar() {
    	return InventoryDB.checkInventory("sugar");
    }
    
    /**
     * Sets the number of sugar units in the inventory
     * to the specified amount.
     * @param sugar
     */
    public synchronized void setSugar(int sugar) {
    	storeInventory("sugar", sugar);
    }
    
    /**
     * Add the number of sugar units in the inventory 
     * to the current amount of sugar units.
     * @param sugar
     * @throws InventoryException
     */
    public synchronized void addSugar(String sugar) throws InventoryException {
    	int amtSugar = 0;
    	try {
    		amtSugar = Integer.parseInt(sugar);
    	} catch (NumberFormatException e) {
    		throw new InventoryException("Units of sugar must be a positive integer");
    	}
		if (amtSugar >= 0) { 
			InventoryDB.addInventory("sugar", amtSugar);
		} else {
			throw new InventoryException("Units of sugar must be a positive integer");
		}
    }
    
    /**
     * Returns true if there are enough ingredients to make
     * the beverage.
     * @param r
     * @return boolean
     */
    protected synchronized boolean enoughIngredients(Recipe r) {
        boolean isEnough = true;
		do {
			if (InventoryDB.checkInventory("coffee") < r.getAmtCoffee()) {
				isEnough = false;
			}
			if (InventoryDB.checkInventory("milk") < r.getAmtMilk()) {
				isEnough = false;
			}
			if (InventoryDB.checkInventory("sugar") < r.getAmtSugar()) {
				isEnough = false;
			}
			if (InventoryDB.checkInventory("chocolate") < r.getAmtChocolate()) {
				isEnough = false;
			}
		} while (false);
        return isEnough;
    }
    
    /**
     * Removes the ingredients used to make the specified 
     * recipe.  Assumes that the user has checked that there
     * are enough ingredients to make 
     * @param r
     */
    public synchronized boolean useIngredients(Recipe r) {
    	if (enoughIngredients(r)) {
	    	InventoryDB.useInventory("coffee", r.getAmtCoffee());
	    	InventoryDB.useInventory("milk", r.getAmtMilk());
	    	InventoryDB.useInventory("sugar", r.getAmtSugar());
	    	InventoryDB.useInventory("chocolate" ,r.getAmtChocolate());
	    	return true;
    	} else {
    		return false;
    	}
    }
    
    /**
     * Returns a string describing the current contents 
     * of the inventory.
     * @return String
     */
    public String toString() {
    	StringBuffer buf = new StringBuffer();
    	buf.append("Coffee: ");
    	buf.append(getCoffee());
    	buf.append("\n");
    	buf.append("Milk: ");
    	buf.append(getMilk());
    	buf.append("\n");
    	buf.append("Sugar: ");
    	buf.append(getSugar());
    	buf.append("\n");
    	buf.append("Chocolate: ");
    	buf.append(getChocolate());
    	buf.append("\n");
    	return buf.toString();
    }
    
    public void setInventory(String name, int amt) {
    	if ("coffee".equals(name)) {
    		setCoffee(amt);
    	} else if ("milk".equals(name)) {
    		setMilk(amt);
    	} else if ("sugar".equals(name)) {
    		setSugar(amt);
    	} else if ("chocolate".equals(name)) {
    		setChocolate(amt);
    	}
    }
}
