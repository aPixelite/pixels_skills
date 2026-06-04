package net.apixelite.skills.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.apixelite.skills.command.suggestions.SkillSuggestionProvider;
import net.apixelite.skills.skills.SkillData;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.Objects;

public class ModCommands {

    public static void registerModCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(CommandManager.literal("skills")
                .then(CommandManager.literal("reset")
                        .then(CommandManager.argument("type", StringArgumentType.string())
                                .suggests(new SkillSuggestionProvider())
                                .executes(ModCommands::executeResetSkill)))
                .then(CommandManager.literal("add")
                        .then(CommandManager.literal("level")
                                .then(CommandManager.argument("amount", IntegerArgumentType.integer())
                                        .then(CommandManager.argument("type", StringArgumentType.string())
                                                .suggests(new SkillSuggestionProvider())
                                                .executes(ModCommands::addSkillLevel))))

                        .then(CommandManager.literal("exp")
                                .then(CommandManager.argument("amount", IntegerArgumentType.integer())
                                        .then(CommandManager.argument("type", StringArgumentType.string())
                                                .suggests(new SkillSuggestionProvider())
                                                .executes(ModCommands::addSkillExp)))))));
    }

    public static int addSkillExp(CommandContext<ServerCommandSource> context) {
        String type = StringArgumentType.getString(context, "type");
        int amount = IntegerArgumentType.getInteger(context, "amount");
        SkillData.setExp(context.getSource().getPlayer(), amount, SkillData.ESkillDataKeys.valueOf(type));
        return 0;
    }

    public static int addSkillLevel(CommandContext<ServerCommandSource> context) {
        String type = StringArgumentType.getString(context, "type");
        int amount = IntegerArgumentType.getInteger(context, "amount");
        SkillData.setLevel(context.getSource().getPlayer(), amount, SkillData.ESkillDataKeys.valueOf(type));
        return 0;
    }


    public static int executeResetSkill(CommandContext<ServerCommandSource> context) {
        String type = StringArgumentType.getString(context, "type");
        if (Objects.equals(type, "all")) {
            SkillData.resetAllData(context.getSource().getPlayer());
        } else {
            SkillData.resetData(context.getSource().getPlayer(), SkillData.ESkillDataKeys.valueOf(type));
        }

        context.getSource().sendFeedback(() -> Text.literal("Reset " + type + " skill data for " + context.getSource().getPlayer().getName().getString()), false);

        return 0;
    }

}
