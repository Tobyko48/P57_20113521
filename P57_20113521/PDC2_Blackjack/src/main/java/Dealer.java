/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Pikachu
 */

public class Dealer extends Player {

    public Dealer(Deck deck) {
        super(deck);
    }

    // dealer hits if total hand value < 17
    public boolean shouldHit() {
        return sum() < 17;
    }
}
