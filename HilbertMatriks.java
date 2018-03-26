import java.util.Scanner;
import java.util.Locale;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

public class HilbertMatriks {

  static Matriks mat;

  private static long gcd (long a, long b) {
    while (b > 0) {
      long temp = b;
      b = a % b;
      a = temp;
    }
    return a;
  }

  private static long lcm (long a, long b) {
    return a / gcd(a,b) * b;
  }

  public static void inputMatriks() {

    int n;
    Scanner sin = new Scanner(System.in);
    Scanner in  = null;

    System.out.print("Input file? [y/N] : ");
    String isFileInput = sin.next();

    if (!isFileInput.equals("y") && !isFileInput.equals("Y")) {
      // read input from stdin
      in = new Scanner(System.in).useLocale(Locale.US);
    } else {
      // read input from file
      System.out.print("Input file name : ");
      String filename = sin.next();
      while (true) {
        try {
          File fin = new File(filename);
          in = new Scanner(fin).useLocale(Locale.US);
          break;
        } catch (FileNotFoundException e) {
          System.out.println("No File " + filename + " found.");
          System.out.print("Input file name : ");
          filename = sin.next();
        }
      } 
    }

    if (!isFileInput.equals("y") && !isFileInput.equals("Y")) {
      System.out.print("Masukkan N = ");
    }
    n = in.nextInt();

    long kpk = 1;
    for (long i = 1; i <= 2*n-1; i++) {
      kpk = lcm(i, kpk);
    }

    mat = new Matriks(n, n+1);
    for (int row_i = 0; mat.isRowValid(row_i); row_i++) {
      for (int col_i = 0; col_i < mat.getNCol() - 1; col_i++) {
        mat.setMatElement(row_i, col_i, (double) kpk/(row_i + col_i + 1));
      }
    }

    for (int row_i = 0; mat.isRowValid(row_i); row_i++) {
      mat.setMatElement(row_i, mat.getNCol() - 1, kpk);
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

      for (int var_i = 0; var_i < mat.getNCol() - 1; var_i++) {
        System.out.println("x[" + var_i + "]" + " = " + sols[var_i]);
        if (isSaveToFile) { pw.println("x[" + var_i + "]" + " = " + sols[var_i]); }
      }
    } else {
      // many solution exist
      String[] sols = mat.parseManySolution();

      System.out.print("Assume :\n");
      if (isSaveToFile) { pw.print("Assume :\n"); }

      for (int var_i = 0; var_i < mat.getNCol() - 1; var_i++) {
        char leading_char = sols[var_i].charAt(0);
        if ((leading_char < '0' || leading_char > '9') && leading_char != '-') {

          System.out.println("x[" + var_i + "]" + " = " + sols[var_i]);
          if (isSaveToFile) { pw.println("x[" + var_i + "]" + " = " + sols[var_i]); }

        }
      }

      System.out.print("\nThen :\n");
      if (isSaveToFile) { pw.print("\nThen :\n"); }

      for (int var_i = 0; var_i < mat.getNCol() - 1; var_i++) {
        char leading_char = sols[var_i].charAt(0);
        if ((leading_char >= '0' && leading_char <= '9') || leading_char == '-') {

          System.out.println("x[" + var_i + "]" + " = " + sols[var_i]);
          if (isSaveToFile) { pw.println("x[" + var_i + "]" + " = " + sols[var_i]); }

        }
      }

    }

    if (pw != null) {
      pw.close();
    }

  }

  public void run2() {

    mat = new Matriks(0,0);

    inputMatriks();

    mat.solveGaussJordan();

    outputSolution();
  }

}