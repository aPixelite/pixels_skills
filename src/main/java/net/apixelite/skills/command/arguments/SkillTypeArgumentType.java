package net.apixelite.skills.command.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.apixelite.skills.skills.SkillData;

public class SkillTypeArgumentType implements ArgumentType<SkillData.ESkillDataKeys> {
    @Override
    public SkillData.ESkillDataKeys parse(StringReader reader) throws CommandSyntaxException {
        try {
            String type = reader.getString();

            return SkillData.ESkillDataKeys.valueOf(type.toUpperCase());

        } catch (Exception e) {
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherParseException().create("Invalid skill type");
        }

    }
}
