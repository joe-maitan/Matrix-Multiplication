package csx55.threads;

import java.util.Random;

public class Matrix {

    private char name;
    public int[][] data;
    private double timeToComputeSum;

    public Matrix() {} // End default constructor

    public Matrix(char name, int dimensions) {
        this.name = name;
        this.data = new int[dimensions][dimensions];
    } // End Matrix() constructor

    public Matrix(char name, int dimensions, Random gen) {
        this(name, dimensions);

        for (int column = 0; column < data.length; ++column) {
            for (int row = 0; row < data.length; ++row) {
                // int randomValue = gen.nextInt(10) + 1;
                int randomValue = 1000 - gen.nextInt(2000);
                data[column][row] = randomValue;
            } // End nested for loop
        } // End for loop

        System.out.println("Sum of the elements in input matrix " + getName() + " = " + sumOfMatrixElements(data, dimensions));
    } // End Matrix() constructor

    public char getName() {
        return this.name;
    } // End getName() method

    public int[][] getData() {
        return this.data;
    } // End getData() method

    public void setData(int[][] d) {
        this.data = d;
    } // End setData() method

    public double getTime() {
        return this.timeToComputeSum;
    } // End getTime() method

    public String toString() {
        String output = "";

        for (int i = 0; i < this.data.length; ++i) {
            output += "[";
            for (int j = 0; j < this.data[0].length; ++j) {
                if (j == this.data[0].length - 1) {
                    output += data[i][j];
                } else {
                    output += data[i][j] + " ";
                }
            }
            output += "]\n";
        }

        return output;
    } // End toString() method

    public int[][] transposeMatrix(int[][] originalMatrix) {
        int[][] transposedMatrix = new int[originalMatrix.length][originalMatrix.length];

        for (int row = 0; row < transposedMatrix.length; ++row) {
            for (int col = 0; col < transposedMatrix.length; ++col) {
                transposedMatrix[row][col] = originalMatrix[col][row];
            } // End nested for loop
        } // End for loop

        return transposedMatrix;
    } // End transposeMatrix(int[][]) method

    public long sumOfMatrixElements(int[][] arr, int dimensions) { /* DO NOT SYNCHRONIZE */
        long sum = 0;
        
        for (int row = 0; row < dimensions; ++row) {
            for (int column = 0; column < dimensions; ++column) {
                sum += (long) arr[row][column];
            } // End nested for loop
        } // End for loop

        return sum;
    } // End sumOfMatrixElements(int[][], int) method

    public void getColumn(int[][] array, int columnIndex, int[] column) {
        for (int i = 0; i < array.length; i++) {
            column[i] = array[i][columnIndex];
        } // End for loop
    } // End getColumn() method

    public int[][] multiplyMatrices(Matrix one, Matrix two, int desiredDimensions, ThreadPool pool) {
        long startTime;
        long endTime;
        
        int[][] arr_one = one.getData();
        int[][] arr_two = two.getData();

        arr_two = transposeMatrix(arr_two);

        int[][] productArr = new int[desiredDimensions][desiredDimensions];
        
        // int[] rowArr = new int[desiredDimensions];
        // int[] columnArr = new int[desiredDimensions];

        startTime = System.nanoTime();
        for (int row = 0; row < desiredDimensions; ++row) {
            // rowArr = arr_one[row];
            for (int column = 0; column < desiredDimensions; ++column) {
                // getColumn(arr_two, column, columnArr);

                Job newJob = new Job(arr_one, arr_two, productArr, row, column);
                pool.addJob(newJob);
                // pool.dotProduct(rowArr, columnArr);
                // productArr[row][column] = pool.getProduct();
            } // End for loop
        } // End for loop

        while (!pool.isJobQueueEmpty()) { 
            Thread.onSpinWait(); /* spin and wait for jobs to be done */ 
        }

        endTime = System.nanoTime();

        double totalTime = (endTime - startTime) / 1e9;

        System.out.println("Calculation of matrix " + this.getName() + " (Product of " + one.getName() + " and " + two.getName() + ") complete - sum of the elements in " + this.getName() + " is: " + sumOfMatrixElements(productArr, desiredDimensions));
        String timeToCompute = String.format("Time to compute matrix " + this.getName() + ": %.3f s", totalTime);
        System.out.println(timeToCompute);
        System.out.println();

        this.timeToComputeSum = totalTime;

        return productArr;
    } // End multiplyMatrices() method

} // End Matrix class