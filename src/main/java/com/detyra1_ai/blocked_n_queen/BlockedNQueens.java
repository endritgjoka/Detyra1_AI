package com.detyra1_ai.blocked_n_queen;

import java.util.*;

class BlockedNQueens {
    private static final int N = 8; 
    private static final int[][] BLOCKED_CELLS = {{1, 1}, {2, 3}, {4, 4}};

    // Represents a state in the search
    static class State {
        int[] queens; // Column positions for queens in each row
        int row;
        int cost; // g(n): cost to reach this state
        int heuristic; // h(n): heuristic value

        State(int[] queens, int row, int cost, int heuristic) {
            this.queens = queens.clone();
            this.row = row;
            this.cost = cost;
            this.heuristic = heuristic;
        }

        int getTotalCost() {
            return cost + heuristic; // f(n) = g(n) + h(n)
        }
    }

    public static void main(String[] args) {
        solveBlockedNQueens();
    }

    public static void solveBlockedNQueens() {
        PriorityQueue<State> openSet = new PriorityQueue<>(Comparator.comparingInt(State::getTotalCost));
        Set<String> closedSet = new HashSet<>();

        int[] initialQueens = new int[N];
        Arrays.fill(initialQueens, -1);

        openSet.add(new State(initialQueens, 0, 0, heuristic1(initialQueens)));

        while (!openSet.isEmpty()) {
            State current = openSet.poll();
            String stateKey = Arrays.toString(current.queens);

            if (closedSet.contains(stateKey)) continue;
            closedSet.add(stateKey);

            if (isGoalState(current.queens)) {
                printSolution(current.queens);
                return;
            }

            for (int col = 0; col < N; col++) {
                if (isSafe(current.queens, current.row, col)) {
                    int[] newQueens = current.queens.clone();
                    newQueens[current.row] = col;

                    int heuristicValue = heuristic1(newQueens);
                    openSet.add(new State(newQueens, current.row + 1, current.cost + 1, heuristicValue));
                }
            }
        }

        System.out.println("No solution found!");
    }

    public static boolean isGoalState(int[] queens) {
        for (int i = 0; i < N; i++) {
            if (queens[i] == -1) return false;
        }
        return true;
    }

    public static boolean isSafe(int[] queens, int row, int col) {
        for (int i = 0; i < row; i++) {
            int placedCol = queens[i];
            if (placedCol == col || Math.abs(row - i) == Math.abs(col - placedCol)) return false;
        }

        for (int[] blocked : BLOCKED_CELLS) {
            if (blocked[0] == row && blocked[1] == col) return false;
        }

        return true;
    }

    // Heuristic 1: Number of rows left to place queens
    public static int heuristic1(int[] queens) {
        int count = 0;
        for (int i = 0; i < N; i++) {
            if (queens[i] == -1) count++;
        }
        return count;
    }

    // Heuristic 2: Number of conflicts between queens
    public static int heuristic2(int[] queens) {
        int conflicts = 0;
        for (int i = 0; i < N; i++) {
            if (queens[i] == -1) continue;
            for (int j = i + 1; j < N; j++) {
                if (queens[j] == -1) continue;
                if (queens[i] == queens[j] || Math.abs(i - j) == Math.abs(queens[i] - queens[j])) {
                    conflicts++;
                }
            }
        }
        return conflicts;
    }

    // Heuristic 3: Number of blocked cells in unplaced rows
    public static int heuristic3(int[] queens) {
        int blockedCount = 0;
        for (int i = 0; i < N; i++) {
            if (queens[i] != -1) continue;
            for (int[] blocked : BLOCKED_CELLS) {
                if (blocked[0] == i) blockedCount++;
            }
        }
        return blockedCount;
    }

    public static void printSolution(int[] queens) {
        System.out.println("Solution found:");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (queens[i] == j) {
                    System.out.print("Q ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }
}
