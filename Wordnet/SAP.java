/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

public class SAP {

    private Digraph wordGraph;
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException();
        wordGraph = G;
    }


    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        BreadthFirstDirectedPaths path1 = new BreadthFirstDirectedPaths(wordGraph, v);
        BreadthFirstDirectedPaths path2 = new BreadthFirstDirectedPaths(wordGraph, w);
        int len = -1;
        for (int i = 0; i < wordGraph.V(); i++) {
            if (path1.hasPathTo(i) && path2.hasPathTo(i)) {
                if (path1.distTo(i) > len) {
                    len = path1.distTo(i);
                }
            }
        }
        return len;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        BreadthFirstDirectedPaths path1 = new BreadthFirstDirectedPaths(wordGraph, v);
        BreadthFirstDirectedPaths path2 = new BreadthFirstDirectedPaths(wordGraph, w);
        int ancestor = -1;
        int len = -1;
        for (int i = 0; i < wordGraph.V(); i++) {
            if (path1.hasPathTo(i) && path2.hasPathTo(i)) {
                if (path1.distTo(i) > len) {
                    len = path1.distTo(i);
                    ancestor = i;
                }
            }
        }
        return ancestor;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
            BreadthFirstDirectedPaths path1 = new BreadthFirstDirectedPaths(wordGraph, v);
            BreadthFirstDirectedPaths path2 = new BreadthFirstDirectedPaths(wordGraph, w);
            int len = -1;
            for (int i = 0; i < wordGraph.V(); i++) {
                if (path1.hasPathTo(i) && path2.hasPathTo(i)) {
                    if (path1.distTo(i) > len) {
                        len = path1.distTo(i);
                    }
                }
            }
        return len;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        BreadthFirstDirectedPaths path1 = new BreadthFirstDirectedPaths(wordGraph, v);
        BreadthFirstDirectedPaths path2 = new BreadthFirstDirectedPaths(wordGraph, w);
        int ancestor = -1;
        int len = -1;
        for (int i = 0; i < wordGraph.V(); i++) {
            if (path1.hasPathTo(i) && path2.hasPathTo(i)) {
                if (path1.distTo(i) > len) {
                    len = path1.distTo(i);
                    ancestor = i;
                }
            }
        }
        return ancestor;
    }

    // do unit testing of this class
    public static void main(String[] args) {

    }
}
