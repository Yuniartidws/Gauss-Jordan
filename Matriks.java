import java.util.Scanner;
import java.util.Locale;
import java.io.File;
import java.io.FileNotFoundException;

public class Matriks {

  private int n_row;
  private int n_col;
  private double[][] mat;
  private final double fepsilon = 1e-9;

  /********* CONSTRUCTOR *********/
  /**
   * Make Identity Matriks
   */
  Matriks(int _n_row, int _n_col) {
    this.n_row = _n_row;
    this.n_col = _n_col;
    this.mat = new double[_n_row][_n_col];

    for (int row_i = 0; row_i < _n_row; row_i++) {
      for (int col_i = 0; col_i < _n_col; col_i++) {
        if (row_i == col_i) {
          this.mat[row_i][col_i] = 1;
        } else {
          this.mat[row_i][col_i] = 0;
        }
      }
    }
  }

  /********* GETTER *********/

  public int getNRow() {
    return this.n_row;
  }

  public int getNCol() {
    return this.n_col;
  }

  public double getMatElement(int row, int col) {
    if (this.isRowValid(row) && this.isColValid(col)) {
      return this.mat[row][col];
    } else {
      // row or column index is not valid
      return -1.00;
    }
  }

  /********* PREDICATE *********/

  public boolean isRowValid(int row) {
    return row >= 0 && row < this.getNRow();
  }

  public boolean isColValid(int col) {
    return col >= 0 && col < this.getNCol();
  }

  /********* SETTER *********/

  public void setNRow(int row) {
    this.n_row = row;
  }

  public void setNCol(int col) {
    this.n_col = col;
  }

  public void setMatElement(int row, int col, double value) {
    if (this.isRowValid(row) && this.isColValid(col)) {
      this.mat[row][col] = value;
    } else {
      // row or column index is not valid
    }
  }

  /********* IO *********/
  /**
   * If filename is empty string, read Matriks from stdin
   * Otherwise, read Matriks from file filename
   */
  public void inputMatriks(String filename) throws FileNotFoundException {

    Scanner in = null;
    if (filename == "") {
      in = new Scanner(System.in).useLocale(Locale.US);
    } else {
      File fin = new File(filename);
      in = new Scanner(fin).useLocale(Locale.US);
    }

    this.setNRow(in.nextInt());
    this.setNCol(in.nextInt());
    this.mat = new double[this.getNRow()][this.getNCol()];

    for (int row_i = 0; this.isRowValid(row_i); row_i++) {
      for (int col_i = 0; this.isColValid(col_i); col_i++) {
        this.setMatElement(row_i, col_i, in.nextDouble());
      }
    }

  }

  public void outputMatriks() {
    for (int row_i = 0; this.isRowValid(row_i); row_i++) {
      for (int col_i = 0; this.isColValid(col_i); col_i++) {

        System.out.print(this.getMatElement(row_i, col_i));

        if (col_i == this.getNCol() - 1) {
          System.out.print("\n");
        } else {
          System.out.print(" ");
        }
      }
    }
  }

  /********* OTHERS *********/

  public boolean fEqual(double a, double b) {
    return Math.abs(a-b) < this.fepsilon;
  }

  /********* OPERATION *********/

  /**
   * divide all matrix elements in row_i-th row with value
   */
  public void divOneRow(int row_i, double value) {
    if (this.isRowValid(row_i)) {
      for (int col_i = 0; this.isColValid(col_i); col_i++) {
        this.mat[row_i][col_i] /= value;
      }
    } else {
      // row is not valid
    }
  }

  /**
   * all matrix elements in row_source-th row substracted
   * with k times of elements in row_other row and corresponding column
   */
  private void minusKOtherRow(int row_source, int row_other, double k) {
    if (this.isRowValid(row_source) && this.isRowValid(row_other)) {
      for (int col_i = 0; this.isColValid(col_i); col_i++) {
        this.mat[row_source][col_i] -= k * this.mat[row_other][col_i];
      }
    } else {
      // one of the rows is not valid
    }
  }

  /**
   * return i such that element in [i][col] is the greatest
   * for all possible i and i >= row_start
   * if there is multiple answer, return the minimum one.
   */
  private int findGreatestRowForColumn(int row_start, int col) {
    if (this.isRowValid(row_start) && this.isColValid(col)) {
      int row_greatest = row_start;
      for (int row_i = row_start; this.isRowValid(row_i); row_i++) {
        if (Math.abs(this.getMatElement(row_greatest, col)) < Math.abs(this.getMatElement(row_i, col))) {
          row_greatest = row_i;
        }
      }

      return row_greatest;
    } else {
      // row_start or col is not valid
      return -1;
    }
  }

  /**
   * swap the content of row_i and row_j
   */
  private void swapTwoRows(int row_i, int row_j) {
    if (this.isRowValid(row_i) && this.isRowValid(row_j)) {
      double tempval;

      for (int col_i = 0; this.isColValid(col_i); col_i++) {
        tempval = this.getMatElement(row_i, col_i);
        this.setMatElement(row_i, col_i, this.getMatElement(row_j, col_i));
        this.setMatElement(row_j, col_i, tempval);
      }
    } else {
      // row_i or row_j is not valid
    }
  }

