	package quiz;

	import java.util.List;

	/**
	 * 
	 * This class represents a standard multiple choice question.
	 * Each question has a text and a list of options the student can choose from.
	 * Only one of the options is correct, stored by its index.
	 */

	public class MultipleChoiceQuestion extends Question{
		
		 private List<String> options;
		
		 private int correctOptionIndex;
		 
		 public MultipleChoiceQuestion(int id, String text, List<String> options, int correctOptionIndex, double points) {
			 
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
		     * Supported input formats:
		     * Letter choice (not case insensitive),
		     * Typing the full text of the correct option.
		     */
		 
		 @Override
		 public boolean checkAnswer(String userAnswer) {
		     if (userAnswer == null || options == null ||
		         correctOptionIndex < 0 || correctOptionIndex >= options.size()) {

		         return false;
		     }

		     String trimmed = userAnswer.trim().toLowerCase();

		     // First try: assume the student answered with a letter (a, b, c, ...)
		     if (!trimmed.isEmpty()) {
		         char ch = trimmed.charAt(0);  // first character
		         
		         // Convert letters to indexes: a = 0, b = 1, c = 2 ...
		         if (ch >= 'a' && ch <= 'z') {
		             int chosenIndex = ch - 'a';
		             return chosenIndex == correctOptionIndex;
		         }
		     }

		     // Second try: check if what they typed matches the correct option text exactly.
		     String correctText = options.get(correctOptionIndex);
		     return correctText.trim().equalsIgnoreCase(trimmed);
		 }
		        
	}


