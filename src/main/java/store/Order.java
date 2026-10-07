package store;

import java.math.BigDecimal;
import java.util.Map;

public record Order(String id, Map<Product, Integer> products, OrderStatus status) {

    public Order {
        products = Map.copyOf(products);
    }

    public Order(String id, Map<Product, Integer> products) {
        this(id, Map.copyOf(products), OrderStatus.PROCESSING);
    }
    public BigDecimal totalSum() {
        BigDecimal sum =  BigDecimal.ZERO;
        for(Map.Entry<Product, Integer> entry: products.entrySet()) {
            BigDecimal productItem = entry.getKey().price().multiply(BigDecimal.valueOf(entry.getValue()));
            sum = sum.add(productItem);
        }
        return sum;
    }
}
