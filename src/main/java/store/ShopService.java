package store;

import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.*;

@RequiredArgsConstructor
public class ShopService {
    private final ProductRepo productRepo;
    private final OrderRepo orderRepo;
    private final Inventory inventory;
    private final IdService idService;

    @Override
    public String toString() {
        return "ShopService{" +
                "productRepo=" + productRepo +
                ", orderRepo=" + orderRepo +
                '}';
    }

    public Order placeOrder(Map<String, Integer> idsWithQuantity) throws ProductNotFoundException {
        if(idsWithQuantity == null || idsWithQuantity.isEmpty()) {
            return null;
        }

        Map<Product, Integer> items = new HashMap<>();

        for(Map.Entry<String, Integer> item: idsWithQuantity.entrySet()) {
            Optional<Product> product = productRepo.getById(item.getKey());
            if(product.isEmpty()) {
                throw new ProductNotFoundException("Product with ID: " + item.getKey() + " is not found.");
            }
            items.put(product.get(), item.getValue());

        }

        for(Map.Entry<Product, Integer> item: items.entrySet()) {
            inventory.reduce(item.getKey().id(), item.getValue());

        }
        Order order = new Order(idService.generateId().toString(), items, OrderStatus.PROCESSING, Instant.now());
        orderRepo.add(order);
        return order;
    }

    public Order changeQuantity(String orderId, String productId, int newQuantity) {

        Order targetOrder = orderRepo.getById(orderId);
        Optional<Product> targetOpt = productRepo.getById(productId);
        if(targetOrder == null  || targetOpt.isEmpty() || !targetOrder.products().containsKey(targetOpt.get())) {
            System.out.println("Product or order not found");
            return null;
        }
        Product targetProduct = targetOpt.get();
        Map<Product, Integer> items = new HashMap<>(targetOrder.products());


        int oldQuantity = targetOrder.products().get(targetProduct);
        int diff = newQuantity - oldQuantity;

        if(diff> 0) {
            if(!inventory.isAvailable(targetProduct.id(), diff)) {
                System.out.println("No enough stock for product " + productId);
                return null;
            }
            else {
                inventory.reduce(targetProduct.id(), diff);
            }
        } else if(diff < 0) {
            inventory.add(targetProduct.id(), -diff);
        }
        items.put(targetProduct, newQuantity);
        Order newOrder = new Order(orderId, items, targetOrder.status(), targetOrder.createdAt());
        orderRepo.remove(orderId);
        orderRepo.add(newOrder);
        return newOrder;

    }

    public List<Order> findOrderByStatus(OrderStatus status) {
        return orderRepo.getAll().stream()
                .filter(o-> o.status().equals(status)).toList();
    }
    public List<Order> getAll() {
        return orderRepo.getAll();
    }

    public Order changeStatus(String orderId, OrderStatus newStatus) {
        Order targetOrder = orderRepo.getById(orderId);
        if(targetOrder == null) {
            System.out.println("There is no order with id: " + orderId);
            return null;
        }
        Order updatedOrder = targetOrder.withStatus(newStatus);
        orderRepo.remove(orderId);
        orderRepo.add(updatedOrder);
        return updatedOrder;
    }

    public Map<OrderStatus, Order> getOldestOrderPerStatus() {
        Map<OrderStatus, Order> oldestOrdersPerStatus = new HashMap<>();
               for(OrderStatus s: OrderStatus.values()){
                   findOrderByStatus(s).stream()
                           .min(Comparator.comparing(Order::createdAt))
                           .ifPresent(o -> oldestOrdersPerStatus.put(s, o));
               }
        return oldestOrdersPerStatus;
    }
}
