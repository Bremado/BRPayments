package me.toddydev.core.model.rewards.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

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
