package quiz;

// Abstract base class for a single quiz question.

public abstract class Question {
	
    // Unique id of the question.
	private int id;
	
	// Text shown to the student.
	private String text;
	
	private int difficulty; 
	
	private double points;
	
	// Creates a new question with the given information.
    public Question(int id, String text, int difficulty, double points) {
    	this.id = id;
    	this.text = text;
    	this.difficulty = difficulty;
    	this.points = points;
    }
    
    // Getters
    public int getId() {
        return id;
    }
    public String getText() {
        return text;
    }
    public int getDifficulty() { 
    	return difficulty; 
    	}
    public double getPoints() {
    	return points;
    }

    public abstract boolean checkAnswer(String userAnswer);
}
