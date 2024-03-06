package csx55.threads;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ThreadPool {

    private Thread[] threads; /* used to hold our threads */
    private static volatile ConcurrentLinkedQueue<Job> jobQueue = new ConcurrentLinkedQueue<>();

    public ThreadPool(int size) {
        threads = new Thread[size];

        for (int i = 0; i < threads.length; ++i) {
            threads[i] = new Thread(this::workerThread, "" + i);
            threads[i].setDaemon(true);
            threads[i].start();
        } // End for loop
    } // End ThreadPool(int) constructor
 
    public void addJob(Job newJob) {
        jobQueue.add(newJob);
    } // End addJob(j) method

    public boolean isJobQueueEmpty() {
        return jobQueue.isEmpty();
    } // End isJobQueueEmpty() method
    
    private void workerThread() { /* the code the thread will run */
        while (true) {
            Job j;

            // try {
            //     // System.out.println(Thread.currentThread().getName());
            //     j = jobQueue.take();
            // } catch (InterruptedException err) {
            //     break;
            // }
            
            j = jobQueue.poll();

            if (j != null) {
                int product = 0;
                int[][] m1 = j.getMatrixOne();
                int[][] m2 = j.getMatrixTwo();
                int[][] productMatrix = j.getProductMatrix();
    
                int[] m1Row = m1[j.getRow()];
                int[] m2Col = m2[j.getCol()];
    
                for (int pos = 0; pos < m1.length; ++pos) {
                    // This works with a second matrix that is not transposed
                    // product += m1[j.getRow()][pos] * m2[pos][j.getCol()];
    
                    // Case where m2 is transposed
                    product += m1Row[pos] * m2Col[pos];
                } // End outside for loop
    
                productMatrix[j.getRow()][j.getCol()] = product; 
            } else {
                Thread.onSpinWait();
            } // End if-else statement

        } // End while loop

    } // End workerThread() method

} // End ThreadPool class
