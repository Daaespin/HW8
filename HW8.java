package cen3024;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HW8 {
    private static final int ARRAY_SIZE = 200_000_000;
    private static final int THREAD_COUNT = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) {
        int[] array = generateRandomArray(ARRAY_SIZE);

        long parallelStart = System.currentTimeMillis();
        long parallelSum = ParallelSum(array);
        long parallelEnd = System.currentTimeMillis();

        long singleTstart = System.currentTimeMillis();
        long singleTsum = SingleTsum(array);
        long singleTEnd = System.currentTimeMillis();

        	System.out.println("parallel array sum: " + parallelSum);
        	System.out.println("parallel array sum: " + (parallelEnd - parallelStart) + "ms");

        	System.out.println("single thread sum: " + singleTsum);
        	System.out.println("single thread sum: " + (singleTEnd - singleTstart) + "ms");
    }

    private static int[] generateRandomArray(int size) {
        int[] array = new int[size];
        Random random = new Random();
        
        for (int i=0;i<size;i++) {
            array[i] = random.nextInt(10) + 1; 
        }
        return array;
    }

    public static long ParallelSum(int[] array) {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        int size = array.length/THREAD_COUNT;
        long[] partialSums = new long[THREAD_COUNT];

        for (int i=0;i<THREAD_COUNT;i++) {
            final int index = i; 

            int start = i * size;
            int end = (i==THREAD_COUNT-1) ? array.length : start + size;

            executorService.execute(() -> {
                long sum = 0;
                for (int j=start;j<end;j++) {
                    sum += array[j];
                }
                partialSums[index] = sum;
            });
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        long totalSum = 0;
        for (long partialSum : partialSums) {
            totalSum += partialSum;
        }
        return totalSum;
    }

    public static long SingleTsum(int[] array) {
        long sum = 0;
        for (int i : array) {
            sum += i;
        }
        return sum;
    }
}
