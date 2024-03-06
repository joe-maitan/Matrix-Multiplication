package csx55.threads;

import java.util.Random;

public class MatrixThreads {

    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println("Invalid # of arguments");
            System.exit(1);
        } // End if statement

        final int THREAD_POOL_SIZE = Integer.parseInt(args[0]);
        final int MATRIX_DIMENSIONS = Integer.parseInt(args[1]);
        final int SEED = Integer.parseInt(args[2]);

        if (THREAD_POOL_SIZE <= 0 || MATRIX_DIMENSIONS <= 1 || SEED < 0) {
            System.err.println("Invalid entry for arguments");
            System.exit(1);
        } // End if statement

        // Could pass in the THREAD_POOL_SIZE argument or could pass in Runtime.getRuntime().availableProcessors()
        ThreadPool pool = new ThreadPool(THREAD_POOL_SIZE);

        System.out.println("Dimensionality of the square matrices is: " + MATRIX_DIMENSIONS);
        System.out.println("The thread pool size has been initialized to: " + THREAD_POOL_SIZE);
        System.out.println();

        /* These are our four matrices used to calculate X and Y 
        *   X = A * B 
        *   and 
        *   Y = C * D
        */
        Random numberGenerator = new Random(SEED);
        Matrix a = new Matrix('A', MATRIX_DIMENSIONS, numberGenerator);
        Matrix b = new Matrix('B', MATRIX_DIMENSIONS, numberGenerator);
        Matrix c = new Matrix('C', MATRIX_DIMENSIONS, numberGenerator);
        Matrix d = new Matrix('D', MATRIX_DIMENSIONS, numberGenerator);

        System.out.println();

        Matrix x = new Matrix('X', MATRIX_DIMENSIONS);
        Matrix y = new Matrix('Y', MATRIX_DIMENSIONS);
        Matrix z = new Matrix('Z', MATRIX_DIMENSIONS);

        // System.out.println("Calculating Matrix X");
        // System.out.println(a.toString());
        // System.out.println(b.toString());
        x.data = x.multiplyMatrices(a, b, MATRIX_DIMENSIONS, pool);
        // System.out.println(x.toString());

        // System.out.println("Calculating Matrix Y");
        // System.out.println(c.toString());
        // System.out.println(d.toString());
        y.data = y.multiplyMatrices(c, d, MATRIX_DIMENSIONS, pool);
        // System.out.println(y.toString());

        // System.out.println("Calculating Matrix Z");
        // System.out.println(x.toString());
        // System.out.println(y.toString());
        z.data = z.multiplyMatrices(x, y, MATRIX_DIMENSIONS, pool);
        // System.out.println(z.toString());
        
        double cumulativeTime = x.getTime() + y.getTime() + z.getTime();
       
        String output = String.format("Cumulative time to compute matrices X, Y, and Z using a thread pool of size = %d is : %.3f s", THREAD_POOL_SIZE, cumulativeTime);
        System.out.println(output);
    } // End main method
    
} // End MatrixThreads class
