//package com.flipkart.dao;
//
//import com.flipkart.bean.Customer;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class CustomerDaoImpl implements CustomerDaoInterface {
//
//    // Key: customerId (String), Value: Customer Object
//    // Static ensures the data stays in memory as long as the app is running
//    private static final Map<String, Customer> customerMap = new HashMap<>();
//
//    @Override
//    public void addCustomer(Customer customer) {
//        customerMap.put(customer.getCustomerId(), customer);
//        System.out.println("DAO: Customer registered successfully with ID: " + customer.getCustomerId());
//    }
//
//    @Override
//    public Customer getCustomerById(String customerId) {
//        return customerMap.get(customerId);
//    }
//
//    @Override
//    public Customer getCustomerByEmail(String email) {
//        // Since the map key is ID, we stream values to find the email match
//        return customerMap.values().stream()
//                .filter(customer -> customer.getEmail().equalsIgnoreCase(email))
//                .findFirst()
//                .orElse(null);
//    }
//
//    @Override
//    public List<Customer> getAllCustomers() {
//        // Return a new list to avoid external modification of the internal map
//        return new ArrayList<>(customerMap.values());
//    }
//}
package com.flipkart.dao;

import com.flipkart.bean.Customer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDaoInterface {

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/flipfit_schema";
    private static final String USER = "root";
    private static final String PASS = "admin123";

    // Helper method to get a connection
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    @Override
    public void addCustomer(Customer customer) {
        String sql = "INSERT INTO Customer (customerId, name, email, phoneNumber,password) VALUES (?, ?, ?, ?,?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, customer.getCustomerId());
            pstmt.setString(2, customer.getName());
            pstmt.setString(3, customer.getEmail());
            pstmt.setString(4, customer.getPhoneNumber());
            pstmt.setString(5, customer.getPasswordHash());

            pstmt.executeUpdate();
            System.out.println("DAO: Customer registered successfully in DB with ID: " + customer.getCustomerId());
        } catch (SQLException e) {
            System.err.println("Error adding customer: " + e.getMessage());
        }
    }

    @Override
    public Customer getCustomerById(String customerId) {
        String sql = "SELECT * FROM Customer WHERE customerId = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, customerId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCustomer(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        String sql = "SELECT * FROM Customer WHERE email = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCustomer(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM Customer";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                customers.add(mapResultSetToCustomer(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    // Helper method to reduce code duplication
    private Customer mapResultSetToCustomer(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setCustomerId(rs.getString("customerId"));
        customer.setName(rs.getString("name"));
        customer.setEmail(rs.getString("email"));
        customer.setPhoneNumber(rs.getString("phoneNumber"));
        customer.setPasswordHash(rs.getString("password"));
        return customer;
    }
}