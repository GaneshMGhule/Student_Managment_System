package stud_M;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Stud_prc {
	

	    private static final String URL = "jdbc:postgresql://localhost:8494/student_db";
	    private static final String USER = "postgres";
	    private static final String PASSWORD = "1234";

	    public static void main(String[] args) {
	        Scanner scanner = new Scanner(System.in);
	       // createDB("student_db");
	        createTable();
	        while (true) {
	            System.out.println("\n************** Student Management System ******************\n");
	            System.out.println("1. Add Student");
	            System.out.println("2. View Students");
	            System.out.println("3. Update Student");
	            System.out.println("4. Delete Student");
	            System.out.println("5. Exit");
	            System.out.print("Enter choice: ");
	            int choice = scanner.nextInt();
	            scanner.nextLine();

	            switch (choice) {
	                case 1:
	                    addStudent(scanner);
	                    break;
	                case 2:
	                    viewStudents();
	                    break;
	                case 3:
	                    updateStudent(scanner);
	                    break;
	                case 4:
	                    deleteStudent(scanner);
	                    break;
	                case 5:
	                    System.out.println("Exiting...");
	                    scanner.close();
	                    return;
	                default:
	                    System.out.println("Invalid choice, try again.");
	            }
	        }
	    }
	    
	    public static void createDB(String dbName) {
	        

	        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
	             Statement stmt = con.createStatement()) {

	            String query = "CREATE DATABASE " + dbName;
	            stmt.executeUpdate(query);
	            System.out.println("Database '" + dbName + "' created successfully!");

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    private static void createTable() {
	        String sql = "CREATE TABLE IF NOT EXISTS students ("
	                   + "id SERIAL PRIMARY KEY, "
	                   + "name VARCHAR(100) NOT NULL, "
	                   + "age INT NOT NULL)";
	        try (Connection conn = DriverManager.getConnection(URL , USER, PASSWORD);
	             Statement stmt = conn.createStatement()) {
	            stmt.executeUpdate(sql);
	            System.out.println("Table 'students' is ready.");
	        } catch (SQLException e) {
	            System.out.println("Error creating table: " + e.getMessage());
	        }
	    }

	    private static void addStudent(Scanner scanner) {
	    	System.out.println("-----Add Student Info-----\n");
	        System.out.print("Enter student name: ");
	        String name = scanner.nextLine();
	        System.out.print("Enter student age: ");
	        int age = scanner.nextInt();
	        scanner.nextLine();

	        String sql = "INSERT INTO students (name, age) VALUES (?, ?)";
	        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
	             PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setString(1, name);
	            stmt.setInt(2, age);
	            stmt.executeUpdate();
	            System.out.println("Student added successfully.");
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	 

private static void viewStudents() {
    String sql = "SELECT * FROM students";
    List<String> students = new ArrayList<>(); 

    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        while (rs.next()) {
            String student = "ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") + ", Age: " + rs.getInt("age");
            students.add(student);
        }

        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
        	for (int i = 0; i < students.size(); i++) {
                System.out.println(students.get(i).toString());
           }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

	    private static void updateStudent(Scanner scanner) {
	        System.out.print("Enter student ID to update: ");
	        int id = scanner.nextInt();
	        scanner.nextLine();
	        System.out.print("Enter new name: ");
	        String name = scanner.nextLine();
	        System.out.print("Enter new age: ");
	        int age = scanner.nextInt();

	        String sql = "UPDATE students SET name = ?, age = ? WHERE id = ?";
	        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
	             PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setString(1, name);
	            stmt.setInt(2, age);
	            stmt.setInt(3, id);
	            int rowsUpdated = stmt.executeUpdate();
	            if (rowsUpdated > 0) {
	                System.out.println("Student updated successfully.");
	            } else {
	                System.out.println("Student not found.");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    private static void deleteStudent(Scanner scanner) {
	        System.out.print("Enter student ID to delete: ");
	        int id = scanner.nextInt();

	        String sql = "DELETE FROM students WHERE id = ?";
	        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
	             PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setInt(1, id);
	            int rowsDeleted = stmt.executeUpdate();
	            if (rowsDeleted > 0) {
	                System.out.println("Student deleted successfully.");
	            } else {
	                System.out.println("Student not found.");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}