  public void solveGaussJordan() {
    int[] identity = new int[this.getNRow()];
    int n_identity = 0;

    for (int row = 0, col = 0; this.isRowValid(row) && col < this.getNCol()-1; col++) {
      this.swapTwoRows(row, this.findGreatestRowForColumn(row, col));

      if (!this.fEqual(this.getMatElement(row, col), 0.00)) {
        this.divOneRow(row, this.getMatElement(row, col));

        for (int row_i = row + 1; this.isRowValid(row_i); row_i++) {
          this.minusKOtherRow(row_i, row, this.getMatElement(row_i, col));
        }

        identity[n_identity++] = col;
        row++;
      }
    }

    for (int identity_i = n_identity - 1; identity_i >= 0; identity_i--) {
      for (int row_i = identity_i - 1; this.isRowValid(row_i); row_i--) {
        this.minusKOtherRow(row_i, identity_i, this.getMatElement(row_i, identity[identity_i]));
      }
    }
  }

  /**
   * return:
   * 0 if there is no solution available
   * 1 if ther is exist one solution only
   * 2 if ther is many solutions.
   */
  public int typeSolution() {
    int ret;
    if (this.getNRow() < this.getNCol() - 1) {
      // if the row is not sufficient enough,
      // there is no way the solution is one solution only
      ret = 2;
    } else {
      if (this.fEqual(this.getMatElement(this.getNRow() - 1, this.getNCol() - 2), 0.00)) {
        if (this.fEqual(this.getMatElement(this.getNRow() - 1, this.getNCol() - 1), 0.00)) {
          ret = 2;
        } else {
          ret = 0;
        }
      } else {
        ret = 1;
      }
    }
/*
TODO : DELETE INI, HARUSNYA ATAS SUDAH CUKUP *
    for (int row_i = 0; this.isRowValid(row_i) && ret != 0; row_i++) {
      int first_one = -1;
      // -1 if all zero
      // [0..col) if begin with 1 in the first_one
      for (int col_i = 0; col_i < this.getNCol() - 1; col_i++) {
        if (this.fEqual(this.getMatElement(row_i, col_i), 1.00)) {
          if (first_one == -1) {
            first_one = col_i;
          }
        }
      }

      if (first_one == -1) {
        if (this.fEqual(this.getMatElement(row_i, this.getNCol() - 1), 0.00)) {
          ret = 2;
        } else {
          ret = 0;
        }
      } else if (first_one != row_i) {
        ret = 2;
      }
    }
    */

    return ret;
  }

  /**
   * Called ONLY when typeSolution() == 1
   * return array of double, indicating the solution from matrix
   */
  public double[] parseOneSolution() {
    double[] ret = new double[this.getNCol() - 1];

    for (int row_i = 0; this.isRowValid(row_i); row_i++) {
      ret[row_i] = this.getMatElement(row_i, this.getNCol() - 1);
    }

    return ret;
  }

  /**
   * Return result of every variables, expressed as function
   * from other variables
   */
  public String[] parseManySolution() {
    String ret[] = new String[this.getNCol() - 1];

    boolean[] is_free_var = new boolean[this.getNCol() - 1];
    int n_free_var = 0;
    int[] reverse_free_var = new int[this.getNCol() - 1];

    // first, assume all variables are free var
    for (int is_free_var_i = 0; is_free_var_i < is_free_var.length; is_free_var_i++) {
      is_free_var[is_free_var_i] = true;
    }

    // find all the variables that can be expressed as function from other free variables
    for (int row_i = 0; this.isRowValid(row_i); row_i++) {
      int first_one = -1;
      for (int col_i = 0; col_i < this.getNCol() - 1; col_i++) {
        if (this.fEqual(this.getMatElement(row_i, col_i), 1.00)) {
          if (first_one == -1) {
            first_one = col_i;
          }
        }
      }

      if (first_one >= 0) {
        is_free_var[first_one] = false;
      }
    }

    // listing all free vars
    for (int is_free_var_i = 0; is_free_var_i < is_free_var.length; is_free_var_i++) {
      if (is_free_var[is_free_var_i]) {
        reverse_free_var[is_free_var_i] = n_free_var++;
      }
    }

    // create the solution as string
    for (int var = 0, row_i = 0; var < this.getNCol() - 1; var++) {
      if (is_free_var[var] || row_i >= this.getNRow()) {
        ret[var] = "a[" + reverse_free_var[var] + "]";
      } else {
        ret[var] = "" + this.getMatElement(row_i, this.getNCol() - 1);
        for (int col_i = 0; col_i < this.getNCol()-1; col_i++) {
          if (col_i != var && !this.fEqual(this.getMatElement(row_i, col_i), 0.00)) {
            ret[var] += " - (" + this.getMatElement(row_i, col_i) + " * a[" + reverse_free_var[col_i] + "])";
          }
        }
        row_i++;
      }
    }

    return ret;
  }


}