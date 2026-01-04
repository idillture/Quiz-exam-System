package quiz;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class StudentCSVReaderTest {

    @Test
    void loadsStudentsFromCsv() {
        URL resource = getClass().getClassLoader().getResource("students.csv");
        assertNotNull(resource, "students.csv should be found in test resources");

        List<Student> students = StudentCSVReader.loadStudents("students.csv");

        assertNotNull(students);
        assertFalse(students.isEmpty(), "Student list should not be empty");
    }

    @TempDir
    Path tempDir;

    @Test
    void savedStudentIsLoadedBackFromRuntimeCsv() {
        Path runtimeFile = tempDir.resolve("students_runtime.csv");

        Student s = new Student(50, "idilture_test", "123", "Idil Ture");
        StudentCSVReader.saveStudent(s, runtimeFile.toString());

        List<Student> loaded = new ArrayList<>();
        StudentCSVReader.loadStudentsInto(runtimeFile.toString(), loaded);

        assertTrue(
                loaded.stream().anyMatch(st -> st.getUsername().equals("idilture_test")),
                "Saved student should be loaded back from runtime CSV"
        );
    }
}