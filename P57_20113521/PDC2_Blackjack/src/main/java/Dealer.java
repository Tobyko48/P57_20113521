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


public class Dealer implements PlayerInterface {
    private String name;
    private List<Card> hand;
    
    private int wins = 0;
    private int losses = 0;

    private String password;

    public Dealer(String name) {
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
    
    @Override
    public void clearHand() {
        hand.clear();
    }
    
    // empty methods 
    @Override
    public int getWins() {
        return 0;
    }

    @Override
    public void setWins(int wins) {
    }

    @Override
    public int getLosses() {
        return 0;
    }

    @Override
    public void setLosses(int losses) {
    }
    
    @Override
    public void setPassword(String password) {
    }
    
    public String getPassword() {
        return null;
    }
}
