package store;

import java.util.List;

public interface OrderRepo {

    void add(Order order);

    Order getById(String id);

    void remove(String id);

    List<Order> getAll();
}
