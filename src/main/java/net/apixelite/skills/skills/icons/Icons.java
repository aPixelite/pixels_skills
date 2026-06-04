package net.apixelite.skills.skills.icons;

import net.apixelite.skills.PixelsSkills;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class Icons {

    public static final List<ItemStack> ICON_ITEMS = new ArrayList<>();

    public static final Item COMBAT_SKILL_ICON = registerIconItem("combat");
    public static final Item MINING_SKILL_ICON = registerIconItem("mining");
    public static final Item FORAGING_SKILL_ICON = registerIconItem("foraging");
    public static final Item FARMING_SKILL_ICON = registerIconItem("farming");
    public static final Item FISHING_SKILL_ICON = registerIconItem("fishing");
    public static final Item EXPLORING_SKILL_ICON = registerIconItem("exploring");

    public static Item registerIconItem(String name) {
        return registerItem(name + "_skill_icon",
                new SkillIcon(name, new Item.Settings().registryKey(
                        RegistryKey.of(RegistryKeys.ITEM, Identifier.of(PixelsSkills.MOD_ID, name + "_skill_icon"))
                )));
    }

    public static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, RegistryKey.of(RegistryKeys.ITEM, Identifier.of(PixelsSkills.MOD_ID, name)), item);
    }

    public static void registerSkillIcons() {
        PixelsSkills.LOGGER.info("Registering Mod Items for " + PixelsSkills.MOD_ID);
    }

}
