package quiz;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class StudentCSVReader {

    public static List<Student> loadStudents(String filename) {

        List<Student> students = new ArrayList<>();

        try {
            InputStream is = StudentCSVReader.class
                    .getClassLoader()
                    .getResourceAsStream(filename);

            if (is == null) {
                System.err.println("Student CSV not found: " + filename);
                return students;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            // ✅ HEADER'I ATLA
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {

                if (line.isBlank()) continue;

                // ✅ CSV ayırıcı (senin dosyana göre değiştir)
                String[] parts = line.split("[,;]");

                if (parts.length < 4) {
                    System.err.println("Skipping invalid line: " + line);
                    continue;
                }

                int id = Integer.parseInt(parts[0].trim());
                String username = parts[1].trim();
                String password = parts[2].trim();
                String fullName = parts[3].trim();

                students.add(new Student(id, username, password, fullName));
            }

        } catch (Exception e) {
            System.err.println("Error reading students CSV: " + e.getMessage());
        }

        return students;
    }
}