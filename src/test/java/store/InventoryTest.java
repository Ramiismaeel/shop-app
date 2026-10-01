package store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {
    private Inventory inventory;
    Product product = new Product("1", "Laptop", new BigDecimal("500"));
    @BeforeEach
    void setUp() {
        inventory = new Inventory();
    }

    @Test
    void addProduct_shouldReturn4Products_byGiven4Products() {

        inventory.add(product.id(), 4);
        assertEquals(4, inventory.getQuantity("1"));
    }

    @Test
    void reduce_shouldReturn3_byGiven4AndReduce1() {
        inventory.add(product.id(), 4);
        inventory.reduce(product.id(), 1);
        assertEquals(3, inventory.getQuantity("1"));
    }

    @Test
    void isAvailable_shouldReturnTrue_by4Quantity() {
        inventory.add(product.id(), 4);
        assertTrue(inventory.isAvailable("1", 4));
    }
    @Test
    void isAvailable_shouldReturnFalse_by5Quantity() {
        inventory.add(product.id(), 4);
        assertFalse(inventory.isAvailable("1", 5));
    }
    @Test
    void getQuantity_shouldReturn0_byUnknownProductId() {
        assertEquals(0, inventory.getQuantity("unknown"));
    }
}