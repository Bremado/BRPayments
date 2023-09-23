package me.toddydev.core.player.order.gateways;

public enum Gateway {

    MERCADO_PAGO,
    PIC_PAY,
    STRIPE,
    PAG_SEGURO;

    public static Gateway find(String value) {
        for (Gateway gateway : values()) {
            if (gateway.name().equalsIgnoreCase(value)) {
                return gateway;
            }
        }
        return null;
    }

}
