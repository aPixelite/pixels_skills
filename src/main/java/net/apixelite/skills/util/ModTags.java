package net.apixelite.skills.util;

import net.apixelite.skills.PixelsSkills;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> MINING_SKILL_BLOCKS = createTag("mining_skill_blocks");
        public static final TagKey<Block> FORAGING_SKILL_BLOCKS = createTag("foraging_skill_blocks");
        public static final TagKey<Block> FARMING_SKILL_BLOCKS = createTag("farming_skill_blocks");
        public static final TagKey<Block> CROPS = createTag("crops");
        public static final TagKey<Block> TALL_CROPS = createTag("tall_crops");
        public static final TagKey<Block> FLOWERS = createTag("flowers");

        public static final TagKey<Block> BREAKING_POWER_1 = createTag("breaking_power_1");
        public static final TagKey<Block> BREAKING_POWER_2 = createTag("breaking_power_2");
        public static final TagKey<Block> BREAKING_POWER_3 = createTag("breaking_power_3");
        public static final TagKey<Block> BREAKING_POWER_4 = createTag("breaking_power_4");
        public static final TagKey<Block> BREAKING_POWER_5 = createTag("breaking_power_5");

        public static final TagKey<Block> INCORRECT_FOR_POWER_1 = createTag("incorrect_for_power_1");
        public static final TagKey<Block> INCORRECT_FOR_POWER_2 = createTag("incorrect_for_power_2");
        public static final TagKey<Block> INCORRECT_FOR_POWER_3 = createTag("incorrect_for_power_3");
        public static final TagKey<Block> INCORRECT_FOR_POWER_4 = createTag("incorrect_for_power_4");
        public static final TagKey<Block> INCORRECT_FOR_POWER_5 = createTag("incorrect_for_power_5");

        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(PixelsSkills.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> FISHING_JUNK = createTag("fishing_junk");
        public static final TagKey<Item> FISHING_TREASURE = createTag("fishing_treasure");

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(PixelsSkills.MOD_ID, name));
        }
    }

}
