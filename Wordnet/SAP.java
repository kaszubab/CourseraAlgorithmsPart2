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
        wordGraph = new Digraph(G);
    }


    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        BreadthFirstDirectedPaths path1 = new BreadthFirstDirectedPaths(wordGraph, v);
        BreadthFirstDirectedPaths path2 = new BreadthFirstDirectedPaths(wordGraph, w);
        int len = -1;
        for (int i = 0; i < wordGraph.V(); i++) {
            if (path1.hasPathTo(i) && path2.hasPathTo(i)) {
                if (len == -1) {
                    len = path1.distTo(i) + path2.distTo(i);
                }
                if (path1.distTo(i) + path2.distTo(i) < len) {
                    len = path1.distTo(i) + path2.distTo(i);
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
                if (len == -1) {
                    len = path1.distTo(i) + path2.distTo(i);
                    ancestor = i;
                }
                if (path1.distTo(i) + path2.distTo(i) < len) {
                    len = path1.distTo(i) + path2.distTo(i);
                    ancestor = i;
                }
            }
        }
        return ancestor;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException();
        for (Integer x : v) {
            if (x == null) throw new IllegalArgumentException();
        }
        for (Integer x : w) {
            if (x == null) throw new IllegalArgumentException();
        }
        BreadthFirstDirectedPaths path1 = new BreadthFirstDirectedPaths(wordGraph, v);
        BreadthFirstDirectedPaths path2 = new BreadthFirstDirectedPaths(wordGraph, w);
        int len = -1;
        for (int i = 0; i < wordGraph.V(); i++) {
            if (path1.hasPathTo(i) && path2.hasPathTo(i)) {
                if (len == -1) {
                    len = path1.distTo(i) + path2.distTo(i);
                }
                if (path1.distTo(i) + path2.distTo(i) < len) {
                    len = path1.distTo(i) + path2.distTo(i);
                }
            }
        }
        return len;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException();
        for (Integer x : v) {
            if (x == null) throw new IllegalArgumentException();
        }
        for (Integer x : w) {
            if (x == null) throw new IllegalArgumentException();
        }
        BreadthFirstDirectedPaths path1 = new BreadthFirstDirectedPaths(wordGraph, v);
        BreadthFirstDirectedPaths path2 = new BreadthFirstDirectedPaths(wordGraph, w);
        int ancestor = -1;
        int len = -1;
        for (int i = 0; i < wordGraph.V(); i++) {
            if (path1.hasPathTo(i) && path2.hasPathTo(i)) {
                if (len == -1) {
                    len = path1.distTo(i) + path2.distTo(i);
                    ancestor = i;
                }
                if (path1.distTo(i) + path2.distTo(i) < len) {
                    len = path1.distTo(i) + path2.distTo(i);
                    ancestor = i;
                }
            }
        }
        return ancestor;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        Digraph grph = new Digraph(5);
        grph.addEdge(0, 1);
        grph.addEdge(1, 2);
        grph.addEdge(2, 3);
        BreadthFirstDirectedPaths path1 = new BreadthFirstDirectedPaths(grph, 0);
        BreadthFirstDirectedPaths path2 = new BreadthFirstDirectedPaths(grph, 0);
        int len = -1;
        for (int i = 0; i < grph.V(); i++) {
            if (path1.hasPathTo(i) && path2.hasPathTo(i)) {
                if (len == -1) {
                    len = path1.distTo(i);
                }
                if (path1.distTo(i) > len) {
                    len = path1.distTo(i);
                }
            }
        }
    }
}
