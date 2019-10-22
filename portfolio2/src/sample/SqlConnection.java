package sample;
import java.sql.*;

// connection class for connecting to database, got alot of errors so used following source for debuging - https://www.youtube.com/watch?v=JPsWaI5Z3gs
public class SqlConnection {


    private static Connection con;
    private  static  boolean hasdata = false;

    /// gets resultSet from input SQL string(quary)
    public ResultSet select(String quary)throws ClassNotFoundException, SQLException
    {
        //make connection if null found
        if(con == null)
        {
            getConnection();
        }

        Statement state = con.createStatement(); // creating statement sql for server
        ResultSet res = state.executeQuery(quary);// adding our sql string to statement af execute
        return res;

    }


    //connection settings
    private void  getConnection() throws ClassNotFoundException, SQLException
    {
        Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection("jdbc:sqlite:db.db");
    }



}
