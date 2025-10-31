package net.apixelite.skills.datagen;

import net.apixelite.skills.skills.icons.Icons;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(Icons.COMBAT_SKILL_ICON, Models.HANDHELD);
        itemModelGenerator.register(Icons.MINING_SKILL_ICON, Models.HANDHELD);
        itemModelGenerator.register(Icons.FORAGING_SKILL_ICON, Models.HANDHELD);
        itemModelGenerator.register(Icons.FARMING_SKILL_ICON, Models.HANDHELD);
        itemModelGenerator.register(Icons.FISHING_SKILL_ICON, Models.HANDHELD);
        itemModelGenerator.register(Icons.EXPLORING_SKILL_ICON, Models.HANDHELD);

    }
}
