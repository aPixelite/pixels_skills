package net.apixelite.skills.item;

import net.apixelite.skills.item.components.ModDataComponentTypes;
import net.apixelite.skills.item.components.ModToolComponent;
import net.apixelite.skills.item.components.ModToolMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ModItem extends Item {
    public ModItem(ModSettings settings) {
        super(settings);
    }

    @Override
    public float getMiningSpeed(ItemStack stack, BlockState state) {
        ModToolComponent toolComponent = stack.get(ModDataComponentTypes.MOD_TOOL);
        return toolComponent != null ? toolComponent.getSpeed(state) : 1.0F;
    }

    @Override
    public boolean isCorrectForDrops(ItemStack stack, BlockState state) {
        ModToolComponent toolComponent = stack.get(ModDataComponentTypes.MOD_TOOL);
        return toolComponent != null && toolComponent.isCorrectForDrops(state);
    }

    @Override
    public boolean canMine(ItemStack stack, BlockState state, World world, BlockPos pos, LivingEntity user) {
        if (stack.isSuitableFor(state)) {
            return true;
        } else {
            if (world.isClient()) {
                PlayerEntity player = world.getPlayerByUuid(user.getUuid());
                assert player != null;
                player.sendMessage(Text.literal("§cBlock requires more breaking power!"), true);
            }
            return false;
        }
    }

    public static class ModSettings extends Item.Settings {
        public Item.Settings pickaxe(ModToolMaterial material, float attackDamage, float attackSpeed) {
            return this.tool(material, BlockTags.PICKAXE_MINEABLE, attackDamage, attackSpeed, 0.0F);
        }

        public Item.Settings tool(ModToolMaterial material, TagKey<Block> effectiveBlocks, float attackDamage, float attackSpeed, float disableBlockingForSeconds) {
            return material.applyToolSettings(this, effectiveBlocks, attackDamage, attackSpeed, disableBlockingForSeconds);
        }
    }
}
