import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

import java.util.Iterator;
import java.util.Comparator;

public class Solver {
    private static final Comparator<SearchNode> PRIORITY = new Priority();
    
    private MinPQ<SearchNode> initialPq;
    private MinPQ<SearchNode> twinPq;
    private Stack<Board> solution;
    
    private class SearchNode {
        private Board curr;
        private SearchNode prev;
        private int priority;
        private int move;
        
        public SearchNode(Board curr, SearchNode prev, int move) {
            this.curr = curr;
            this.prev = prev;
            this.move = move;
            priority = move + curr.manhattan();
        }
    }
    
    private static class Priority implements Comparator<SearchNode> {
        public int compare(SearchNode n1, SearchNode n2) {
            return n1.priority - n2.priority;
        }
    }
    
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        initialPq = new MinPQ<>(PRIORITY);
        twinPq = new MinPQ<>(PRIORITY);
        initialPq.insert(new SearchNode(initial, null, 0));
        twinPq.insert(new SearchNode(initial.twin(), null, 0));
        solution = new Stack<>();
        findSolution();
    }
    
    private void findSolution() {
        boolean isSolvable = false;
        boolean isTwinSolvable = false;
        do {
            SearchNode initialMin = initialPq.delMin();
            SearchNode twinMin = twinPq.delMin();
            Board initialCurr = initialMin.curr;
            Board twinCurr = twinMin.curr;
            isSolvable = initialCurr.isGoal();
            isTwinSolvable = twinCurr.isGoal();
            if (isSolvable) {
                while (initialMin != null) {
                    solution.push(initialMin.curr);
                    initialMin = initialMin.prev;
                }
            }
            if (isSolvable || isTwinSolvable) {
                break;
            }
            for (Board neighbor : initialCurr.neighbors()) {
                if (initialMin.prev != null && neighbor.equals(initialMin.prev.curr)) {
                    continue;
                }
                initialPq.insert(new SearchNode(neighbor,
                                                initialMin,
                                                initialMin.move + 1));
            }
            for (Board neighbor : twinCurr.neighbors()) {
                if (twinMin.prev != null && neighbor.equals(twinMin.prev.curr)) {
                    continue;
                }
                twinPq.insert(new SearchNode(neighbor,
                                             twinMin,
                                             twinMin.move + 1));
            }
            
        } while (true);
    }
    
    // is the initial board solvable?
    public boolean isSolvable() {
        return !solution.isEmpty();
    }
    
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return solution.size() - 1;
    }
    
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (solution.isEmpty()) {
            return null;
        }
        return new Solution();
    }
    
    private class Solution implements Iterable<Board> {
        public Iterator<Board> iterator() {
            return solution.iterator();
        }
    }
    
    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[i][j] = in.readInt();
            }
        }
            
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}