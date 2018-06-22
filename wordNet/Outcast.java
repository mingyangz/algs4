import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private WordNet wordNet;
    
    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        wordNet = wordnet;
    }
    
    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        String outcast = nouns[0];
        int distance = distance(nouns, 0);
        for (int i = 1; i < nouns.length; i++) {
            if (distance(nouns, i) > distance) {
                outcast = nouns[i];
                distance = distance(nouns, i);
            }
        }
        return outcast;
    }
    
    private int distance(String[] nouns, int j) {
        int distance = 0;
        for (int i = 0; i < nouns.length; i++) {
            distance += wordNet.distance(nouns[i], nouns[j]);
        }
        return distance;
    }
    
    // see test client below
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}