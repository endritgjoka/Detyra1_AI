package com.detyra1_ai.social_golfers.BackTracking;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BacktrackingTest {
    public static void main(String[] args) {
        int G = 8;
        int P = 4;

        boolean isValid = validateSolution("sgp-backtracking.txt", G, P);
        System.out.println("Is the solution valid? " + isValid);
    }

    public static boolean validateSolution(String fileName, int G, int P) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            Set<String> seenPairs = new HashSet<>();
            Set<Integer> seenPlayersInWeek = new HashSet<>();
            int weekCount = 0;
            boolean isValid = true;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Week")) {
                    weekCount++;
                    int groupCount = 0;
                    seenPlayersInWeek.clear();
                    while ((line = reader.readLine()) != null && line.startsWith("Formed group")) {
                        groupCount++;
                        String groupLine = line.trim().substring(12);
                        List<Integer> group = parseGroup(groupLine);

                        // Validate group size
                        if (group.size() != P) {
                            System.out.println("Invalid group size: " + group);
                            isValid = false;
                        }


                        for (Integer player : group) {
                            if (seenPlayersInWeek.contains(player)) {
                                System.out.println("Repeated player found in week " + weekCount + ": " + player);
                                isValid = false;
                            }
                            seenPlayersInWeek.add(player);
                        }

                        for (int i = 0; i < group.size(); i++) {
                            for (int j = i + 1; j < group.size(); j++) {
                                String pair = generatePairKey(group.get(i), group.get(j));
                                if (seenPairs.contains(pair)) {
                                    System.out.println("Repeated pair found: " + pair);
                                    isValid = false;
                                }
                                seenPairs.add(pair);
                            }
                        }
                    }

                    if (groupCount != G) {
                        System.out.println("Invalid number of groups in week " + weekCount);
                        isValid = false;
                    }
                }
            }

            if (weekCount == 0) {
                System.out.println("No weeks found in the file.");
                isValid = false;
            }

            return isValid;
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            return false;
        }
    }

    private static List<Integer> parseGroup(String groupLine) {
        List<Integer> group = new ArrayList<>();
        String[] players = groupLine.replace("[", "").replace("]", "").split(",");
        for (String player : players) {
            group.add(Integer.parseInt(player.trim().replace(":", "").trim()));
        }
        return group;
    }

    private static String generatePairKey(int player1, int player2) {
        return player1 < player2 ? player1 + "-" + player2 : player2 + "-" + player1;
    }
}
