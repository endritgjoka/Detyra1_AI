package com.detyra1_ai.social_golfers.BackTracking;

import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SocialGolfersBacktracking {

    private static final String FILE_PATH = "sgp-backtracking.txt";
    private static Set<String> allPlayedPairs = new HashSet<>();
    private static final long TIME_LIMIT = 5 * 60 * 1000; // 5 minutes in milliseconds

    public static List<List<List<Integer>>> solve(int G, int P) {
        int N = G * P;
        List<List<List<Integer>>> weeks = new ArrayList<>();

        List<Integer> allPlayers = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            allPlayers.add(i);
        }

        clearFile();

        int weekNumber = 1;
        int retryLimit = 1000000;
        long startTime = System.currentTimeMillis();

        while (true) {
            if (System.currentTimeMillis() - startTime >= TIME_LIMIT) {
                System.out.println("Time limit of 5 minutes reached. Terminating program.");
                break;
            }

            List<List<Integer>> weekGroups = new ArrayList<>();
            boolean validWeekFound = false;
            int retries = 0;

            while (!validWeekFound && retries < retryLimit) {
                Collections.shuffle(allPlayers);

                if (backtrack(weekGroups, G, P, allPlayers, new boolean[N])) {
                    if (!isWeekRepeated(weekGroups)) {
                        validWeekFound = true;
                        weeks.add(weekGroups);
                        saveWeekToFile(weekNumber, weekGroups);
                        updateGlobalPairFrequency(weekGroups, true);
                    } else {
                        retries++;
                    }
                } else {
                    retries++;
                }
            }

            if (validWeekFound) {
                System.out.println("Week " + weekNumber + " formed successfully.");
                weekNumber++;
            } else {
                System.out.println("Could not generate a valid grouping for week " + weekNumber + " after " + retryLimit + " attempts.");
                break;
            }
        }

        return weeks;
    }

    private static boolean backtrack(List<List<Integer>> weekGroups, int G, int P, List<Integer> allPlayers, boolean[] used) {
        if (weekGroups.size() == G) {
            return true;
        }

        List<Integer> currentGroup = new ArrayList<>();
        if (formGroup(currentGroup, P, allPlayers, used)) {
            weekGroups.add(currentGroup);

            if (backtrack(weekGroups, G, P, allPlayers, used)) {
                return true;
            }

            weekGroups.remove(weekGroups.size() - 1);
            for (int player : currentGroup) {
                used[player] = false;
            }
        }
        return false;
    }

    private static boolean formGroup(List<Integer> currentGroup, int P, List<Integer> allPlayers, boolean[] used) {
        if (currentGroup.size() == P) {
            return true;
        }

        List<Integer> remainingPlayers = new ArrayList<>();
        for (int player : allPlayers) {
            if (!used[player]) {
                remainingPlayers.add(player);
            }
        }

        Collections.shuffle(remainingPlayers);

        for (int player : remainingPlayers) {
            if (!used[player] && isValidForGroup(currentGroup, player)) {
                System.out.println("Hint: Attempting to add player " + player + " to group " + currentGroup);
                currentGroup.add(player);
                used[player] = true;

                if (formGroup(currentGroup, P, allPlayers, used)) {
                    return true;
                }

                // Backtrack
                System.out.println("Hint: Removing player " + player + " from group " + currentGroup);
                currentGroup.remove(currentGroup.size() - 1);
                used[player] = false;
            }
        }
        return false;
    }

    private static boolean isValidForGroup(List<Integer> currentGroup, int player) {
        for (int member : currentGroup) {
            String pair = generatePairString(player, member);
            if (allPlayedPairs.contains(pair)) {
                System.out.println("Hint: Pair (" + player + ", " + member + ") already played together.");
                return false;
            }
        }
        return true;
    }

    private static String generatePairString(int player1, int player2) {
        return player1 < player2 ? player1 + "," + player2 : player2 + "," + player1;
    }

    private static boolean isWeekRepeated(List<List<Integer>> weekGroups) {
        for (List<Integer> group : weekGroups) {
            for (int i = 0; i < group.size(); i++) {
                for (int j = i + 1; j < group.size(); j++) {
                    String pair = generatePairString(group.get(i), group.get(j));
                    if (allPlayedPairs.contains(pair)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static void updateGlobalPairFrequency(List<List<Integer>> weekGroups, boolean add) {
        for (List<Integer> group : weekGroups) {
            for (int i = 0; i < group.size(); i++) {
                for (int j = i + 1; j < group.size(); j++) {
                    String pair = generatePairString(group.get(i), group.get(j));
                    if (add) {
                        allPlayedPairs.add(pair);
                    } else {
                        allPlayedPairs.remove(pair);
                    }
                }
            }
        }
    }

    private static void saveWeekToFile(int weekNumber, List<List<Integer>> weekGroups) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write("Week " + weekNumber + ":");
            writer.newLine();
            for (List<Integer> group : weekGroups) {
                writer.write("Formed group: " + group);
                writer.newLine();
            }
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    private static void clearFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write("");
        } catch (IOException e) {
            System.err.println("Error clearing the file: " + e.getMessage());
        }
    }
}
