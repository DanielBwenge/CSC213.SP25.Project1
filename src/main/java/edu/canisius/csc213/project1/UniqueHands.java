package main.java.edu.canisius.csc213.project1;

import java.util.HashSet;
import java.util.Set;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class UniqueHands {
    public static void main(String[] args) {
        int[] deckSizes = {24, 28};
        int[] handSizes = {6, 7};
        int trials = 5;
    
        try (PrintWriter csvWriter = createCSVWriter()) {
            System.out.println("🃏 Deck Simulation: How long to see every possible hand?");
            System.out.println("------------------------------------------------------");
    
            for (int deckSize : deckSizes) {
                for (int handSize : handSizes) {
                    System.out.printf("\nDeck Size: %d | Hand Size: %d%n", deckSize, handSize);
                    System.out.println("--------------------------------");
                    
                    int totalUniqueHands = calculateTotalUniqueHands(deckSize, handSize);
                    
                    for (int trial = 1; trial <= trials; trial++) {
                        long startTime = System.nanoTime();
                        int attempts = runTrialWithProgress(deckSize, handSize, totalUniqueHands, trial);
                        long endTime = System.nanoTime();
                        double seconds = (endTime - startTime) / 1_000_000_000.0;
                        
                        // Write to CSV
                        csvWriter.printf("%d,%d,%d,%,d,%.3f%n", 
                                      deckSize, handSize, trial, attempts, seconds);
                        
                        System.out.printf("Deck Size: %d | Hand Size: %d | Trial %d | Attempts: %,d | Time: %.3f sec%n",
                                        deckSize, handSize, trial, attempts, seconds);
                    }
                }
            }
            System.out.println("\nResults saved to unique_hands_results.csv");
        } catch (IOException e) {
            System.err.println("Error writing CSV file: " + e.getMessage());
        }
    }

    private static int runTrialWithProgress(int deckSize, int handSize, int totalUniqueHands, int trialNum) {
        Set<Set<Card>> seenHands = new HashSet<>();
        int attempts = 0;
        int nextMilestone = 100_000;
        
        while (seenHands.size() < totalUniqueHands) {
            // Draw a hand
            Deck deck = new Deck(deckSize);
            deck.shuffle();
            Set<Card> hand = new HashSet<>();
            
            while (hand.size() < handSize && deck.size() > 0) {
                hand.add(deck.draw());
            }
            
            if (hand.size() == handSize) {
                seenHands.add(hand);
            }
            attempts++;
            
            // Progress reporting
            if (attempts == nextMilestone || seenHands.size() == totalUniqueHands) {
                double coverage = (double) seenHands.size() / totalUniqueHands * 100;
                int remaining = totalUniqueHands - seenHands.size();
                
                System.out.printf("Progress: %.2f%% coverage after %,d attempts (Unique Hands: %,d / %,d | Needed: %,d)\n",
                                coverage, attempts, seenHands.size(), totalUniqueHands, remaining);
                
                if (seenHands.size() == totalUniqueHands) {
                    System.out.printf("100.00%% coverage reached after %,d attempts (Unique Hands: %,d / %,d | Needed: 0)\n",
                                    attempts, seenHands.size(), totalUniqueHands);
                }
                
                // Update next milestone (every 100k attempts or when complete)
                nextMilestone = (attempts / 100_000 + 1) * 100_000;
            }
        }
        return attempts;
    }

    public static int calculateTotalUniqueHands(int deckSize, int handSize) {
        long result = 1;
        for (int i = 1; i <= handSize; i++) {
            result = result * (deckSize - handSize + i) / i;
        }
        return (int) result;
    }

    //CSV writer had help creating this method 
    private static PrintWriter createCSVWriter() throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter("unique_hands_results.csv"));
        writer.println("Deck Size,Hand Size,Trial,Attempts,Time (sec)");
        return writer;
    }

}