
package service;

import config.DbConfig;
import entity.Customer;

import java.sql.*;

public class CustomerService {

    public void addCustomer(Customer customer) throws SQLException {

        Connection conn = DbConfig.getConnection();

        String sql = "INSERT INTO customer(name, phone) VALUES (?, ?)"; // here ?? are states that i will provide
                                                                        // values later ?-1 and ?-2

        PreparedStatement ps = conn.prepareStatement(sql);

        // here preparestatement is used to prepare the sql statement thatw e wrote
        // and conn is used to create a conn
        // ps is used to store that query


        ps.setString(1, customer.getName());
      //  There are two important parts:
       // ps.setString(1, ...)
       // 1 means first ?.

        ps.setString(2, customer.getPhone());
        //2 means second ?.

        ps.executeUpdate();
//        This is the line that actually executes your SQL query.
//        Before this, you had:
//
//        String sql = "INSERT INTO customer(name, phone) VALUES (?, ?)";
//        PreparedStatement ps = conn.prepareStatement(sql);
//        ps.setString(1, customer.getName());
//        ps.setString(2, customer.getPhone());
//
//        At this point, the query is prepared and the values are inserted into the ?.
//        But nothing has been inserted into the database yet.
//
//                Then:
//        ps.executeUpdate();
//        means:
//        Execute the INSERT query in the database.
//                So your customer gets inserted into the customer table.

        ps.close();
//        You created a PreparedStatement:
//        PreparedStatement ps = conn.prepareStatement(sql);
//        After you're finished using it, you close it.

        conn.close();
        // this is used to close the database connection
    }

    public Customer getCustomerByPhone(String phone) throws SQLException {

        Connection conn = DbConfig.getConnection();

        String sql = "SELECT * FROM customer WHERE phone = ?";

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, phone);

        ResultSet rs = ps.executeQuery();

        Customer customer = null;
       // Initially, we don't know whether the customer exists.

        if (rs.next()) {
        //basically means  //"If I found a customer.

            //takes the data from the database and creates a Java Customer object.
            customer = new Customer(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("phone")
            );
        }

        rs.close();
        ps.close();
        conn.close();

        return customer;
    }

    public Customer getCustomerById(int id) throws SQLException {

        Connection conn = DbConfig.getConnection();

        String sql = "SELECT * FROM customer WHERE id = ?";

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        Customer customer = null;

        if (rs.next()) {

            customer = new Customer(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("phone")
            );
        }

        rs.close();
        ps.close();
        conn.close();

        return customer;
    }
}