package me.toddydev.bukkit.command;

import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import org.bukkit.entity.Player;

public class PaymentCommand {

    @Command(
            name = "payment",
            aliases = { "pagamento" },
            permission = "payment.admin"
    )
    public void handleCommand(Context<Player> context) {
        context.sendMessage("");
        context.sendMessage("§6Comandos disponíveis:");
        context.sendMessage("");
        context.sendMessage("§e/pagamento listar <jogador> §8- §7Lista os pagamentos de um jogador.");
        context.sendMessage("§e/pagamento concluir <jogador> <referência> §8- §7Conclui um pagamento.");
        context.sendMessage("§e/pagamento cancelar <jogador> <referência> §8- §7Cancela um pagamento.");
        context.sendMessage("");
    }

}
