package main.java.edu.canisius.csc213.project1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

public class Deck {
    private final List<Card> cards;

    public Deck(int size) { 
        if (size <= 0 || size > 52 || size % 4 != 0) {
            throw new IllegalArgumentException("Invalid deck size. Must be a multiple of 4 and <= 52.");
        }
        
        this.cards = new ArrayList<>(size);
        int numRanks = size / 4;
        for (Card.Suit suit : Card.Suit.values()) {
            for (int i = 0; i < numRanks; i++) {
                this.cards.add(new Card(suit, Card.Rank.values()[i]));
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card draw() {
        if (cards.isEmpty()) {
            throw new NoSuchElementException("Deck is empty");
        }
        return cards.remove(0);
    }

    public int size() {
        return cards.size();
    }
}