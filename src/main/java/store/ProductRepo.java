package store;

import java.util.List;
import java.util.Optional;

public interface ProductRepo {

    void add(Product product);

    Optional<Product> getById(String id);

    void remove(String id);

    List<Product> getAll();

}
