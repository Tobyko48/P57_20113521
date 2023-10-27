/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Pikachu
 */

import java.util.ArrayList;

public class Player implements PlayerInterface {
    private ArrayList<Card> hand;
    private Deck deck;

    public Player(Deck deck) {
        this.deck = deck;
        this.hand = new ArrayList<Card>();
        this.hand.add(deck.draw());
        this.hand.add(deck.draw());
    }

    @Override
    public ArrayList<Card> getHand() {
        return hand;
    }
    
    @Override
    public void hit(Deck deck) {
        hand.add(deck.draw());
    }
    
    @Override
    public int sum() {
        int totalValue = 0;
        int aces = 0;
        for (Card card : hand) {
            if (card.getRank().equals("Ace")) {
                aces++;
            }
            totalValue += card.getValue();
        }
        while (totalValue > 21 && aces > 0) {
            totalValue -= 10;
            aces--;
        }
        return totalValue;
    }

    public boolean hasBusted() {
        return sum() > 21;
    }
}
