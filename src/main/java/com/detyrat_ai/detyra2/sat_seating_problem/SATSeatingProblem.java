package com.detyrat_ai.detyra2.sat_seating_problem;

import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.IVecInt;

public class SATSeatingProblem {
    public static void main(String[] args) throws Exception {
        int numGuests = 100;
        int numTables = 10;

        ISolver solver = SolverFactory.newDefault();
        solver.newVar(numGuests * numTables);

        addGuestSeatingConstraints(solver, numGuests, numTables);
        addSingleTableConstraints(solver, numGuests, numTables);
        addCannotSitTogetherConstraints(solver, new int[][]{{2, 3}, {4, 6},{1,6},{2,96},{74,32}}, numTables);
        addMustSitTogetherConstraints(solver, new int[][]{{4, 10}, {10, 7},{1,6}}, numTables);
        addTableCapacityConstraints(solver, numGuests, numTables);

        printSolution(solver, numGuests, numTables);
    }

    private static void addGuestSeatingConstraints(ISolver solver, int numGuests, int numTables) throws Exception {
        for (int i = 1; i <= numGuests; i++) {
            IVecInt clause = new VecInt();
            for (int j = 1; j <= numTables; j++) {
                clause.push(getVariable(i, j, numTables));
            }
            solver.addClause(clause);
        }
    }

    private static void addSingleTableConstraints(ISolver solver, int numGuests, int numTables) throws Exception {
        for (int i = 1; i <= numGuests; i++) {
            for (int j = 1; j <= numTables; j++) {
                for (int k = j + 1; k <= numTables; k++) {
                    solver.addClause(new VecInt(new int[] {
                            -getVariable(i, j, numTables),
                            -getVariable(i, k, numTables)
                    }));
                }
            }
        }
    }

    private static void addCannotSitTogetherConstraints(ISolver solver, int[][] cannotSitTogether, int numTables) throws Exception {
        for (int[] pair : cannotSitTogether) {
            for (int j = 1; j <= numTables; j++) {
                solver.addClause(new VecInt(new int[] {
                        -getVariable(pair[0], j, numTables),
                        -getVariable(pair[1], j, numTables)
                }));
            }
        }
    }

    private static void addMustSitTogetherConstraints(ISolver solver, int[][] mustSitTogether, int numTables) throws Exception {
        for (int[] pair : mustSitTogether) {
            for (int j = 1; j <= numTables; j++) {
                solver.addClause(new VecInt(new int[] {
                        -getVariable(pair[0], j, numTables),
                        getVariable(pair[1], j, numTables)
                }));
                solver.addClause(new VecInt(new int[] {
                        -getVariable(pair[1], j, numTables),
                        getVariable(pair[0], j, numTables)
                }));
            }
        }
    }

    private static void addTableCapacityConstraints(ISolver solver, int numGuests, int numTables) throws Exception {
        for (int j = 1; j <= numTables; j++) {
            IVecInt clause = new VecInt();
            for (int i = 1; i <= numGuests; i++) {
                clause.push(getVariable(i, j, numTables));
            }
            solver.addExactly(clause, 10);
        }
    }

    private static void printSolution(ISolver solver, int numGuests, int numTables) throws Exception {
        if (solver.isSatisfiable()) {
            System.out.println("Gjendet një zgjidhje e vlefshme!");
            int[] model = solver.model();
            for (int i = 1; i <= numGuests; i++) {
                for (int j = 1; j <= numTables; j++) {
                    if (model[getVariable(i, j, numTables) - 1] > 0) {
                        System.out.println("Mysafiri " + i + " është ulur në tavolinën " + j);
                    }
                }
            }
        } else {
            System.out.println("Nuk ka zgjidhje të vlefshme.");
        }
    }

    private static int getVariable(int guest, int table, int numTables) {
        return (guest - 1) * numTables + table;
    }
}
