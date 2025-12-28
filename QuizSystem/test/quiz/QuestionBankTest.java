package quiz;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URL;

import org.junit.jupiter.api.Test;

class QuestionBankTest {

    @Test
    void loadsQuestionsFromCsv() {
        URL resource = getClass()
                .getClassLoader()
                .getResource("questions.csv");

        assertNotNull(resource, "questions.csv should exist in resources");
        
        QuestionBank bank = new QuestionBank();
        bank.loadFromCsv("questions.csv");

        assertFalse(
                bank.getAllQuestions().isEmpty(),
                "QuestionBank should not be empty after loading CSV"
        );
    }

    @Test
    void filtersQuestionsByDifficulty() {

        QuestionBank bank = new QuestionBank();
        bank.loadFromCsv("questions.csv");

        assertTrue(
                bank.getTrueFalseQuestionsByDifficulty(1)
                        .stream()
                        .allMatch(q -> q.getDifficulty() == 1)
        );

        assertTrue(
                bank.getMultipleChoiceQuestionsByDifficulty(1)
                        .stream()
                        .allMatch(q -> q.getDifficulty() == 1)
        );
    }
}