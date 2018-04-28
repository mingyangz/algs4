import java.util.Arrays;
import edu.princeton.cs.algs4.Queue;

public class FastCollinearPoints {
    private Queue<LineSegment> segments;
    private Point[] points;
    private int size;
    
    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
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
        Arrays.sort(this.points);
        checkDuplicatedPoints();
        segments = new Queue<>();
        size = 0;
        findAllCollinears();
    }
    
    private void findAllCollinears() {
        for (int i = 0; i < points.length; i++) {
            Point[] copy = copy();
            Arrays.sort(copy, points[i].slopeOrder());
            findCollinear(copy);
        }
    }
    
    private void findCollinear(Point[] copy) {
        Point p = copy[0];
        for (int i = 1; i < copy.length - 2; i++) {
            Point q = copy[i];
            int j = i + 1;
            while (j < copy.length && p.slopeTo(q) == p.slopeTo(copy[j])) {
                j++;
            }
            if (j - i < 3) {
                continue;
            }
            int start = i;
            i = j - 1;
            boolean duplicated = false;
            for (int k = start; k < j; k++) {
                if (p.compareTo(copy[k]) > 0) {
                    duplicated = true;
                }
            }
            if (!duplicated) {
                size++;
                segments.enqueue(new LineSegment(p, copy[i]));
            }
        }
    }
    
    private Point[] copy() {
        Point[] copy = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            copy[i] = points[i];
        }
        return copy;
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
}
