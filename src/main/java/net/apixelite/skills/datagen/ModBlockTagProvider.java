package net.apixelite.skills.datagen;

import net.apixelite.skills.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ModTags.Blocks.BREAKING_POWER_1)
                .add(
                        Blocks.STONE,
                        Blocks.GRANITE,
                        Blocks.DIORITE,
                        Blocks.ANDESITE,
                        Blocks.COAL_BLOCK
                )
                .forceAddTag(BlockTags.COAL_ORES);

        getOrCreateTagBuilder(ModTags.Blocks.BREAKING_POWER_2)
                .add(
                        Blocks.COPPER_BLOCK,
                        Blocks.RAW_COPPER_BLOCK
                )
                .forceAddTag(BlockTags.COPPER_ORES);

        getOrCreateTagBuilder(ModTags.Blocks.BREAKING_POWER_3)
                .add(
                        Blocks.DEEPSLATE,
                        Blocks.TUFF,
                        Blocks.GOLD_BLOCK,
                        Blocks.IRON_BLOCK,
                        Blocks.RAW_IRON_BLOCK
                )
                .forceAddTags(BlockTags.GOLD_ORES, BlockTags.IRON_ORES);

        getOrCreateTagBuilder(ModTags.Blocks.BREAKING_POWER_4)
                .add(Blocks.DIAMOND_BLOCK)
                .forceAddTag(BlockTags.DIAMOND_ORES)
                .add(Blocks.EMERALD_BLOCK)
                .forceAddTag(BlockTags.EMERALD_ORES);

        getOrCreateTagBuilder(ModTags.Blocks.BREAKING_POWER_5)
                .add(Blocks.NETHERITE_BLOCK, Blocks.ANCIENT_DEBRIS);

        getOrCreateTagBuilder(ModTags.Blocks.INCORRECT_FOR_POWER_1)
                .forceAddTags(ModTags.Blocks.BREAKING_POWER_2, ModTags.Blocks.BREAKING_POWER_3, ModTags.Blocks.BREAKING_POWER_4, ModTags.Blocks.BREAKING_POWER_5);
        getOrCreateTagBuilder(ModTags.Blocks.INCORRECT_FOR_POWER_2)
                .forceAddTags(ModTags.Blocks.BREAKING_POWER_3, ModTags.Blocks.BREAKING_POWER_4, ModTags.Blocks.BREAKING_POWER_5);
        getOrCreateTagBuilder(ModTags.Blocks.INCORRECT_FOR_POWER_3)
                .forceAddTags(ModTags.Blocks.BREAKING_POWER_4, ModTags.Blocks.BREAKING_POWER_5);
        getOrCreateTagBuilder(ModTags.Blocks.INCORRECT_FOR_POWER_4)
                .forceAddTags(ModTags.Blocks.BREAKING_POWER_5);
        getOrCreateTagBuilder(ModTags.Blocks.INCORRECT_FOR_POWER_5);

        getOrCreateTagBuilder(ModTags.Blocks.MINING_SKILL_BLOCKS)
                .forceAddTag(BlockTags.COAL_ORES)
                .forceAddTag(BlockTags.COPPER_ORES)
                .forceAddTag(BlockTags.IRON_ORES)
                .forceAddTag(BlockTags.GOLD_ORES)
                .forceAddTag(BlockTags.LAPIS_ORES)
                .forceAddTag(BlockTags.REDSTONE_ORES)
                .forceAddTag(BlockTags.DIAMOND_ORES)
                .forceAddTag(BlockTags.EMERALD_ORES)
                .forceAddTag(BlockTags.BASE_STONE_OVERWORLD)
                .add(Blocks.CALCITE)

                .forceAddTag(BlockTags.BASE_STONE_NETHER)
                .add(Blocks.NETHER_QUARTZ_ORE)
                .add(Blocks.NETHER_GOLD_ORE)
                .add(Blocks.ANCIENT_DEBRIS)
                .add(Blocks.CRYING_OBSIDIAN)
                .add(Blocks.OBSIDIAN)

                .add(Blocks.END_STONE);

        getOrCreateTagBuilder(ModTags.Blocks.FORAGING_SKILL_BLOCKS)
                .add(Blocks.MANGROVE_ROOTS)
                .forceAddTag(BlockTags.LEAVES)
                .forceAddTag(BlockTags.LOGS);

        getOrCreateTagBuilder(ModTags.Blocks.CROPS)
                .add(Blocks.WHEAT)
                .add(Blocks.POTATOES)
                .add(Blocks.CARROTS)
                .add(Blocks.BEETROOTS)
                .add(Blocks.NETHER_WART)
                .add(Blocks.PITCHER_PLANT)
                .add(Blocks.SWEET_BERRY_BUSH)
        ;

        getOrCreateTagBuilder(ModTags.Blocks.TALL_CROPS)
                .add(Blocks.SUGAR_CANE)
                .add(Blocks.CACTUS)
                .add(Blocks.BAMBOO)
                .add(Blocks.KELP)
                .add(Blocks.TWISTING_VINES_PLANT)
        ;

        getOrCreateTagBuilder(ModTags.Blocks.FLOWERS)
                .add(Blocks.CACTUS_FLOWER)
                .add(Blocks.PINK_PETALS)
                .add(Blocks.WILDFLOWERS)
                .add(Blocks.SPORE_BLOSSOM)
                .add(Blocks.PEONY)
                .add(Blocks.SUNFLOWER)
                .add(Blocks.LILAC)
                .add(Blocks.ROSE_BUSH)
                .forceAddTag(BlockTags.SMALL_FLOWERS)
        ;

        getOrCreateTagBuilder(ModTags.Blocks.FARMING_SKILL_BLOCKS)
                .forceAddTag(ModTags.Blocks.CROPS)
                .forceAddTag(ModTags.Blocks.TALL_CROPS)
                .forceAddTag(ModTags.Blocks.FLOWERS)

                .add(Blocks.PUMPKIN)
                .add(Blocks.MELON)
                .add(Blocks.BROWN_MUSHROOM)
                .add(Blocks.RED_MUSHROOM)

                .add(Blocks.TORCHFLOWER)

                .add(Blocks.WARPED_ROOTS)
                .add(Blocks.CRIMSON_ROOTS)
                .add(Blocks.NETHER_SPROUTS)
                .add(Blocks.CRIMSON_FUNGUS)
                .add(Blocks.WARPED_FUNGUS)
                .add(Blocks.WEEPING_VINES_PLANT)

                .add(Blocks.CHORUS_FLOWER)
                .add(Blocks.CHORUS_PLANT)
        ;
    }
}
