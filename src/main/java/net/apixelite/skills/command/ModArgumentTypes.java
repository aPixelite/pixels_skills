package net.apixelite.skills.command;

import net.apixelite.skills.PixelsSkills;
import net.apixelite.skills.command.arguments.SkillTypeArgumentType;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer;
import net.minecraft.util.Identifier;

public class ModArgumentTypes {

    public static void registerArgumentTypes() {
        ArgumentTypeRegistry.registerArgumentType(
                Identifier.of(PixelsSkills.MOD_ID, "block_pos"),
                SkillTypeArgumentType.class,
                ConstantArgumentSerializer.of(SkillTypeArgumentType::new)
        );
    }
}
