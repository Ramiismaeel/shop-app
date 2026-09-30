package store;

import java.util.*;

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

    public Order placeOrder(Map<String, Integer> idsWithQuantity) {
        if(idsWithQuantity == null || idsWithQuantity.isEmpty()) {
            return null;
        }

        Map<Product, Integer> items = new HashMap<>();

        for(Map.Entry<String, Integer> item: idsWithQuantity.entrySet()) {
            Product product = productRepo.getById(item.getKey());
            if(product == null) {
                System.out.println("this product with ID " + item.getKey() + " is not found");
                return null;
            }
            items.put(product, item.getValue());


        }
        Order order = new Order(UUID.randomUUID().toString(), items);
        orderRepo.add(order);
        return order;
    }

    public Order changeQuantity(String orderId, String productId, int newQuantity) {

        Order targetOrder = orderRepo.getById(orderId);
        Product targetProduct = productRepo.getById(productId);
        if(targetOrder == null || targetProduct ==null || !targetOrder.products().containsKey(targetProduct)) {
            System.out.println("Product or order not found");
            return null;
        }
        Map<Product, Integer> items = new HashMap<>(targetOrder.products());
        items.put(targetProduct, newQuantity);

        Order newOrder = new Order(orderId, items);
        orderRepo.remove(orderId);
        orderRepo.add(newOrder);
        return newOrder;



    }
}
