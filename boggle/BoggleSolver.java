import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BoggleSolver
{

    private Trie dict;


    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {

        this.dict = new Trie();

        for (String x : dictionary) {
            this.dict.insertWord(x);
            //StdOut.println(this.dict.contains(x));
        }



    }

    private boolean isValidIndex(int x, int y, BoggleBoard board) {
        if (y < board.cols() && y >= 0 && x < board.cols() && x >= 0) return true;
        return false;
    }

    private void boardDfs(int x, int y, boolean [][] visited, Set<String> validWords, StringBuilder string, BoggleBoard board) {

        char letter = board.getLetter(x, y);
        if (letter == 'Q') {
            string.append("QU");
        }

        else {
            string.append(board.getLetter(x, y));
        }

        String currentString = string.toString();

        if (!this.dict.isValidPrefix(currentString)) {

            visited[x][y] = false;

            if (letter == 'Q') {
                string.setLength(string.length()-2);
            }
            else {
                string.setLength(string.length()-1);
            }

            return;
        }

        if (currentString.length() >= 3 && this.dict.contains(currentString))
        {
            validWords.add(currentString);
        }

        visited[x][y] = true;

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (!(i == 0 && j == 0) && isValidIndex(x+i, y+j, board) && !visited[x+i][y+j]) {
                    boardDfs(x+i, y+j, visited, validWords, string, board);
                }
            }
        }

        visited[x][y] = false;

        if (letter == 'Q') {
            string.setLength(string.length()-2);
        }
        else {
            string.setLength(string.length()-1);
        }

    }

    private class Trie {

        public TrieNode root;

        Trie() {
            root = new TrieNode();
        }

        private void insertWordPrivate(TrieNode node, String word, int position) {
            if (position == word.length()) {
                node.endOfWord = true;
                return;
            }

            int leafIndex = ((int) word.charAt(position))-65;
            if (node.children[leafIndex] == null) {
                node.children[leafIndex] = new TrieNode();
            }

            insertWordPrivate(node.children[leafIndex], word, position+1);
        }

        public void insertWord(String word) {
            insertWordPrivate(this.root, word, 0);
        }

        public boolean contains(String word) {

            TrieNode node = this.root;
            int pos = 0;

            while (node != null && pos != word.length()) {
                node = node.children[ ((int) word.charAt(pos)) - 65];
                pos++;
            }
            if (node != null && node.endOfWord) {
                return true;
            }

            return false;
        }


        public boolean isValidPrefix(String word) {

            TrieNode node = this.root;
            int pos = 0;

            while (node != null && pos != word.length()) {
                node = node.children[ ((int) word.charAt(pos)) - 65];
                pos++;
            }

            if (node != null) {
                return true;
            }

            return false;
        }








        private class TrieNode {

            public boolean endOfWord;
            public TrieNode [] children;

            TrieNode() {
                endOfWord = false;
                children = new TrieNode[26];
            }
        }

    }





    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {

        boolean [][] visited = new boolean[board.rows()][board.cols()];

        for (boolean [] x : visited) Arrays.fill(x, false);

        Set<String> validWords = new HashSet<>();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {

                boardDfs(i, j, visited, validWords, stringBuilder, board);
            }
        }

        return  validWords;

    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (this.dict.contains(word)) {

            if (word.length() < 3) return 0;

            switch (word.length()) {
                case 3:
                case 4:
                    return 1;
                case 5:
                    return 2;
                case 6:
                    return 3;
                case 7:
                    return 5;
                default:
                    return 11;
            }

        }
        return 0;
    }


    public static void main(String[] args) {
        In in = new In(args[0]);

        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);

        int score = 0;


        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }

        StdOut.println("Score = " + score);
    }

}