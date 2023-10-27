/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Pikachu
 */

import java.sql.*;

public class ProfileDatabase {
    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:derby:BlackjackProfilesDB;create=true", "pdc", "pdc");
    }

    public static void createTable() {
        String tableName = "PROFILES";
        String createTableSQL = "CREATE TABLE " + tableName + " (USERNAME VARCHAR(255), PASSWORD VARCHAR(255), WINS INT, LOSSES INT)";
        
        try (Connection connection = DriverManager.getConnection("jdbc:derby:BlackjackProfilesDB;create=true", "pdc", "pdc");
             Statement statement = connection.createStatement()) {
            
            // Check if the table already exists
            DatabaseMetaData metadata = connection.getMetaData();
            ResultSet tables = metadata.getTables(null, null, tableName, null);
        
            // Create table if doesnt exist
            if (!tables.next()) {
            statement.executeUpdate(createTableSQL);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public Profile getProfile(String username) throws SQLException {
        String sql = "SELECT * FROM profiles WHERE username = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String hashedPassword = rs.getString("password");
                int wins = rs.getInt("wins");
                int losses = rs.getInt("losses");
                return new Profile(username, hashedPassword, wins, losses);
            }
            return null;
        }
    }

    public void saveOrUpdateProfile(Profile profile) throws SQLException {
        try (Connection conn = connect()) {        
            // Check if the profile already exists in the database
            String selectSql = "SELECT * FROM profiles WHERE username = ?";
            try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                selectStmt.setString(1, profile.getUsername());
                ResultSet rs = selectStmt.executeQuery();

                if (rs.next()) {
                    // Profile exists, update it
                    String updateSql = "UPDATE profiles SET password = ?, wins = ?, losses = ? WHERE username = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setString(1, profile.getHashedPassword());
                        updateStmt.setInt(2, profile.getWins());
                        updateStmt.setInt(3, profile.getLosses());
                        updateStmt.setString(4, profile.getUsername());
                        updateStmt.executeUpdate();
                    }
                    } else {
                    // Profile does not exist, insert it
                    String insertSql = "INSERT INTO profiles (username, password, wins, losses) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                        insertStmt.setString(1, profile.getUsername());
                        insertStmt.setString(2, profile.getHashedPassword());
                        insertStmt.setInt(3, profile.getWins());
                        insertStmt.setInt(4, profile.getLosses());
                        insertStmt.executeUpdate();
                    }
                }
            }
        }
    }
}