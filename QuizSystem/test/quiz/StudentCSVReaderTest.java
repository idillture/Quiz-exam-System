package quiz;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.List;

class StudentCSVReaderTest {

    @Test
    void loadsStudentsFromCsv() {
        URL resource = getClass()
                .getClassLoader()
                .getResource("students.csv");

        assertNotNull(resource, "students.csv should be found in test resources");
        List<Student> students = StudentCSVReader.loadStudents("students.csv");

        assertNotNull(students);
        assertFalse(students.isEmpty(), "Student list should not be empty");
    }
}