package store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    private ProductRepo productRepo;
    private OrderRepo orderRepo;
    private Product product1;
    private ShopService shop;
    private Inventory inventory;
    private IdService idService;

    @BeforeEach
    void setUp() {
        orderRepo = new OrderMapRepo();
        productRepo = new ProductListRepo();
        inventory = new Inventory();
        product1 = new Product("1" , "Laptop", new BigDecimal("600"));
        inventory.add(product1.id(), 7);
        idService = new IdService();
        shop = new ShopService(productRepo, orderRepo, inventory, idService);

    }

    @Test
    void placeOrderShouldPlaceOrderInShopAndReturnOrder_byGivenProductAndOrderAndQuantity() throws ProductNotFoundException {

        productRepo.add(product1);
        inventory = new Inventory();
        inventory.add(product1.id(), 4);
        ShopService shop = new ShopService(productRepo, orderRepo, inventory, idService);

        Instant before = Instant.now();
        Order order1 =  shop.placeOrder(Map.of(product1.id(), 2));
        Instant after = Instant.now();

        assertEquals(order1.products(), Map.of(product1, 2));
        assertEquals(Map.of(product1, 2), order1.products());
        assertFalse(order1.createdAt().isBefore(before));
        assertFalse(order1.createdAt().isAfter(after));

    }

    @Test
    void placeOrderShouldThrowException_byGivenNoneExistProductId() {
        productRepo.add(product1);
        inventory = new Inventory();
        inventory.add(product1.id(), 3);
        shop = new ShopService(productRepo, orderRepo, inventory, idService);
        assertThrows(ProductNotFoundException.class, ()-> shop.placeOrder(Map.of("3", 2)));
        assertTrue(orderRepo.getAll().isEmpty());

    }

    @Test
    void changeQuantity_shouldReturnNull_byEmptyInventory() throws ProductNotFoundException {
        productRepo.add(product1);
        inventory = new Inventory();
        inventory.add(product1.id(), 3);
        ShopService shop = new ShopService(productRepo, orderRepo, inventory, idService);
        Order order =  shop.placeOrder(Map.of(product1.id(), 2));
        assertEquals(order.products(), Map.of(product1, 2));

        order = shop.changeQuantity(order.id(), product1.id(), 5);
        assertNull( order);

    }

    @Test
    void changeQuantity_shouldReturnOrder_byFullInventory() throws ProductNotFoundException {
        productRepo.add(product1);
        inventory = new Inventory();
        inventory.add(product1.id(), 9);
        ShopService shop = new ShopService(productRepo, orderRepo, inventory, idService);
        Order order =  shop.placeOrder(Map.of(product1.id(), 2));
        assertEquals(order.products(), Map.of(product1, 2));

        order = shop.changeQuantity(order.id(), product1.id(), 5);
        assertEquals(order.products(), Map.of(product1, 5));

    }

    @Test
    void findOrderByStatus_shouldReturn2Orders_byGivenPROCESSING() throws ProductNotFoundException {
        productRepo.add(product1);
        inventory = new Inventory();
        inventory.add(product1.id(), 6);
        shop = new ShopService(productRepo, orderRepo, inventory, idService);
        shop.placeOrder(Map.of("1", 2));
        shop.placeOrder(Map.of("1", 1));
        shop.placeOrder(Map.of("1", 2));

        assertEquals(3,shop.findOrderByStatus(OrderStatus.PROCESSING).size());

    }

    @Test
    void updateOrder_shouldReturnOrderWithStatusCOMPLETED_byGiven_OrderStatus_COMPLETED() {
        productRepo.add(product1);
        inventory = new Inventory();
        inventory.add(product1.id(), 6);
        shop = new ShopService(productRepo, orderRepo, inventory, idService);
        Order order = shop.placeOrder(Map.of(product1.id(), 2));
        assertEquals(OrderStatus.PROCESSING, order.status());

        Order updatedOrder = shop.changeStatus(order.id(), OrderStatus.COMPLETED);
        assertEquals(OrderStatus.COMPLETED, updatedOrder.status());
        assertEquals(orderRepo.getById(order.id()).id(), updatedOrder.id());

    }

    @Test
    void getOldestOrderPerStatus_shouldReturnOldestOrder_forEachStatus() {
        Instant now = Instant.now();
        Order oldProcessing = new Order("o1", Map.of(product1, 1), OrderStatus.PROCESSING, now.minusSeconds(60));
        Order newProcessing = new Order("o2", Map.of(product1, 1), OrderStatus.PROCESSING, now);
        Order completed = new Order("o3", Map.of(product1, 1), OrderStatus.COMPLETED, now.minusSeconds(30));
        orderRepo.add(oldProcessing);
        orderRepo.add(newProcessing);
        orderRepo.add(completed);

        Map<OrderStatus, Order> result = shop.getOldestOrderPerStatus();

        assertEquals(2, result.size());
        assertEquals(oldProcessing, result.get(OrderStatus.PROCESSING));
        assertEquals(completed, result.get(OrderStatus.COMPLETED));
    }
}