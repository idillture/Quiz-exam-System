package quiz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class QuizSystem {

	 private static Teacher teacher;
	 private static List<Student> students;
	 private static QuestionBank questionBank;

	 private static Scanner scanner = new Scanner(System.in);

	 public static void main(String[] args) {
		 
		 initializeData();
	    	
		 while (true) {
	            System.out.println(" QUIZ SYSTEM LOGIN ");
	            System.out.print(" Username : ");
	            System.out.print(" Exit ");
	            String username = scanner.nextLine().trim();

	            if (username.equalsIgnoreCase("Exit")) {
	                System.out.println("Exiting the system.");
	                break;
	            }
	            
	            System.out.print(" Password : ");
	            String password = scanner.nextLine().trim();

	            User user = login(username, password);

	            if (user == null) {
	                System.out.println("Invalid username or password. \n");
	            } 
	            else if (user instanceof Teacher) {
	                System.out.println("\nWelcome, " + user.getFullName() + "!\n");
	                showTeacherMenu((Teacher) user);
	            } 
	            else if (user instanceof Student) {
	                System.out.println("\nWelcome, " + user.getFullName() + "!\n");
	                showStudentMenu((Student) user);
	            }
		 }
		  scanner.close();
	 }
		  
		  private static void initializeData() {
		        teacher = new Teacher(99, "teacher", "12345", "Teacher");

		        students = new ArrayList<>();
		        students.add(new Student(100, "idilture", "230303020", "İdil Türe"));
		        students.add(new Student(101, "eylultuncel", "230303021", "Eylül Tuncel"));
		        students.add(new Student(102, "sudenazibis", "230303022", "Sudenaz İbiş"));

		        questionBank = new QuestionBank();

		        questionBank.loadFromCsv("questions.csv");

		        System.out.println("Loaded " + questionBank.getAllQuestions().size()
		                + " questions from CSV.\n");
		    }
		  
		  private static User login(String username, String password) {
			  
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
		  
		  //student menu
		  private static void showStudentMenu(Student student) {
		        while (true) {
		            System.out.println(" STUDENT MENU ");
		            System.out.println("1) Start new quiz");
		            System.out.println("2) View last exam result");
		            System.out.println("3) Logout");
		            System.out.print("Select an option: ");

		            String choice = scanner.nextLine().trim();

		            switch (choice) {
		                case "1":
		                    startQuizForStudent(student);
		                    break;
		                case "2":
		                    showLastExamResult(student);
		                    break;
		                case "3":
		                    System.out.println("Logging out.\n");
		                    return;
		                default:
		                    System.out.println("Invalid option. Please try again.\n");
		            }
		        }
		    }

		    private static void showLastExamResult(Student student) {
		        System.out.println("\n LAST EXAM RESULT ");
		        System.out.println("Student : " + student.getFullName());
		        System.out.println("Last Score : " + student.getLastScore());
		        System.out.println("Correct Answers : " + student.getLastCorrectCount());
		        System.out.println("Wrong Answers : " + student.getLastWrongCount());
		        System.out.println();
		    }
		    
		    private static void startQuizForStudent(Student student) {
		        System.out.println("/n START NEW QUIZ ");
		        System.out.println("The exam will have 10 questions:");
		        System.out.println("5 True/False");
		        System.out.println("5 Multiple Choice");

		        List<Question> examQuestions = new ArrayList<>();

		        // For each difficulty from 1 to 5, pick 1 TF and 1 MC
		        for (int difficulty = 1; difficulty <= 5; difficulty++) {

		            List<TrueFalseQuestion> tfList =
		                    questionBank.getTrueFalseQuestionsByDifficulty(difficulty);
		            List<MultipleChoiceQuestion> mcList =
		                    questionBank.getMultipleChoiceQuestionsByDifficulty(difficulty);

		            if (tfList.isEmpty() || mcList.isEmpty()) {
		                System.out.println("Not enough questions for difficulty " + difficulty +
		                        ". Need at least 1 TF and 1 MC.");
		                System.out.println("Quiz cannot be started. Please ask teacher to add more questions.\n");
		                return;
		            }

		            // Shuffle lists so we pick a random question from each
		            Collections.shuffle(tfList);
		            Collections.shuffle(mcList);

		            examQuestions.add(tfList.get(0));
		            examQuestions.add(mcList.get(0));
		        }

		        // Shuffle the final list so the order is mixed 
		        Collections.shuffle(examQuestions);

		        Quiz quiz = new Quiz(1, "Mixed difficulty quiz");
		        for (Question q : examQuestions) {
		            quiz.addQuestion(q);
		        }

		        double totalScore = 0.0;
		        int correctCount = 0;
		        int wrongCount = 0;

		        System.out.println(" Answer the following questions: \n");

		        int index = 1;
		        for (Question q : quiz.getQuestions()) {
		            System.out.println("Question " + index + ": " + q.getText());

		            if (q instanceof MultipleChoiceQuestion) {
		                MultipleChoiceQuestion mcq = (MultipleChoiceQuestion) q;
		                mcq.shuffleOptions(); // shuffle options 

		                List<String> options = mcq.getOptions();
		                char letter = 'a';
		                for (String opt : options) {
		                    System.out.println("  " + letter + ") " + opt);
		                    letter++;
		                }
		                System.out.print("Your answer : ");

		            } else if (q instanceof TrueFalseQuestion) {
		                System.out.print("Your answer : ");
		            }

		            String answer = scanner.nextLine();
		            boolean correct = q.checkAnswer(answer);

		            if (correct) {
		                System.out.println("Correct\n");
		                correctCount++;
		                totalScore += q.getPoints();
		            } else {
		                System.out.println("Wrong\n");
		                wrongCount++;
		            }
		            index++;
		        }

		        // Save last exam results
		        student.setLastScore(totalScore);
		        student.setLastCorrectCount(correctCount);
		        student.setLastWrongCount(wrongCount);

		        System.out.println(" QUIZ FINISHED ");
		        System.out.println("Total Score   : " + totalScore);
		        System.out.println("Correct Answers : " + correctCount);
		        System.out.println("Wrong Answers : " + wrongCount + "\n");
		    }
		    
		    //teacher menu
		    private static void showTeacherMenu(Teacher teacher) {
		        while (true) {
		            System.out.println(" TEACHER MENU ");
		            System.out.println("1) View all students last results");
		            System.out.println("2) Search student by ID");
		            System.out.println("3) Add new question");
		            System.out.println("4) Logout");
		            System.out.print("Select an option: ");

		            String choice = scanner.nextLine().trim();

		            switch (choice) {
		                case "1":
		                    viewAllStudentResults();
		                    break;
		                case "2":
		                    searchStudentById();
		                    break;
		                case "3":
		                    addQuestionFromTeacher();
		                    break;
		                case "4":
		                    System.out.println("Logging out.\n");
		                    return;
		                default:
		                    System.out.println("Invalid option. Please try again.\n");
		            }
		        }
		    }

		    private static void viewAllStudentResults() {
		        System.out.println("\n ALL STUDENT RESULTS ");
		        for (Student s : students) {
		            System.out.println(s.toString());
		        }
		        System.out.println();
		    }

		    private static void searchStudentById() {
		        int id = askInt("\nEnter student ID: ");

		        for (Student s : students) {
		            if (s.getId() == id) {
		                System.out.println("Students results :\n" + s.toString() + "\n");
		                return;
		            }
		        }
		        System.out.println("No student found with " + id + "\n");
		    }
		    
		    




	
	 
	 
		 


	    	
	    	
	    	
	    	
	    	
	    }



