package quiz;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

class ExamResultCSVReaderTest {

    @TempDir
    Path tempDir;

    @Test
    void loadsExamResultsAndSetsStudentFields() throws Exception {
        Path f = tempDir.resolve("exam_results.csv");

                String content = "id;score;correct;wrong\n" + "5;40.0;8;2\n";
        Files.writeString(f, content);

        List<Student> students = new ArrayList<>();
        Student s = new Student(5, "u5", "123", "User 5");
        students.add(s);

        ExamResultCSVReader.loadResults(f.toString(), students);

        assertEquals(40.0, s.getLastScore(), 0.0001);
        assertEquals(8, s.getLastCorrectCount());
        assertEquals(2, s.getLastWrongCount());
    }
}