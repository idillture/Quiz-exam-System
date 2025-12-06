package quiz;

/**
 * Base class for a single quiz question.
 *
 * This is an abstract class, so we do not create Question objects directly.
 * Instead, other classes like MultipleChoiceQuestion and TrueFalseQuestion
 * extend this class.
 */
public abstract class Question {
	
    // Unique id of the question.
	private int id;
	
    // The text that will be shown to the student.
	private String text;
	
    // How many points this question is worth.
	private double points;
	
	// Creates a new question with the given information.
    public Question(int id, String text, double points) {
    	this.id = id;
    	this.text = text;
    	this.points = points;
    }
    
    // Returns the id, text, and points of this question.
    public int getId() {
        return id;
    }
    public String getText() {
        return text;
    }
    public double getPoints() {
    	return points;
    }

    /**
     * Checks if the student's answer is correct.
     * 
     * This method is abstract because each question type
     * checks the answer in a different way.
     * MultipleChoiceQuestion compares the answer with the correct choice.
     * TrueFalseQuestion compares the answer with true or false.
     * 
     * We use boolean because it returns true when the student's answer is correct, and false when it is wrong.
     */
    
    public abstract boolean checkAnswer(String userAnswer);
}
