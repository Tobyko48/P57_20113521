/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Pikachu
 */

import javax.swing.SwingUtilities;

public class BlackjackGame {

    public static void main(String[] args) {
        // Create Profiles database table first
        ProfileDatabase.createTable();
        
        // Start Game by invoking LoginUI
        SwingUtilities.invokeLater(() -> {
            new LoginUI();
        });
    }
}
