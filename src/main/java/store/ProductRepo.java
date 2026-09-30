package store;

import java.util.List;

public interface ProductRepo {

    void add(Product product);

    Product getById(String id);

    void remove(String id);

    List<Product> getAll();

}
