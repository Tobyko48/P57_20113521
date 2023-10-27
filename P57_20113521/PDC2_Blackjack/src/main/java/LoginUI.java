/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Pikachu
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.border.LineBorder;

public class LoginUI extends JFrame {
    
    // Text fields for user input: username and password.
    private JTextField usernameField = new JTextField(20);
    private JPasswordField passwordField = new JPasswordField(20);

    // Buttons for login and creating a profile.
    private JButton loginButton = new JButton("Login");
    private JButton createProfileButton = new JButton("Create Profile");

    public LoginUI() {
        // Setup the main window properties.
        setTitle("Login");
        setSize(450, 250);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.DARK_GRAY);

        // Create and add title label.
        JLabel titleLabel = new JLabel("Blackjack", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel, BorderLayout.NORTH);

        // Setup the main panel.
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.DARK_GRAY);
        panel.add(Box.createVerticalGlue());

        // Setup the username input row.
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        JPanel row1 = new JPanel();
        row1.setBackground(Color.DARK_GRAY);
        row1.add(usernameLabel);
        row1.add(usernameField);
        usernameField.setForeground(Color.BLACK);
        usernameField.setCaretColor(Color.BLACK);
        panel.add(row1);

        // Setup the password input row.
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        JPanel row2 = new JPanel();
        row2.setBackground(Color.DARK_GRAY);
        row2.add(passwordLabel);
        row2.add(passwordField);
        passwordField.setForeground(Color.BLACK);
        passwordField.setCaretColor(Color.BLACK);
        panel.add(row2);

        // Setup the buttons row.
        JPanel row3 = new JPanel();
        row3.setBackground(Color.DARK_GRAY);
        configureButton(loginButton);
        configureButton(createProfileButton);
        row3.add(loginButton);
        row3.add(createProfileButton);
        panel.add(row3);

        panel.add(Box.createVerticalGlue());
        add(panel, BorderLayout.CENTER);

        // Action listener for the login button.
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        // Action listener for the create profile button.
        createProfileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleCreateProfile();
            }
        });

        // Center the window on the screen and make it visible.
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void configureButton(JButton button) {
        button.setBackground(Color.GRAY);
        button.setForeground(Color.WHITE);
        button.setBorder(new LineBorder(Color.WHITE));
        button.setPreferredSize(new Dimension(120, 30));
        button.setMargin(new Insets(5, 15, 5, 15));
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Attempt to load the user's profile.
        Profile profile = Profile.loadProfile(username, password);
        if (profile != null) {
            // If successful, open the Blackjack game UI and close the login window.
            new BlackjackGUI(profile);
            dispose();
        } else {
            // If unsuccessful, show an error message.
            JOptionPane.showMessageDialog(null, "Incorrect username or password.");
        }
    }

    private void handleCreateProfile() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        ProfileDatabase database = new ProfileDatabase();
        try {
            // Attempt to create a new profile.
            if (database.getProfile(username) == null) {
                new Profile(username, password);
                JOptionPane.showMessageDialog(null, "Profile created.");
            } else {
                // if already exists display error message
                JOptionPane.showMessageDialog(null, "Profile already exists.");
            }
            // if error accessing database display error msg
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred while accessing the database.");
        }
    }
}

