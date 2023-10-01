package me.toddydev.core.model.product.rewards.item;

import lombok.*;
import me.toddydev.core.utils.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RewardItem {

    private Material material;
    private short data;
    private int amount;

    public ItemStack stack() {
        return new ItemBuilder(material,data).amount(amount).build();
    }
}
