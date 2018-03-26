import java.util.Scanner;
import java.util.Locale;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

public class CircuitMatriks {

  static Matriks mat;

  private static void prepareMatriks() {

    mat = new Matriks(10, 11);

    for (int row_i = 0; mat.isRowValid(row_i); row_i++) {
      for (int col_i = 0; mat.isColValid(col_i); col_i++) {
        mat.setMatElement(row_i, col_i, 0.00);
      }
    }

    int[][] ones = {
      {0, 0}, {0, 1}, {0, 2},
      {1, 3}, {2, 5}, {3, 4},
      {4, 7}, {5, 7}, {6, 9},
      {7, 6}, {8, 8}, {9, 6}
    };

    int[][] minusones = {
      {1, 1}, {1, 4}, {2, 2},
      {3, 5}, {4, 6}, {5, 8},
      {8, 9}, {9, 9}
    };

    for (int ones_i = 0; ones_i < ones.length; ones_i++) {
      mat.setMatElement(ones[ones_i][0], ones[ones_i][1],  1.00);
    }

    for (int minusones_i = 0; minusones_i < minusones.length; minusones_i++) {
      mat.setMatElement(minusones[minusones_i][0], minusones[minusones_i][1],  -1.00);
    }
  }

  public static void inputMatriks() {

    Scanner sin = new Scanner(System.in).useLocale(Locale.US);

    System.out.print("Input file? [y/N] : ");
    String isFileInput = sin.next();

    if (!isFileInput.equals("y") && !isFileInput.equals("Y")) {
      // read input from stdin
      System.out.print("Masukkan : \n");
      System.out.print("R[12] = "); mat.setMatElement(7, 0, sin.nextDouble());
      System.out.print("R[52] = "); mat.setMatElement(9, 1, sin.nextDouble());
      System.out.print("R[32] = "); mat.setMatElement(4, 2, sin.nextDouble());
      System.out.print("R[65] = "); mat.setMatElement(6, 3, sin.nextDouble());
      System.out.print("R[54] = "); mat.setMatElement(8, 4, sin.nextDouble());
      System.out.print("R[34] = "); mat.setMatElement(5, 5, sin.nextDouble());
      System.out.print("V[1] = "); mat.setMatElement(7, 10, sin.nextDouble());
      System.out.print("V[6] = "); mat.setMatElement(6, 10, sin.nextDouble());
    } else {
      // read input from file
      System.out.print("Input file name : ");
      String filename = sin.next();
      while (true) {
        try {
          File fin = new File(filename);
          Scanner sfin = new Scanner(fin).useLocale(Locale.US);

          mat.setMatElement(7, 0, sfin.nextDouble());
          mat.setMatElement(9, 1, sfin.nextDouble());
          mat.setMatElement(4, 2, sfin.nextDouble());
          mat.setMatElement(6, 3, sfin.nextDouble());
          mat.setMatElement(8, 4, sfin.nextDouble());
          mat.setMatElement(5, 5, sfin.nextDouble());
          mat.setMatElement(7, 10, sfin.nextDouble());
          mat.setMatElement(6, 10, sfin.nextDouble());
          break;
        } catch (FileNotFoundException e) {
          System.out.println("No File " + filename + " found.");
          System.out.print("Input file name : ");
          filename = sin.next();
        }
      } 
    }
  }

  public static void outputSolution() {

    Scanner sin = new Scanner(System.in);

    System.out.print("Save to file? [y/N] : ");
    String inputSaveToFile = sin.next();
    File fout = null;
    PrintWriter pw = null;
    boolean isSaveToFile = false;

    if (inputSaveToFile.equals("y") || inputSaveToFile.equals("Y")) {
      isSaveToFile = true;

      System.out.print("Output file name : ");
      String filename = sin.next();

      fout = new File(filename);
      try {
        pw = new PrintWriter(filename);
      } catch (FileNotFoundException e) {
        //
      }
    }

    System.out.print("\nEchelon matrix :\n");
    mat.outputMatriks();

    System.out.print("\nSolution : \n");
    if (mat.typeSolution() == 0) {
      // no solution to the matrix

      System.out.println("No solution.");
      if (isSaveToFile) { pw.println("No solution."); }

    } else if (mat.typeSolution() == 1) {
      // only one solution exist
      double[] sols = mat.parseOneSolution();

      String[] label = { "i[12]", "i[52]", "i[32]", "i[65]", "i[54]", "i[13]", "V[2]", "V[3]", "V[4]", "V[5]" };

      for (int label_i = 0; label_i < label.length; label_i++) {
        System.out.println(label[label_i] + " = " + sols[label_i]);  
        if (isSaveToFile) { pw.println(label[label_i] + " = " + sols[label_i]); }
      }

    } else {
      // many solution exist
      String[] sols = mat.parseManySolution();

      String[] label = { "i[12]", "i[52]", "i[32]", "i[65]", "i[54]", "i[13]", "V[2]", "V[3]", "V[4]", "V[5]" };

      System.out.print("Assume :\n");
      if (isSaveToFile) { pw.print("Assume :\n"); }

      for (int var_i = 0; var_i < mat.getNCol() - 1; var_i++) {
        char leading_char = sols[var_i].charAt(0);
        if ((leading_char < '0' || leading_char > '9') && leading_char != '-') {

          System.out.println(label[var_i] + " = " + sols[var_i]);
          if (isSaveToFile) { pw.println(label[var_i] + " = " + sols[var_i]); }

        }
      }

      System.out.print("\nThen :\n");
      if (isSaveToFile) { pw.print("\nThen :\n"); }

      for (int var_i = 0; var_i < mat.getNCol() - 1; var_i++) {
        char leading_char = sols[var_i].charAt(0);
        if ((leading_char >= '0' && leading_char <= '9') || leading_char == '-') {

          System.out.println(label[var_i] + " = " + sols[var_i]);
          if (isSaveToFile) { pw.println(label[var_i] + " = " + sols[var_i]); }

        }
      }

    }

    if (pw != null) {
      pw.close();
    }

  }

  public void run3() {

    mat = new Matriks(0,0);

    prepareMatriks();

    inputMatriks();

    mat.solveGaussJordan();

    outputSolution();
  }

}
