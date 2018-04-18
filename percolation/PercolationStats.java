import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    
    private double[] x;
    private final int t;
    private final int n;
    private double mean;
    private double stddev;
    
    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException(
                "n and trials must be greater than 0.");
        }
        t = trials;
        this.n = n;
        x = new double[t];
        performTrials();
    }
    
    private void performTrials() {
        for (int i = 0; i < t; i++) {
            x[i] = singleExperiment();
        }
        mean = StdStats.mean(x);
        stddev = StdStats.stddev(x);
    }
    
    private double singleExperiment() {
        Percolation p = new Percolation(n);
        while (!p.percolates()) {
            int row = StdRandom.uniform(n) + 1;
            int col = StdRandom.uniform(n) + 1;
            p.open(row, col);
        }
        return (double) p.numberOfOpenSites() / (n * n);
    }
    
    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }
   
    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }
   
    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(t);
    }
    
    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(t);
    }
    
    // test client (described below)
    public static void main(String[] args) {        
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats p = new PercolationStats(n, t);
        StdOut.println("mean                    = " + p.mean());
        StdOut.println("stddev                  = " + p.stddev());
        StdOut.println("95% confidence interval = [" + p.confidenceLo()
            + ", " + p.confidenceHi() + "]");
    }
}