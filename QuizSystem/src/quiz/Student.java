package quiz;

// A student who takes quizzes in the system.
public class Student extends User {
	
	private double lastScore;
	private int lastCorrectCount;
	private int lastWrongCount;
	
	
	public Student(int id, String username, String password, String fullName) {
        super(id, username, password, fullName);
        this.lastScore = 0.0;
        this.lastCorrectCount = 0;  
        this.lastWrongCount = 0;  
	}
	
	 public double getLastScore() {
	        return lastScore;
	    }
	 
	 public int getLastCorrectCount() {
	        return lastCorrectCount;
	    }

	    public int getLastWrongCount() {
	        return lastWrongCount;
	    }
	   
	    
}

