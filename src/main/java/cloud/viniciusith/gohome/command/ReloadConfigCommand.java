package cloud.viniciusith.gohome.command;

import cloud.viniciusith.gohome.config.ModConfig;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class ReloadConfigCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("ghreload").executes(ReloadConfigCommand::run));
    }

    private static int run(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ModConfig.reloadConfigs();
        return 1;
    }
}
