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
		        createDemoQuestions();
		    }
		  
		  private static void createDemoQuestions() {
			  
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



	
	 
	 
		 


	    	
	    	
	    	
	    	
	    	
	    }



