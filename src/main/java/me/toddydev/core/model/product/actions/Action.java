package me.toddydev.core.model.product.actions;

import lombok.*;
import me.toddydev.core.model.product.actions.screen.Screen;
import me.toddydev.core.model.product.actions.type.ActionType;
import org.bukkit.Sound;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Action {

    private ActionType type;

    private Sound sound;

    private String message;
    private String actionBar;

    private Screen screen;

}
