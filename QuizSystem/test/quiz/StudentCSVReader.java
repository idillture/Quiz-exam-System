package quiz;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class StudentCSVReader {

    public static List<Student> loadStudents(String fileName) {

        List<Student> students = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            String line = br.readLine(); 

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");

                int id = Integer.parseInt(parts[0].trim());
                String username = parts[1].trim();
                String password = parts[2].trim();
                String fullName = parts[3].trim();

                students.add(
                        new Student(id, username, password, fullName)
                );
            }

        } catch (Exception e) {
            System.out.println("Error loading students from CSV: " + e.getMessage());
        }

        return students;
    }
}