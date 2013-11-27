package classes;

public class ListItem {
	public String username;
	
	// main constructor
	public ListItem(String username) {
		super();
		this.username = username;
	}

	// String representation
	public String toString() {
		return this.username;
	}
}