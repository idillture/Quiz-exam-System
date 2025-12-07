	package quiz;

public class TrueFalseQuestion extends Question {

/**
 * A question that can be answered with true or false.
 *
 * The correct answer is stored as a boolean value.
 */
	
	    private boolean correctAnswer;

	    public TrueFalseQuestion(int id, String text, double points, boolean correctAnswer) {

	        super(id, text, points);

	        this.correctAnswer = correctAnswer;
	    }

	    public boolean isCorrectAnswer() {
	        return correctAnswer;
	    }

	    /**
	     * Reads the user's answer as true/false (also accepts t/f, yes/no, y/n)
	     * and compares it with the correct answer.
	     */
	    
	    @Override
	    public boolean checkAnswer(String userAnswer) {
	        if (userAnswer == null) {
	            return false;
	        }

	        String trimmed = userAnswer.trim().toLowerCase();

	        boolean userValue;

	        if (trimmed.equals("true") || trimmed.equals("t")
	                || trimmed.equals("yes") || trimmed.equals("y")) {

	            userValue = true;

	        } else if (trimmed.equals("false") || trimmed.equals("f")
	                || trimmed.equals("no") || trimmed.equals("n")) {

	            userValue = false;

	        } else {
	            
	            return false;
	        }

	        return userValue == correctAnswer;
	    }
	}