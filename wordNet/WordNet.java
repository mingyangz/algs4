import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.ST;

import java.lang.IllegalArgumentException;

public class WordNet {
    private ST<String, Bag<Integer>> synToId;
    private ST<Integer, String> idToSyn;
    private Digraph wordNet;
    private SAP sap;
    
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException();
        }
        synToId = new ST<>();
        idToSyn = new ST<>();
        In in = new In(synsets);
        while (in.hasNextLine()) {
            String[] line = in.readLine().split(",");
            for (String noun : line[1].split(" ")) {
                Bag<Integer> ids = new Bag<>();
                if (synToId.contains(noun)) {
                    ids = synToId.get(noun);
                }
                ids.add(Integer.parseInt(line[0]));
                synToId.put(noun, ids);
            }
            idToSyn.put(Integer.parseInt(line[0]), line[1]);
        }
        System.out.println(idToSyn.size());
        wordNet = new Digraph(idToSyn.size());
        in = new In(hypernyms);
        while (in.hasNextLine()) {
            String[] line = in.readLine().split(",");
            for (int i = 1; i < line.length; i++) {
                wordNet.addEdge(Integer.parseInt(line[0]), Integer.parseInt(line[i]));
            }
        }
        checkRootedDag();
        sap = new SAP(wordNet);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return synToId.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return synToId.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("Not a noun");
        }
        return sap.length(synToId.get(nounA), synToId.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("Not a noun");
        }
        int ancestor = sap.ancestor(synToId.get(nounA), synToId.get(nounB));
        return idToSyn.get(ancestor);
    }
    
    private void checkRootedDag() {
        int count = 0;
        for (int i = 0; i < wordNet.V(); i++) {
            if (wordNet.outdegree(i) == 0) {
                count++;
            }
        }
        if (count != 1) {
            throw new IllegalArgumentException("Not a rooted DAG.");
        }
    }

    // do unit testing of this class
    public static void main(String[] args) {
    }
}