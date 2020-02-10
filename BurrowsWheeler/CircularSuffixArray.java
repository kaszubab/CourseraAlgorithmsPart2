/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class CircularSuffixArray {


    private String mainString;
    private int [] circularSuffixesOrder;

    // circular suffix array of s
    public CircularSuffixArray(String s) {

        if (s == null) throw new IllegalArgumentException();

        this.mainString = s;

        this.circularSuffixesOrder = new int[s.length()];

        for (int i = 0; i < s.length(); i++) {
            circularSuffixesOrder[i] = i;
        }

        sort(0, s.length()-1,0);

    }

    private int compare(int suffixIndex1, int suffixIndex2) {
        for (int i = 0; i < this.mainString.length(); i++) {
            if (this.mainString.charAt((suffixIndex1+i) % this.mainString.length()) < this.mainString.charAt((suffixIndex2+i) % this.mainString.length())) {
                return -1;
            }
            else if (this.mainString.charAt((suffixIndex1+i) % this.length()) > this.mainString.charAt((suffixIndex2+i) % this.mainString.length())) {
                return 1;
            }
        }
        return 0;
    }






    private void sort(int lo, int hi, int d)
        {
            if (hi <= lo) return;
            int lt = lo, gt = hi;
            int v = -1;
            if (d != this.mainString.length()) v = this.mainString.charAt((this.circularSuffixesOrder[lo] + d) % this.length());
            int i = lo + 1;
            while (i <= gt)
            {
                int t = this.mainString.charAt((this.circularSuffixesOrder[i] + d) % this.length());
                if      (t < v) {
                    int tmp = this.circularSuffixesOrder[lt];
                    this.circularSuffixesOrder[lt] = this.circularSuffixesOrder[i];
                    this.circularSuffixesOrder[i] = tmp;
                    i++;
                    lt++;
                }
                else if (t > v) {
                    int tmp = this.circularSuffixesOrder[i];
                    this.circularSuffixesOrder[i] = this.circularSuffixesOrder[gt];
                    this.circularSuffixesOrder[gt] = tmp;
                    gt--;
                }
                else            i++;
            }
            // a[lo..lt-1] < v = a[lt..gt] < a[gt+1..hi]
            sort(lo, lt-1, d);
            if (v >= 0) sort(lt, gt, d+1);
            sort(gt+1, hi, d);
        }


    // length of s
    public int length() {
        return mainString.length();
    }

    // returns index of ith sorted suffix
    public int index(int i) {

        if (i < 0 || i >= this.length()) throw new IllegalArgumentException();

        return this.circularSuffixesOrder[i];
    }

    // unit testing (required)
    public static void main(String[] args) {
        CircularSuffixArray a =
                new CircularSuffixArray("ABRACADABRA!");
        for (int i = 0; i < a.circularSuffixesOrder.length; i++) {
            System.out.print(a.circularSuffixesOrder[i] + " ");
        }
    }

}