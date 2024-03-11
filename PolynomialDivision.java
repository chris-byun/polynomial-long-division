import java.util.Scanner;

// made by Christopher Byun :D

  public class PolynomialDivision {

    Scanner ScannerPublic = new Scanner(System.in);

    public static void main(String[] Args) {

      Scanner ScannerStatic = new Scanner(System.in);
      PolynomialDivision dividend = new PolynomialDivision();
      PolynomialDivision divisor = new PolynomialDivision();
      PolynomialDivision quotient = new PolynomialDivision();

      
      int divdDegInit = dividend.collectDegree(true);
      // punishment for being stupid
      if (divdDegInit <= 0) {
        while (divdDegInit <= 0) {
          System.out.print("you messed up. I'M IN YOUR COMPUTER ");
        }
      }
      while (ScannerStatic.nextBoolean() == false) {
        divdDegInit = dividend.collectDegree(true);
        // punishment
        if (divdDegInit <= 0) {
          while (divdDegInit <= 0) {
            System.out.print("you messed up. I'M IN YOUR COMPUTER ");
          }
        }
      }

      int divsDeg = divisor.collectDegree(false);
      // punishment
      if (divsDeg <= 0) {
        while (divsDeg <= 0) {
          System.out.print("you messed up. I'M IN YOUR COMPUTER ");
        }
      }
      while (ScannerStatic.nextBoolean() == false) {
        divsDeg = divisor.collectDegree(false);
        // punishment
        if (divsDeg <= 0) {
          while (divsDeg <= 0) {
            System.out.print("you messed up. I'M IN YOUR COMPUTER ");
          }
        }
      } 
      double[] divdCoeffs = dividend.collectCoeffs(divdDegInit, 
      true);
      dividend.printExpression(divdCoeffs, divdDegInit,
      false);
      while (ScannerStatic.nextBoolean() == false) {
        divdCoeffs = dividend.collectCoeffs(divdDegInit, 
        true);
        dividend.printExpression(divdCoeffs, divdDegInit,
        false);
      }

      double[] divsCoeffs = divisor.collectCoeffs(divsDeg, 
        false);
      divisor.printExpression(divsCoeffs, divsDeg, false);
      while (ScannerStatic.nextBoolean() == false) {
        divsCoeffs = divisor.collectCoeffs(divsDeg, false);
        divisor.printExpression(divdCoeffs, divdDegInit,
        false);
      }
      
      double[] quotCoeffs = quotient.findQuotient(divdCoeffs, divsCoeffs,
      divdDegInit, divsDeg, false);

      double[] remCoeffs = quotient.findQuotient(divdCoeffs, divsCoeffs,
      divdDegInit, divsDeg, true);

      quotient.printAnswer(quotCoeffs, remCoeffs, divdCoeffs,
      divdDegInit, divsDeg);
      
      quotient.printExpression(divsCoeffs, divsDeg,true);
    }

    public int collectDegree(boolean dividendCheck) {
      System.out.println("Degree MUST be expressed with an integer.");
      if (dividendCheck == true) {
        System.out.println("What is the degree of the dividend?");
      } else {
        System.out.println("what is the degree of the divisor?");
      }
      int degree = ScannerPublic.nextInt();
      System.out.println("You entered " + degree + ". type true to confirm" +
      " and false to reenter");
      return degree;
    }

    public double[] collectCoeffs(int degree, boolean dividendCheck) {
      System.out.println("coefficients may only be integers or decimals.");
      if (dividendCheck == true) {
        System.out.println("initializing dividend coefficients.");
      } else {
        System.out.println("initializing divisor coefficients.");
      }
      int coeffArraySize = degree + 1;
      double[] coeffs = new double[coeffArraySize];

      System.out.println("If a term is missing, input it as 0.");
      System.out.println();
      for (int i = degree; i >= 0; i--) {
        if (i != 0) {
          System.out.println("give a coefficient.");
        } else {
          System.out.println("now give the constant.");
        }
        coeffs[i] = ScannerPublic.nextInt();
      }
      return coeffs;
    }

    public double[] findQuotient(double[] divdCoeffs, double[] divsCoeffs,
      int divdDegInit, int divsDeg, boolean findRemainder) {

      // collecting constant variables
      int quotArraySize = divdDegInit + 1;
      double[] quotCoeffs = new double[quotArraySize];
      int divdExp = 0;
      double divsLeadCoeff = divsCoeffs[divsDeg];
      int resultArraySize = divsDeg + 1;
      int remainderArraySize = divsDeg + 1;
      double[] remCoeffs = new double[remainderArraySize];

      //dynamic variables
      double[] result = new double[resultArraySize];
      int quotArrayPos;
      double divdLeadCoeff;
      double factor;
      int expDiff;

      for (divdExp = divdDegInit; divdExp >= divsDeg; divdExp--) {
        //collecting dynamic variables
        quotArrayPos = divdExp - divsDeg;
        divdLeadCoeff = divdCoeffs[divdExp];
        factor = divdLeadCoeff / divsLeadCoeff;
        expDiff = divdExp - divsDeg;
        
        // creating result row
        for (int k = 0; k <= divsDeg; k++) {
          result[divsDeg - k] = divdCoeffs[divdExp - k] - 
          divsCoeffs[divsDeg - k] * factor;
        }

        // setting up quotient
        quotCoeffs[quotArrayPos] = factor;
        
        // reinitializing dividend
        for (int n = 0; n <= divsDeg; n++) {
          divdCoeffs[expDiff + n] = result[n];
        }

        if (divdExp == divsDeg) {
          remCoeffs = result.clone();
          break;
        }
      }
      if (findRemainder == false) {
        return quotCoeffs;
      } else {
        return remCoeffs;
      }
    }

    public void printExpression(double[] expressionCoeffs, int degree,
    boolean printRemBottom) {

      int expressionLength = degree + 1;
      String[] expression = new String[expressionLength];
      for (int r = degree; r >= 0; r--) {
        expression[r] = expressionCoeffs[r] + "x^" + r;
        if (r > 0) {
          if (expressionCoeffs[r - 1] >= 0) {
            expression[r] = expression[r] + " + ";
          } else {
            expression[r] = expression[r] + " ";
          }
        }
        System.out.print(expression[r]);
      }
      if (printRemBottom == false) {
        System.out.println();
        System.out.println("type 'true' to confirm and 'false' to reenter");
      } else {
        System.out.print(")");
      }
    }

    public void printAnswer(double[] quotCoeffs, double[] remCoeffs, 
      double[] divdCoeffsInit, int divdDegInit, int divsDeg) {

      System.out.println("The answer is ");
      // express polynomial part of quotient
      int quotientLength = divdDegInit + 1;
      String[] quotient = new String[quotientLength];
      for (int p = divdDegInit - divsDeg; p >= 0; p--) {
        quotient[p] = quotCoeffs[p] + "x^" + p;
        if (p > 0) {
          if (quotCoeffs[p - 1] >= 0) {
            quotient[p] = quotient[p] + " + ";
          } else {
            quotient[p] = quotient[p] + " ";
          }
        }
        if (p <= quotientLength - divsDeg) {
          System.out.print(quotient[p]);
        }
      }

      int remTopLength = divsDeg;
      String[] remTop = new String[remTopLength];
      System.out.print(" + (");
      for (int j = divsDeg - 1; j >= 0; j--) {
        remTop[j] = remCoeffs[j] + "x^" + j;
        if (j > 0) {
          if (remCoeffs[j - 1] >= 0) {
            remTop[j] = remTop[j] + " + ";
          } else {
            remTop[j] = remTop[j] + " ";
          }
        }
        System.out.print(remTop[j]);
      }
      System.out.print(")/(");
    }

    public void dividend() {}
    public void divisor() {}
    public void quotient(){}

  }