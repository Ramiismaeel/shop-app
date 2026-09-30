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

    @BeforeEach
    void setUp() {
        orderRepo = new OrderMapRepo();
        productRepo = new ProductListRepo();
        product1 = new Product("1" , "Laptop", new BigDecimal("600"));
        shop = new ShopService(productRepo, orderRepo);

    }

    @Test
    void placeOrderShouldPlaceOrderInShopAndReturnOrder_byGivenProductAndOrderAndQuantity() {

        productRepo.add(product1);

        ShopService shop = new ShopService(productRepo, orderRepo);
        order1 =  shop.placeOrder(Map.of(product1.id(), 2));
        assertEquals(order1.products(), Map.of(product1, 2));

    }

    @Test
    void placeOrderShouldReturnNull_byGivenNoneExistProductId() {
        productRepo.add(product1);
        shop = new ShopService(productRepo, orderRepo);
        order1 =  shop.placeOrder(Map.of("3", 2));

        assertNull(order1);
        assertTrue(orderRepo.getAll().isEmpty());

    }

    @Test
    void changeQuantity() {
        productRepo.add(product1);

        ShopService shop = new ShopService(productRepo, orderRepo);
        Order order =  shop.placeOrder(Map.of(product1.id(), 2));
        assertEquals(order.products(), Map.of(product1, 2));

        order = shop.changeQuantity(order.id(), product1.id(), 5);
        assertEquals(order.products(), Map.of(product1, 5));

    }
}