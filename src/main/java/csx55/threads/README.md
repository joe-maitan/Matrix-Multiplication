# How to run the main program!
The main file for this program is MatrixThreads.java

Again, this was previously built using Gradle. I am figuring out how to download the Gradle dependency and get updated commands.

## Commands to remember:
```
gradle build
```

The main method of this program takes in three arguments. The desired size of the thread pool, size of the matrix, and our seed.
This seed was mainly used to ensure we were getting the correct sum for each matrix.
```
java -cp build/classes/java/main/ csx55.threads.MatrixThreads 8 3000 31459
```
