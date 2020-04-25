package jdbcexamples;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class CallableSP {

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
		ResultSet rs = null;

		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost/hr?" + "user=hr&password=hr");

			// Do something with the Connection
			System.out.println("Connected to mySQL using Driver");

			//
			// Prepare a call to the stored procedure 'demoSp'
			// with two parameters
			//
			// Notice the use of JDBC-escape syntax ({call ...})
			//

			CallableStatement cStmt = connection.prepareCall("{call demoSp(?, ?)}");

			//cStmt.setString(1, "abcdefg");
			
			//
			// Connector/J supports both named and indexed
			// output parameters. You can register output
			// parameters using either method, as well
			// as retrieve output parameters using either
			// method, regardless of what method was
			// used to register them.
			//
			// The following examples show how to use
			// the various methods of registering
			// output parameters (you should of course
			// use only one registration per parameter).
			//

			//
			// Registers the second parameter as output, and
			// uses the type 'INTEGER' for values returned from
			// getObject()
			//

			//cStmt.registerOutParameter(2, Types.INTEGER);

			//
			// Registers the named parameter 'inOutParam', and
			// uses the type 'INTEGER' for values returned from
			// getObject()
			//

			//cStmt.registerOutParameter("inOutParam", Types.INTEGER);

			//
			// Set a parameter by index
			//

			//cStmt.setString(1, "abcdefg");

			//
			// Alternatively, set a parameter using
			// the parameter name
			//

			cStmt.setString("inputParam", "abcdefg");

			//
			// Set the 'in/out' parameter using an index
			//

			//cStmt.setInt(2, 1);

			//
			// Alternatively, set the 'in/out' parameter
			// by name
			//

			cStmt.setInt("inOutParam", 1);

			boolean hadResults = cStmt.execute();

			//
			// Process all returned result sets
			//

			while (hadResults) {
				rs = cStmt.getResultSet();

				// process result set

				hadResults = cStmt.getMoreResults();
			}

			//
			// Retrieve output parameters
			//
			// Connector/J supports both index-based and
			// name-based retrieval
			//

			int outputValue = cStmt.getInt(2); // index-based

			outputValue = cStmt.getInt("inOutParam"); // name-based
			System.out.println(outputValue);
			System.out.println("executed stored procedure");

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

            if (rs != null) {
                try {
                	rs.close();
                } catch (SQLException sqlEx) { } // ignore

                rs = null;
            }
		}
	}
}
