	package quiz;

	import java.io.BufferedReader;
	import java.io.FileWriter;
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
	            boolean header = true;

	            while ((line = br.readLine()) != null) {

	                if (line.isBlank()) continue;

	                if (header) {
	                    header = false;
	                    continue;
	                }

	                String[] parts = line.split(";", -1);
	                if (parts.length < 7) continue;

	                int id = Integer.parseInt(parts[0]);
	                String type = parts[1];
	                int difficulty = Integer.parseInt(parts[2]);
	                double points = Double.parseDouble(parts[3]);
	                String text = parts[4];
	                String optionsPart = parts[5];
	                String answerPart = parts[6];

	                if (type.equalsIgnoreCase("MC")) {

	                    List<String> options =
	                            optionsPart.isEmpty()
	                                    ? new ArrayList<>()
	                                    : Arrays.asList(optionsPart.split("\\|"));

	                    int correctIndex = Integer.parseInt(answerPart);

	                    addQuestion(new MultipleChoiceQuestion(
	                            id, text, difficulty, options, correctIndex, points
	                    ));

	                } else if (type.equalsIgnoreCase("TF")) {

	                    boolean correct = Boolean.parseBoolean(answerPart);

	                    addQuestion(new TrueFalseQuestion(
	                            id, text, difficulty, points, correct
	                    ));
	                }
	            }

	        } catch (Exception e) {
	            System.err.println("Error reading questions CSV: " + e.getMessage());
	        }
	    }


	    public void saveQuestionToCsv(Question q) {

	        String filename = "questions_runtime.csv";

	        try (FileWriter fw = new FileWriter(filename, true)) {
	            fw.write("\n" + toCsvLine(q));
	            System.out.println(" Question written to CSV. ");
	            System.out.println("Path: " + new java.io.File(filename).getAbsolutePath());

	        } catch (Exception e) {
	            System.out.println("Error writing to CSV: " + e.getMessage());
	        }
	    }

	    private String toCsvLine(Question q) {

	        if (q instanceof MultipleChoiceQuestion mcq) {
	            String options = String.join("|", mcq.getOptions());

	            return mcq.getId() + ";MC;"
	                    + mcq.getDifficulty() + ";"
	                    + mcq.getPoints() + ";"
	                    + mcq.getText() + ";"
	                    + options + ";"
	                    + mcq.getCorrectOptionIndex();
	        }

	        if (q instanceof TrueFalseQuestion tfq) {
	            return tfq.getId() + ";TF;"
	                    + tfq.getDifficulty() + ";"
	                    + tfq.getPoints() + ";"
	                    + tfq.getText() + ";;"
	                    + tfq.isCorrectAnswer();
	        }

	        throw new IllegalArgumentException("Unknown question type");
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
	    
	   