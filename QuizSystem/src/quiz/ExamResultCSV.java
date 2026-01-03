package quiz;

import java.io.FileWriter;
import java.time.LocalDate;

public class ExamResultCSV {

    private static final String FILE = "exam_results.csv";

    public static void saveResult(Student s, double score, int correct, int wrong, int examId) {

        String date = LocalDate.now().toString();

        try (FileWriter fw = new FileWriter(FILE, true)) {
            fw.write( s.getId() + ";" + examId + ";" + score + ";" + correct + ";" + wrong + ";" + date + "\n"
            );

            System.out.println("Exam result saved to CSV:");
            System.out.println("Path: " + new java.io.File(FILE).getAbsolutePath());

        } catch (Exception e) {
            System.out.println("Error saving exam result: " + e.getMessage());
        }
    }
}