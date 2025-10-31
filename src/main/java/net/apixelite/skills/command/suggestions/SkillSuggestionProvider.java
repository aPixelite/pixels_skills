package net.apixelite.skills.command.suggestions;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.server.command.ServerCommandSource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SkillSuggestionProvider implements SuggestionProvider<ServerCommandSource> {
    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) throws CommandSyntaxException {

        List<String> skills = new ArrayList<>();

        skills.add("combat");
        skills.add("mining");
        skills.add("foraging");
        skills.add("farming");
        skills.add("fishing");
        skills.add("exploring");
        skills.add("all");

        for (String skill : skills) {
            builder.suggest(skill);
        }

        return builder.buildFuture();
    }
}
