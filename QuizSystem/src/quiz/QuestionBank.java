	package quiz;

	import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
	import java.util.Arrays;
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
	    
	    public void loadFromCsv(String filename) {

	        try {
	            InputStream is = QuestionBank.class
	                    .getClassLoader()
	                    .getResourceAsStream(filename);

	            if (is == null) {
	                System.err.println("Questions CSV not found: " + filename);
	                return;
	            }

	            BufferedReader br = new BufferedReader(new InputStreamReader(is));

	            String line;
	            boolean isFirstLine = true;

	            while ((line = br.readLine()) != null) {

	                line = line.trim();
	                if (line.isEmpty()) continue;

	                if (isFirstLine) { // header
	                    isFirstLine = false;
	                    continue;
	                }

	                String[] parts = line.split(";", -1);
	                if (parts.length < 7) {
	                    System.out.println("Invalid line: " + line);
	                    continue;
	                }

	                int id = Integer.parseInt(parts[0].trim());
	                String type = parts[1].trim();
	                int difficulty = Integer.parseInt(parts[2].trim());
	                double points = Double.parseDouble(parts[3].trim());
	                String text = parts[4].trim();
	                String optionsPart = parts[5].trim();
	                String answerPart = parts[6].trim();

	                if (type.equalsIgnoreCase("MC")) {

	                    List<String> options = new ArrayList<>();
	                    if (!optionsPart.isEmpty()) {
	                        options = Arrays.asList(optionsPart.split("\\|"));
	                    }

	                    int correctIndex = Integer.parseInt(answerPart);

	                    addQuestion(new MultipleChoiceQuestion(
	                            id, text, difficulty, options, correctIndex, points
	                    ));

	                } else if (type.equalsIgnoreCase("TF")) {

	                    boolean correctAnswer = Boolean.parseBoolean(answerPart);

	                    addQuestion(new TrueFalseQuestion(
	                            id, text, difficulty, points, correctAnswer
	                    ));

	                } else {
	                    System.out.println("Unknown question type: " + type);
	                }
	            }

	        } catch (Exception e) {
	            System.err.println("Error reading questions CSV: " + e.getMessage());
	        }
	    }
	    


	    public int getNextQuestionId() {
	        int maxId = 0;
	        for (Question q : questions) {
	            if (q.getId() > maxId) {
	                maxId = q.getId();
	            }
	        }
	        return maxId + 1;
	    }

	    public double calculatePoints(int difficulty) {
	        return difficulty * 2.0;
	    }
	}
	    
	   