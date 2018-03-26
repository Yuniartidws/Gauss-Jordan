import java.util.Scanner;
import java.util.Locale;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

public class InterpolationMatriks {

  InterpolationMatriks(){
    
  }

  static Matriks mat;
  public static double pivot = 0.0;

  public static void inputMatriks() {

    Scanner sin = new Scanner(System.in);
    Scanner in = null;

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

    if (!isFileInput.equals("y") && !isFileInput.equals("Y")) System.out.print("Masukan jumlah titik : ");
    int tmp = in.nextInt();
    mat = new Matriks(tmp, tmp+1);

    for (int i = 0; i < mat.getNRow(); i++) {
      if (!isFileInput.equals("y") && !isFileInput.equals("Y")) System.out.print("Masukan nilai x" + (i+1) + " : ");
      double temp = in.nextDouble();
      if(i == 0){
          if(temp>1900)
            pivot = temp;
          else
            pivot = 0;
      }
      for (int j = 0; j < mat.getNCol()-1; j++) {
        double tempp = 1.0;
        for(int k = 0; k < j; k++)
          tempp*=(temp-pivot);
        mat.setMatElement(i, j, tempp);
      }
      if (!isFileInput.equals("y") && !isFileInput.equals("Y")) System.out.print("Masukan nilai f(x" +(i+1)+ ") : ");
      mat.setMatElement(i, mat.getNCol()-1, in.nextDouble());
      
    }


  }

  public static void outputSolution() {

    Scanner sin = new Scanner(System.in);

    System.out.print("\nSave to file? [y/N] : ");
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
      /* there must be exist one solution */

    } else if (mat.typeSolution() == 1) {
      // only one solution exist
      double[] sols = mat.parseOneSolution();

      for (int var_i = 0; var_i < mat.getNCol() - 1; var_i++) {
        System.out.println("x[" + var_i + "]" + " = " + sols[var_i]);
        if (isSaveToFile) { pw.println("x[" + var_i + "]" + " = " + sols[var_i]); }
      }
    } else {
      /* there must be exist one solution */

    }

    if (pw != null) {
      pw.close();
    }
  }

  public static void test(){
      
      double ans = 0,tmp,x,xx;

      System.out.print("Masukkan nilai x : ");
      
      Scanner scan = new Scanner(System.in);
      x = scan.nextDouble();
      xx = x-pivot;
      double[] sols = mat.parseOneSolution();
      for(int i=0;i<mat.getNCol()-1;i++){
          tmp = Math.pow(xx,i);
          ans += tmp*sols[i];
      }
      System.out.println("f(" + x +") = " + ans);
  }

  public static void outputPolinom(){
    double ans[] = mat.parseOneSolution();
      
    System.out.print("\nP(X) = ");
    for(int i=0;i<mat.getNCol()-1;i++){
      if(ans[i] > 0)
      {
        if(i>0) System.out.print("+ ");
        System.out.print(ans[i] + "x^" + i+" ");
      }
      else
      {
        if(i>0) System.out.print("- ");
        ans[i] *= -1;
        System.out.print(ans[i] + "x^" + i+" ");  
      }
    }
    System.out.println("\n");
  }

  public void run5() {

    mat = new Matriks(0,0);

    inputMatriks();

    mat.solveGaussJordan();
  
    outputSolution();
    
    outputPolinom();

    test();

  }

}