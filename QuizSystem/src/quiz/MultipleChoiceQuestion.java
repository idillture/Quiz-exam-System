	package quiz;

	import java.util.List;
	import java.util.Collections; 

	public class MultipleChoiceQuestion extends Question{
		
		 private List<String> options;
		
		 private int correctOptionIndex;
		 
		 public MultipleChoiceQuestion(int id, String text, int difficulty, List<String> options, int correctOptionIndex, double points) {
			    super(id, text, difficulty, points);
			    this.options = options;
			    this.correctOptionIndex = correctOptionIndex;
		 }
		 
		 // Getters
		 
		 public List<String> getOptions() {
		        return options;
		    }
		 
		 public int getCorrectOptionIndex() {
		        return correctOptionIndex;
		    }

		 public void shuffleOptions() {
		        if (options == null || options.isEmpty()) {
		            return;
		        }

		        // Save the correct option text before shuffling
		        String correctText = options.get(correctOptionIndex);

		        // Shuffle the list
		        Collections.shuffle(options);

		        for (int i = 0; i < options.size(); i++) {
		            if (options.get(i).equals(correctText)) {
		                correctOptionIndex = i;
		                break;
		            }
		        }
		    }
		 

	     /**
	     * Checks if the user's answer matches the correct option.
	     * Supported input formats:
	     * Letter choice (case insensitive),
	     * Typing the full text of the correct option.
	     */
		 
	         @Override
	         public boolean checkAnswer(String userAnswer) {
	             if (userAnswer == null || options == null ||
	                 correctOptionIndex < 0 || correctOptionIndex >= options.size()) {

	                 return false;
	             }

	             String trimmed = userAnswer.trim();
	             if (trimmed.isEmpty()) {
	                 return false;
	             }

	             String lower = trimmed.toLowerCase();

	             // Assume the student answered with a letter (a, b, c, ...)
	             char ch = lower.charAt(0);  // first character

	             // Convert letters to indexes: a = 0, b = 1, c = 2 ...
	             if (ch >= 'a' && ch <= 'z') {
	                 int chosenIndex = ch - 'a';
	                 if (chosenIndex == correctOptionIndex) {
	                     return true;
	                 }
	             }

	             // Check if what they typed matches the correct option text
	             String correctText = options.get(correctOptionIndex);
	             return correctText.trim().equalsIgnoreCase(trimmed);
	         }
	     }


		     



