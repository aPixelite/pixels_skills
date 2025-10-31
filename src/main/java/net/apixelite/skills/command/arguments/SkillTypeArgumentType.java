package net.apixelite.skills.command.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.apixelite.skills.util.SkillDataKeys;

public class SkillTypeArgumentType implements ArgumentType<SkillDataKeys> {
    @Override
    public SkillDataKeys parse(StringReader reader) throws CommandSyntaxException {
        try {
            String type = reader.getString();

            return SkillDataKeys.valueOf(type);

        } catch (Exception e) {
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherParseException().create("Invalid skill type");
        }

    }
}
