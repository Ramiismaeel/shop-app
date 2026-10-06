package store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    private ProductRepo productRepo;
    private OrderRepo orderRepo;
    private Order order1;
    private Product product1;
    private ShopService shop;
    private Inventory inventory;

    @BeforeEach
    void setUp() {
        orderRepo = new OrderMapRepo();
        productRepo = new ProductListRepo();
        inventory = new Inventory();
        product1 = new Product("1" , "Laptop", new BigDecimal("600"));
        inventory.add(product1.id(), 7);
        shop = new ShopService(productRepo, orderRepo, inventory);

    }

    @Test
    void placeOrderShouldPlaceOrderInShopAndReturnOrder_byGivenProductAndOrderAndQuantity() {

        productRepo.add(product1);
        inventory = new Inventory();
        inventory.add(product1.id(), 4);
        ShopService shop = new ShopService(productRepo, orderRepo, inventory);
        order1 =  shop.placeOrder(Map.of(product1.id(), 2));
        assertEquals(order1.products(), Map.of(product1, 2));

    }

    @Test
    void placeOrderShouldReturnNull_byGivenNoneExistProductId() {
        productRepo.add(product1);
        inventory = new Inventory();
        inventory.add(product1.id(), 3);
        shop = new ShopService(productRepo, orderRepo, inventory);
        order1 =  shop.placeOrder(Map.of("3", 2));

        assertNull(order1);
        assertTrue(orderRepo.getAll().isEmpty());

    }

    @Test
    void changeQuantity_shouldReturnNull_byEmptyInventory() {
        productRepo.add(product1);
        inventory = new Inventory();
        inventory.add(product1.id(), 3);
        ShopService shop = new ShopService(productRepo, orderRepo, inventory);
        Order order =  shop.placeOrder(Map.of(product1.id(), 2));
        assertEquals(order.products(), Map.of(product1, 2));

        order = shop.changeQuantity(order.id(), product1.id(), 5);
        assertNull( order);

    }

    @Test
    void changeQuantity_shouldReturnOrder_byFullInventory() {
        productRepo.add(product1);
        inventory = new Inventory();
        inventory.add(product1.id(), 9);
        ShopService shop = new ShopService(productRepo, orderRepo, inventory);
        Order order =  shop.placeOrder(Map.of(product1.id(), 2));
        assertEquals(order.products(), Map.of(product1, 2));

        order = shop.changeQuantity(order.id(), product1.id(), 5);
        assertEquals(order.products(), Map.of(product1, 5));

    }

    @Test
    void findOrderByStatus_shouldReturn2Orders_byGivenPROCESSING() {
        productRepo.add(product1);
        inventory = new Inventory();
        inventory.add(product1.id(), 6);
        shop = new ShopService(productRepo, orderRepo, inventory);
        shop.placeOrder(Map.of("1", 2));
        shop.placeOrder(Map.of("1", 1));
        shop.placeOrder(Map.of("1", 2));

        assertEquals(3,shop.findOrderByStatus(OrderStatus.PROCESSING).size());

    }
}