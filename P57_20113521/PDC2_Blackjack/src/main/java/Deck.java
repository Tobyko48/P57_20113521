/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Pikachu
 */

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> cards;

    // initialize deck of cards
    public Deck() {
        cards = new ArrayList<Card>();
        for (String suit : new String[]{"Hearts", "Diamonds", "Clubs", "Spades"}) {
            for (String rank : new String[]{"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"}) {
                cards.add(new Card(rank, suit));
            }
        }
        Collections.shuffle(cards);
    }

    // draw card to dealer/player hand, remove card from deck 
    public Card draw() {
        return cards.remove(cards.size() - 1);
    }
}