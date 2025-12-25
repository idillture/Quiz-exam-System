package quiz;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;

public class MultipleChoiceQuestionTest {

    @Test
    void correctAnswerReturnsTrue() {
        List<String> options = List.of("6", "7", "8", "9");

        MultipleChoiceQuestion q =
                new MultipleChoiceQuestion(1, "3 + 5 = ?", 1, options, 2, 5);

        assertTrue(q.checkAnswer("c"));  
    }

    @Test
    void wrongAnswerReturnsFalse() {
        List<String> options = List.of("6", "7", "8", "9");

        MultipleChoiceQuestion q =
                new MultipleChoiceQuestion(1, "3 + 5 = ?", 1, options, 2, 5);

        assertFalse(q.checkAnswer("a"));  
    }
    
    @Test
    void invalidInputReturnsFalse() {
        List<String> options = List.of("6", "7", "8", "9");

        MultipleChoiceQuestion q =
            new MultipleChoiceQuestion(1, "3 + 5 = ?", 1, options, 2, 5);

        assertFalse(q.checkAnswer("z"));
    }
}