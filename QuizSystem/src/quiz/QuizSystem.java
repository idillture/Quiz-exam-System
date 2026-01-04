package quiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class QuizSystem {

    private Teacher teacher;
    private List<Student> students;
    private QuestionBank questionBank;
    private Scanner scanner;

    public QuizSystem() {
        scanner = new Scanner(System.in);
        initializeData();
    }

    public void start() {

        while (true) {

            System.out.println("\n QUIZ SYSTEM LOGIN ");
            System.out.print("\n (type 'exit' to quit) \n Username: ");
            String username = scanner.nextLine().trim();

            if (username.equalsIgnoreCase("exit")) {
                System.out.println("Exiting system.");
                break;
            }

            if (username.isEmpty()) {
                System.out.println("Invalid input.\n");
                continue;
            }

            System.out.print("Password: ");
            String password = scanner.nextLine().trim();

            if (password.isEmpty()) {
                System.out.println("Invalid input.\n");
                continue;
            }

            User user = login(username, password);

            if (user == null) {
                System.out.println("Invalid username or password.\n");
            } else if (user instanceof Teacher) {
                System.out.println("\nWelcome, " + user.getFullName() + "!\n");
                showTeacherMenu((Teacher) user);
            } else {
                System.out.println("\nWelcome, " + user.getFullName() + "!\n");
                showStudentMenu((Student) user);
            }
        }
    }

    private void initializeData() {

        teacher = new Teacher(1, "teacher", "123", "Teacher");

        students = StudentCSVReader.loadStudents("students.csv");
        if (students == null) {
            students = new ArrayList<>();
        }

        StudentCSVReader.loadStudentsInto("students_runtime.csv", students);

        questionBank = new QuestionBank();
        questionBank.loadFromCsv("questions.csv");
        
        ExamResultCSVReader.loadResults("exam_results.csv", students);

        System.out.println("Loaded " + students.size() + " students.");
        System.out.println("Loaded " + questionBank.getAllQuestions().size() + " questions.");
    }

    // LOGIN 

    private User login(String username, String password) {

        if (teacher.getUsername().equals(username) &&
                teacher.checkPassword(password)) {
            return teacher;
        }

        for (Student s : students) {
            if (s.getUsername().equals(username) &&
                    s.checkPassword(password)) {
                return s;
            }
        }
        return null;
    }

    // STUDENT MENU 

    private void showStudentMenu(Student student) {
        while (true) {
            System.out.println("\n STUDENT MENU ");
            System.out.println("1) Start new quiz");
            System.out.println("2) View last exam result");
            System.out.println("3) Logout");
            System.out.print("Select option: ");

            switch (scanner.nextLine().trim()) {
                case "1" -> startQuizForStudent(student);
                case "2" -> showLastExamResult(student);
                case "3" -> {
                    System.out.println("Logging out.\n");
                    return;
                }
                default -> System.out.println("Invalid option.\n");
            }
        }
    }

    private void showLastExamResult(Student student) {
        System.out.println("\n LAST EXAM RESULT ");
        System.out.println("Score : " + student.getLastScore());
        System.out.println("Correct Answers : " + student.getLastCorrectCount());
        System.out.println("Wrong Answers : " + student.getLastWrongCount());
    }

    // QUIZ 

    private void startQuizForStudent(Student student) {

        System.out.println("\n STARTING QUIZ ");

        List<Question> examQuestions = new ArrayList<>();

        for (int difficulty = 1; difficulty <= 5; difficulty++) {

            List<TrueFalseQuestion> tf =
                    questionBank.getTrueFalseQuestionsByDifficulty(difficulty);
            List<MultipleChoiceQuestion> mc =
                    questionBank.getMultipleChoiceQuestionsByDifficulty(difficulty);

            if (tf.isEmpty() || mc.isEmpty()) {
                System.out.println("Not enough questions for difficulty " + difficulty);
                return;
            }

            Collections.shuffle(tf);
            Collections.shuffle(mc);

            examQuestions.add(tf.get(0));
            examQuestions.add(mc.get(0));
        }

        Collections.shuffle(examQuestions);

        double totalScore = 0;
        int correct = 0;
        int wrong = 0;

        int index = 1;
        for (Question q : examQuestions) {

            System.out.println("\nQuestion " + index + ": " + q.getText());

            if (q instanceof MultipleChoiceQuestion mcq) {
                mcq.shuffleOptions();
                char letter = 'a';
                for (String opt : mcq.getOptions()) {
                    System.out.println("  " + letter++ + ") " + opt);
                }
            }
            
            System.out.print("Your answer: ");
            String answer;

            while (true) {
                answer = scanner.nextLine().trim().toLowerCase();

                if (answer.isEmpty()) {
                    System.out.print("Answer cannot be empty. Please try again: ");
                    continue;
                }
                if (q instanceof MultipleChoiceQuestion) {
                    if (!answer.matches("[abcd]")) {
                        System.out.println("Please enter a valid option (a, b, c, or d).");
                        continue;
                    }
                }
                if (q instanceof TrueFalseQuestion) {
                    if (!(answer.equals("true") || answer.equals("false")
                    		|| answer.equals("t") || answer.equals("f")
                            || answer.equals("yes") || answer.equals("no")
                            || answer.equals("y") || answer.equals("n"))) {

                        System.out.println("Please enter true/false, t/f, yes/no, or y/n.");
                        continue;
                    }
                }
                break;
            }

            boolean isCorrect = q.checkAnswer(answer);

            if (isCorrect) {
                System.out.println("Correct");
                totalScore += q.getPoints();
                correct++;
            } else {
                System.out.println("Wrong");
                wrong++;
            }
            index++;
        }

        student.setLastScore(totalScore);
        student.setLastCorrectCount(correct);
        student.setLastWrongCount(wrong);
        
        int examId = questionBank.getNextQuestionId(); 
        ExamResultCSV.saveResult(student, totalScore, correct, wrong, examId);

        System.out.println("\n QUIZ FINISHED ");
        System.out.println("Total Score    : " + totalScore);
        System.out.println("Correct Answers: " + correct);
        System.out.println("Wrong Answers  : " + wrong);
    }

    // TEACHER MENU 

    private void showTeacherMenu(Teacher teacher) {
        while (true) {
            System.out.println("\n TEACHER MENU ");
            System.out.println("1) View all students results");
            System.out.println("2) Search student by ID");
            System.out.println("3) Add new question");
            System.out.println("4) Add new student");
            System.out.println("5) Logout");
            System.out.print("Select option: ");

            switch (scanner.nextLine().trim()) {
                case "1" -> viewAllStudentResults();
                case "2" -> searchStudentById();
                case "3" -> addQuestionFromTeacher();
                case "4" -> addStudentFromTeacher();
                case "5" -> {
                    System.out.println("Logging out.\n");
                    return;
                }
                default -> System.out.println("Invalid option.\n");
            }
        }
    }
    
    private void viewAllStudentResults() {

        System.out.println("\nALL STUDENTS RESULTS");
        System.out.println("Total students: " + students.size());
        System.out.println("ID   NAME                 SCORE  CORRECT  WRONG");

        for (Student s : students) {
            System.out.printf(
                    "%-4d %-20s %-6.1f %-8d %-5d%n",
                    s.getId(),s.getFullName(),s.getLastScore(),s.getLastCorrectCount(),s.getLastWrongCount()
            );
        }

        System.out.println();
    }
    
    private void searchStudentById() {
        int id = askInt("Enter student ID: ");
        students.stream() .filter(s -> s.getId() == id) .findFirst() .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("No student found.")
                );
    }
    private String askStringOrCancel(String msg) {
        System.out.print(msg);
        String input = scanner.nextLine().trim();

        if (input.equalsIgnoreCase("cancel")) {
            return null;
        }
        if (input.isEmpty()) {
            System.out.println("Input cannot be empty.");
            return askStringOrCancel(msg);
        }
        return input;
    }
    private int askIntInRangeOrCancel(String msg, int min, int max) {

        int attempts = 3;

        while (attempts-- > 0) {
            System.out.print(msg);
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("cancel")) {
                return -1;
            }

            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException ignored) {}

            System.out.println("Invalid input (" + (attempts) + " attempt(s) left)");
        }

        System.out.println("Too many invalid attempts. Cancelled.");
        return -1;
    }

    // ADD QUESTION 
    
    private void addQuestionFromTeacher() {

        System.out.println("\n ADD NEW QUESTION ");
        System.out.println("(type 'cancel' anytime to abort)");

        System.out.print("Type (1 = MC, 2 = TF): ");
        String type = scanner.nextLine().trim();

        if (type.equalsIgnoreCase("cancel")) return;
        if (!type.equals("1") && !type.equals("2")) {
            System.out.println("Invalid type.\n");
            return;
        }

        int difficulty = askIntInRangeOrCancel("Difficulty (1–5): ", 1, 5);
        if (difficulty == -1) return;

        int id = questionBank.getNextQuestionId();
        double points = questionBank.calculatePoints(difficulty);

        System.out.println("ID: " + id + " | Points: " + points);

        String text = askStringOrCancel("Question text: ");
        if (text == null) return;
        
        if (type.equals("1")) {
            List<String> options = new ArrayList<>();

            for (int i = 0; i < 4; i++) {
                String opt = askStringOrCancel("Option " + (char) ('a' + i) + ": ");
                if (opt == null) return;

                opt = opt.trim();

                if (options.contains(opt)) {
                    System.out.println("This option already exists. Please enter a different one.");
                    i--;          
                    continue;    
                }

                options.add(opt);
            }

            int correct = askIntInRangeOrCancel("Correct index (0–3): ", 0, 3);
            if (correct == -1) return;
            MultipleChoiceQuestion mcq =
            	    new MultipleChoiceQuestion(id, text, difficulty, options, correct, points);

            	questionBank.addQuestion(mcq);
            	questionBank.saveQuestionToCsv(mcq);

        }

         else {

            boolean correct;
            while (true) {
                String input = askStringOrCancel("Correct answer (true/false): ");
                if (input == null) return;

                if (input.equalsIgnoreCase("true")) {
                    correct = true;
                    break;
                }
                if (input.equalsIgnoreCase("false")) {
                    correct = false;
                    break;
                }
                System.out.println("Please enter 'true' or 'false'.");
            }

            TrueFalseQuestion tfq =
            	    new TrueFalseQuestion(id, text, difficulty, points, correct);

            	questionBank.addQuestion(tfq);
            	questionBank.saveQuestionToCsv(tfq);
        }

        System.out.println("Question added successfully.\n");
    }
    private void addStudentFromTeacher() {

        System.out.println("\n ADD NEW STUDENT ");
        
        int id = getNextStudentId();

        String username = askStringOrCancel("Username: ");
        if (username == null) return;

        String password = askStringOrCancel("Password: ");
        if (password == null) return;

        String fullname = askStringOrCancel("Full name: ");
        if (fullname == null) return;

        Student s = new Student(id, username, password, fullname);

        students.add(s);
        StudentCSVReader.saveStudent(s);

        System.out.println("Student added successfully.\n");
    }

    // HELPERS
    
    private int getNextStudentId() {
        int maxId = 0;
        for (Student s : students) {
            if (s.getId() > maxId) maxId = s.getId();
        }
        return maxId + 1;
    }

    private int askInt(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}