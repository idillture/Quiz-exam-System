package quiz;

public class Teacher extends User {
	
	 public Teacher(int id, String username, String password, String fullName) {

	        super(id, username, password, fullName);
	 }
	 
	 @Override
	    public String toString() {
	        return "Teacher:\n" +
	               "  ID : " + getId() + "\n" +
	               "  Username : " + getUsername() + "\n" +
	               "  Full Name : " + getFullName();
	    }
}
