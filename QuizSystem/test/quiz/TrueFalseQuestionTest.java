package quiz;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TrueFalseQuestionTest {

    private TrueFalseQuestion getAnyTFQuestionFromCsv() {
        QuestionBank bank = new QuestionBank();
        bank.loadFromCsv("questions.csv");

        return bank.getAllQuestions().stream()
                .filter(q -> q instanceof TrueFalseQuestion)
                .map(q -> (TrueFalseQuestion) q)
                .findFirst()
                .orElseThrow(() ->
                        new AssertionError("No True/False question found in CSV"));
    }

    @Test
    void acceptsValidBooleanInputs() {
        TrueFalseQuestion q = getAnyTFQuestionFromCsv();

        assertTrue(q.checkAnswer("true") || q.checkAnswer("false"));
        assertTrue(q.checkAnswer("yes") || q.checkAnswer("no"));
        assertTrue(q.checkAnswer("t") || q.checkAnswer("f"));
        assertTrue(q.checkAnswer("y") || q.checkAnswer("n"));
    }

    @Test
    void rejectsInvalidInputs() {
        TrueFalseQuestion q = getAnyTFQuestionFromCsv();

        assertFalse(q.checkAnswer("1"));
        assertFalse(q.checkAnswer("0"));
        assertFalse(q.checkAnswer("abc"));
        assertFalse(q.checkAnswer(""));
    }
    
}