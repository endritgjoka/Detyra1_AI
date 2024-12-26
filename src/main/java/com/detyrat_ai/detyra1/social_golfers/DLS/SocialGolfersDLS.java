package com.detyrat_ai.detyra1.social_golfers.DLS;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SocialGolfersDLS {

    private static final long TIME_LIMIT = 5 * 60 * 1000;
    private static final String FILE_PATH = "sgp-dls.txt";

    public static List<List<List<Integer>>> solve(int G, int P, int depthLimit) {
        int N = G * P;
        int[][] pairFrequency = new int[N][N];
        List<List<List<Integer>>> allWeeks = new ArrayList<>();
        long startTime = System.currentTimeMillis();

        clearFile();

        List<List<List<Integer>>> weeks = new ArrayList<>();
        Set<String> localPlayedPairs = new HashSet<>();
        Arrays.stream(pairFrequency).forEach(row -> Arrays.fill(row, 0));

        while (System.currentTimeMillis() - startTime < TIME_LIMIT) {
            List<List<Integer>> weekGroups = new ArrayList<>();
            int dynamicDepth = depthLimit;

            // Try to form the groups for the week with Depth-Limited Search (DLS)
            if (dls(weekGroups, pairFrequency, G, P, N, new boolean[N], dynamicDepth, 0, localPlayedPairs, allWeeks.size())) {
                allWeeks.add(new ArrayList<>(weekGroups));
                saveWeekToFile(allWeeks.size(), weekGroups);
                System.out.println("Week " + allWeeks.size() + " formed successfully.");
                addWeekToPairHistory(weekGroups, localPlayedPairs);
            } else {
                System.out.println("Could not generate valid groups for week " + (allWeeks.size() + 1) + ".");
            }
        }

        return allWeeks;
    }

    private static boolean dls(List<List<Integer>> weekGroups, int[][] pairFrequency,
                               int G, int P, int N, boolean[] used, int depthLimit, int depth, Set<String> localPlayedPairs, int currentWeek) {
        if (weekGroups.size() == G) {
            return true;
        }

        if (depth > depthLimit) {
            return false;
        }

        List<Integer> currentGroup = new ArrayList<>();
        if (formGroup(currentGroup, pairFrequency, used, P, N, localPlayedPairs, currentWeek)) {
            weekGroups.add(currentGroup);
            boolean continueSearch = false;
            if (dls(weekGroups, pairFrequency, G, P, N, used, depthLimit, depth + 1, localPlayedPairs, currentWeek)) {
                continueSearch = true;
            }

            if (continueSearch) {
                return true;
            }

            // Backtracking
            weekGroups.remove(weekGroups.size() - 1);
            updatePairFrequency(currentGroup, pairFrequency, false);
            for (int player : currentGroup) {
                used[player] = false;
            }
        }

        return false;
    }

    private static boolean formGroup(List<Integer> currentGroup, int[][] pairFrequency,
                                     boolean[] used, int P, int N, Set<String> localPlayedPairs, int currentWeek) {
        if (currentGroup.size() == P) {
            return true;
        }

        List<Integer> playersList = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            if (!used[i]) {
                playersList.add(i);
            }
        }

        Collections.shuffle(playersList);

        for (int player : playersList) {
            if (!used[player] && isValidForGroup(currentGroup, player, localPlayedPairs)) {
                System.out.println("Attempting to add player " + player + " to group " + currentGroup); // Print attempt
                currentGroup.add(player);
                used[player] = true;
                updatePairFrequency(currentGroup, pairFrequency, true);

                if (formGroup(currentGroup, pairFrequency, used, P, N, localPlayedPairs, currentWeek)) {
                    return true;
                }

                // Backtracking
                System.out.println("Removing player " + player + " from group " + currentGroup); // Print backtrack
                currentGroup.remove(currentGroup.size() - 1);
                used[player] = false;
                updatePairFrequency(currentGroup, pairFrequency, false);
            }
        }

        return false;
    }

    private static boolean isValidForGroup(List<Integer> currentGroup, int player, Set<String> localPlayedPairs) {
        for (int member : currentGroup) {
            String pair = generatePairString(player, member);
            if (localPlayedPairs.contains(pair)) {
                return false;
            }
        }
        return true;
    }

    private static void updatePairFrequency(List<Integer> currentGroup, int[][] pairFrequency, boolean add) {
        for (int i = 0; i < currentGroup.size(); i++) {
            for (int j = i + 1; j < currentGroup.size(); j++) {
                int a = currentGroup.get(i);
                int b = currentGroup.get(j);
                pairFrequency[a][b] += (add ? 1 : -1);
                pairFrequency[b][a] += (add ? 1 : -1);
            }
        }
    }

    private static String generatePairString(int player1, int player2) {
        return player1 < player2 ? player1 + "," + player2 : player2 + "," + player1;
    }

    private static void addWeekToPairHistory(List<List<Integer>> weekGroups, Set<String> localPlayedPairs) {
        for (List<Integer> group : weekGroups) {
            for (int i = 0; i < group.size(); i++) {
                for (int j = i + 1; j < group.size(); j++) {
                    String pair = generatePairString(group.get(i), group.get(j));
                    localPlayedPairs.add(pair);
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
