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
        energy = new double[picture.width()][picture.height()];
        for (int i = 0; i < picture.width(); i++) {
            for (int j = 0; j < picture.height(); j++) {
                energy[j][i] = energy(i, j);
            }
        }
    }

    private void checkValue(int a) {
        if (a <= 0 || a >= picture.width()) throw new IllegalArgumentException();
    }
    private void checkNull(Object a) {
        if (a == null) throw new IllegalArgumentException();
    }

    private void checkSeam(int [] seam, int range) {
        if (seam.length != range) throw new IllegalArgumentException();
        checkValue(seam[0]);
        for (int i = 1; i < range; i++) {
            checkValue(seam[i]);
            if (Math.abs(seam[i-1]-seam[i]) > 1) throw new IllegalArgumentException();
        }
    }

    private double calculateEnergy(int mainCoordinate, int secondaryCoordinate) {
        int point2 = picture.getRGB(mainCoordinate+1, secondaryCoordinate);
        int point3 = picture.getRGB(mainCoordinate-1, secondaryCoordinate);
        return Math.pow(((point2 >> 16) & 0xFF) - ((point3 >> 16) & 0xFF), 2) +
        Math.pow(((point2 >> 8) & 0xFF) - ((point3 >> 8) & 0xFF), 2) +
        Math.pow((point2  & 0xFF) - (point3 & 0xFF), 2);
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
        return Math.sqrt(calculateEnergy(x, y) + calculateEnergy(y, x));
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        int [][] parent = new int [picture.width()][picture.height()];
        double [][] distTo = new double[picture.width()][picture.height()];
        for (int j = 0; j < picture.height(); j++) {
            parent[0][j] = -1;
            distTo[0][j] = energy[0][j];
        }
        // dynamic programming solution
        for (int i = 1; i < picture.width(); i++) {

            // calculating edges
            if (distTo[i-1][1] < distTo[i-1][0]) {
                distTo[i][0] = energy[i][0] + distTo[i-1][1];
                parent[i][0] = 1;
            }
            else {
                distTo[i][0] = energy[i][0] + distTo[i-1][0];
                parent[i][0] = 0;
            }

            if (distTo[i-1][picture.height()-2] < distTo[i-1][picture.height()-1]) {
                distTo[i][picture.height()-1] = energy[i][picture.height()-1] + distTo[i-1][picture.height()-2];
                parent[i][0] = picture.height()-2;
            }
            else {
                distTo[i][picture.height()-1] = energy[i][picture.height()-1] + distTo[i-1][picture.height()-1];
                parent[i][0] = picture.height()-1;
            }
            // calculating middle of picture
            for (int j = 1; j < picture.height()-1; j++) {
                if (distTo[i-1][j] < distTo[i - 1][j - 1]) {
                    if (distTo[i-1][j] < distTo[i-1][j+1]) {
                        distTo[i][j] = energy[i][j] + distTo[i-1][j];
                        parent[i][j] = j;
                    }
                    else {
                        distTo[i][j] = energy[i][j] + distTo[i-1][j+1];
                        parent[i][j] = j+1;
                    }
                }

                else if (distTo[i-1][j - 1] < distTo[i - 1][j + 1]) {
                    distTo[i][j] = energy[i][j] + distTo[i-1][j-1];
                    parent[i][j] = j-1;
                }
                else {
                    distTo[i][j] = energy[i][j] + distTo[i-1][j+1];
                    parent[i][j] = j+1;
                }
            }
        }
        // getting shortest path
        int  minIndex = -1;
        double minValue = Double.POSITIVE_INFINITY;
        for (int i = 0; i < picture.height(); i++) {
            if (distTo[picture.width()-1][i] < minValue) {
                minValue = distTo[picture.width()-1][i];
                minIndex = i;
            }
        }
        // getting seam
        int [] seam = new int[picture.width()];
        int i = picture.width()-1;
        seam[i] = minIndex;
        while (parent[i][minIndex] != -1) {
            seam[i-1] = parent[i][minIndex];
            minIndex = parent[i][minIndex];
        }
        return seam;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int [][] parent = new int [picture.width()][picture.height()];
        double [][] distTo = new double[picture.width()][picture.height()];
        for (int j = 0; j < picture.width(); j++) {
            parent[j][0] = -1;
            distTo[j][0] = energy[0][j];
        }
        // dynamic programming solution
        for (int i = 1; i < picture.height(); i++) {

            // calculating edges
            if (distTo[1][i-1] < distTo[0][i-1]) {
                distTo[0][i] = energy[0][i] + distTo[1][i-1];
                parent[0][i] = 1;
            }
            else {
                distTo[0][i] = energy[0][i] + distTo[0][i-1];
                parent[0][i] = 0;
            }

            if (distTo[picture.width()-2][i-1] < distTo[picture.width()-1][i-1]) {
                distTo[picture.width()-1][i] = energy[picture.width()-1][i] + distTo[picture.width()-2][i-1];
                parent[i][0] = picture.width()-2;
            }
            else {
                distTo[picture.width()-1][i] = energy[picture.width()-1][i] + distTo[picture.width()-1][i-1];
                parent[i][0] = picture.width()-1;
            }
            // calculating middle of picture
            for (int j = 1; j < picture.width()-1; j++) {
                if (distTo[j][i-1] < distTo[j-1][i-1]) {
                    if (distTo[j][i-1] < distTo[j+1][i-1]) {
                        distTo[j][i] = energy[j][i] + distTo[j][i-1];
                        parent[j][i] = j;
                    }
                    else {
                        distTo[j][i] = energy[j][i] + distTo[j+1][i-1];
                        parent[j][i] = j+1;
                    }
                }

                else if (distTo[j-1][i - 1] < distTo[j + 1][i - 1]) {
                    distTo[j][i] = energy[j][i] + distTo[j-1][i-1];
                    parent[j][i] = j-1;
                }
                else {
                    distTo[j][i] = energy[j][i] + distTo[j+1][i-1];
                    parent[j][i] = j+1;
                }
            }
        }
        // getting shortest path
        int  minIndex = -1;
        double minValue = Double.POSITIVE_INFINITY;
        for (int i = 0; i < picture.width(); i++) {
            if (distTo[picture.height()-1][i] < minValue) {
                minValue = distTo[i][picture.width()-1];
                minIndex = i;
            }
        }
        // getting seam
        int [] seam = new int[picture.width()];
        int i = picture.height()-1;
        seam[i] = minIndex;
        while (parent[i][minIndex] != -1) {
            seam[i-1] = parent[i][minIndex];
            minIndex = parent[i][minIndex];
        }
        return seam;

    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        checkNull(seam);
        checkSeam(seam, picture.height());
        if (picture.height() == 1) throw new IllegalArgumentException();
        Picture newPicture = new Picture(picture.width(), picture.height()-1);
        double [][] newEnergy = new double[picture.width()][picture.height()-1]
        for (int i = 0; i < picture.width(); i++) {
            int k = 0;
            for (int j = 0; j < picture.height(); j++) {
                if (seam[i] != j) {
                    newPicture.setRGB(i, k, picture.getRGB(i, j));
                    newEnergy[i][k] = energy[i][j]
                    k++;
                }
            }
        }
        for (int i = 0; i < picture.width(); i++) {
            if (seam[i] > 0)newEnergy[i][seam[i]-1] = energy(i,seam[i]-1);
            if (seam[i]<picture.height()-1)newEnergy[i][seam[i]] = energy(i,seam[i]);
        }
        picture = newPicture;
        energy = newEnergy;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        checkNull(seam);
        checkSeam(seam, picture.width());
        if (picture.width() == 1) throw new IllegalArgumentException();
        Picture newPicture = new Picture(picture.width()-1, picture.height());
        double [][] newEnergy = new double[picture.width()-1][picture.height()]
        for (int i = 0; i < picture.height(); i++) {
            int k = 0;
            for (int j = 0; j < picture.width(); j++) {
                if (seam[i] != j) {
                    newPicture.setRGB(k, i, picture.getRGB(j, i));
                    newEnergy[k][i] = energy[j][i]
                    k++;
                }
            }
        }
        for (int i = 0; i < picture.height(); i++) {
            if (seam[i] > 0)newEnergy[i][seam[i]-1] = energy(i,seam[i]-1);
            if (seam[i]<picture.width()-1)newEnergy[i][seam[i]] = energy(i,seam[i]);
        }
        picture = newPicture;
        energy = newEnergy;
    }

    //  unit testing (optional)
    public static void main(String[] args) {

    }

}