package me.toddydev.core.database.tables.orders;

import me.toddydev.core.Core;
import me.toddydev.core.cache.Caching;
import me.toddydev.core.model.order.Order;
import me.toddydev.core.model.order.gateway.type.GatewayType;
import me.toddydev.core.model.order.status.OrderStatus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Orders {

    public void create() {
        try {
            PreparedStatement ps = Core.getDatabase().getConnection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS orders(id INT(255) AUTO_INCREMENT, payerId VARCHAR(36), referenceId VARCHAR(255), paymentId VARCHAR(255), productId VARCHAR(255), gateway VARCHAR(255), status VARCHAR(255), code VARCHAR(255), cost DOUBLE,  PRIMARY KEY(id), FOREIGN KEY (payerId) REFERENCES users(uniqueId));"
            );
            ps.execute();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void create(Order order) {
        try {
            PreparedStatement ps = Core.getDatabase().getConnection().prepareStatement(
                    "INSERT INTO orders(payerId, referenceId, paymentId, productId, gateway, status, code, cost) VALUES(?, ?, ?, ?, ?, ?, ?, ?);"
            );
            ps.setString(1, order.getPayerId().toString());
            ps.setString(2, order.getReferenceId());
            ps.setString(3, order.getPaymentId());
            ps.setString(4, order.getProductId());
            ps.setString(5, order.getGateway().getType().name());
            ps.setString(6, order.getStatus().name());
            ps.setString(7, order.getCode());
            ps.setDouble(8, order.getCost());
            ps.execute();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Order order) {
        try {
            PreparedStatement ps = Core.getDatabase().getConnection().prepareStatement(
                    "UPDATE orders SET referenceId=?, paymentId=?, productId=?, gateway=?, status=?, code=?, cost=? WHERE payerId=? AND paymentId=?;"
            );
            ps.setString(1, order.getReferenceId());
            ps.setString(2, order.getPaymentId());
            ps.setString(3, order.getProductId());
            ps.setString(4, order.getGateway().getType().name());
            ps.setString(5, order.getStatus().name());
            ps.setString(6, order.getCode());
            ps.setDouble(7, order.getCost());
            ps.setString(8, order.getPayerId().toString());
            ps.setString(9, order.getPaymentId());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Order find(UUID uniqueId) {
        try {
            PreparedStatement ps = Core.getDatabase().getConnection().prepareStatement(
                    "SELECT * FROM orders WHERE payerId=?;"
            );
            ps.setString(1, uniqueId.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return Order.builder()
                        .payerId(uniqueId)
                        .referenceId(rs.getString("referenceId"))
                        .paymentId(rs.getString("paymentId"))
                        .productId(rs.getString("productId"))
                        .gateway(Caching.getGatewaysCache().find(GatewayType.find(rs.getString("gateway"))))
                        .code(rs.getString("code"))
                        .status(OrderStatus.valueOf(rs.getString("status")))
                        .cost(rs.getDouble("cost"))
                        .build();
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Order findByReferenceId(String referenceId) {
        try {
            PreparedStatement ps = Core.getDatabase().getConnection().prepareStatement(
                    "SELECT * FROM orders WHERE referenceId=?;"
            );
            ps.setString(1, referenceId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return Order.builder()
                        .payerId(UUID.fromString(rs.getString("payerId")))
                        .referenceId(rs.getString("referenceId"))
                        .paymentId(rs.getString("paymentId"))
                        .productId(rs.getString("productId"))
                        .gateway(Caching.getGatewaysCache().find(GatewayType.find(rs.getString("gateway"))))
                        .code(rs.getString("code"))
                        .status(OrderStatus.valueOf(rs.getString("status")))
                        .cost(rs.getDouble("cost"))
                        .build();
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<Order> findAll() {
        try {
            List<Order> orders = new ArrayList<>();
            PreparedStatement ps = Core.getDatabase().getConnection().prepareStatement(
                    "SELECT * FROM orders;"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order order = Order.builder()
                        .payerId(UUID.fromString(rs.getString("payerId")))
                        .referenceId(rs.getString("referenceId"))
                        .paymentId(rs.getString("paymentId"))
                        .productId(rs.getString("productId"))
                        .gateway(Caching.getGatewaysCache().find(GatewayType.find(rs.getString("gateway"))))
                        .code(rs.getString("code"))
                        .status(OrderStatus.valueOf(rs.getString("status")))
                        .cost(rs.getDouble("cost"))
                        .build();
                orders.add(order);
            }
            rs.close();
            ps.close();
            return orders;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Order> findAllByStatus(OrderStatus status) {
        try {
            List<Order> orders = new ArrayList<>();
            PreparedStatement ps = Core.getDatabase().getConnection().prepareStatement(
                    "SELECT * FROM orders WHERE status=?;"
            );
            ps.setString(1, status.name());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order order = Order.builder()
                        .payerId(UUID.fromString(rs.getString("payerId")))
                        .referenceId(rs.getString("referenceId"))
                        .paymentId(rs.getString("paymentId"))
                        .productId(rs.getString("productId"))
                        .gateway(Caching.getGatewaysCache().find(GatewayType.find(rs.getString("gateway"))))
                        .code(rs.getString("code"))
                        .status(OrderStatus.valueOf(rs.getString("status")))
                        .cost(rs.getDouble("cost"))
                        .build();
                orders.add(order);
            }
            rs.close();
            ps.close();
            return orders;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

