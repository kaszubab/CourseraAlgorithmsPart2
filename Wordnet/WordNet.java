import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

import java.util.HashMap;

public class WordNet {

    private HashMap<String, Bag<String>> nounDict;
    private HashMap<String, String> getWords;
    private Digraph wordGraph;
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null ) {
            throw new IllegalArgumentException();
        }
        In file = new In(synsets);
        nounDict = new HashMap<>();
        getWords = new HashMap<>();
        while (file.hasNextLine()) {
            String line  = file.readLine();
            String [] fields = line.split(",");
            if (!nounDict.containsKey(fields[1])) {
                Bag<String> newBag = new Bag<>();
                nounDict.put(fields[1], newBag);
            }
            Bag<String> currBag = nounDict.get(fields[1]);
            currBag.add(fields[0]);
            getWords.put(fields[0], fields[1]);
        }
        file = new In(hypernyms);
        wordGraph = new Digraph(getWords.size());
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
        Stack<Integer> verticesV = new Stack<>();
        Stack<Integer> verticesW = new Stack<>();
        for (String x : nounDict.get(nounA)) {
            verticesV.push(Integer.parseInt(x));
        }
        for (String x : nounDict.get(nounB)) {
            verticesW.push(Integer.parseInt(x));
        }
        return mysap.length(verticesV, verticesW);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        SAP mysap = new SAP(wordGraph);
        Stack<Integer> verticesV = new Stack<>();
        Stack<Integer> verticesW = new Stack<>();
        for (String x : nounDict.get(nounA)) {
            verticesV.push(Integer.parseInt(x));
        }
        for (String x : nounDict.get(nounB)) {
            verticesW.push(Integer.parseInt(x));
        }
        return getWords.get(mysap.ancestor(verticesV, verticesW));
    }

    // do unit testing of this class
    public static void main(String[] args) {

    }
}