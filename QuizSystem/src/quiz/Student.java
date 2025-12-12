package quiz;

// A student who takes quizzes in the system.
public class Student extends User {
	
	private double lastScore;
	
	public Student(int id, String username, String password, String fullName) {
        super(id, username, password, fullName);
        this.lastScore = 0.0;
	}
	
	 public double getLastScore() {
	        return lastScore;
	    }
}

