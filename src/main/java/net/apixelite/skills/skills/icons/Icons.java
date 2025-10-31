package net.apixelite.skills.skills.icons;

import net.apixelite.skills.PixelsSkills;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class Icons {

    public static final Item COMBAT_SKILL_ICON = registerItem("combat_skill_icon",
            new SkillIcon("combat", new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(PixelsSkills.MOD_ID, "combat_skill_icon")))));
    public static final Item MINING_SKILL_ICON = registerItem("mining_skill_icon",
            new SkillIcon("mining", new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(PixelsSkills.MOD_ID, "mining_skill_icon")))));
    public static final Item FORAGING_SKILL_ICON = registerItem("foraging_skill_icon",
            new SkillIcon("foraging", new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(PixelsSkills.MOD_ID, "foraging_skill_icon")))));
    public static final Item FARMING_SKILL_ICON = registerItem("farming_skill_icon",
            new SkillIcon("farming", new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(PixelsSkills.MOD_ID, "farming_skill_icon")))));
    public static final Item FISHING_SKILL_ICON = registerItem("fishing_skill_icon",
            new SkillIcon("fishing", new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(PixelsSkills.MOD_ID, "fishing_skill_icon")))));
    public static final Item EXPLORING_SKILL_ICON = registerItem("exploring_skill_icon",
            new SkillIcon("exploring", new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(PixelsSkills.MOD_ID, "exploring_skill_icon")))));

    public static Item registerItem(String name, Item.Settings settings) {
        return Registry.register(Registries.ITEM, RegistryKey.of(RegistryKeys.ITEM, Identifier.of(PixelsSkills.MOD_ID, name)), new Item(settings));
    }

    public static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, RegistryKey.of(RegistryKeys.ITEM, Identifier.of(PixelsSkills.MOD_ID, name)), item);
    }

    public static void registerSkillIcons() {
        PixelsSkills.LOGGER.info("Registering Mod Items for " + PixelsSkills.MOD_ID);
    }

}
