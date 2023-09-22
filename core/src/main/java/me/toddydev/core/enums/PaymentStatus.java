package me.toddydev.core.enums;

public enum PaymentStatus {

    WAITING("Aguardando"),
    PAID("Pago"),
    REFUNDED("Reembolsado"),
    CANCELLED("Cancelado");

    final String description;

    PaymentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
