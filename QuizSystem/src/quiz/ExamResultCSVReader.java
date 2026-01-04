package quiz;

import java.io.*;
import java.util.List;

public class ExamResultCSVReader {

    public static void loadResults(String filename, List<Student> students) {

        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("No previous exam results found.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;
            br.readLine(); 

            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;

                String[] parts = line.split(";");
                int id = Integer.parseInt(parts[0]);
                double score = Double.parseDouble(parts[1]);
                int correct = Integer.parseInt(parts[2]);
                int wrong = Integer.parseInt(parts[3]);

                students.stream().filter(s -> s.getId() == id).findFirst().ifPresent(s -> {s.setLastScore(score); s.setLastCorrectCount(correct); s.setLastWrongCount(wrong);
                        });
            }

            System.out.println("Exam results loaded.");

        } catch (Exception e) {
            System.err.println("Error reading exam results: " + e.getMessage());
        }
    }
}