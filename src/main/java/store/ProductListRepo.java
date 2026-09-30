package store;

import java.util.ArrayList;
import java.util.List;

public class ProductListRepo implements ProductRepo{
    private final List<Product> products = new ArrayList<>();

    @Override
    public void add(Product product) {
        products.add(product);

    }

    @Override
    public Product getById(String id) {
        for(Product product: products) {
            if (product.id().equals(id)) {
                return product;
            }
        }
        return null;
    }

    @Override
    public void remove(String id) {
        products.removeIf(product -> product.id().equals(id));

    }

    @Override
    public List<Product> getAll() {
        return new ArrayList<>(products);
    }

    @Override
    public String toString() {
        return "ProductListRepo{" +
                "products=" + products +
                '}';
    }
}
