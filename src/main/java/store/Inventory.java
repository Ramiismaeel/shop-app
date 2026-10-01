package store;

import java.util.HashMap;
import java.util.Map;

public class Inventory {

    private final Map<String, Integer> products = new HashMap<>();

    public void add(String productId, int quantity) {
        int current = products.getOrDefault(productId, 0);
        products.put(productId,  current +quantity);
    }
    public void reduce(String productId, int quantity) {
        int current = products.getOrDefault(productId, 0);
        if(quantity> current) {
            throw new IllegalArgumentException("there are only" + current + " items in stock" );
        } else {
            products.put(productId, current - quantity);
        }
    }
    public boolean isAvailable(String productId, int quantity) {
        int current = products.getOrDefault(productId, 0);
        return current>= quantity;
    }
    public int getQuantity(String productId) {
        return products.getOrDefault(productId, 0);
    }

}
