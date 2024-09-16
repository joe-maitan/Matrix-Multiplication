# How to run the main program!
You must have Java version 8 or higher to build and run the program.

## Commands to remember:
```
module load dev/gradle
```
```
gradle build
```

```
gradle clean
```

## Execution of the main program:
The main file for this program is MatrixThreads.java


The main method of this program takes in three arguments. The desired size of the thread pool, size of the matrix, and our seed.
This seed was mainly used to ensure we were getting the correct sum for each matrix.
```
java -cp build/classes/java/main/ csx55.threads.MatrixThreads 8 3000 31459
```
