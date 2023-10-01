package me.toddydev.core.model.order.gateway;

import lombok.*;
import me.toddydev.core.model.order.gateway.type.GatewayType;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Gateway {

    private String token;
    private GatewayType type;

}
