package me.toddydev.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.toddydev.core.enums.Gateway;
import me.toddydev.core.enums.PaymentStatus;

@Getter @Setter
@AllArgsConstructor
public class Order {

    private String referenceId;

    private Gateway gateway;
    private PaymentStatus status;

    private double cost;

}
