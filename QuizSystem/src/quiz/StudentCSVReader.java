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

    public static List<Student> loadStudents(String filename) {
    	 List<Student> students = new ArrayList<>();
         loadStudentsInto(filename, students);
         return students;
     }

     public static void loadStudentsInto(String filename, List<Student> students) {
         if (students == null) return;

         boolean loadedFromResource = false;

         try {
             InputStream is = StudentCSVReader.class.getClassLoader().getResourceAsStream(filename);
             if (is != null) {
                 try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                     readStudentsFromBufferedReader(br, students);
                 }
                 loadedFromResource = true;
             }
         } catch (Exception e) {
             System.err.println("Error reading students from resources: " + e.getMessage());
         }

         if (loadedFromResource) return;

         File file = new File(filename);
         if (!file.exists()) {
             System.err.println("Student CSV not found: " + filename);
             return;
         }

         try (BufferedReader br = new BufferedReader(new FileReader(file))) {
             readStudentsFromBufferedReader(br, students);
         } catch (Exception e) {
             System.err.println("Error reading students CSV from file: " + e.getMessage());
         }
     }

     private static void readStudentsFromBufferedReader(BufferedReader br, List<Student> students) throws Exception {
         String line;
         boolean firstLine = true;

         while ((line = br.readLine()) != null) {
             line = line.trim();
             if (line.isEmpty()) continue;

             if (firstLine) {
                 firstLine = false;

                 String[] maybeHeader = line.split("[,;]", -1);
                 if (maybeHeader.length > 0 && !isInt(maybeHeader[0].trim())) {
                     continue;
                 }
             }

             String[] parts = line.split("[,;]", -1);
             if (parts.length < 4) {
                 continue;
             }

             int id = Integer.parseInt(parts[0].trim());
             String username = parts[1].trim();
             String password = parts[2].trim();
             String fullName = parts[3].trim();

             if (username.isEmpty()) continue;

             if (existsByUsernameOrId(students, username, id)) {
                 continue;
             }

             students.add(new Student(id, username, password, fullName));
         }
     }

     private static boolean existsByUsernameOrId(List<Student> students, String username, int id) {
         for (Student s : students) {
             if (s.getId() == id) return true;
             if (s.getUsername() != null && s.getUsername().equalsIgnoreCase(username)) return true;
         }
         return false;
     }

     private static boolean isInt(String s) {
         if (s == null || s.isEmpty()) return false;
         for (int i = 0; i < s.length(); i++) {
             char c = s.charAt(i);
             if (i == 0 && (c == '-' || c == '+')) continue;
             if (c < '0' || c > '9') return false;
         }
         return true;
     }

     public static int getNextStudentId(List<Student> students) {
         int maxId = 0;
         for (Student s : students) {
             if (s.getId() > maxId) maxId = s.getId();
         }
         return maxId + 1;
     }

     public static void saveStudent(Student student) {
         String filename = "students_runtime.csv";
         File file = new File(filename);

         try (FileWriter fw = new FileWriter(file, true)) {

             if (!file.exists() || file.length() == 0) {
                 fw.write("id,username,password,fullName");
             }

             fw.write("\n"
                     + student.getId() + ","
                     + student.getUsername() + ","
                     + student.getPassword() + ","
                     + student.getFullName());

             System.out.println("Student saved.");
             System.out.println("Path: " + file.getAbsolutePath());

         } catch (Exception e) {
             System.out.println("Error writing student CSV: " + e.getMessage());
         }
     }
 }
   
