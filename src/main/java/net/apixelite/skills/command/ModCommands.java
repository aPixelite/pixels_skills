package net.apixelite.skills.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.apixelite.skills.command.suggestions.SkillSuggestionProvider;
import net.apixelite.skills.util.SkillData;
import net.apixelite.skills.util.SkillDataKeys;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

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

        switch (type) {
            case "combat" -> SkillData.setExp(context.getSource().getPlayer(), amount, SkillDataKeys.COMBAT);
            case "mining" -> SkillData.setExp(context.getSource().getPlayer(), amount, SkillDataKeys.MINING);
            case "foraging" -> SkillData.setExp(context.getSource().getPlayer(), amount, SkillDataKeys.FORAGING);
            case "farming" -> SkillData.setExp(context.getSource().getPlayer(), amount, SkillDataKeys.FARMING);
            case "fishing" -> SkillData.setExp(context.getSource().getPlayer(), amount, SkillDataKeys.FISHING);
            case "exploring" -> SkillData.setExp(context.getSource().getPlayer(), amount, SkillDataKeys.EXPLORING);
        }

        return 0;
    }

    public static int addSkillLevel(CommandContext<ServerCommandSource> context) {
        String type = StringArgumentType.getString(context, "type");
        int amount = IntegerArgumentType.getInteger(context, "amount");

        switch (type) {
            case "combat" -> SkillData.setLevel(context.getSource().getPlayer(), amount, SkillDataKeys.COMBAT);
            case "mining" -> SkillData.setLevel(context.getSource().getPlayer(), amount, SkillDataKeys.MINING);
            case "foraging" -> SkillData.setLevel(context.getSource().getPlayer(), amount, SkillDataKeys.FORAGING);
            case "farming" -> SkillData.setLevel(context.getSource().getPlayer(), amount, SkillDataKeys.FARMING);
            case "fishing" -> SkillData.setLevel(context.getSource().getPlayer(), amount, SkillDataKeys.FISHING);
            case "exploring" -> SkillData.setLevel(context.getSource().getPlayer(), amount, SkillDataKeys.EXPLORING);
        }

        return 0;
    }


    public static int executeResetSkill(CommandContext<ServerCommandSource> context) {
        String type = StringArgumentType.getString(context, "type");

        switch (type) {
            case "combat" -> SkillData.resetData(context.getSource().getPlayer(), SkillDataKeys.COMBAT);
            case "mining" -> SkillData.resetData(context.getSource().getPlayer(), SkillDataKeys.MINING);
            case "foraging" -> SkillData.resetData(context.getSource().getPlayer(), SkillDataKeys.FORAGING);
            case "farming" -> SkillData.resetData(context.getSource().getPlayer(), SkillDataKeys.FARMING);
            case "fishing" -> SkillData.resetData(context.getSource().getPlayer(), SkillDataKeys.FISHING);
            case "exploring" -> SkillData.resetData(context.getSource().getPlayer(), SkillDataKeys.EXPLORING);
            case "all" -> SkillData.resetAllData(context.getSource().getPlayer());
        }

        context.getSource().sendFeedback(() -> Text.literal("Reset " + type + " skill data for " + context.getSource().getPlayer().getName().getString()), false);

        return 0;
    }

}
