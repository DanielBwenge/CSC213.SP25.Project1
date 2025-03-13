package edu.canisius.csc213.project1;

import java.util.Objects;   
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

public class Deck {
    private final List<Card> cards;

    public Deck(int size) { 
        if ( size <= 0 || size > 52 || size %4 != 0) {
            throw new IllegalArgumentException("Invalid deck size  Must be a multiple of 4 and <= 52.");
        }
    }

    
}   