package me.toddydev.core.model.rewards.item;

import lombok.*;
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
        return new ItemStack(material, amount, data);
    }
}
