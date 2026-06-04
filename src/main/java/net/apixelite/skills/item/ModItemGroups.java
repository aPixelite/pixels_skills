package net.apixelite.skills.item;

import net.apixelite.skills.PixelsSkills;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static final ItemGroup SUBTERRA_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(PixelsSkills.MOD_ID, "skills_group"), FabricItemGroup.builder()
                    .displayName(Text.translatable("itemgroup.skills_group"))
                    .icon(() -> new ItemStack(Items.WOODEN_PICKAXE))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.MOD_PICKAXE);
                    }).build());

    public static void registerItemGroups() {
        PixelsSkills.LOGGER.info("Registering Item Groups for " + PixelsSkills.MOD_ID);
    }
}
