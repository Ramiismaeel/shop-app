package store;

import java.math.BigDecimal;
import java.util.List;

public record Order(String id, List<Product> products) {
    public Order {
        products = List.copyOf(products);
    }
    public BigDecimal totalSum() {
        BigDecimal sum =  BigDecimal.ZERO;
        for(Product product: products) {
            sum = sum.add(product.price());
        }
        return sum;
    }
}
