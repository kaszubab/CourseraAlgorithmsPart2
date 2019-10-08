/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

    private Picture picture;
    private double [][] energy;
    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        checkNull(picture);
        this.picture = new Picture(picture);
        for (int i = 0; i < picture.width();i++) {
            for (int j = 0; j < picture.height(); j++) {
                energy[j][i] = energy(i,j);
            }
        }
    }

    private void checkValue (int a) {
        if (a <= 0 || a >= picture.width()) throw new IllegalArgumentException();
    }
    private void checkNull(Object a) {
        if(a == null) throw new IllegalArgumentException();
    }

    private void checkSeam (int [] seam , int range) {
        if (seam.length != range) throw new IllegalArgumentException();
        checkValue(seam[0]);
        for (int i = 1; i < range; i++) {
            checkValue(seam[i]);
            if (Math.abs(seam[i-1]-seam[i]) > 1) throw new IllegalArgumentException();
        }
    }

    private double calculateEnergy (int mainCoordinate, int secondaryCoordinate) {
        int point2 = picture.getRGB(mainCoordinate+1,secondaryCoordinate);
        int point3 = picture.getRGB(mainCoordinate-1,secondaryCoordinate);
        return Math.pow(((point2 >> 16) & 0xFF) - ((point3 >> 16) & 0xFF),2) +
        Math.pow(((point2 >> 8) & 0xFF) - ((point3 >> 8) & 0xFF),2) +
        Math.pow((point2  & 0xFF) - (point3 & 0xFF),2);
    }
    // current picture
    public Picture picture() {
        return new Picture(picture);
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        checkNull(x);
        checkNull(y);
        checkValue(x);
        checkValue(y);
        if (x == 0 || x == picture.width()-1  || y == 0 || y == picture.height()-1) return 1000;
        return Math.sqrt(calculateEnergy(x,y) + calculateEnergy(y,x));
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam()

    // sequence of indices for vertical seam
    public int[] findVerticalSeam()

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        checkNull(seam);
        checkSeam(seam,picture.height());
        if (picture.height() == 1) throw new IllegalArgumentException();
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        checkNull(seam);
        checkSeam(seam,picture.width());
        if (picture.width() == 1) throw new IllegalArgumentException();
    }

    //  unit testing (optional)
    public static void main(String[] args) {

    }

}