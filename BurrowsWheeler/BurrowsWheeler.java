/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.Arrays;
import java.util.HashMap;

public class BurrowsWheeler {

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {

        String s = BinaryStdIn.readString();

        CircularSuffixArray suffArr = new CircularSuffixArray(s);

        for (int i = 0; i < s.length(); i++) {
            if (suffArr.index(i) == 0) {
                BinaryStdOut.write( (int) i);
                break;
            }

        }

        char [] resultString = new char[s.length()];

        for (int i = 0; i < suffArr.length(); i++) {

            int characterPosition = suffArr.index(i) -1;
            if (characterPosition == -1) characterPosition = s.length() - 1;
            resultString[i] = s.charAt(characterPosition);
        }

        BinaryStdOut.write(new String(resultString));

        BinaryStdOut.close();

    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {


        int first = BinaryStdIn.readInt();
        String s = BinaryStdIn.readString();

        if (s.length() == 1) {
            BinaryStdOut.write(s);
            BinaryStdOut.close();
            return;
        }

        char [] sortedS = new char[s.length()];

        int [] pos = new int[256];
        Arrays.fill(pos, 0);

        for (char x : s.toCharArray()) pos[x]++;
        for (int i = 1; i < pos.length; i++) pos[i] = pos[i-1] + pos[i];
        for (int i = 0; i < s.length(); i++) sortedS[pos[s.charAt(i)]-- - 1] = s.charAt(i);

        int [] next = new int[s.length()];
        HashMap<Character, Integer> seenRepetitions = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            int repetition = seenRepetitions.computeIfAbsent(sortedS[i], k -> 0);
            int location = s.indexOf(sortedS[i], repetition);
            if (location == i)  location++;
            next[i] = s.indexOf(sortedS[i], location);
            seenRepetitions.put(sortedS[i], s.indexOf(sortedS[i], location) + 1);
        }

        int tmp = first;
        StringBuilder resultS = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            resultS.append(sortedS[tmp]);
            tmp = next[tmp];
        }


        BinaryStdOut.write(resultS.toString());
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {

        if (args[0].equals("-")) {
            transform();
        }
        else if (args[0].equals("+")) {
            inverseTransform();
        }


    }

}
