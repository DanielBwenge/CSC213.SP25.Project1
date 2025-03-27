package main.java.edu.canisius.csc213.project1;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

//Ran into issues creating the CSV file and writing to it, so i had help in order to get this part to work as required.

public class UniqueHands {
    public static void main(String[] args) {
        int[] deckSizes = {24, 28};
        int[] handSizes = {6, 7};
        int trials = 5;

        System.out.println("Deck Simulation: How long to see every possible hand?");
        System.out.println("------------------------------------------------------");

        // Initialize CSV file
        initializeCSV();

        for (int deckSize : deckSizes) {
            for (int handSize : handSizes) {
                int totalUniqueHands = calculateTotalUniqueHands(deckSize, handSize);
                
                for (int trial = 1; trial <= trials; trial++) {
                    System.out.printf("\nDeck Size: %d | Hand Size: %d | Trial %d\n", 
                                    deckSize, handSize, trial);
                    System.out.println("----------------------------------------");
                    
                    long startTime = System.nanoTime();
                    int attempts = runTrialWithDetailedProgress(deckSize, handSize, totalUniqueHands);
                    double seconds = (System.nanoTime() - startTime) / 1_000_000_000.0;
                    writeToCSV(deckSize, handSize, trial, attempts, seconds);
                    
                    System.out.printf("\nDeck Size: %d | Hand Size: %d | Trial %d | Attempts: %,d | Time: %.3f sec\n",
                                    deckSize, handSize, trial, attempts, seconds);
                }
            }
        }
    }

    private static int runTrialWithDetailedProgress(int deckSize, int handSize, int totalUniqueHands) {
        Set<Set<Card>> seenHands = new HashSet<>();
        int attempts = 0;
        int nextMilestone = 100_000;
        
        while (seenHands.size() < totalUniqueHands) {
            // Create and shuffle a new deck
            Deck deck = new Deck(deckSize);
            deck.shuffle();
            
            // Draw a hand
            Set<Card> hand = new HashSet<>();
            while (hand.size() < handSize && deck.size() > 0) {
                hand.add(deck.draw());
            }
            
            // Only count full hands
            if (hand.size() == handSize) {
                seenHands.add(hand);
            }
            attempts++;
            
            // Progress reporting
            if (attempts >= nextMilestone || seenHands.size() == totalUniqueHands) {
                double coverage = (double) seenHands.size() / totalUniqueHands * 100;
                int remaining = totalUniqueHands - seenHands.size();
                
                System.out.printf("Progress: %.2f%% coverage after %,d attempts (Unique Hands: %,d / %,d | Needed: %,d)\n",
                                coverage, attempts, seenHands.size(), totalUniqueHands, remaining);
                
                if (seenHands.size() == totalUniqueHands) {
                    System.out.printf("100.00%% coverage reached after %,d attempts (Unique Hands: %,d / %,d | Needed: 0)\n",
                                    attempts, seenHands.size(), totalUniqueHands);
                }
                
                nextMilestone = attempts + 100_000;
            }
        }
        return attempts;
    }

    private static void initializeCSV() {
        try {
            Paths.get("unique_hands_results.csv").toFile().delete();
            PrintWriter writer = new PrintWriter(new FileWriter("unique_hands_results.csv", true));
            writer.println("Deck Size,Hand Size,Trial,Attempts,Time (sec)");
            writer.close();
        } catch (IOException e) {
            System.err.println("Error initializing CSV: " + e.getMessage());
        }
    }

    private static void writeToCSV(int deckSize, int handSize, int trial, 
                                 int attempts, double timeSec) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("unique_hands_results.csv", true))) {
            writer.printf("%d,%d,%d,%d,%.3f\n", 
                         deckSize, handSize, trial, attempts, timeSec);
        } catch (IOException e) {
            System.err.println("Error writing to CSV: " + e.getMessage());
        }
    }

    public static int calculateTotalUniqueHands(int deckSize, int handSize) {
        long result = 1;
        for (int i = 1; i <= handSize; i++) {
            result = result * (deckSize - handSize + i) / i;
        }
        return (int) result;
    }
}