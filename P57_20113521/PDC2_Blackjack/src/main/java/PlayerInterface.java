/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Pikachu
 */

import java.util.List;


public interface PlayerInterface {
    void addCard(Card card);
    List<Card> getHand();
    String getName();
    int getHandValue();
    void setPassword(String password);
     int getWins();
    void setWins(int wins);
    int getLosses();
    void setLosses(int losses);
    public void clearHand();

}


