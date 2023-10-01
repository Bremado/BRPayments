package me.toddydev.core.model.order.gateway.type;

public enum GatewayType {

    MERCADO_PAGO,
    PIC_PAY,
    STRIPE,
    PAG_SEGURO;

    public static GatewayType find(String value) {
        for (GatewayType gatewayType : values()) {
            if (gatewayType.name().equalsIgnoreCase(value)) {
                return gatewayType;
            }
        }
        return null;
    }

}
