package jdbcexamples;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Statement;
import java.sql.ResultSet;

// Notice, do not import com.mysql.cj.jdbc.*
// or you will have problems!

public class ConnectDB {
    public static void main(String[] args) {
        try {
            // The newInstance() call is a work around for some
            // broken Java implementations

            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            System.out.println("Loaded Driver");
        } catch (Exception ex) {
            // handle the error
        }
        
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/hr?" +
                                           "user=hr&password=hr");

            // Do something with the Connection
            System.out.println("Connected to mySQL using Driver");
            
            statement = connection.createStatement();
            //resultSet = stmt.executeQuery("SELECT * FROM jobs");

            // or alternatively, if you don't know ahead of time that
            // the query will be a SELECT...

            if (statement.execute("SELECT job_title FROM jobs")) {
                resultSet = statement.getResultSet();  
                System.out.println("Executed Statement and filled the resultSet");
            }

            // Now do something with the ResultSet ....
            System.out.println("<------------------Displaying resultSet------------------------->");
            while(resultSet.next()) {
            	System.out.println(resultSet.getString("job_title"));            	
            }
            

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        finally {
            // it is a good idea to release
            // resources in a finally{} block
            // in reverse-order of their creation
            // if they are no-longer needed

            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException sqlEx) { } // ignore

                resultSet = null;
            }

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqlEx) { } // ignore

                statement = null;
            }
        }
    }
}
