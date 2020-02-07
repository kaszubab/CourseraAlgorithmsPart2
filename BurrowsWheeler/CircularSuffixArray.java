/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class CircularSuffixArray {


    String mainString;
    // circular suffix array of s
    public CircularSuffixArray(String s) {
        this.mainString = s;
    }

    // length of s
    public int length() {
        return mainString.length();
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        return 1;
    }

    // unit testing (required)
    public static void main(String[] args) {

    }

}