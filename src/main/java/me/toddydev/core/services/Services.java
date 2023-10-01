package me.toddydev.core.services;

import lombok.Getter;
import me.toddydev.core.services.mercadopago.MPService;

public class Services {

    @Getter
    private static MPService mercadoPagoService = new MPService();
}
