	package quiz;

	import java.util.ArrayList;
	import java.util.List;

	public class QuestionBank {

	    private List<Question> questions;

	    // Creates an empty QuestionBank.

	    public QuestionBank() {
	        this.questions = new ArrayList<>();
	    }

	    public void addQuestion(Question question) {
	        if (question == null) {
	            throw new IllegalArgumentException("Question cannot be null");
	        }
	        questions.add(question);
	    }

	    public List<Question> getAllQuestions() {
	        return questions;
	    }

	    public List<Question> getQuestionsByDifficulty(int difficulty) {
	        List<Question> result = new ArrayList<>();
	        for (Question q : questions) {
	            if (q.getDifficulty() == difficulty) {
	                result.add(q);
	            }
	        }
	        return result;
	    }

	    // Returns all MultipleChoiceQuestion objects

	    public List<MultipleChoiceQuestion> getMultipleChoiceQuestionsByDifficulty(int difficulty) {
	        List<MultipleChoiceQuestion> result = new ArrayList<>();
	        for (Question q : questions) {
	            if (q instanceof MultipleChoiceQuestion && q.getDifficulty() == difficulty) {
	                result.add((MultipleChoiceQuestion) q);
	            }
	        }
	        return result;
	    }

	    // Returns all TrueFalseQuestion objects with the given difficulty.

	    public List<TrueFalseQuestion> getTrueFalseQuestionsByDifficulty(int difficulty) {
	        List<TrueFalseQuestion> result = new ArrayList<>();
	        for (Question q : questions) {
	            if (q instanceof TrueFalseQuestion && q.getDifficulty() == difficulty) {
	                result.add((TrueFalseQuestion) q);
	            }
	        }
	        return result;
	    }
	}

