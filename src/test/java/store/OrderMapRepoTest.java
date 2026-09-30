package store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OrderMapRepoTest {
    private OrderRepo orderRepo;
    private Order order1;
    private Order order2;
    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        orderRepo = new OrderMapRepo();
        product1 = new Product("1" , "Laptop", new BigDecimal("600"));
        product2 = new Product("2" , "Desktop" , new BigDecimal("1000"));
        order1 = new Order("order-1", Map.of(product1, 1));
        order2 = new Order("order-2", Map.of(product2, 4));
    }

    @Test
    void add_shouldAddNewOrderToTheRepoList_byGivenThisOrder() {
        assertEquals(List.of(), orderRepo.getAll());
        orderRepo.add(order1);

        assertEquals(List.of(order1), orderRepo.getAll());
    }

    @Test
    void getById_shouldReturnTheOrder_byGivenItsId() {
        orderRepo.add(order2);
        assertEquals(order2, orderRepo.getById(order2.id()));
    }

    @Test
    void remove_shouldDeleteTheOrderFromRepoList_byGivenItsId() {
        orderRepo.add(order2);
        orderRepo.remove(order2.id());
        assertNull( orderRepo.getById(order2.id()));

    }

    @Test
    void getAll() {
        orderRepo.add(order2);
        orderRepo.add(order1);
        assertEquals(List.of(order1, order2), orderRepo.getAll());
    }
}