package beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.rowset.JdbcRowSet;

import com.sun.rowset.JdbcRowSetImpl;

public class EmployeeBean {
	private int empid;
	private int salary;
	private String message;
	private JdbcRowSet rowSet = null;

	public int getEmpid() {
		return empid;
	}

	public void setEmpid(int empid) {
		this.empid = empid;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	// Readonly property 
	public String getMessage() {
		return message;
	}
	public void JdbcRowSetDemo() {		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/hr?user=root&password=V1sh@9632");
			Statement st = con.createStatement();
	        ResultSet rs = st.executeQuery("select * from jobs");
	        while(rs.next()) {
                System.out.println( rs.getString("title"));
            }
	        st.close();
            rs.close();
            con.close();
		} catch (ClassNotFoundException|SQLException e) {
			e.printStackTrace();
		}
        
    }
	// Returns number of rows updates - 0,1 or -1 for error
	public int updateSalary() {
		try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "hr", "hr")) {
			PreparedStatement ps = con.prepareStatement("update employees set salary = ? where employee_id = ?");
			ps.setInt(1, salary);
			ps.setInt(2, empid);
			int count = ps.executeUpdate();
			if (count == 1)
				message = "Updated Salary Successfully!";
			else
				message = "Employee Id Not Found!";
			ps.close();
			return count;
		} catch (Exception ex) {
			message = "Error :" + ex.getMessage();
			return -1;
		}
	}
}
