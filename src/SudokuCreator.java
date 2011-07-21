
import java.util.Random;

public class SudokuCreator {

    int[][] s = new int[9][9];

    public void setArray(int[][] a) {
        this.s = a;
    }

    public int[][] getArray() {
        return s;
    }

    public void prepareArray() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                s[i][j] = 0;
            }
        }
    }

    public int[][] createArray(int level) {

        //scf.setVisible(true);
        SudokuPatterns sp = new SudokuPatterns();
        SudokuSolver ss = new SudokuSolver();
        int[][] raw = createRandomArray();
        String sudo = sp.getPattern(level);
        char c;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                c = sudo.charAt((i * 9) + j);
                if (c == '0') {
                    s[i][j] = 0;
                } else {
                    s[i][j] = raw[i][j];
                }
            }
        }

        //if (!solve(0, 0)) {
            // System.out.println("Retry...");
            //return createArray(level);
        //}
        //scf.setVisible(false);
        //getAsString();
        return s;
    }

    public int[][] createRandomArray() {
        prepareArray();
        for (int i = 0; i < 9; i++) {
            //for (int j = 0; j < 9; j++) {
            s[i][i] = fill(i, i);
        //}
        }
       solve(0, 0);

        //System.out.println("Rastgele Dizi Oluşturuldu.");
        return s;
    }

    public int fill(int x, int y) {
        Random rnd = new Random();

        int r = (int) rnd.nextInt(9) + 1;
        // System.out.println("r:" + r);
        if (isLegal(x, y, r)) {
            return r;
        }
        return fill(x, y);
    }

    public void printArray() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(s[i][j] + " ");
                if (j % 3 == 2 && j != 8) {
                    System.out.print(" | ");
                }
            }
            if (i % 3 == 2 && i != 8) {
                System.out.println("\n-----------------------");
            } else {
                System.out.println("");
            }
        }
    //outString(s);
    }

    public String getAsString() {
        String sdk = "";
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sdk += s[i][j] + "";
            }
        }
        return sdk;
    }

    public boolean solve(int x, int y) {
        if (x > 8) {
            x = 0;
            y++;

            if (y > 8) {
                return true;
            }
        }

        if (s[x][y] != 0) {
            return solve(x + 1, y);
        }

        for (int d = 1; d < 10; d++) {
            if (isLegal(x, y, d)) {
                //   System.out.println("x:" + x + ", y:" + y + ", d:" + d + "");
                s[x][y] = d;
                if (solve(x + 1, y)) {
                    //s[x][y] = 0;
                    return true;
                }
                s[x][y] = 0;
            }
        }

        return false;
    }

    public boolean isLegal(int x, int y, int d) {
        for (int l = 0; l < 9; l++) {
            if (s[x][l] == d || s[l][y] == d) {
                return false;
            }
        }

        int i1 = (x / 3) * 3;
        int j1 = (y / 3) * 3;
        for (int i = i1; i < i1 + 3; i++) {
            for (int j = j1; j < j1 + 3; j++) {
                if (s[i][j] == d) {
                    return false;
                }
            }
        }

        return true;
    }

    public static void main(String[] args) {
        SudokuCreator sc = new SudokuCreator();

        int[][] s = new int[9][9];
        sc.createArray(1);

        //s = sc.createRandomArray(0, 0);

        //System.out.println("isLegal:" + sc.isLegal(s, 0, 0, 1));
        if (s != null) {
            sc.printArray();
        } else {
            System.out.println("Dolduramadı...");
        }

    }
}
