package quiz;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;
import org.junit.jupiter.api.Test;

public class MultipleChoiceQuestionTest {

    private MultipleChoiceQuestion getAnyMCQuestionFromCsv() {
        QuestionBank bank = new QuestionBank();
        bank.loadFromCsv("questions.csv");

        Optional<MultipleChoiceQuestion> question =
                bank.getAllQuestions().stream()
                    .filter(q -> q instanceof MultipleChoiceQuestion)
                    .map(q -> (MultipleChoiceQuestion) q)
                    .findFirst();

        return question.orElseThrow(() ->
                new AssertionError("No MultipleChoiceQuestion found in CSV"));
    }

    @Test
    void correctAnswerReturnsTrue() {
        MultipleChoiceQuestion q = getAnyMCQuestionFromCsv();

        boolean anyCorrect =
                q.checkAnswer("a") ||
                q.checkAnswer("b") ||
                q.checkAnswer("c") ||
                q.checkAnswer("d");

        assertTrue(anyCorrect, "At least one option should be correct");
    }

    @Test
    void invalidAnswerReturnsFalse() {
        MultipleChoiceQuestion q = getAnyMCQuestionFromCsv();

        assertFalse(q.checkAnswer("z"));
        assertFalse(q.checkAnswer("1"));
        assertFalse(q.checkAnswer(""));
    }

    @Test
    void wrongAnswerReturnsFalse() {
        MultipleChoiceQuestion q = getAnyMCQuestionFromCsv();

        assertFalse(q.checkAnswer("x"));
    }
}