import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF ufFull;
    private boolean[] grid;
    private int openSiteNumber;
    private final int n;
    
    // create n-by-n grid, with all sites blocked 
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than 0.");
        }
        // any number of [0, n * n - 1] represents one sites in n-by-n grid
        // n * n represents the top site
        // n * n + 1 represents the bottom site
        uf = new WeightedQuickUnionUF(n * n + 2);
        ufFull = new WeightedQuickUnionUF(n * n + 2);
        grid = new boolean[n * n + 2];
        grid[n * n] = true;
        grid[n * n + 1] = true;
        openSiteNumber = 0;
        this.n = n;
    }
    
    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }
        openSiteNumber++;
        int site = getSite(row, col);
        grid[site] = true;
        union(row, col);
    }
    
    private void union(int row, int col) {
        int up = row == 1 ? n * n : getSite(row - 1, col);
        int left = col == 1 ? -1 : getSite(row, col - 1);
        int right = col == n  ? -1 : getSite(row, col + 1);
        int down = row == n ? n * n + 1 : getSite(row + 1, col);
        union(row, col, up);
        union(row, col, left);
        union(row, col, right);
        union(row, col, down);
    }
    
    private void union(int row, int col, int site) {
        if (site == -1) {
            return;
        }
        if (isOpen(site)) {
            int curr = getSite(row, col);
            uf.union(site, curr);
            if (site != n * n + 1) {
                ufFull.union(site, curr);
            }
        }
    }
    
    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        return isOpen(getSite(row, col));
    }
    
    private boolean isOpen(int site) {
        return grid[site];
    }
    
    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        int site = getSite(row, col);
        return ufFull.connected(site, n * n);
    }
    
    // number of open sites
    public int numberOfOpenSites() {
        return openSiteNumber;
    }
    
    // does the system percolate?
    public boolean percolates() {
        return uf.connected(n * n, n * n + 1);
    }
    
    private int getSite(int row, int col) {
        rangeCheck(row, col);
        return (row - 1) * n + col - 1;
    }
    
    private void rangeCheck(int row, int col) {
        if (row < 1 || col < 1 || row > n || col > n) {
            throw new IllegalArgumentException("row(" + row + ") and col(" 
                + col + ") must be in range [1, " + n + "].");
        }
    }

    // test client (optional)
    public static void main(String[] args) {
        // no test client
        Percolation p = new Percolation(4);
        p.open(4, 1);
        System.out.println(p.isFull(4, 4));
        p.open(3, 1);
        System.out.println(p.isFull(4, 4));
        p.open(2, 1);
        System.out.println(p.isFull(4, 4));
        p.open(1, 1);
        System.out.println(p.isFull(4, 4));
        p.open(1, 4);
        System.out.println(p.isFull(4, 4));
        p.open(2, 4);
        System.out.println(p.isFull(4, 4));
        p.open(4, 4);
        System.out.println(p.isFull(4, 4));
    }
    
}