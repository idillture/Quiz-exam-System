package quiz;
	
	//Base class for all users in the system (Student, Teacher).
	 
	public abstract class User {

	    private int id;

	    private String username;

	    private String password;

	    private String fullName;

	    // Creates a new user with given information.
	    public User(int id, String username, String password, String fullName) {
	        this.id = id;
	        this.username = username;
	        this.password = password;
	        this.fullName = fullName;
	    }

	    // Getter

	    public int getId() {
	        return id;
	    }

	    public String getUsername() {
	        return username;
	    }

	    public String getFullName() {
	        return fullName;
	    }
}