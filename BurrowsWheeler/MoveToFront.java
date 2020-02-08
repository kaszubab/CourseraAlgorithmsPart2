import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.LinkedList;

public class MoveToFront {

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {

        LinkedList<Integer> characterSequence = new LinkedList<>();

        for (int i = 0; i < 256; i++) {
            characterSequence.add(i);
        }

        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            int asciiCode = (int) c;

            BinaryStdOut.write((char) characterSequence.indexOf(asciiCode));

            characterSequence.remove((Integer) asciiCode);
            characterSequence.addFirst(asciiCode);

        }

        BinaryStdOut.close();

    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {

        LinkedList<Integer> characterSequence = new LinkedList<>();

        for (int i = 0; i < 256; i++) {
            characterSequence.add(i);
        }

        while (!BinaryStdIn.isEmpty()) {

            char c = BinaryStdIn.readChar();

            int asciiCode = (int) c;
            BinaryStdOut.write((char) (int) characterSequence.get(asciiCode));

            int character = characterSequence.remove(asciiCode);
            characterSequence.addFirst(character);

        }

        BinaryStdOut.close();


    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {

        if (args[0].equals("-")) {
            encode();
        }
        else if (args[0].equals("+")) {
            decode();
        }
    }

}