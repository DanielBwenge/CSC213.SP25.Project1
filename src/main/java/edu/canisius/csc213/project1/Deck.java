package main.java.edu.canisius.csc213.project1;

import java.util.*;

/**
 * Represents a deck of playing cards with a configurable size.
 */
public class Deck {
    private final List<Card> cards;
    private final Random random = new Random();

    /**
     * Creates a deck with a given size.
     * The size must be a multiple of 4 and at most 52.
     * 
     * @param size The number of cards in the deck.
     * @throws IllegalArgumentException if size is invalid.
     */
    public Deck(int size) {
        if (size < 4 || size > 52 || size % 4 != 0) {
            throw new IllegalArgumentException("Deck size must be a multiple of 4 and between 4 and 52.");
        }

        cards = new ArrayList<>();
        initializeDeck(size);
    }

    /**
     * Initializes the deck with the correct cards.
     */
    private void initializeDeck(int size) {
        Card.Rank[] ranks = Card.Rank.values();
        int numRanks = size / 4; // Number of ranks included in the deck

        for (int i = 0; i < numRanks; i++) {
            for (Card.Suit suit : Card.Suit.values()) {
                cards.add(new Card(suit, ranks[i]));
            }
        }
    }

    /**
     * Shuffles the deck.
     */
    public void shuffle() {
        Collections.shuffle(cards, random);
    }

    /**
     * Draws the top card from the deck.
     * 
     * @return The drawn card.
     * @throws NoSuchElementException if the deck is empty.
     */
    public Card draw() {
        if (cards.isEmpty()) {
            throw new NoSuchElementException("Cannot draw from an empty deck.");
        }
        return cards.remove(cards.size() - 1); // Remove and return the top card
    }

    /**
     * Gets the number of remaining cards in the deck.
     *
     * @return The number of cards left.
     */
    public int size() {
        return cards.size();
    }
    public static void main(String[] args) {
        Deck deck = new Deck(52);
        System.out.println("Deck size: " + deck.size());
        deck.shuffle();
        System.out.println("First card drawn: " + deck.draw());
        System.out.println("Deck size after drawing: " + deck.size());
    }
}