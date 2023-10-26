
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Pikachu
 */

import java.util.Scanner;
import java.io.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class Game {
    private Scanner scanner = new Scanner(System.in);
    private boolean playAgain = true;
    private Deck deck;
    private PlayerInterface player;
    private PlayerInterface dealer;
    private String currentUsername = null; // Store the current username

    private String getUserProfile() {
        System.out.print("Enter your username: ");
        return scanner.nextLine().trim();
    }

    private void loadUserProfile(String username) {
        try {
            File profileFile = new File(username + "Profile.txt");
            if (profileFile.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(profileFile));
                String storedPassword = null;
                int storedWins = 0;
                int storedLosses = 0;

                // Read the password, wins, and losses from the file
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("password: ")) {
                        storedPassword = line.substring("password: ".length());
                    } else if (line.startsWith("W: ")) {
                        storedWins = Integer.parseInt(line.substring(3));
                    } else if (line.startsWith("L: ")) {
                        storedLosses = Integer.parseInt(line.substring(3));
                    }
                }

                if (storedPassword != null) {
                    // Set the retrieved win and loss records to the player object
                    player.setWins(storedWins);
                    player.setLosses(storedLosses);

                    while (true) {
                        System.out.print("Enter your password (or 'X' to exit): ");
                        String enteredPassword = scanner.nextLine().trim();

                        if (enteredPassword.equals(storedPassword)) {
                            System.out.println("Profile loaded successfully!");
                            break;
                        } else if (enteredPassword.equalsIgnoreCase("X")) {
                            System.out.println("Exiting.");
                            System.exit(0);
                        } else {
                            System.out.println("Incorrect password. Try again.");
                        }
                    }
                }
            } else {
                createNewProfile(username);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createNewProfile(String username) {
        System.out.print("Create a new password: ");
        String password = scanner.nextLine().trim();

        try {
            File profileFile = new File(username + "Profile.txt");
            FileWriter writer = new FileWriter(profileFile);

            // Write username and password to the file
            writer.write("username: " + username + "\n");
            writer.write("password: " + password + "\n");
            writer.write("W: 0\n"); // Initialize wins to 0
            writer.write("L: 0\n"); // Initialize losses to 0

            writer.close();
            System.out.println("Profile created successfully!");

            // Automatically set the currentUsername to the new username
            currentUsername = username;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateUserProfile(String username, int wins, int losses) {
    try {
        File profileFile = new File(username + "Profile.txt");
        BufferedReader reader = new BufferedReader(new FileReader(profileFile));
        StringBuilder updatedProfile = new StringBuilder();

        String line;

        // Update the lines in the updatedProfile StringBuilder
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("W: ")) {
                line = "W: " + wins;
            } else if (line.startsWith("L: ")) {
                line = "L: " + losses;
            }
            updatedProfile.append(line).append("\n");
        }

        reader.close();

        // Write the updated profile back to the file
        FileWriter writer = new FileWriter(profileFile);
        writer.write(updatedProfile.toString());
        writer.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    public void initializeGame() {
        deck = new Deck();
        deck.shuffle();
        currentUsername = getUserProfile();
        player = new Player(currentUsername);
        dealer = new Dealer("Dealer");
        loadUserProfile(currentUsername);

        for (int i = 0; i < 2; i++) {
            player.addCard(deck.dealCard());
            dealer.addCard(deck.dealCard());
        }
    }

    public void playGame() {
        initializeGame();

        while (playAgain) {
            deck = new Deck();
            deck.shuffle();
            player.clearHand();
            dealer.clearHand();

            boolean playerBust = false;
            boolean dealerBust = false;
            boolean playerBlackjack = false;
            boolean dealerBlackjack = false;

            // Deal initial cards to player and dealer
            player.addCard(deck.dealCard());
            dealer.addCard(deck.dealCard());
            player.addCard(deck.dealCard());
            dealer.addCard(deck.dealCard());

            // Check for player's blackjack
            if (player.getHandValue() == 21) {
                playerBlackjack = true;
            }

            // Check for dealer's blackjack
            if (dealer.getHandValue() == 21) {
                dealerBlackjack = true;
            }

            // If both player and dealer have blackjack, it's a draw.
            // If only dealer has blackjack, dealer wins.
            // If only player has blackjack, player wins.
            if (dealerBlackjack || playerBlackjack) {
                if (dealerBlackjack && playerBlackjack) {
                    System.out.println("Both player and dealer have blackjack! It's a draw.");
                    System.out.println(player.getName() + "'s record: W: " + player.getWins() + " | L: " + player.getLosses());
                } else if (dealerBlackjack) {
                    System.out.println("Dealer has blackjack! Dealer wins.");
                    player.setLosses(player.getLosses() + 1); // Increment losses

                    updateUserProfile(currentUsername, player.getWins(), player.getLosses()); // update wins and losses
                    System.out.println(player.getName() + "'s record: W: " + player.getWins() + " | L: " + player.getLosses());
                } else {
                    System.out.println("Player has blackjack! Player wins.");
                    player.setWins(player.getWins() + 1); // Increment losses
                    
                    updateUserProfile(currentUsername, player.getWins(), player.getLosses()); // update wins and losses
                    System.out.println(player.getName() + "'s record: W: " + player.getWins() + " | L: " + player.getLosses());
                }
                continue; // Start next round
            }

            while (true) {
                // Display hands and total values
                System.out.println("\n" + player.getName() + "'s hand: " + player.getHand());
                System.out.println("Total Value: " + player.getHandValue());
                System.out.println();
                System.out.println(dealer.getName() + "'s hand: " + dealer.getHand());
                System.out.println("Total Value: " + dealer.getHandValue());

                // Prompt player for action
                System.out.print("\nDo you want to (H)it, (S)tand, or (X) to quit? ");
                String choice = scanner.nextLine().trim().toLowerCase();

                if (choice.equals("h")) {
                    player.addCard(deck.dealCard());
                } else if (choice.equals("s")) {
                    break; // End the player's turn
                } else if (choice.equals("x")) {
                    System.out.println("\nThanks for playing! Goodbye.");
                    playAgain = false; // Set playAgain to false to quit the game
                    return; // End the game immediately
                } else {
                    System.out.println("\nInvalid input. Please enter 'H' to hit, 'S' to stand, or 'X' to quit.\n");
                }

                // Check for player's blackjack
                if (player.getHandValue() == 21) {
                    playerBlackjack = true;
                    break; // End the current round
                }

                // Check for player bust
                if (player.getHandValue() > 21) {
                    playerBust = true;
                    break; // End the current round
                }
            }

            // Dealer's turn
            while (dealer.getHandValue() < 17 && !playerBust) {
                dealer.addCard(deck.dealCard());
            }
            
            // Check for dealer bust
            if (dealer.getHandValue() > 21) {
                dealerBust = true;
            }
            
            // Check for dealer's blackjack
            if (dealer.getHandValue() == 21) {
                dealerBlackjack = true;
            }

            // Display hands and total values after dealer's turn
            System.out.println(player.getName() + "'s hand: " + player.getHand());
            System.out.println("Total Value: " + player.getHandValue());
            System.out.println();
            System.out.println(dealer.getName() + "'s hand: " + dealer.getHand());
            System.out.println("Total Value: " + dealer.getHandValue());

            // Determine the winner
            // Dealer loses by bust
            if (dealerBust && !playerBust) {
                System.out.println("\nDealer busts! Player wins.\n");
                player.setWins(player.getWins() + 1); // Increment wins

                // Update the user's profile file with the new wins and losses
                updateUserProfile(currentUsername, player.getWins(), player.getLosses());

                System.out.println(player.getName() + "'s record: W: " + player.getWins() + " | L: " + player.getLosses());
            }
            // player loses by bust
            else if (playerBust && !dealerBust) {
                System.out.println("\nPlayer busts! Dealer wins.\n");
                player.setLosses(player.getLosses() + 1); // Increment losses

                // Update the user's profile file with the new wins and losses
                updateUserProfile(currentUsername, player.getWins(), player.getLosses());

                System.out.println(player.getName() + "'s record: W: " + player.getWins() + " | L: " + player.getLosses());
            }
            // dealer wins by blackjack
            else if (dealerBlackjack && !playerBlackjack) {
                System.out.println("\nDealer wins with blackjack!\n");
                player.setLosses(player.getLosses() + 1); // Increment losses

                // Update the user's profile file with the new wins and losses
                updateUserProfile(currentUsername, player.getWins(), player.getLosses());

                System.out.println(player.getName() + "'s record: W: " + player.getWins() + " | L: " + player.getLosses());
            }
            // player wins by blackjack
            else if (playerBlackjack && !dealerBlackjack) {
                System.out.println("\nPlayer wins with blackjack!\n");
                player.setWins(player.getWins() + 1); // Increment wins

                // Update the user's profile file with the new wins and losses
                updateUserProfile(currentUsername, player.getWins(), player.getLosses());

                System.out.println(player.getName() + "'s record: W: " + player.getWins() + " | L: " + player.getLosses());
            }
            // player wins with higher value
            else if (player.getHandValue() > dealer.getHandValue()) {
                System.out.println("\nPlayer wins!\n");
                player.setWins(player.getWins() + 1); // Increment wins

                // Update the user's profile file with the new wins and losses
                updateUserProfile(currentUsername, player.getWins(), player.getLosses());

                System.out.println(player.getName() + "'s record: W: " + player.getWins() + " | L: " + player.getLosses());
            }
            // dealer wins with higher value
            else if (player.getHandValue() < dealer.getHandValue()) {
                System.out.println("\nDealer wins!\n");
                player.setLosses(player.getLosses() + 1); // Increment losses

                // Update the user's profile file with the new wins and losses
                updateUserProfile(currentUsername, player.getWins(), player.getLosses());

                System.out.println(player.getName() + "'s record: W: " + player.getWins() + " | L: " + player.getLosses());
            }
            // draw
            else {
                System.out.println("\nIt's a tie!\n");
                System.out.println(player.getName() + "'s record: W: " + player.getWins() + " | L: " + player.getLosses());
            }

            // Prompt for another round
            System.out.print("\nDo you want to play another round? (Y/N): ");
            while (true) {
                String playAnotherRound = scanner.nextLine().trim().toLowerCase();
                if (playAnotherRound.equals("n")) {
                    System.out.println("\nThanks for playing! Goodbye.");
                    playAgain = false;
                    return;
                } else if (playAnotherRound.equals("y")) {
                    break; // Exit the loop and continue to the next round
                } else {
                    System.out.println("\nInvalid input. Please enter 'Y' to play another round or 'N' to quit.");
                }
            }
        }
    }
}

