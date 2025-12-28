package quiz;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

class QuestionBankTest {

	    @Test
	    void loadsQuestionsFromCsv() {
	        QuestionBank bank = new QuestionBank();
	        bank.loadFromCsv("questions.csv");

	        assertFalse(bank.getAllQuestions().isEmpty());
	    }

	    @Test
	    void filtersQuestionsByDifficulty() {
	        QuestionBank bank = new QuestionBank();
	        bank.loadFromCsv("questions.csv");

	        assertTrue(
	            bank.getTrueFalseQuestionsByDifficulty(1).stream()
	                .allMatch(q -> q.getDifficulty() == 1)
	        );
	    }
	}

