package store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductListRepoTest {
    private ProductRepo productRepo;
    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        productRepo = new ProductListRepo();
        product1 = new Product("1" , "Laptop", new BigDecimal("600"));
        product2 = new Product("2" , "Desktop" , new BigDecimal("1000"));
    }

    @Test
    void addNewProduct_shouldReturnIt_inTheProductList() {
        assertEquals(0, productRepo.getAll().size());

        productRepo.add(product1);
        assertEquals(1, productRepo.getAll().size());
        assertEquals(Optional.of(product1), productRepo.getById(product1.id()));
    }

    @Test
    void getById_shouldReturn_theTargetProduct() {
        productRepo.add(product2);
        assertEquals(Optional.of(product2), productRepo.getById(product2.id()));
    }

    @Test
    void remove_shouldRemoveTheProductFromTheList_byGivenId() {
        productRepo.add(product2);
        productRepo.remove(product2.id());
        assertTrue(productRepo.getById(product2.id()).isEmpty());
    }

    @Test
    void getAll_shouldReturn_allProducts() {
        assertEquals(List.of(), productRepo.getAll());
        productRepo.add(product1);
        assertEquals(List.of(product1) , productRepo.getAll());
    }

}