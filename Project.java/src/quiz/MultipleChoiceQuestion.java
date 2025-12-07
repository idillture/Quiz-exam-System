package quiz;

import java.util.List;

/**
 * A multiple choice question with several answer options.
 *
 * The student will see the question text and the list of options.
 * One of the options is correct, stored by its index.
 */

public class MultipleChoiceQuestion extends Question{
	
	 private List<String> options;
	
	 private int correctOptionIndex;
	 
	 public MultipleChoiceQuestion(int id, String text, double points, List<String> options, int correctOptionIndex) {
		 
		 super(id, text, points);

	        this.options = options;
	        this.correctOptionIndex = correctOptionIndex;
	 }
	 
	 public List<String> getOptions() {
	        return options;
	    }
	 
	 public int getCorrectOptionIndex() {
	        return correctOptionIndex;
	    }

	 /**
	     * Checks if the user's answer matches the correct option.
	     * We compare the text of the chosen option with the correct option text.
	     */
	 
	 @Override
	    public boolean checkAnswer(String userAnswer) {
	        if (userAnswer == null || options == null ||
	            correctOptionIndex < 0 || correctOptionIndex >= options.size()) {

	            return false;
	        }
	        String correctText = options.get(correctOptionIndex);
	        
	        return correctText.trim().equalsIgnoreCase(userAnswer.trim());
	    }
}
