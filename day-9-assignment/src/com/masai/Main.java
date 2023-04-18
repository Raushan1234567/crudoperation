package com.masai;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {

	
		// TODO Auto-generated method stub
		static {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");				
			}catch(ClassNotFoundException ex) {
				System.exit(1);
			}
		}
		
		//System.exit(0); treated as normal exit by OS
		//System.exit(other than 0); treated as abnormal exit by OS
		
		final static String URL = "jdbc:mysql://localhost:3306/raushan";
		final static String USER = "root";
		final static String PASS = "Raushan@123456789";
		
		static Connection makeConnection() throws SQLException{
			return DriverManager.getConnection(URL, USER, PASS);
		}
		
		//Any more improvements?
		
		static void closeConnection(Connection connection) throws SQLException{
			if(connection != null)
				connection.close();
		}
		
		static void insertMobile(Scanner sc) {
			try {
				Connection connection = makeConnection();
				
				//INSERT operation for the student table
				//step-1: Take input from user
				System.out.print("Enter id ");
				int id = sc.nextInt();
				System.out.print("Enter modelno ");
				String modelno = sc.next();
				System.out.print("Enter company name ");
				String company_name = sc.next();
				System.out.print("Enter price ");
				double price= sc.nextDouble();
				System.out.print("Enter manufacture  date ");
				LocalDate manufactureDate = LocalDate.parse(sc.next());
				
				//step-2: query created
				String insertQuery = "INSERT INTO mobile (id, model_no,  company , price,MFGdate) VALUES (?, ?, ?, ? , ?)";
				
				//step-3: get object of PreparedStatement for this connection
				PreparedStatement ps = connection.prepareStatement(insertQuery);
				
				//provide value for the ?
				ps.setInt(1, id);
				ps.setString(2, modelno);
				ps.setString(3, company_name);
				
				ps.setDouble(4, price);
				ps.setDate(5, Date.valueOf(manufactureDate));
				
				//For debugging, print the query
				//System.out.println(ps);
				
				//step-5
				if(ps.executeUpdate() > 0) {
					System.out.println("Mobile  added successfully");
				}else {
					System.out.println("No Mobile Added");
				}
				
				closeConnection(connection);
			}catch(SQLException ex) {
				System.out.println(ex);
			}
		
	}
		
		
		public static void main(String[] args) {
			Scanner sc = new Scanner(System.in);
			int choice = 0;
			do {
				System.out.println("1. Add Mobile");
				System.out.println("2. View Mobile List");
				System.out.println("3. Update Mobile Details");
				System.out.println("4. Delete Mobile Details");
				System.out.println("5. Search Mobile model Number");
				System.out.println("0. Exit");
				System.out.print("Enter selection ");
				choice = sc.nextInt();
				switch(choice) {
					case 1:
						insertMobile(sc);
						break;
					case 2:
						viewStudentList();
						break;
					case 3:
						updateMobile(sc);
						break;
					case 4:
						deleteMobile(sc);
						break;
					case 5:
						searchStudentByRollNo(sc);
						break;
					case 0:
						System.out.println("Thank you, visit again");
						break;
					default:
						System.out.println("Invalid selection, please try again");
				}
			}while(choice != 0);
			sc.close();
		}

		static void updateMobile(Scanner sc) {
			try {
				Connection connection = makeConnection();
				
				//INSERT operation for the student table
				//step-1: Take input from user
				System.out.print("Enter id ");
				int id = sc.nextInt();
				System.out.print("Enter modelno ");
				String modelno = sc.next();
				System.out.print("Enter company name ");
				String company = sc.next();
				System.out.print("Enter price ");
				double price= sc.nextDouble();
				System.out.print("Enter manufacture  date ");
				LocalDate manufactureDate = LocalDate.parse(sc.next());
				
				//step-2: query created
				String updateQuery = "UPDATE mobile SET id = ?,  company  = ?, price= ? ,MFGdate=? WHERE model_no = ?";
				
				//step-3: get object of PreparedStatement for this connection
				PreparedStatement ps = connection.prepareStatement(updateQuery);
				
				//provide value for the ?
				
				ps.setInt(1, id);
			
				ps.setString(2, company);
				ps.setDouble(3, price);
				ps.setDate(4, Date.valueOf(manufactureDate));
				ps.setString(5, modelno);
				
				
				//For debugging, print the query
				//System.out.println(ps);
				
				//step-5
				if(ps.executeUpdate() > 0) {
					System.out.println("Mobile updated successfully");
				}else {
					System.out.println("No Mobile updated");
				}
				
				closeConnection(connection);
			}catch(SQLException ex) {
				System.out.println(ex);
			}
		}

		private static void viewStudentList() {
			// TODO Auto-generated method stub
			
		}

		static void deleteMobile(Scanner sc) {
			try {
				Connection connection = makeConnection();
				
				//INSERT operation for the student table
				//step-1: Take input from user
				System.out.print("Enter model no ");
				String modelno = sc.next();
				
				//step-2: query created
				String updateQuery = "DELETE FROM mobile WHERE model_no = ?";
				
				//step-3: get object of PreparedStatement for this connection
				PreparedStatement ps = connection.prepareStatement(updateQuery);
				
				//provide value for the ?
				ps.setString(1, modelno);
				
				//For debugging, print the query
				//System.out.println(ps);
				
				//step-5
				if(ps.executeUpdate() > 0) {
					System.out.println("Mobile deleted successfully");
				}else {
					System.out.println("No Mobile deleted");
				}
				
				closeConnection(connection);
			}catch(SQLException ex) {
				System.out.println(ex);
			}
		}

		static void searchStudentByRollNo(Scanner sc) {
			try {
				Connection connection = makeConnection();
				
				//code to the input the rollNo
				System.out.print("Enter roll number ");
				String modelno = sc.next();
				
				//create SELECT query
				String selectQuery = "SELECT model_no, company  , price, MFGdate FROM mobile WHERE model_no = ?";
				
				//create preparaedStatement
				PreparedStatement ps = connection.prepareStatement(selectQuery);
				
				//fill the data
				ps.setString(1, modelno);
				
				//call the executeQuery() method
				ResultSet rs = ps.executeQuery();
				
				//code to print the record
				if(isResultSetEmpty(rs)) {
					//you are here means no record is available fo his roll no
					System.out.println("No student with roll number " + modelno);
				}else {
					//you are here means student with rollNo is found and resultset has only one record
					//ResultSet is now before the first
					rs.next();
					System.out.println("model no " + rs.getString(1) + "company Name " + rs.getString(2) + " Price " + rs.getDouble(3) + " Manufacture Date " + rs.getDate(4));
				}
				
				closeConnection(connection);
			}catch(SQLException ex) {
				System.out.println(ex);
			}
		}

		static boolean isResultSetEmpty(ResultSet rs) throws SQLException {
		//The returned value of rs.isBeforeFirst() is false when no record in the resultset
		//the returned value of rs.getRow() is zero when no record in the resultset
		return (!rs.isBeforeFirst() && rs.getRow() == 0);
	}
	

}
