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
