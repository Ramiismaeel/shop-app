package store;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Product p1 = new Product("1", "Laptop", new BigDecimal("500"));
        Product p2 = new Product("2", "Desktop" , new BigDecimal("700"));
        Product p3 = new Product("3", "Mouse" , new BigDecimal("20"));
        Product p4 = new Product("4" , "Keyboard", new BigDecimal("80"));

        ProductRepo productRepo = new ProductMapRepo();
        productRepo.add(p1);
        productRepo.add(p2);

        OrderRepo orderRepo = new OrderMapRepo();

        ShopService shop = new ShopService(productRepo, orderRepo);
        Order order = shop.placeOrder(List.of("1", "2"));
        System.out.println(shop);

        System.out.println(orderRepo.getAll());
        System.out.println(order.totalSum());


    }
}
