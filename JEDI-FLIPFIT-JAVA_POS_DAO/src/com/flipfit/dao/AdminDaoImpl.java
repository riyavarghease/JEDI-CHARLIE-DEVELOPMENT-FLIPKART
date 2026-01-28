package com.flipfit.dao;

import com.flipfit.bean.Admin;
import com.flipfit.utils.DBConnection;
import com.flipfit.constant.SQLConstants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDaoImpl implements AdminDaoInterface {

    @Override
    public void addAdmin(Admin admin) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.INSERT_ADMIN)) {

            pstmt.setString(1, admin.getAdminId());
            pstmt.setString(2, admin.getName());
            pstmt.setString(3, admin.getEmail());
            pstmt.setString(4, admin.getUserId());
            // Setting new fields
            pstmt.setString(5, admin.getAadharNumber());
            pstmt.setString(6, admin.getPanNumber());

            pstmt.executeUpdate();
            System.out.println("DAO: Admin registered successfully in DB with ID: " + admin.getAdminId());
        } catch (SQLException e) {
            System.err.println("Error adding admin: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Admin getAdminById(String adminId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.SELECT_ADMIN_BY_ID)) {

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
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.SELECT_ADMIN_BY_EMAIL)) {

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
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.SELECT_ALL_ADMINS);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                admins.add(mapResultSetToAdmin(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admins;
    }

    private Admin mapResultSetToAdmin(ResultSet rs) throws SQLException {
        Admin admin = new Admin();
        admin.setAdminId(rs.getString("adminId"));
        admin.setName(rs.getString("name"));
        admin.setEmail(rs.getString("email"));
        admin.setUserId(rs.getString("userId"));
        // Retrieve new fields
        admin.setAadharNumber(rs.getString("aadharNumber"));
        admin.setPanNumber(rs.getString("panNumber"));
        return admin;
    }
}