package main.java.edu.canisius.csc213.project1;

import java.util.Objects;

public class Card {
    public enum Suit {
        HEARTS, DIAMONDS, CLUBS, SPADES
    }

    public enum Rank {
        ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING
    }

    private final Suit suit;
    private final Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    //@Override
    public String toString() {
        return rank + " of " + suit;
    }

    //@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Card card = (Card) obj;
        return suit == card.suit && rank == card.rank;
    }

    //@Override
    public int hashCode() {
        return Objects.hash(suit, rank);
    }
    public static void main(String[] args) {
        Card card1 = new Card(Suit.HEARTS, Rank.ACE);
        Card card2 = new Card(Suit.SPADES, Rank.KING);
        Card card3 = new Card(Suit.HEARTS, Rank.ACE);

        System.out.println("Card 1: " + card1);
        System.out.println("Card 2: " + card2);
        System.out.println("Card 3: " + card3);

        System.out.println("Card 1 equals Card 2? " + card1.equals(card2));
        System.out.println("Card 1 equals Card 3? " + card1.equals(card3));

        System.out.println("Card 1 hashCode: " + card1.hashCode());
        System.out.println("Card 2 hashCode: " + card2.hashCode());
        System.out.println("Card 3 hashCode: " + card3.hashCode());
    }

}