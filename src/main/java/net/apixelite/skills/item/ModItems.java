package net.apixelite.skills.item;

import net.apixelite.skills.PixelsSkills;
import net.apixelite.skills.item.components.ModToolMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final ModItem MOD_PICKAXE = registerItem("mod_pickaxe",
            (ModItem.ModSettings) new ModItem.ModSettings().pickaxe(ModToolMaterial.WOOD, 1.0F, -2.8F)
                    .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(PixelsSkills.MOD_ID, "mod_pickaxe"))));

    public static ModItem registerItem(String name, ModItem.ModSettings settings) {
        return Registry.register(Registries.ITEM, RegistryKey.of(RegistryKeys.ITEM, Identifier.of(PixelsSkills.MOD_ID, name)), new ModItem(settings));
    }

    public static void registerModItems() {
        PixelsSkills.LOGGER.info("Registering Mod Items for " + PixelsSkills.MOD_ID);
    }
}
