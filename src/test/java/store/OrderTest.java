package store;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private final Product laptop = new Product("1", "Laptop", new BigDecimal("500"));
    private final Product mouse = new Product("3", "Mouse", new BigDecimal("20"));

    @Test
    void totalSum_withOneProduct_returnsPriceTimesQuantity() {
        Order order = new Order("o1", Map.of(laptop, 2));
        assertEquals(new BigDecimal("1000"), order.totalSum());

    }

    @Test
    void totalSum_returnsZero_byNoProducts() {
        Order order = new Order("o1", Map.of());

        assertEquals(BigDecimal.ZERO, order.totalSum());
    }
}