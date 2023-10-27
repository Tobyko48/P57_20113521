/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Pikachu
 */

import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Profile {
    // variablies storing user information and game stats
    private String username;
    private String hashedPassword;
    private int wins;
    private int losses;
    private ProfileDatabase database;

    // Constructor for creating a new profile
    public Profile(String username, String password) {
        this.username = username;
        this.hashedPassword = hashPassword(password);
        this.wins = 0;
        this.losses = 0;
        this.database = new ProfileDatabase();
        saveProfile();
    }

    // Constructor for loading a profile from the database
    public Profile(String username, String hashedPassword, int wins, int losses) {
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.wins = wins;
        this.losses = losses;
        this.database = new ProfileDatabase();
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String password) {
        return this.hashedPassword.equals(hashPassword(password));
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public int getWins() {
        return wins;
    }

    // increment win
    public void addWin() {
        this.wins++;
        saveProfile();
    }

    public int getLosses() {
        return losses;
    }

    // increment loss
    public void addLoss() {
        this.losses++;
        saveProfile();
    }

    //save and update profile
    private void saveProfile() {
        try {
            database.saveOrUpdateProfile(this);
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }

    // load profile
    public static Profile loadProfile(String username, String password) {
        ProfileDatabase database = new ProfileDatabase();
        try {
            Profile profile = database.getProfile(username);
            if (profile != null && profile.checkPassword(password)) {
                return profile;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // hash password
    private static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");          
            byte[] hash = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
