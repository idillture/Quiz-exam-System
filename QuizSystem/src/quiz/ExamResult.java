package quiz;

public class ExamResult {

    private int studentId;
    private int examId;
    private double score;
    private int correct;
    private int wrong;

    public ExamResult(int studentId, int examId,
                      double score, int correct, int wrong, String date) {
        this.studentId = studentId;
        this.examId = examId;
        this.score = score;
        this.correct = correct;
        this.wrong = wrong;
    }

    public int getStudentId() { return studentId; }
    public int getExamId() { return examId; }
    public double getScore() { return score; }
    public int getCorrect() { return correct; }
    public int getWrong() { return wrong; }
}