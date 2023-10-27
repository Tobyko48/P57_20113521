
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
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;

public class BlackjackGUI extends JFrame {
    private JButton hitButton = new JButton("Hit");
    private JButton standButton = new JButton("Stand");
    private JButton playAgainButton = new JButton("Play Again");
    private JButton quitButton = new JButton("Quit");
    private JPanel playerCardPanel = new JPanel(new FlowLayout());
    private JPanel dealerCardPanel = new JPanel(new FlowLayout());
    private int cardWidth = 125;
    private int cardHeight = 180;

    private Player user;
    private Player dealer;
    private Deck deck;
    private Profile userProfile;
    private JLabel winLossLabel = new JLabel();
    private JLabel dealerTotalLabel = new JLabel();
    private JLabel userTotalLabel = new JLabel();
    private JLabel resultImageLabel = new JLabel();
    private JTextArea gameOutput = new JTextArea(20, 40);
    private boolean gameEnded;

    // initialize gui, componenets, layout, and event listeners
    public BlackjackGUI(Profile profile) {
        this.gameEnded = true;
        this.userProfile = profile;

        setTitle("Blackjack Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        winLossLabel.setForeground(Color.WHITE);
        topPanel.add(winLossLabel, BorderLayout.WEST);
        topPanel.add(quitButton, BorderLayout.EAST);
        topPanel.setBackground(Color.DARK_GRAY);
                topPanel.setForeground(Color.WHITE);

        add(topPanel, BorderLayout.NORTH);

        gameOutput.setEditable(false);
        gameOutput.setForeground(Color.WHITE);
        gameOutput.setBackground(Color.DARK_GRAY);
        gameOutput.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(gameOutput);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JLabel dealerLabel = new JLabel("Dealer's Cards:");
        dealerLabel.setForeground(Color.WHITE);

        JLabel yourLabel = new JLabel("Your Cards:");
        yourLabel.setForeground(Color.WHITE);

        // ChatGPT generated this
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.CENTER;

        mainPanel.add(dealerLabel, gbc);
        gbc.gridy++;
        mainPanel.add(dealerCardPanel, gbc);
        gbc.gridy++;
        mainPanel.add(dealerTotalLabel, gbc);
        gbc.gridy++;
        mainPanel.add(yourLabel, gbc);
        gbc.gridy++;
        mainPanel.add(playerCardPanel, gbc);
        gbc.gridy++;
        mainPanel.add(userTotalLabel, gbc);
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(resultImageLabel, gbc);
        // ChatGPT generated above
        
        resultImageLabel.setVisible(false);
        mainPanel.setBackground(Color.DARK_GRAY);

        add(mainPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);
        buttonPanel.add(playAgainButton);
        buttonPanel.add(quitButton);
        buttonPanel.setBackground(Color.DARK_GRAY);
        add(buttonPanel, BorderLayout.SOUTH);

        hitButton.addActionListener(e -> hitAction());
        standButton.addActionListener(e -> standAction());
        playAgainButton.addActionListener(e -> playAgainAction());
        quitButton.addActionListener(e -> quitAction());

        setButtonStyles(hitButton);
        setButtonStyles(standButton);
        setButtonStyles(playAgainButton);
        setButtonStyles(quitButton);

        initializeGame();
        setVisible(true);
    }

    // sets up new round of game
    private void initializeGame() {
        deck = new Deck();
        user = new Player(deck);
        dealer = new Player(deck);

        gameOutput.setText("");
        winLossLabel.setText("Wins: " + userProfile.getWins() + " Losses: " + userProfile.getLosses());
        winLossLabel.setForeground(Color.WHITE);       
        dealerTotalLabel.setText("Dealer's total: " + dealer.getHand().get(0).getValue());
        dealerTotalLabel.setForeground(Color.WHITE);
        userTotalLabel.setText("Your total: " + user.sum());
        userTotalLabel.setForeground(Color.WHITE);

        updateCardDisplay();
    }

    // player hit action
    private void hitAction() {
        user.hit(deck);
        gameOutput.append("\nYou drew a card: " + user.getHand().get(user.getHand().size() - 1) + "\n");
        userTotalLabel.setText("Your total: " + user.sum());

        updateCardDisplay();

        if (user.sum() > 21) {
            gameOutput.append("You busted! Dealer wins.\n");
            showResultImage(false, false);
            userProfile.addLoss();
            disableGameButtons();
            gameEnded = true;
            playAgainButton.setEnabled(true);
        }
    }

    // player stand action
    private void standAction() {
        gameOutput.append("\nDealer's turn:\n");
        while (dealer.sum() < 17) {
            dealer.hit(deck);
            gameOutput.append("Dealer draws: " + dealer.getHand().get(dealer.getHand().size() - 1) + "\n");
        }
        dealerTotalLabel.setText("Dealer's total: " + dealer.sum());

        updateCardDisplay();
        
        // determine win
        if (dealer.sum() > 21 || user.sum() > dealer.sum()) {
            gameOutput.append("You win!\n");
            userProfile.addWin();
            showResultImage(true, false);
        } else if (dealer.sum() == user.sum()) {
            gameOutput.append("It's a draw!\n");
            showResultImage(false, true);
        } else {
            gameOutput.append("Dealer wins.\n");
            userProfile.addLoss();
            showResultImage(false, false);
        }

        disableGameButtons();
        gameEnded = true;
        playAgainButton.setEnabled(true);
    }

    // reset game for new round
    private void playAgainAction() {
        initializeGame();
        hitButton.setEnabled(true);
        standButton.setEnabled(true);
        hideResultImage();

        playAgainButton.setEnabled(false);
        gameEnded = false;
    }

    //exit application
    private void quitAction() {
        System.exit(0);
    }
    
    // disable hit and stand button when game over
    private void disableGameButtons() {
        hitButton.setEnabled(false);
        standButton.setEnabled(false);
    }

    // updates cards display for dealer and player
    private void updateCardDisplay() {
        playerCardPanel.removeAll();
        dealerCardPanel.removeAll();

        playerCardPanel.setBackground(Color.DARK_GRAY);
        dealerCardPanel.setBackground(Color.DARK_GRAY);

        for (Card card : user.getHand()) {
            ImageIcon cardImage = new ImageIcon(card.getImagePath());
            Image resizedImage = cardImage.getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH);
            cardImage = new ImageIcon(resizedImage);
            playerCardPanel.add(new JLabel(cardImage));
        }

        for (Card card : dealer.getHand()) {
            ImageIcon cardImage = new ImageIcon(card.getImagePath());
            Image resizedImage = cardImage.getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH);
            cardImage = new ImageIcon(resizedImage);
            dealerCardPanel.add(new JLabel(cardImage));
        }

        playerCardPanel.revalidate();
        playerCardPanel.repaint();
        dealerCardPanel.revalidate();
        dealerCardPanel.repaint();
    }

    private void showResultImage(boolean userWins, boolean isDraw) {
        String imagePath;
        if (userWins) {
            imagePath = "resources/text/userwin.png";
        } else if (isDraw) {
            imagePath = "resources/text/draw.png";
        } else {
            imagePath = "resources/text/dealerwin.png";
        }

        ImageIcon resultImage = new ImageIcon(imagePath);
        resultImageLabel.setIcon(resultImage);
        resultImageLabel.setVisible(true);
    }

    private void hideResultImage() {
        resultImageLabel.setVisible(false);
    }

    private void setButtonStyles(JButton button) {
        button.setBackground(Color.GRAY);
        button.setForeground(Color.WHITE);
        button.setBorder(new LineBorder(Color.WHITE));
        button.setPreferredSize(new Dimension(80, 35));
    }
}
