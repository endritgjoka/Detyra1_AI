package com.detyra1_ai.social_golfers.DFS;

import java.util.*;

public class SocialGolfersDFS {

    public static List<List<List<Integer>>> solve(int G, int P) {
        int N = G * P;
        int[][] pairFrequency = new int[N][N];
        List<List<List<Integer>>> weeks = new ArrayList<>();

        int maxWeeks = calculateMaxWeeks(G, P, N);

        for (int week = 0; week < maxWeeks; week++) {
            List<List<Integer>> weekGroups = new ArrayList<>();
            if (dfs(weekGroups, pairFrequency, G, P, N, new boolean[N])) {
                weeks.add(weekGroups);
            } else {
                break;
            }
        }
        return weeks;
    }

    private static int calculateMaxWeeks(int G, int P, int N) {
        int totalPairs = (N * (N - 1)) / 2;

        int pairsPerWeek = G * ((P * (P - 1)) / 2);

        return totalPairs / pairsPerWeek;
    }

    private static boolean dfs(List<List<Integer>> weekGroups, int[][] pairFrequency,
                               int G, int P, int N, boolean[] used) {
        if (weekGroups.size() == G) {
            return true;
        }

        List<Integer> currentGroup = new ArrayList<>();
        if (formGroup(currentGroup, pairFrequency, used, P, N)) {
            weekGroups.add(currentGroup);
            if (dfs(weekGroups, pairFrequency, G, P, N, used)) {
                return true;
            }

            // Backtrack
            weekGroups.remove(weekGroups.size() - 1);
            updatePairFrequency(currentGroup, pairFrequency, false);
            for (int player : currentGroup) {
                used[player] = false;
            }
        }

        return false;
    }

    private static boolean formGroup(List<Integer> currentGroup, int[][] pairFrequency,
                                     boolean[] used, int P, int N) {
        if (currentGroup.size() == P) {
            return true;
        }

        PriorityQueue<Integer> candidates = new PriorityQueue<>(
                Comparator.comparingInt(player -> calculateConflicts(player, currentGroup, pairFrequency))
        );

        for (int player = 0; player < N; player++) {
            if (!used[player]) {
                candidates.offer(player);
            }
        }

        while (!candidates.isEmpty()) {
            int player = candidates.poll();

            currentGroup.add(player);
            used[player] = true;
            updatePairFrequency(currentGroup, pairFrequency, true);

            if (formGroup(currentGroup, pairFrequency, used, P, N)) {
                return true;
            }

            // Backtrack
            currentGroup.remove(currentGroup.size() - 1);
            used[player] = false;
            updatePairFrequency(currentGroup, pairFrequency, false);
        }

        return false;
    }

    private static int calculateConflicts(int player, List<Integer> currentGroup, int[][] pairFrequency) {
        int conflicts = 0;
        for (int member : currentGroup) {
            conflicts += pairFrequency[player][member];
        }
        return conflicts;
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
}
