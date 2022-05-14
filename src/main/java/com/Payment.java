package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Payment {
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/payments", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	 
	//*************************read payments***********************************************
	public String readPayments() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
// Prepare the html table to be displayed
			output = "<table><tr><th style=width:100px; >Payment ID</th>"
					+ "<th style=width:100px; >Amount</th>"
					+ "<th style=width:100px; >Card Number</th>"
					+ "<th style=width:100px; >Card Holder</th>"
					+ "<th style=width:100px; >CVV</th>"
					+ "<th style=width:100px; >Expire Date</th>"
					+ "<th style=width:50px; >Update</th>"
					+ "<th style=width:50px; >Remove</th></tr>";

			
			String query = "select * from payment";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
// iterate through the rows in the result set
			while (rs.next()) {
				String paymentId = Integer.toString(rs.getInt("paymentId"));
				String amount = rs.getString("amount");
				String cardNo = rs.getString("cardNo");
				String cardHolder = rs.getString("cardHolder");
				String cvv = rs.getString("cvv");
				String expDate = rs.getString("expDate");
// Add into the html table
				output += "<tr><td><input id='hidpaymentIdUpdate'name='hidpaymentIdUpdate' value='" + paymentId+"' ></td>";
				output += "<td>" + amount + "</td>";
				output += "<td>" + cardNo + "</td>";
				output += "<td>" + cardHolder + "</td>";
				output += "<td>" + cvv + "</td>";
				output += "<td>" + expDate + "</td>";
// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'>"
						+ "</td><td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-paymentId='"+paymentId+"'></td></tr>";
			}
			con.close();
// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading payments.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	//***********************************insert payments****************************************
public String insertPayment(String amount, String cardNo,String cardHolder, String cvv, String expDate)
{
String output = "";
try
{
Connection con = connect();
if (con == null)
{
return "Error while connecting to the database for inserting.";
}
// create a prepared statement
String query = " insert into payment(`paymentId`,`amount`,`cardNo`,`cardHolder`,`cvv`,`expDate`) values (?, ?, ?, ?, ?,?)";
PreparedStatement preparedStmt = con.prepareStatement(query);
// binding values
preparedStmt.setInt(1, 0);
preparedStmt.setString(2, amount);
preparedStmt.setString(3, cardNo);
preparedStmt.setString(4, cardHolder);
preparedStmt.setString(5, cvv);
preparedStmt.setString(6, expDate);
// execute the statement
preparedStmt.execute();
con.close();
String newPayments = readPayments();
output = "{\"status\":\"success\", \"data\": \"" +
		newPayments + "\"}";
}
catch (Exception e)
{
output = "status:error ,data:Error while inserting the payment.";
System.err.println(e.getMessage());
}
return output;
}

//***********************************update payments****************************************
public String updatePayment(String paymentId, String amount, String cardNo,String cardHolder, String cvv, String expDate)
{
String output = "";
try
{
Connection con = connect();
if (con == null)
{
return "Error while connecting to the database for updating.";
}
// create a prepared statement
String query = "UPDATE payment SET amount=?,cardNo=?,cardHolder=?,cvv=?,expDate=? WHERE paymentId=?";
PreparedStatement preparedStmt = con.prepareStatement(query);
// binding values
preparedStmt.setString(1, amount);
preparedStmt.setString(2, cardNo);
preparedStmt.setString(3, cardHolder);
preparedStmt.setString(4, cvv);
preparedStmt.setString(5, expDate);
preparedStmt.setInt(6, Integer.parseInt(paymentId));
// execute the statement
preparedStmt.execute();
con.close();
String newPayments = readPayments();
output = "{\"status\":\"success\", \"data\": \"" +
		newPayments + "\"}";
}
catch (Exception e)
{
output = "status:error data Error while updating the payment.";
System.err.println(e.getMessage());
}
return output;
}



//***********************************delete payments****************************************

public String deletePayment(String paymentId)
{
String output = "";
try
{
Connection con = connect();
if (con == null)
{
return "Error while connecting to the database for deleting.";
}
// create a prepared statement
String query = "delete from payment where paymentId=?";
PreparedStatement preparedStmt = con.prepareStatement(query);
// binding values
preparedStmt.setInt(1, Integer.parseInt(paymentId));
// execute the statement
preparedStmt.execute();
con.close();
String newPayments= readPayments();
output = "{\"status\":\"success\", \"data\": \"" +
newPayments + "\"}";
}
catch (Exception e)
{
output = "status:error data :Error while deleting payment.";
System.err.println(e.getMessage());
}
return output;
}
}