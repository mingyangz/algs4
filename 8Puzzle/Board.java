import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Board {
    private int[][] board;
    private final int n;
    
    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        n = blocks.length;
        board = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = blocks[i][j];
            }
        }
    }
    
    // board dimension n
    public int dimension() {
        return n;
    }
    
    // number of blocks out of place
    public int hamming() {
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 0) {
                    continue;
                }
                if (isWrongPosition(i, j, board[i][j])) {
                    count++;
                }
            }
        }
        return count;
    }
    
    private boolean isWrongPosition(int i, int j, int value) {
        return i * n + j + 1 != value;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                final int value = board[i][j];
                if (value == 0) {
                    continue;
                }
                final int correctI = (value - 1) / n;
                final int correctJ = (value - 1) % n;
                sum += Math.abs(i - correctI) + Math.abs(j - correctJ);
            }
        }
        return sum;
    }
    
    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }
    
    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        Board copy = new Board(board);
        if (copy.board[0][0] == 0) {
            copy.swap(1, 0, 0, 1);
        } else if (copy.board[0][1] == 0) {
            copy.swap(0, 0, 1, 0);
        } else {
            copy.swap(0, 0, 0, 1);
        }
        return copy;
    }
    
    private void swap(int x1, int y1, int x2, int y2) {
        final int temp = board[x1][y1];
        board[x1][y1] = board[x2][y2];
        board[x2][y2] = temp;
    }
    
    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) {
            return true;
        }
        if (y == null) {
            return false;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }
        
        Board that = (Board) y;
        if (that.board.length != n) {
            return false;
        }
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != that.board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    // all neighboring boards
    public Iterable<Board> neighbors() {
        return new Neighbors();   
    }
    
    private class Neighbors implements Iterable<Board> {
        private Bag<Board> bag;
        
        public Neighbors() {
            bag = new Bag<>();
            int x = 0;
            int y = 0;
            boolean findBlank = false;
            for (x = 0; x < n; x++) {
                for (y = 0; y < n; y++) {         
                    if (board[x][y] == 0) {
                        findBlank = true;
                        break;
                    }
                }
                if (findBlank) {
                    break;
                }
            }
            
            addNeighbors(x, y);
        }
        
        public Iterator<Board> iterator() {
            return bag.iterator();
        }
        
        private void addNeighbors(int x, int y) {
            if (x > 0) {
                Board copy = new Board(board);
                copy.swap(x, y, x - 1, y);
                bag.add(copy);
            }
            if (x < n - 1) {
                Board copy = new Board(board);
                copy.swap(x, y, x + 1, y);
                bag.add(copy);
            }
            if (y > 0) {
                Board copy = new Board(board);
                copy.swap(x, y, x, y - 1);
                bag.add(copy);
            }
            if (y < n - 1) {
                Board copy = new Board(board);
                copy.swap(x, y, x, y + 1);
                bag.add(copy);
            }
        }
    }
    
    
    
    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(n);
        sb.append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(" ");
                sb.append(board[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        int[][] blocks = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Board board = new Board(blocks);
        StdOut.println(board.manhattan());

        for (Board b : board.neighbors()) {
            StdOut.println(b);
        }
    }
}