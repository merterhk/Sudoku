
public class SudokuSolver {

    int[][] s = new int[9][9];

    public void createArray(String sudo) {
        //String sudo;
        //sudo = "500100070670094000003750900160000450402309706087000091008063500000520048040001007"; // kolay *çözdü
        //sudo = "070000000000002800530060000004601000060070030000803900000030057001900000000000040"; // Baya Zor :) (Milliyet) *çözdü *(21392.ms)*
        //sudo = "031000060400003800000407050000000020060295030080000000050306000006900001040000670"; // çok zor (Milliyet) *çözdü
        //sudo = "803004000400010086000000009000003400500090001002700000600000000190050008000800704"; // çok zor (Milliyet) *çözdü
        //sudo = "005200000060000407700005030010500000800672003000008040020800006507000080000004900"; // çok zor (Telefon) *çözdü
        //sudo = "060030050035000007000095400003007009650409013400300600006970000300000570080010060"; // zor (Milliyet) *çözdü
        //sudo = "304070905500402001000000000420000067036000180710000059000000000600103004102090708"; // kolay (Milliyet) *çözdü
        //sudo = "010000060040702010207000803001080300080603050006050900605000402090204070030000090"; // kolay (Milliyet) *çözdü
        //sudo = "000084610380000507050060902007001004210806059900200300609020070104000038025430000"; // çok kolay (Milliyet) *çözdü
        //sudo = ;
        //sudo = ;
        //sudo = ;
        //sudo = ;
        //sudo = ;
        //sudo = ;
        //sudo = ;
        //sudo = ;
        //sudo = ;
        //sudo = ;
        //sudo = ;
        //sudo = ;
        //sudo = ;
        //sudo = ;
        //sudo = ;
        //sudo = ;

        char c;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                c = sudo.charAt((i * 9) + j);
                s[i][j] = Integer.parseInt(c + "");
            }
        }
    }

    public void setArray(int[][] a) {
        s = a;
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
    }

    public boolean solution() {
        if (solve(0, 0)) {
            return true;
        } else {
            return false;
        }
    }

    public int[][] solutionArray() {
        if (solve(0, 0)) {
            return s;
        } else {
            return null;
        }
    }

    public boolean isNull(int x, int y) {
        if (s[x][y] != 0) {
            return false;
        } else {
            return true;
        }
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

    public boolean isLegal(int[][] s, int x, int y, int d) {
        for (int l = 0; l < 9; l++) {
            if  ((s[x][l] == d && l!=y) || (s[l][y] == d && l!=x)) {
                return false;
            }
        }

        int i1 = (x / 3) * 3;
        int j1 = (y / 3) * 3;
        for (int i = i1; i < i1 + 3; i++) {
            for (int j = j1; j < j1 + 3; j++) {
                if (s[i][j] == d && !(i==x && j==y) ) {
                    return false;
                }
            }
        }

        return true;
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
                    return true;
                }
                s[x][y] = 0;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        SudokuSolver sd = new SudokuSolver();
        SudokuCreator sc = new SudokuCreator();
        sc.createArray(1);
        System.out.println(sc.getAsString());
        sc.printArray();
        System.out.println("\n***********************\n");
        sd.createArray("060007901100620040500000000809060000740000305000090070078100003004008006000200100");//sc.getAsString() + "");
        /*
           060 007 901
            100 620 040
            500 000 000

            809 060 000
            740 000 305
            000 090 070

            781 000 030
            040 080 060
            002 001 00
         */
        long bas = System.currentTimeMillis();
        if (sd.solve(0, 0)) {
            System.out.println("Çözüm: True");
            long son = System.currentTimeMillis();
            System.out.println("Çözme Süresi : " + (son - bas) + ".ms");
            sd.printArray();
        } else {
            System.out.println("Çözüm : False");
        }
    }
}
