/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;

public class CircularSuffixArray {


    char [] mainString;
    ArrayList<Integer> circularSuffixesOrder;

    // circular suffix array of s
    public CircularSuffixArray(String s) {

        if (s == null) throw new IllegalArgumentException();

        this.mainString = new char[s.length()];

        for (int i = 0; i < s.length(); i++) {
            mainString[i] = s.charAt(i);
        }

        this.circularSuffixesOrder = new ArrayList<>();

        for (int i = 0; i < s.length(); i++) {
            circularSuffixesOrder.add(i);
        }

        charQuickSort(0, s.length() - 1);

        ArrayList<Integer> tmp = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            tmp.add(0);
        }

        for (int i = 0; i < s.length(); i++) {
            tmp.set(this.circularSuffixesOrder.get(i), i);
        }

        this.circularSuffixesOrder = tmp;

    }

    private int compare(int suffixIndex1, int suffixIndex2) {
        for (int i = 0; i < this.length(); i++) {
            if (this.mainString[ (suffixIndex1+i) % this.length()] < this.mainString[ (suffixIndex2+i) % this.length()]) {
                return -1;
            }
            else if (this.mainString[(suffixIndex1+i) % this.length()] > this.mainString[(suffixIndex2+i) % this.length()]) {
                return 1;
            }
        }
        return 0;
    }

    private int partition(int left, int right) {
        int pivot = this.circularSuffixesOrder.get(right);

        int i = left -1;

        for (int j = left; j <= right; j++) {
            if (compare(this.circularSuffixesOrder.get(j), pivot) < 0) {
                i++;
                int tmp = this.circularSuffixesOrder.get(i);
                this.circularSuffixesOrder.set(i, this.circularSuffixesOrder.get(j));
                this.circularSuffixesOrder.set(j, tmp);
            }

        }

        this.circularSuffixesOrder.set(right, this.circularSuffixesOrder.get(i+1));
        this.circularSuffixesOrder.set(i + 1, pivot);

        return i+1;
    }

    private void charQuickSort(int left, int right) {
        if (left < right) {
            int pivot = partition(left, right);
            charQuickSort(left, pivot-1);
            charQuickSort(pivot+1, right);
        }

    }

    // length of s
    public int length() {
        return mainString.length;
    }

    // returns index of ith sorted suffix
    public int index(int i) {

        if (i < 0 && i >= this.length()) throw new IllegalArgumentException();

        return this.circularSuffixesOrder.get(i);
    }

    // unit testing (required)
    public static void main(String[] args) {
        CircularSuffixArray a = new CircularSuffixArray("ABRACADABRA!");
        System.out.println(a.circularSuffixesOrder); // check the result of algorithm with the example
    }

}