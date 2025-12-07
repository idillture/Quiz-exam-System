package quiz;

// A student who takes quizzes in the system.
public class Student {

	 private String id; 
	 private String name; 
	 private double lastScore; // Score of the last quiz taken by the student.

	 public Student(String id, String name) {
		    this.id = id;
	        this.name = name;    
	        this.lastScore = 0.00; // before any quiz is taken.
	        }
	 
	 // Getter
	 // Returns the id, name, and lastScore of the student.
	    public String getId() {
	        return id;
	    }
	    public String getName() {
	        return name;
	    }
	    public double getLastScore() {
	        return lastScore;
	    }
	     
	     // Setter
	     // Updates the lastScore after the student takes a quiz.
	     public void setLastScore(double lastScore) {
	        this.lastScore = lastScore;
	     }
}
