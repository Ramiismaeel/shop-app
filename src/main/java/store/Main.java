package store;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Product p1 = new Product("1", "Laptop", new BigDecimal("500"));
        Product p2 = new Product("2", "Desktop" , new BigDecimal("700"));
        Product p3 = new Product("3", "Mouse" , new BigDecimal("20"));
        Product p4 = new Product("4" , "Keyboard", new BigDecimal("80"));

        ProductRepo productRepo = new ProductMapRepo();
        OrderRepo orderRepo = new OrderMapRepo();
        Inventory inventory = new Inventory();
        IdService idService = new IdService();


        List.of(p1, p2, p3 , p4).forEach(p-> {
            inventory.add(p.id(), 10);
            productRepo.add(p);
        });

        ShopService shop = new ShopService(productRepo, orderRepo, inventory, idService);

        Order order1 = shop.placeOrder(Map.of("1", 2, "2", 1));
        Order order2 = shop.placeOrder(Map.of("3", 2, "4", 1));
        Order order3 = shop.placeOrder(Map.of("1", 1, "4", 1));
        Order order4 = shop.placeOrder(Map.of("4", 1, "2", 1));
        System.out.println(shop);

        System.out.println(orderRepo.getAll());
        System.out.println(order1.totalSum());

        Order updatedOrder = shop.changeQuantity(order1.id(), "1", 3);
        System.out.println(updatedOrder.totalSum());

        for(Order order: shop.getAll()) {
            System.out.println(order.id() + " | " + order.status()+ " | " + order.createdAt().toString() + " | " + order.totalSum());
        }


    }
}
