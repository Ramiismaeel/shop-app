package store;

import java.util.*;

public class ProductMapRepo implements ProductRepo{

    private final Map<String, Product> products = new HashMap<>();

    @Override
    public void add(Product product) {
        this.products.put(product.id(), product);
    }

    @Override
    public Optional<Product> getById(String id) {
      return Optional.ofNullable(this.products.get(id));

    }

    @Override
    public void remove(String id) {
        this.products.remove(id);
    }

    @Override
    public List<Product> getAll() {
        return new ArrayList<>(products.values());
    }

    @Override
    public String toString() {
        return "ProductMapRepo{" +
                "products=" + products +
                '}';
    }
}
