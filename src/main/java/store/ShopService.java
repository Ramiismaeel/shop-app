package store;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShopService {
    private final ProductRepo productRepo;
    private final OrderRepo orderRepo;

    public ShopService(ProductRepo productRepo, OrderRepo orderRepo) {
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
    }

    @Override
    public String toString() {
        return "ShopService{" +
                "productRepo=" + productRepo +
                ", orderRepo=" + orderRepo +
                '}';
    }

    public Order placeOrder(List<String> productIds) {
        if(productIds == null || productIds.isEmpty()) {
            return null;
        }
        List<Product> orderedProducts = new ArrayList<>();
        for(String productId : productIds) {
            Product product = productRepo.getById(productId);
            if(product == null) {
                System.out.println("this product with ID " + productId + " is not found");
                return null;
            }
            orderedProducts.add(product);
        }
        Order order = new Order(UUID.randomUUID().toString(), orderedProducts);
        orderRepo.add(order);
        return order;

    }
}
