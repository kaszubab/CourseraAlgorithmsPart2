import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;

public class WordNet {

    private HashMap<String, String> nounDict;
    private HashMap<String, String> getWords;
    private Digraph wordGraph;
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null ) {
            throw new IllegalArgumentException();
        }
        In file = new In(synsets);
        nounDict = new HashMap<>();
        while (file.hasNextLine()) {
            String line  = file.readLine();
            String [] fields = line.split(",");
            nounDict.put(fields[1], fields[0]);
            getWords.put(fields[0], fields[1]);
        }
        file = new In(hypernyms);
        wordGraph = new Digraph(nounDict.size());
        while (file.hasNextLine()) {
            String line  = file.readLine();
            String [] fields = line.split(",");
            int v = Integer.parseInt(fields[0]);
            for (int i = 1; i < fields.length; i++) {
                wordGraph.addEdge(v, Integer.parseInt(fields[i]));
            }
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nounDict.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return nounDict.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        SAP mysap = new SAP(wordGraph);
        int v = Integer.parseInt(nounDict.get(nounA));
        int w = Integer.parseInt(nounDict.get(nounB));
        return mysap.length(v,w);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        SAP mysap = new SAP(wordGraph);
        int v = Integer.parseInt(nounDict.get(nounA));
        int w = Integer.parseInt(nounDict.get(nounB));
        return getWords.get(mysap.ancestor(v,w));
    }

    // do unit testing of this class
    public static void main(String[] args) {

    }
}