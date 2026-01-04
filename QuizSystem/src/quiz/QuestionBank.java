package quiz;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionBank {

    private List<Question> questions;

    public QuestionBank() {
        this.questions = new ArrayList<>();
    }

    public void addQuestion(Question question) {
   if (question == null) throw new IllegalArgumentException("Question cannot be null");
        questions.add(question);
    }

    public List<Question> getAllQuestions() {
             return questions;
    }

    public List<Question> getQuestionsByDifficulty(int difficulty) {
              List<Question> result = new ArrayList<>();
        for (Question q : questions) {
            if (q.getDifficulty() == difficulty) result.add(q);
        }
        return result;
    }

    public List<MultipleChoiceQuestion> getMultipleChoiceQuestionsByDifficulty(int difficulty) {
                 List<MultipleChoiceQuestion> result = new ArrayList<>();
        for (Question q : questions) {
            if (q instanceof MultipleChoiceQuestion && q.getDifficulty() == difficulty) {
                result.add((MultipleChoiceQuestion) q);
            }
        }
        return result;
    }

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
        try (InputStream is = QuestionBank.class.getClassLoader().getResourceAsStream(filename)) {
                 if (is != null) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                    readQuestions(br);
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading questions from resources: " + e.getMessage());
        }

        File file = new File(filename);
           if (file.exists()) {
     try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                readQuestions(br);
            } catch (Exception e) {
                System.err.println("Error reading questions from file: " + e.getMessage());
            }
        }
    }

    public void loadFromRuntimeCsv(String runtimeFilename) {
            File file = new File(runtimeFilename);
        if (!file.exists()) return;

     try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            readQuestions(br);
        } catch (Exception e) {
            System.err.println("Error reading runtime questions CSV: " + e.getMessage());
        }
    }

    private void readQuestions(BufferedReader br) throws IOException {
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

            int id = Integer.parseInt(parts[0].trim());
            String type = parts[1].trim();
          int difficulty = Integer.parseInt(parts[2].trim());
            double points = Double.parseDouble(parts[3].trim());
            String text = parts[4].trim();
          String optionsPart = parts[5];
            String answerPart = parts[6].trim();

            if (type.equalsIgnoreCase("MC")) {
                List<String> options =
                        optionsPart.isEmpty() ? new ArrayList<>() : Arrays.asList(optionsPart.split("\\|"));
                     int correctIndex = Integer.parseInt(answerPart);

       addQuestion(new MultipleChoiceQuestion(id, text, difficulty, options, correctIndex, points));

            } else if (type.equalsIgnoreCase("TF")) {
                boolean correct = Boolean.parseBoolean(answerPart);

                addQuestion(new TrueFalseQuestion(id, text, difficulty, points, correct));
            }
        }
    }

    public void saveQuestionToCsv(Question q) {
        String filename = "questions_runtime.csv";

        try (FileWriter fw = new FileWriter(filename, true)) {
            File f = new File(filename);
            if (f.length() == 0) {
                fw.write("id;type;difficulty;points;text;options;answer");
            }
            fw.write("\n" + toCsvLine(q));
        System.out.println("Question written to CSV.");
            System.out.println("Path: " + f.getAbsolutePath());

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
            if (q.getId() > maxId) maxId = q.getId();
        }
        return maxId + 1;
    }

    public double calculatePoints(int difficulty) {
        return difficulty * 2.0;
    }
}