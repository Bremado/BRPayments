package me.toddydev.core.player.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.toddydev.core.player.order.gateways.Gateway;
import me.toddydev.core.player.order.status.OrderStatus;

import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private UUID payerId;
    private String referenceId;

    private Gateway gateway;
    private OrderStatus status;

    private double cost;
}
