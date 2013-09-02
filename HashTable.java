/**
 * 
 */
package hash;



/**
 * @author Andrew
 * creates a hash table with an inner Entry class that allows for mapping a value
 * to a unique key formed by using the hash code of an object.
 */
public class HashTable<K, V>{

	private Entry<K, V>[] table;
	private static final int START_CAPACITY = 3;
	private int numKeys;
	private int numDeletes;
	private String allEntries;
	private final Entry<K, V> DELETED = new Entry<K, V>(null, null);
	
	/**
	 * constructor that creates an empty Entry array with the specified Start Capacity.
	 */
	public HashTable(){
		table = new Entry[START_CAPACITY];
	}
	
	/**
	 * finds an object in the hash table and returns its array location.
	 * @param key
	 * @return index of the specified key
	 */
	private int find(Object key){
		int index = key.hashCode() % table.length;
		if (index < 0){
			index += table.length;
		}
		while ((table[index] != null) && (!key.equals(table[index].key))){
			index++;
			if (index >= table.length){
				index = 0;
			}
		}
		return index;
	}
	
	/**
	 * If the array is full, this method calls the rehash method to create a larger one.
	 */
	private void isFull(){
		if (table.length <= numKeys + numDeletes){
			rehash();
		}
	}
	
	/**
	 * finds a key and returns its corresponding value.
	 * @param key
	 * @return value of a specific key
	 */
	public V get(Object key){
		int index = find(key);
		if (table[index] != null){
			return table[index].value;
		}
		else return null;
	}
	
	/**
	 * recursive method that enters a new key/value pairing if the key does not exist
	 * or replaces the old value of the key if it does
	 * @param key
	 * @param value
	 * @return null or the old value being replaced
	 */
	public V put(K key, V value){
		isFull();
		int index = find(key);
		if (table[index] == null){
			table[index] = new Entry<K, V>(key, value);
			numKeys++;
			return null;
		}
		else if (table[index].key.equals(key)){
			V oldVal = table[index].value;
			table[index].value = value;
			return oldVal;
			}
			else {
				rehash();
				return put(key, value);
			}
	}
	
	/**
	 * removes an object from the hash table
	 * @param key
	 * @return the value of the object being removed
	 */
	public V remove(Object key){
		int index = find(key);
		if (table[index] == null){
			return null;
		}
		V result = table[index].value;
		table[index] = DELETED;
		numKeys --;
		numDeletes ++;
		return result;
	}
	
	/**
	 * creates a string to display all keys and values of the hash table
	 * @return allEntries
	 */
	public String toString(){
		allEntries = null;
		if (table.length == 0){
			return null;
		}
		for (int i = 0; i <= table.length - 1; i++){
			if ((null != table[i]) && (table[i] != DELETED)){
				if (allEntries != null){
					allEntries += "\n";
					allEntries += "name: " + table[i].key + " number: " + table[i].value;
				}
				else
					allEntries = "name: " + table[i].key + " number: " + table[i].value;
			}
		}
		return allEntries;
	}
	
	/**
	 * creates a larger array and copies over all elements already existing in the previous array.
	 */
	private void rehash(){
		Entry<K, V>[] oldTable = table;
		table = new Entry[2 * oldTable.length + 1];
		numKeys = 0;
		numDeletes = 0;
		for (int i = 0; i < oldTable.length; i++){
			if ((oldTable[i] != null) && (oldTable[i] != DELETED)){
				put(oldTable[i].key, oldTable[i].value);
			}
		}
	}
	
	/**
	 * 
	 * @author Andrew
	 * Internal class that creates a key/value pairing with supporting methods
	 * @param <K>
	 * @param <V>
	 */
	private static class Entry<K, V>{
		private K key;
		private V value;
		
		/**
		 * constructor method that sets the entries key and value
		 * @param key
		 * @param value
		 */
		public Entry(K key, V value){
			this.key = key;
			this.value = value;
		}
		
	}
}

/**
 * 
 */
package hash;
import javax.swing.*;
/**
 * @author Andrew
 * Driver for the Hash Table class
 */
public class HashTableDriver {

	/**
	 * Main method of the driver program
	 * Gives the user five options for manipulating the hash table
	 * @param args
	 */
	public static void main(String[] args) {
		HashTable<String, String> table = new HashTable();
		int quit = 0;
		String[] choices = {"Insert new entry", "Find someone's phone number", 
				"Delete an entry", "Display all entries", "Quit"};
		while(quit != 1){
			int choice = JOptionPane.showOptionDialog(null, "Choose an option", "Directory",
					 JOptionPane.DEFAULT_OPTION,
					 JOptionPane.QUESTION_MESSAGE,
					 null, choices, -1);
			switch(choice){
			case 0: 
				String name = JOptionPane.showInputDialog("Please enter a name");
				String number = JOptionPane.showInputDialog("Please enter a phone number");
				table.put(name, number);
				break;
			case 1:
				name = JOptionPane.showInputDialog("Please enter a name to find");
				number = table.get(name);
				JOptionPane.showMessageDialog(null, 
						name + "'s number is " + number, 
						null, JOptionPane.OK_OPTION, null);
				break;
			case 2:
				name = JOptionPane.showInputDialog("Please enter a name to delete");
				number = table.remove(name);
				JOptionPane.showMessageDialog(null, 
						"Deleted " + name + "'s number (" + number + ") from directory", 
						null, JOptionPane.OK_OPTION, null);
				break;
			case 3:
				if (table.toString() == null){
					JOptionPane.showMessageDialog(null, 
							"There are no entries yet", 
							null, JOptionPane.OK_OPTION, null);
				}
				else {JOptionPane.showMessageDialog(null, 
						table.toString(), 
						null, JOptionPane.OK_OPTION, null);
				}
				break;
			case 4:
				quit = 1;
				break;
			}
		}
	}
}
