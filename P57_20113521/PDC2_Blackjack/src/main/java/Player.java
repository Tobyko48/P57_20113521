/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Pikachu
 */

import java.util.ArrayList;
import java.util.List;

public class Player implements PlayerInterface {
    private String name;
    private List<Card> hand;
    private String password; // Add a password field
    private int wins = 0;
    private int losses = 0;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }

    @Override
    public void addCard(Card card) {
        hand.add(card);
    }

    @Override
    public List<Card> getHand() {
        return hand;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getHandValue() {
        int value = 0;
        int numAces = 0;

        for (Card card : hand) {
            if (card.getRank().equals("Ace")) {
                numAces++;
                value += 11;
            } else if (card.getRank().equals("King") || card.getRank().equals("Queen") || card.getRank().equals("Jack")) {
                value += 10;
            } else {
                value += Integer.parseInt(card.getRank());
            }
        }

        while (value > 21 && numAces > 0) {
            value -= 10;
            numAces--;
        }

        return value;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getPassword() {
        return password;
    }
    
    public int getWins() {
    return wins;
}

public void setWins(int wins) {
    this.wins = wins;
}

public int getLosses() {
    return losses;
}

public void setLosses(int losses) {
    this.losses = losses;
}

  @Override
    public void clearHand() {
        hand.clear();
    }
}
