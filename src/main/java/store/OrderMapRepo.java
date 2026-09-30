package store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderMapRepo implements OrderRepo{

    private final Map<String, Order> orders = new HashMap<>();
    @Override
    public void add(Order order) {
        orders.put(order.id(), order);
    }

    @Override
    public Order getById(String id) {
        return orders.get(id);
    }

    @Override
    public void remove(String id) {
        orders.remove(id);

    }

    @Override
    public List<Order> getAll() {
        return new ArrayList<>(orders.values());
    }

    @Override
    public String toString() {
        return "OrderMapRepo{" +
                "orders=" + orders +
                '}';
    }
}
