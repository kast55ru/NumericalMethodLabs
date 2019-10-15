public class LUTask {
    public static void print_matr( double[][] m, int n )
    {
        for(int i = 0 ; i < n; i++) {
            for(int j = 0; j < n; j++) {
                System.out.print( m[i][j] + " ");
            }
            System.out.println();
        }

    }

    public static void matr_mul( double[][] a,  double[][] b,  double[][] c, int n )
    {
        int i,j,k;

        for( i = 0; i < n; i++ )
            for( j = 0; j < n; j++ )
                for( c[i][j]=0., k=0; k<n; k++ ) c[i][j] += a[i][k]*b[k][j];

    }

    public static void LU(double[][] A, double[][] L, double[][] U, int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                U[i][j] = A[i][j];
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                L[j][i] = U[j][i] / U[i][i];
            }
        }

        for (int k = 1; k < n; k++) {
            for(int i = k-1; i < n; i++) {
                for(int j = i; j < n; j++) {
                    L[j][i] = U[j][i] / U[i][i];
                }
            }

            for(int i = k; i < n; i++) {
                for(int j = k-1; j < n; j++) {
                    U[i][j] = U[i][j] - L[i][k-1] * U[k-1][j];
                }
            }
        }
    }

    public static void main(String[] args) {
        //https://ru.wikipedia.org/wiki/LU-%D1%80%D0%B0%D0%B7%D0%BB%D0%BE%D0%B6%D0%B5%D0%BD%D0%B8%D0%B5
        //https://habr.com/ru/sandbox/35982/
        //https://www.webmath.ru/primeri_reshenii/primeri_reshenii_slay_gauss1_1.php
        //https://algowiki-project.org/ru/%D0%9F%D1%80%D1%8F%D0%BC%D0%B0%D1%8F_%D0%BF%D0%BE%D0%B4%D1%81%D1%82%D0%B0%D0%BD%D0%BE%D0%B2%D0%BA%D0%B0_(%D0%B2%D0%B5%D1%89%D0%B5%D1%81%D1%82%D0%B2%D0%B5%D0%BD%D0%BD%D1%8B%D0%B9_%D0%B2%D0%B0%D1%80%D0%B8%D0%B0%D0%BD%D1%82)
        //https://algowiki-project.org/ru/%D0%9E%D0%B1%D1%80%D0%B0%D1%82%D0%BD%D0%B0%D1%8F_%D0%BF%D0%BE%D0%B4%D1%81%D1%82%D0%B0%D0%BD%D0%BE%D0%B2%D0%BA%D0%B0_(%D0%B2%D0%B5%D1%89%D0%B5%D1%81%D1%82%D0%B2%D0%B5%D0%BD%D0%BD%D1%8B%D0%B9_%D0%B2%D0%B0%D1%80%D0%B8%D0%B0%D0%BD%D1%82)

        int n = 3;

        double[][] a = {
                {1, 2, 3},
                {3, 5, 7},
                {1, 3, 4}
        };

        double[] b = {3, 0, 1};

        double[] x = new double[n];

        double[][] l = new double[n][];
        for (int i = 0; i < n; i++)	l[i] = new double[n];

        double[][] u = new double[n][];
        for (int i = 0; i < n; i++)	u[i] = new double[n];

        double[][] r = new double[n][];
        for (int i = 0; i < n; i++)	r[i] = new double[n];

        LU(a, l, u, n);

        System.out.println("\nFirst matrix:");
        print_matr(a, n);

        System.out.println("\nU matrix:");
        print_matr(u, n);

        System.out.println("\nL matrix:");
        print_matr(l, n);

        matr_mul(l, u, r, n);
        System.out.println("\nL*U matrix:");
        print_matr(r, n);

        //L * y = b; y = ...

        double[] y = new double[n];
        y[0] = b[0];
        for (int i = 1; i < n; i++) {
            double sum = 0;
            for (int j = 0; j < i; j++) {
                sum += l[i][j] * y[j];
            }
            y[i] = b[i] - sum;
        }

        //U * x = y; x = ...

        x[n-1] = y[n-1] / u[n-1][n-1];
        for (int i = n-2; i >= 0; i--) {
            double sum = 0;
            for (int j = i + 1; j < n; j++) {
                sum += u[i][j] * x[j];
            }
            x[i] = (y[i] - sum) / u[i][i];
        }

        System.out.println("\nx vector:");
        for (int i = 0; i < n; i++) {
            System.out.print(x[i]);
            System.out.print(" ");
        }
    }
}
