/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Pikachu
 */

import java.util.ArrayList;

public interface PlayerInterface {
    ArrayList<Card> getHand();
    void hit(Deck deck);
    int sum();
    boolean hasBusted();
}


