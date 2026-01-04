package quiz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class StudentCSVReader {

    public static final String RUNTIME_FILE = "students_runtime.csv";

    public static List<Student> loadStudents(String filename) {
        List<Student> students = new ArrayList<>();
        loadStudentsInto(filename, students);
        return students;
    }

    public static void loadStudentsInto(String filename, List<Student> students) {
        if (students == null || filename == null || filename.isBlank()) return;

        InputStream is = StudentCSVReader.class.getClassLoader().getResourceAsStream(filename);
        if (is != null) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                readAll(br, students);
            } catch (Exception e) {
                System.err.println("Error reading students from resources: " + e.getMessage());
            }
            return;
        }

        File file = new File(filename);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            readAll(br, students);
        } catch (Exception e) {
            System.err.println("Error reading students from file: " + e.getMessage());
        }
    }

    private static void readAll(BufferedReader br, List<Student> students) throws Exception {
        String firstLine = br.readLine();
        if (firstLine == null) return;

        if (looksLikeData(firstLine)) {
            addFromLine(firstLine, students);
        }

        String line;
        while ((line = br.readLine()) != null) {
            if (line.isBlank()) continue;
            addFromLine(line, students);
        }
    }

    private static boolean looksLikeData(String line) {
        String t = line.trim();
        if (t.isEmpty()) return false;
        return Character.isDigit(t.charAt(0));
    }

    private static void addFromLine(String line, List<Student> students) {
        String[] parts = line.split("[,;]");
        if (parts.length < 4) return;

        int id = Integer.parseInt(parts[0].trim());
        String username = parts[1].trim();
        String password = parts[2].trim();
        String fullName = parts[3].trim();

        students.add(new Student(id, username, password, fullName));
    }

    public static void saveStudent(Student student) {
        saveStudent(student, RUNTIME_FILE);
    }

    public static void saveStudent(Student student, String filename) {
        if (student == null || filename == null || filename.isBlank()) return;

        File file = new File(filename);

        try {
            boolean newFile = !file.exists() || file.length() == 0;

            try (FileWriter fw = new FileWriter(file, true)) {
                if (newFile) {
                    fw.write("id,username,password,fullName\n");
                }
                fw.write(student.getId() + ","
                        + student.getUsername() + ","
                        + student.getPassword() + ","
                        + student.getFullName() + "\n");
            }

            System.out.println("Student saved.");
            System.out.println("Path: " + file.getAbsolutePath());

        } catch (Exception e) {
            System.out.println("Error writing student CSV: " + e.getMessage());
        }
    }

    public static int getNextStudentId(List<Student> students) {
        int max = 0;
        if (students != null) {
            for (Student s : students) {
                if (s.getId() > max) max = s.getId();
            }
        }
        return max + 1;
    }
}