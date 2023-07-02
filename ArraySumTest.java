package cen3024;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ArraySumTest {

    @Test
    public void testArraySum() {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        long parallelSum = HW8.ParallelSum(array);
        long singleThreadSum = HW8.SingleTsum(array);

        Assertions.assertEquals(55, parallelSum);
        Assertions.assertEquals(55, singleThreadSum);
    }
}
 