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



