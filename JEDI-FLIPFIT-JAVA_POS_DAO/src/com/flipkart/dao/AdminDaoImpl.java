//package com.flipkart.dao;
//
//import com.flipkart.bean.Admin;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class AdminDaoImpl implements AdminDaoInterface {
//
//    // Key: adminId, Value: Admin Object
//    private static final Map<String, Admin> adminMap = new HashMap<>();
//
//    @Override
//    public void addAdmin(Admin admin) {
//        adminMap.put(admin.getAdminId(), admin);
//        System.out.println("DAO: Admin added successfully with ID: " + admin.getAdminId());
//    }
//
//    @Override
//    public Admin getAdminById(String adminId) {
//        return adminMap.get(adminId);
//    }
//
//    @Override
//    public Admin getAdminByEmail(String email) {
//        // Stream values to find an email match
//        return adminMap.values().stream()
//                .filter(admin -> admin.getEmail().equalsIgnoreCase(email))
//                .findFirst()
//                .orElse(null);
//    }
//
//    @Override
//    public List<Admin> getAllAdmins() {
//        return new ArrayList<>(adminMap.values());
//    }
//}
package com.flipkart.dao;

import com.flipkart.bean.Admin;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDaoImpl implements AdminDaoInterface {

    // Database connection details matching your schema
    private static final String DB_URL = "jdbc:mysql://localhost:3306/flipfit_schema";
    private static final String USER = "root";
    private static final String PASS = "admin123";

    // Helper method to get a connection
    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found: " + e.getMessage());
        }
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    @Override
    public void addAdmin(Admin admin) {
        // The query inserts into the Admin table using adminId and the linked userId
        String sql = "INSERT INTO ADMIN (adminId, name, email, userId) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, admin.getAdminId());
            pstmt.setString(2, admin.getName());
            pstmt.setString(3, admin.getEmail());
            pstmt.setString(4, admin.getUserId()); // Links to the base User table

            pstmt.executeUpdate();
            System.out.println("DAO: Admin registered successfully in DB with ID: " + admin.getAdminId());
        } catch (SQLException e) {
            System.err.println("Error adding admin: " + e.getMessage());
        }
    }

    @Override
    public Admin getAdminById(String adminId) {
        String sql = "SELECT * FROM ADMIN WHERE adminId = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, adminId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAdmin(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Admin getAdminByEmail(String email) {
        String sql = "SELECT * FROM ADMIN WHERE email = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAdmin(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Admin> getAllAdmins() {
        List<Admin> admins = new ArrayList<>();
        String sql = "SELECT * FROM ADMIN";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                admins.add(mapResultSetToAdmin(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admins;
    }

    // Helper method to map DB rows to Admin bean
    private Admin mapResultSetToAdmin(ResultSet rs) throws SQLException {
        Admin admin = new Admin();
        admin.setAdminId(rs.getString("adminId"));
        admin.setName(rs.getString("name"));
        admin.setEmail(rs.getString("email"));
        admin.setUserId(rs.getString("userId"));
        return admin;
    }
}