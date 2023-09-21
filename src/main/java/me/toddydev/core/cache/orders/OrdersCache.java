package me.toddydev.core.cache.orders;

import me.toddydev.core.player.order.Order;
import me.toddydev.core.player.order.gateways.Gateway;
import me.toddydev.core.player.order.status.OrderStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrdersCache {

    private List<Order> orders = new ArrayList<>();

    public void add(Order order) {
        orders.add(order);
    }

    public void remove(Order order) {
        orders.remove(order);
    }

    public Order findByPayer(UUID uniqueId) {
        return orders.stream().filter(o -> o.getPayerId().equals(uniqueId)).findFirst().orElse(null);
    }

    public Order findByPayerAndGateway(UUID payerId, Gateway gateway) {
        return orders.stream().filter(o -> o.getPayerId().equals(payerId) && o.getGateway().equals(gateway)).findFirst().orElse(null);
    }

    public List<Order> findByStatus(OrderStatus status) {
        return orders.stream().filter(o -> o.getStatus().equals(status)).collect(Collectors.toList());
    }
}
