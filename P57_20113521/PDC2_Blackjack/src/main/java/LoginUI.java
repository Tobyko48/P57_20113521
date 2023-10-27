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
    private JTextField usernameField = new JTextField(20);
    private JPasswordField passwordField = new JPasswordField(20);
    private JButton loginButton = new JButton("Login");
    private JButton createProfileButton = new JButton("Create Profile");

    public LoginUI() {
        setTitle("Login");
        setSize(450, 250);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.DARK_GRAY);

        // Title
        JLabel titleLabel = new JLabel("Blackjack", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.DARK_GRAY);

        panel.add(Box.createVerticalGlue());

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        JPanel row1 = new JPanel();
        row1.setBackground(Color.DARK_GRAY);
        row1.add(usernameLabel);
        row1.add(usernameField);
        usernameField.setForeground(Color.BLACK);
        usernameField.setCaretColor(Color.BLACK);
        panel.add(row1);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        JPanel row2 = new JPanel();
        row2.setBackground(Color.DARK_GRAY);
        row2.add(passwordLabel);
        row2.add(passwordField);
        passwordField.setForeground(Color.BLACK);
        passwordField.setCaretColor(Color.BLACK);
        panel.add(row2);

        JPanel row3 = new JPanel();
        row3.setBackground(Color.DARK_GRAY);
        
        loginButton.setBackground(Color.GRAY);
        loginButton.setForeground(Color.WHITE);
        loginButton.setBorder(new LineBorder(Color.WHITE));
        loginButton.setPreferredSize(new Dimension(120, 30));
        loginButton.setMargin(new Insets(5, 15, 5, 15));
        createProfileButton.setBackground(Color.GRAY);
        createProfileButton.setForeground(Color.WHITE);
        createProfileButton.setBorder(new LineBorder(Color.WHITE));
        createProfileButton.setPreferredSize(new Dimension(150, 30));
        createProfileButton.setMargin(new Insets(5, 15, 5, 15));
        
        row3.add(loginButton);
        row3.add(createProfileButton);
        panel.add(row3);

        panel.add(Box.createVerticalGlue());

        add(panel, BorderLayout.CENTER);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                Profile profile = Profile.loadProfile(username, password);
                if (profile != null) {
                    new BlackjackGUI(profile);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect username or password.");
                }
            }
        });

        createProfileButton.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        ProfileDatabase database = new ProfileDatabase();
        try {
            if (database.getProfile(username) == null) {
                new Profile(username, password);  
                JOptionPane.showMessageDialog(null, "Profile created.");
            } else {
                JOptionPane.showMessageDialog(null, "Profile already exists.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred while accessing the database.");
        }
    }
});

        setLocationRelativeTo(null);
        setVisible(true);
    }
}

