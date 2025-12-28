package quiz;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class StudentTest {

    @Test
    void constructorSetsFieldsCorrectly() {

        Student student = new Student(
                101,
                "idilture",
                "123",
                "İdil Türe"
        );

        assertEquals(101, student.getId());
        assertEquals("idilture", student.getUsername());
        assertEquals("İdil Türe", student.getFullName());
    }

    @Test
    void checkPasswordReturnsTrueForCorrectPassword() {

        Student student = new Student(
                101,
                "idilture",
                "123",
                "İdil Türe"
        );

        assertTrue(student.checkPassword("123"));
    }

    @Test
    void checkPasswordReturnsFalseForWrongPassword() {

        Student student = new Student(
                101,
                "idilture",
                "123",
                "İdil Türe"
        );

        assertFalse(student.checkPassword("12"));
        assertFalse(student.checkPassword("abc"));
        assertFalse(student.checkPassword(""));
    }

    @Test
    void examResultsAreStoredCorrectly() {

        Student student = new Student(
                101,
                "idilture",
                "123",
                "İdil Türe"
        );

        student.setLastScore(90.0);
        student.setLastCorrectCount(8);
        student.setLastWrongCount(2);

        assertEquals(90.0, student.getLastScore());
        assertEquals(8, student.getLastCorrectCount());
        assertEquals(2, student.getLastWrongCount());
    }
}