	package quiz;

	import java.util.ArrayList;
	import java.util.List;

	public class Quiz implements Gradable{

	    private int id;
	    private String title;
	    private List<Question> questions;

	    public Quiz(int id, String title) {
	        this.id = id;
	        this.title = title;
	        this.questions = new ArrayList<>();
	    }

	    public int getId() {
	        return id;
	    }

	    public String getTitle() {
	        return title;
	    }

	    public List<Question> getQuestions() {
	        return questions;
	    }

	    // Adds a question to the quiz/exam.
	    public void addQuestion(Question question) {
	        if (question == null) {
	            throw new IllegalArgumentException("Question cannot be null");
	        }
	        questions.add(question);
	    }
	    
	    // Returns how many questions this quiz has.
	    public int getNumberOfQuestions() {
	        return questions.size();
	    }

	     // Returns the total max points of all questions in this quiz.
	    public double getTotalPoints() {
	        double total = 0.0;
	        for (Question q : questions) {
	            total += q.getPoints(); 
	        }
	        return total;
	    }
	    
	    @Override
	    public double calculateScore() {
	        return getTotalPoints();
	    }
	}

