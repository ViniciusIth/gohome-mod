package io.github.viniciusith.gohome.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import io.github.viniciusith.gohome.Constants;
import io.github.viniciusith.gohome.config.Config;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class ReloadConfigCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("gohome")
                        .requires(p_137777_ -> p_137777_.hasPermission(2))
                        .then(Commands.literal("reload").executes(ReloadConfigCommand::execute))
        );
    }

    public static int execute(CommandContext<CommandSourceStack> ctx) {
        ctx.getSource().sendSystemMessage(Component.translatable("command.gohome.reload"));
        try {
            if (!Config.reloadConfigs()) {
                ctx.getSource().sendSystemMessage(Component.translatable("command.gohome.reload.failure"));
                Constants.LOG.error("Error while running reload command, no more information available. Is the syntax right?");
                return 0;
            }
        } catch (Exception e) {
            ctx.getSource().sendSystemMessage(Component.translatable("command.gohome.reload.failure"));
            Constants.LOG.error("Error while running reload command: {}", String.valueOf(e));
            return 0;
        }

        ctx.getSource().sendSystemMessage(Component.translatable("command.gohome.reload.success"));
        return 1;
    }
}
