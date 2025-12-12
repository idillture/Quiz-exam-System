package quiz;

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
	
	// Getters
	 public double getLastScore() {
	        return lastScore;
	    }
	 
	 public int getLastCorrectCount() {
	        return lastCorrectCount;
	    }

	 public int getLastWrongCount() {
	        return lastWrongCount;
	    }
	   
	    // Setters
	 public void setLastScore(double lastScore) {
	        this.lastScore = lastScore;
	    }
	    
	 public void setLastCorrectCount(int lastCorrectCount) {
	        this.lastCorrectCount = lastCorrectCount;
	    }
	    
	 public void setLastWrongCount(int lastWrongCount) {
	        this.lastWrongCount = lastWrongCount;
	    }
	    
	    @Override
	 public String toString() {
	        return "Student:\n" +
	               "  ID : " + getId() + "\n" +
	               "  Username : " + getUsername() + "\n" +
	               "  Full Name : " + getFullName() + "\n" +
	               "  Last Score : " + lastScore + "\n" +
	               "  Correct Answers : " + lastCorrectCount + "\n" +
	               "  Wrong Answers : " + lastWrongCount;
	    }
}

