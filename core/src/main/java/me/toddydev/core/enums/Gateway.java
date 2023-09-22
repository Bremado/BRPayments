package me.toddydev.core.enums;

public enum Gateway {

    MERCADO_PAGO("Mercado Pago"),
    PIC_PAY("Pic Pay"),
    STRIPE("Stripe"),
    PAG_SEGURO("PagSeguro"),
    PAYPAL("PayPal");

    final String name;

    Gateway(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
