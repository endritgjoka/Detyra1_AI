

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

