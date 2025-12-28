package quiz;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class StudentTest {
	
	    @Test
	    void lastExamResultsAreStoredCorrectly() {
	        Student s = new Student(1, "user", "123", "Test User");

	        s.setLastScore(8.5);
	        s.setLastCorrectCount(6);
	        s.setLastWrongCount(4);

	        assertEquals(8.5, s.getLastScore());
	        assertEquals(6, s.getLastCorrectCount());
	        assertEquals(4, s.getLastWrongCount());
	    }
	}


