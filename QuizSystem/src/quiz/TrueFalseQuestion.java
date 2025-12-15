package quiz;

public class TrueFalseQuestion extends Question {
	
	// The correct answer is stored as a boolean value.
	
	    private boolean correctAnswer;

	    public TrueFalseQuestion(int id, String text, double points, boolean correctAnswer) {

	        super(id, text, points);

	        this.correctAnswer = correctAnswer;
	    }

	    public boolean isCorrectAnswer() {
	        return correctAnswer;
	    }

	    /**
	     * Checks whether the user's answer matches the correct answer.
	     * Supported input formats:
	     * true / false or t / f
	     * yes / no or y / n
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
	        	
	            // invalid input
	        	
	            return false;
	        }

	        return userValue == correctAnswer;
	    }
	}