package quiz;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URL;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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
        @Test
        void nextQuestionIdIsMaxPlusOne() {
            QuestionBank bank = new QuestionBank();

            List<String> opts = new ArrayList<>();
            opts.add("A"); opts.add("B"); opts.add("C"); opts.add("D");

            bank.addQuestion(new MultipleChoiceQuestion(10, "q1", 1, opts, 0, 2.0));
            bank.addQuestion(new TrueFalseQuestion(25, "q2", 2, 4.0, true));
            bank.addQuestion(new MultipleChoiceQuestion(7, "q3", 3, opts, 1, 6.0));

            assertEquals(26, bank.getNextQuestionId());
        }
        
        @TempDir
        Path tempDir;

        @Test
        void runtimeCsvIsLoadedAndAffectsNextId() throws Exception {
            Path runtimeFile = tempDir.resolve("questions_runtime.csv");

            // header + 1 question (id=100)
            String content =
                    "id;type;difficulty;points;text;options;answer\n" +
                    "100;TF;1;2.0;Temp TF question;;true\n";

            java.nio.file.Files.writeString(runtimeFile, content);

            QuestionBank bank = new QuestionBank();
            bank.loadFromRuntimeCsv(runtimeFile.toString());

            assertEquals(101, bank.getNextQuestionId());
            assertFalse(bank.getAllQuestions().isEmpty());
        }
    
    }
