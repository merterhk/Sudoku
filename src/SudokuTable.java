
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JApplet;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class SudokuTable extends JApplet implements MouseListener, KeyListener {

    BufferedImage table, trans, wrong;//= ImageIO.read(this.getClass().getResource("light.png"));
    BufferedImage[] n = new BufferedImage[9], m = new BufferedImage[9];
    int[][] s = new int[9][9];
    int[][] pattern = new int[9][9];
    int[][] solution = new int[9][9];
    boolean ready = false; // Dizi yazılmaya hazırsa
    boolean solving = false; // Kullanıcı problem çözüyorsa
    boolean preparePuzzle = false; // Bulmaca hazırlıyorsa
    boolean createPattern = false; // Desen oluşturuyorsa
    boolean lights = false;
    int pX = 0, pY = 0;
    int nCount = 0;

    public void init() {
        try {
            // TODO start asynchronous download of heavy resources
            table = ImageIO.read(this.getClass().getResource("table.png"));
            trans = ImageIO.read(this.getClass().getResource("trans.png"));
            wrong = ImageIO.read(this.getClass().getResource("wrong.png"));

            n[0] = ImageIO.read(this.getClass().getResource("n1.png"));
            n[1] = ImageIO.read(this.getClass().getResource("n2.png"));
            n[2] = ImageIO.read(this.getClass().getResource("n3.png"));
            n[3] = ImageIO.read(this.getClass().getResource("n4.png"));
            n[4] = ImageIO.read(this.getClass().getResource("n5.png"));
            n[5] = ImageIO.read(this.getClass().getResource("n6.png"));
            n[6] = ImageIO.read(this.getClass().getResource("n7.png"));
            n[7] = ImageIO.read(this.getClass().getResource("n8.png"));
            n[8] = ImageIO.read(this.getClass().getResource("n9.png"));

            m[0] = ImageIO.read(this.getClass().getResource("m1.png"));
            m[1] = ImageIO.read(this.getClass().getResource("m2.png"));
            m[2] = ImageIO.read(this.getClass().getResource("m3.png"));
            m[3] = ImageIO.read(this.getClass().getResource("m4.png"));
            m[4] = ImageIO.read(this.getClass().getResource("m5.png"));
            m[5] = ImageIO.read(this.getClass().getResource("m6.png"));
            m[6] = ImageIO.read(this.getClass().getResource("m7.png"));
            m[7] = ImageIO.read(this.getClass().getResource("m8.png"));
            m[8] = ImageIO.read(this.getClass().getResource("m9.png"));
            setSize(209, 209);
            addMouseListener(this);
            addKeyListener(this);
        } catch (IOException ex) {
        }
    //System.out.println("Initialized succes..");
    }

    public void setArray(int[][] a) {
        s = a;
        copyFromArray();
        ready = true;

        SudokuSolver ss = new SudokuSolver();
        ss.setArray(solution);
        solution = ss.solutionArray();

        repaint();
    //System.out.println("Array setted..");
    }

    public void showSolution() {
        // s = solution;
        s = solution;
        repaint();
        isComplate();
        ready = false;
    //System.out.println("Showing Solution..");
    }

    public boolean isComplate() {

        System.out.println("nCount : " + nCount);
        if (nCount == 81) {

            System.out.println("is complated?2");
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (s[i][j] != solution[i][j]) {
                        return false;
                    }
                }
            }

            lights = false;
            ready = false;
            System.out.println("Okey baby! :)");
            new JOptionPane().showMessageDialog(this, "Bulmacayı çözdünüz! :)", "Tebrikler!", JOptionPane.PLAIN_MESSAGE);
            return true;

        } else {
            return false;
        }
    }

    public void copyFromArray() {
        nCount = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (s[i][j] != 0) {
                    nCount++;
                }
                pattern[i][j] = s[i][j];
                solution[i][j] = s[i][j];
            }
        }
        System.out.println("Number Count is : " + nCount);
    //System.out.println("Pattern Copied..");
    }

    public void copyToSolution() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                //pattern[i][j] = s[i][j];
                solution[i][j] = s[i][j];
            }
        }
    }

    public int[][] getPattern() {
        return pattern;
    }

    public int[][] getArray() {
        return s;
    }

    public void preparePorblem() {
        s = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                s[i][j] = 0;
                pattern[i][j] = 0;
                solution[i][j] = 0;
            }
        }
        preparePuzzle = true;
        ready = true;
        repaint();
    }

    public void solveProblem() {
        copyToSolution();
        SudokuSolver ss = new SudokuSolver();
        ss.setArray(solution);
        if (ss.solution()) {
            copyFromArray();
            solution = ss.solutionArray();
            s = solution;
            ready = false;
            preparePuzzle = false;
            JOptionPane.showMessageDialog(rootPane, "Bulmaca çözüldü! :)", "Bulmaca Çözücü", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Bulmaca çözülemedi... :(", "Bulmaca Çözücü", JOptionPane.WARNING_MESSAGE);
        }
        repaint();
    }

    public void showArray(Graphics g) {
        SudokuSolver ss = new SudokuSolver();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (s[i][j] != 0) {
                    int ind = (s[i][j] - 1); // Değer indexi!

                    if (pattern[i][j] != 0) {
                        g.drawImage(n[ind], i * 23 + 1, j * 23 + 1, rootPane);
                    } else {
                        g.drawImage(m[ind], i * 23 + 1, j * 23 + 1, rootPane);
                    }
                }
                if (s[i][j] != 0 && pattern[i][j] == 0 && !ss.isLegal(s, i, j, s[i][j])) {
                    // System.out.println("isLegal : " + ss.isLegal(s, i, j, s[i][j]));
                    g.drawImage(wrong, i * 23 + 1, j * 23 + 1, rootPane);
                }
            }
        }
    //showWrongs(g);
    }

    public void paint(Graphics g) {
        g.drawImage(table, 0, 0, rootPane);

        if (lights && pX > -1 && pY > -1) {
            for (int t = 0; t < 9; t++) {
                g.drawImage(trans, pX * 23 + 1, t * 23 + 1, rootPane);
                g.drawImage(trans, t * 23 + 1, pY * 23 + 1, rootPane);
            }
        }
//        if (ready) {
        showArray(g);
    //      }
    }

    // Inheritance Methods //////////////////
    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        if (getMousePosition().x < 207 && getMousePosition().y < 207) {
            pX = getMousePosition().x / 23;
            pY = getMousePosition().y / 23;
            lights = true;
            //System.out.println("pattern[" + pX + "][" + pY + "] : " + pattern[pX][pY]);
            repaint();
        }
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        int kCode = e.getKeyCode();
        if (ready && lights && kCode >= 49 && kCode <= 57 && pattern[pX][pY] == 0) {
            if (s[pX][pY] == 0) {
                nCount++;
            }
            s[pX][pY] = Integer.parseInt(e.getKeyChar() + "");

//            isComplate();
        }

        //System.out.println("Pattern[" + pX + "][" + pY + "] : " + pattern[pX][pY]);
        //System.out.println(e.getKeyChar() + " : " + e.getKeyCode());

        isComplate();

        if (ready && lights && (kCode == 48 || kCode == 8) && pattern[pX][pY] == 0) {
            if (s[pX][pY] != 0) {
                s[pX][pY] = 0;
                nCount--;
            }
        }

        if (kCode == 27) {
            lights = false;
            pX = pY = -1;
        }

        if (kCode == 37) {//sol
            pX = pX > 0 ? pX - 1 : 8;
        }
        if (kCode == 38) {// yukarı
            pY = pY > 0 ? pY - 1 : 8;
        }
        if (kCode == 39) {// sağ
            pX = pX < 8 ? pX + 1 : 0;
        }
        if (kCode == 40) {// aşağı
            pY = pY < 8 ? pY + 1 : 0;
        }
        repaint();
    }

    public void keyReleased(KeyEvent e) {
    }

// TODO overwrite start(), stop() and destroy() methods
}
