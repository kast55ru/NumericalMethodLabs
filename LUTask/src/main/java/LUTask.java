public class LUTask {
    public static void solve(double[][] A, int n, double[] b, double[] x) {
        double[][] l = new double[n][];
        for (int i = 0; i < n; i++)	l[i] = new double[n];

        double[][] u = new double[n][];
        for (int i = 0; i < n; i++)	u[i] = new double[n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                u[i][j] = A[i][j];
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                l[j][i] = u[j][i] / u[i][i];
            }
        }

        for (int k = 1; k < n; k++) {
            for(int i = k-1; i < n; i++) {
                for(int j = i; j < n; j++) {
                    l[j][i] = u[j][i] / u[i][i];
                }
            }

            for(int i = k; i < n; i++) {
                for(int j = k-1; j < n; j++) {
                    u[i][j] = u[i][j] - l[i][k-1] * u[k-1][j];
                }
            }
        }

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
    }

    public static void main(String[] args) {
        /*int n = 3;

        double[][] a = {
                {1, 2, 3},
                {3, 5, 7},
                {1, 3, 4}
        };

        double[] b = {3, 0, 1};

        double[] x = new double[n];

        LUTask.solve(a, n, b, x);

        System.out.println("\nx vector:");
        for (int i = 0; i < n; i++) {
            System.out.print(x[i]);
            System.out.print(" ");
        }*/

        int n = 100;
        double alpha = 1.;
        double beta  = 1.e+1;

        //double alpha = 1.e+1;
        //double beta = 1.;

        double[][] A = new double[n][];
        for (int i = 0; i < n; i++)	A[i] = new double[n];

        double[][] A_inv = new double[n][];
        for (int i = 0; i < n; i++)	A_inv[i] = new double[n];

        Gen g = new Gen();

        //g.mygen ( A, A_inv, n, alpha, beta, 1, 2, 0, 1 ); // симметричная
        g.mygen ( A, A_inv, n, alpha, beta, 1, 2, 1, 1 ); //проостой структуры
        //g.mygen ( A, A_inv, n, alpha, beta, 0, 0, 2, 1 ); //жорданова клетка

        double[] X = new double[n];
        for (int i = 0; i < n; i++) X[i] = Math.sin(i);

        double[] b = new double[n];
        for (int i = 0; i < n; i++) {
            b[i] = 0;
            for (int j = 0; j < n; j++) {
                b[i] += X[j] * A[i][j];
            }
        }

        double[] x = new double[n];
        LUTask.solve(A, n, b, x);

        double[] z = new double[n];
        for (int i = 0; i < n; i++) {
            z[i] = x[i] - X[i];
        }

        double[] r = new double[n];
        for (int i = 0; i < n; i++) {
            r[i] = 0;
            for (int j = 0; j < n; j++) {
                r[i] += A[i][j] * x[j];
            }
            r[i] -= b[i];
        }

        //System.out.println("\na:");
        //g.print_matr(A, n);

        //System.out.println("\na_inv:");
        //g.print_matr(A_inv, n);

        System.out.print("\nalpha = ");
        System.out.println(alpha);

        System.out.print("\nbeta = ");
        System.out.println(beta);

        double A_norm = g.matr_inf_norm(A, n);
        System.out.print("\n||A|| = ");
        System.out.format("%.1e\n", A_norm);

        double A_inv_norm = g.matr_inf_norm(A_inv, n);
        System.out.print("\n||A_inv|| = ");
        System.out.format("%.1e\n", A_inv_norm);

        System.out.print("\nv(A) = ");
        System.out.format("%.1e\n", A_norm * A_inv_norm);

        double z_norm = g.vec_inf_norm(z, n);
        System.out.print("\n||z|| = ");
        System.out.format("%.1e\n", z_norm);

        double otnosit_oshibka = z_norm/g.vec_inf_norm(X, n);
        System.out.print("\nc = ");
        System.out.format("%.1e\n", otnosit_oshibka);

        double r_norm = g.vec_inf_norm(r, n);
        System.out.print("\n||r|| = ");
        System.out.format("%.1e\n", r_norm);

        System.out.print("\np = ");
        System.out.format("%.1e\n", r_norm/g.vec_inf_norm(b, n));

        System.out.print("\nc > 0.1? ");
        System.out.println(otnosit_oshibka > 0.1);
    }
}
