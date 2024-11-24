# Teknikat e Painformuara të Kërkimit (Uninformed Search Techniques)

## Introductory Information

<img src="https://github.com/user-attachments/assets/9002855f-3f97-4b41-a180-85d1e24ad34a" alt="University Logo" width="150" align="right"/>

**University of Prishtina**  
**Faculty of Computer and Software Engineering**  
Master’s Program in **Computer and Software Engineering**  
Course: **Artificial Intelligence**

## Course Professors

- **Avni Rexhepi**
- **Adrian Ymeri**

## Project Team Members (Group 5)

- **Bleron Morina**
- **Endrit Gjoka**
- **Rinesa Bislimi**

## Social Golfers Problem

The Social Golfers Problem is a combinatorial optimization problem where a group of golfers needs to be scheduled into weekly sessions in such a way that no two golfers play together in the same group more than once. The objective is to plan out the weekly groups while adhering to these constraints. The problem can be solved using various algorithms, and in this implementation, we use Depth First Search (DFS), Depth Limited Search (DLS), and Backtracking to generate valid groupings over multiple weeks.

## Problem Description

32 golfers play golf once a week and always in groups of 4. For how many weeks can they play so that no two players play together more than once in the same group?

- Participants: 32 golfers.
- Grouping: The golfers must play in groups of 4.
- Objective: Plan the schedule such that no two golfers play together more than once in the same group over multiple weeks.

## Problem Parameters

- G (Groups per Week): The number of groups in a week.
- P (Players per Group): The number of players per group (fixed at 4 in this problem).
- W (Weeks): The number of weeks of play.

## Algorithmic Approaches

- **Depth First Search (DFS)**:This search strategy explores all possible combinations of groupings for each week, diving deeper into the possibilities until a valid solution is found or the solution space is exhausted.
- **Depth Limited Search (DLS)**: A variation of DFS that limits the search depth, which helps control the computational complexity when exploring large solution spaces.
- **Backtracking**: A fundamental approach that incrementally builds the solution and backtracks partial solutions as soon as it detects that they cannot lead to a valid outcome.

## Solution Approach

**Input Parameters**

The user provides:

- The total number of golfers (e.g., 32) and the group size (fixed at 4).
- The program automatically computes the number of groups per week and verifies if the total number of golfers is divisible by the group size.

**Process Overview**

1. **Step 1**: The user inputs the number of golfers and players per group. The program checks if the total number of golfers is divisible by 4 (i.e., the group size).
2. **Step 2**: The user selects the search algorithm (DFS, DLS or Backtracking ). For DLS, the user can also specify a depth limit to restrict the search space.
3. **Step 3**: The algorithm generates weekly groupings that respect the constraint of no repeated golfer pairings. The program tracks previously paired golfers to ensure no two golfers are grouped together more than once.
4. **Step 4**: The results are displayed in a graphical interface, where each week’s groupings are clearly shown, helping the user visualize the schedule.

**Output**

The solution will be displayed in a graphical user interface (GUI) showing the groups for each week. Each week's groups will be listed, and golfers who are paired together will be highlighted to demonstrate the scheduling.

# Latin Square Generator

This application generates a Latin Square using different solving methods. The user can input the size of the square and choose from two solving methods: **Backtracking** or **IDDFS** (Iterative Deepening Depth-First Search). The solution is validated to check if it's a valid Latin Square.

## Problem Description

A **Latin Square** is an `n x n` grid filled with `n` different symbols, where each symbol appears exactly once in each row and column.

### Features:

- **Input**:
  - Size of the square (`n`).
  - Solving method (Backtracking or IDDFS).
- **Output**:
  - The generated Latin Square or an error message if the solution is invalid.
  - Progress indicator during the solving process.

## Solving Methods

1. **Backtracking**: Solves by trying to place numbers incrementally and backtracking if a conflict occurs.
2. **IDDFS (Iterative Deepening Depth-First Search)**: Solves by performing DFS with a depth limit, incrementing the limit until a solution is found.

## Key Components

- **UI**:
  - Text field for the size input.
  - ComboBox to select the solving method.
  - TextArea to display results or errors.
  - ProgressIndicator for tracking the solving progress.

## Usage

1. Enter a size `n` for the Latin Square.
2. Select a solving method (Backtracking or IDDFS).
3. Click "Generate" to solve and display the Latin Square.
4. Use "Validate" to check if the solution is correct.

## Code Overview

The main logic is implemented in `LatinSquareController.java`, which handles:

- **Generating the Latin Square**.
- **Solving with Backtracking/IDDFS**.
- **Validating the solution**.

## Example

For a 3x3 grid, the generated Latin Square might look like:

| 1   | 2   | 3   |
| --- | --- | --- |
| 2   | 3   | 1   |
| 3   | 1   | 2   |
