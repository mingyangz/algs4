import java.util.Arrays;
import edu.princeton.cs.algs4.Queue;

public class BruteCollinearPoints {
    private Queue<LineSegment> segments;
    private Point[] points;
    private int size;
    
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Null input.");
        }
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException("Null argument.");
            }
        }
        this.points = new Point[points.length];
        
        for (int i = 0; i < points.length; i++) {
            this.points[i] = points[i];
        }
        segments = new Queue<>();
        size = 0;
        Arrays.sort(this.points);
        checkDuplicatedPoints();
        findAllCollinears();
    }
    
    // the number of line segments
    public int numberOfSegments() {
        return size;
    }
    
    // the line segments
    public LineSegment[] segments() {
        LineSegment[] result = new LineSegment[size];
        for (int i = 0; i < size; i++) {
            result[i] = segments.dequeue();
            segments.enqueue(result[i]);
        }
        return result;
    }
    
    private void findAllCollinears() {
        for (int p = 0; p < points.length; p++) {
            for (int q = p + 1; q < points.length; q++) {
                for (int r = q + 1; r < points.length; r++) {
                    double slopePq = getSlope(p, q);
                    double slopePr = getSlope(p, r);
                    if (slopePq != slopePr) {
                        continue;
                    }
                    for (int s = r + 1; s < points.length; s++) {
                        double slopePs = getSlope(p, s);
                        if (slopePq == slopePs) {
                            size++;
                            segments.enqueue(new LineSegment(points[p], points[s])); 
                        }
                    }
                }
            }
        }
    }
    
    private double getSlope(int p, int q) {
        return points[p].slopeTo(points[q]);
    }
    
    private void checkDuplicatedPoints() {
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                Point p = points[i];
                Point q = points[j];
                if (p.slopeTo(q) == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException("Duplicated Points");
                }
            }
        }
        
    }
}
