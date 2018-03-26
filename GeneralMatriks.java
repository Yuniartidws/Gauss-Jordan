import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

public class GeneralMatriks {

  static Matriks mat;

  public static void inputMatriks() {

    Scanner sin = new Scanner(System.in);

    System.out.print("Input file? [y/N] : ");
    String isFileInput = sin.next();

    if (!isFileInput.equals("y") && !isFileInput.equals("Y")) {
      // read input from stdin
      try {
        mat.inputMatriks("");
      } catch (FileNotFoundException e) {
        // this line will never be excuted, because input is from stdin
      }
    } else {
      // read input from file
      System.out.print("Input file name : ");
      String filename = sin.next();
      while (true) {
        try {
          mat.inputMatriks(filename);
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

  public void run1() {

    mat = new Matriks(0,0);

    inputMatriks();

    mat.solveGaussJordan();

    outputSolution();
  }

}