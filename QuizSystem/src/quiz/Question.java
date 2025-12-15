package quiz;

// Abstract base class for a single quiz question.

public abstract class Question {
	
    // Unique id of the question.
	private int id;
	
	// Text shown to the student.
	private String text;
	
	private double points;
	
	// Creates a new question with the given information.
    public Question(int id, String text, double points) {
    	this.id = id;
    	this.text = text;
    	this.points = points;
    }
    
    // Getters
    public int getId() {
        return id;
    }
    public String getText() {
        return text;
    }
    public double getPoints() {
    	return points;
    }

    public abstract boolean checkAnswer(String userAnswer);
}
