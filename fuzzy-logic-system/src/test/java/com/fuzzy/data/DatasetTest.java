package com.fuzzy.data;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class DatasetTest {

    @Test
    void testFromResource() {
        Dataset dataset = Dataset.fromResource("test-dataset.txt");
        assertNotNull(dataset);
        assertEquals(3, dataset.size());

        DataPoint point1 = dataset.get(0);
        assertEquals(List.of(1.1, 2.2), point1.inputs());
        assertEquals(3.3, point1.output());

        DataPoint point2 = dataset.get(1);
        assertEquals(List.of(4.4, 5.5), point2.inputs());
        assertEquals(6.6, point2.output());

        DataPoint point3 = dataset.get(2);
        assertEquals(List.of(7.7, 8.8), point3.inputs());
        assertEquals(9.9, point3.output());
    }

    @Test
    void testFromResource_nonExistentFile() {
        assertThrows(IllegalArgumentException.class, () -> {
            Dataset.fromResource("non-existent-dataset.txt");
        });
    }
}
