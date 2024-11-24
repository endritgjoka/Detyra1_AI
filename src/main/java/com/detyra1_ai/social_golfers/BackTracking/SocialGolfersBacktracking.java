package com.detyra1_ai.social_golfers.BackTracking;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SocialGolfersBacktracking {

    private static final String FILE_PATH = "sgp-backtracking.txt";
    private static Set<String> allPlayedPairs = new HashSet<>();
    private static final long TIME_LIMIT = 30 * 60 * 1000; // 30 minutes in milliseconds

    public static List<List<List<Integer>>> solve(int G, int P) {
        int N = G * P;
        List<List<List<Integer>>> weeks = new ArrayList<>();
        List<Integer> allPlayers = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            allPlayers.add(i);
        }

        clearFile();
        long startTime = System.currentTimeMillis();
        int weekNumber = 1;

        while (System.currentTimeMillis() - startTime < TIME_LIMIT) {
            List<List<Integer>> weekGroups = generateWeek(G, P, allPlayers);

            if (weekGroups != null && !containsDuplicateWeek(weeks, weekGroups)) {
                weeks.add(weekGroups);
                saveWeekToFile(weekNumber, weekGroups);
                updateGlobalPairFrequency(weekGroups, true);
                System.out.println("Week " + weekNumber + " formed successfully.");
                weekNumber++;
            } else {
                // Retry mechanism: shuffle players and attempt again
                Collections.shuffle(allPlayers);
                System.out.println("Retrying to form a valid week...");
            }
        }

        return weeks;
    }

    private static boolean containsDuplicateWeek(List<List<List<Integer>>> weeks, List<List<Integer>> weekGroups) {
        List<List<Integer>> normalizedWeekGroups = normalizeWeekGroups(weekGroups);
        for (List<List<Integer>> week : weeks) {
            List<List<Integer>> normalizedExistingWeek = normalizeWeekGroups(week);
            if (normalizedExistingWeek.equals(normalizedWeekGroups)) {
                return true;
            }
        }
        return false;
    }

    private static List<List<Integer>> normalizeWeekGroups(List<List<Integer>> weekGroups) {
        List<List<Integer>> normalized = new ArrayList<>();
        for (List<Integer> group : weekGroups) {
            List<Integer> sortedGroup = new ArrayList<>(group);
            Collections.sort(sortedGroup);
            normalized.add(sortedGroup);
        }
        normalized.sort((a, b) -> {
            for (int i = 0; i < Math.min(a.size(), b.size()); i++) {
                int cmp = Integer.compare(a.get(i), b.get(i));
                if (cmp != 0) return cmp;
            }
            return Integer.compare(a.size(), b.size());
        });
        return normalized;
    }

    private static List<List<Integer>> generateWeek(int G, int P, List<Integer> allPlayers) {
        List<List<Integer>> weekGroups = new ArrayList<>();
        Set<String> weekPairs = new HashSet<>();
        List<Integer> availablePlayers = new ArrayList<>(allPlayers);
        Collections.shuffle(availablePlayers);

        for (int i = 0; i < G; i++) {
            List<Integer> group = new ArrayList<>();

            for (int j = 0; j < P; j++) {
                Integer player = findValidPlayer(group, weekPairs, availablePlayers);
                if (player == null) {
                    return null; // Fail if no valid player is found
                }
                group.add(player);
                availablePlayers.remove(player);
            }

            weekGroups.add(group);
            updateWeekPairs(group, weekPairs);
        }

        return weekGroups;
    }

    private static Integer findValidPlayer(List<Integer> group, Set<String> weekPairs, List<Integer> availablePlayers) {
        for (Integer player : availablePlayers) {
            boolean isValid = true;
            for (Integer member : group) {
                String pair = generatePairString(player, member);
                if (allPlayedPairs.contains(pair) || weekPairs.contains(pair)) {
                    isValid = false;
                    // Print hint message when the player is invalid
                    System.out.println("Player " + player + " cannot join the group " + group + " because they have already played with player " + member + ".");
                    break;
                }
            }
            if (isValid) {
                return player;
            }
        }
        return null; // No valid player found
    }


    private static void updateWeekPairs(List<Integer> group, Set<String> weekPairs) {
        for (int i = 0; i < group.size(); i++) {
            for (int j = i + 1; j < group.size(); j++) {
                weekPairs.add(generatePairString(group.get(i), group.get(j)));
            }
        }
    }

    private static String generatePairString(int player1, int player2) {
        return player1 < player2 ? player1 + "," + player2 : player2 + "," + player1;
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
