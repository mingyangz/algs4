import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.lang.IllegalArgumentException;

public class SAP {
    private Digraph wordNet;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) {
            throw new IllegalArgumentException();
        }
        wordNet = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(wordNet, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(wordNet, w);
        int length = -1;
        int ancestor = -1;
        for (int i = 0; i < wordNet.V(); i++) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
                if (length == -1 || bfsV.distTo(i) + bfsW.distTo(i) < length) {
                    length = bfsV.distTo(i) + bfsW.distTo(i);
                    ancestor = i;
                }
            }
        }
        return length;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(wordNet, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(wordNet, w);
        int length = -1;
        int ancestor = -1;
        for (int i = 0; i < wordNet.V(); i++) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
                if (length == -1 || bfsV.distTo(i) + bfsW.distTo(i) < length) {
                    length = bfsV.distTo(i) + bfsW.distTo(i);
                    ancestor = i;
                }
            }
        }
        return ancestor;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(wordNet, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(wordNet, w);
        int length = -1;
        int ancestor = -1;
        for (int i = 0; i < wordNet.V(); i++) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
                if (length == -1 || bfsV.distTo(i) + bfsW.distTo(i) < length) {
                    length = bfsV.distTo(i) + bfsW.distTo(i);
                    ancestor = i;
                }
            }
        }
        return length;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(wordNet, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(wordNet, w);
        int length = -1;
        int ancestor = -1;
        for (int i = 0; i < wordNet.V(); i++) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
                if (length == -1 || bfsV.distTo(i) + bfsW.distTo(i) < length) {
                    length = bfsV.distTo(i) + bfsW.distTo(i);
                    ancestor = i;
                }
            }
        }
        return ancestor;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}