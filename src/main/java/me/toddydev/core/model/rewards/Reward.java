package me.toddydev.core.model.rewards;

import lombok.*;
import me.toddydev.core.model.rewards.item.RewardItem;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter @Setter
@AllArgsConstructor
public class Reward {

    private List<String> commands;
    private List<RewardItem> items;

    public Reward() {
        commands = new ArrayList<>();
        items = new ArrayList<>();
    }
}
