package ca.jrvs.apps.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Hello world!
 *
 */
public class App 
{

    public static void main( String[] args )
    {
        DatabaseConnectionManager manager = new DatabaseConnectionManager("localhost", "hplussport", "user", "1234");

        try {
            Connection Connection = manager.getConnection();
            CustomerDAO customerDAO = new CustomerDAO(Connection);
            Customer customer = customerDAO.findById(3);
            System.out.println(customer);
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }
}
