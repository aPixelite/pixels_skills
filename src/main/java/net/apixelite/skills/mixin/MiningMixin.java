package net.apixelite.skills.mixin;

import net.apixelite.skills.PixelsSkills;
import net.apixelite.skills.item.ModItems;
import net.apixelite.skills.item.components.ModDataComponentTypes;
import net.apixelite.skills.item.components.ModToolComponent;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.class)
public class MiningMixin {

    @Inject(method = "calcBlockBreakingDelta", at = @At("HEAD"), cancellable = true)
    protected void injectedMiningMethod(BlockState state, PlayerEntity player, BlockView world, BlockPos pos, CallbackInfoReturnable<Float> cir) {
        float digSpeed = getMiningSpeed(player, state);
        float blockStrength = state.getHardness(world, pos) * 10;
        float breakingSpeed = Math.round((30 * blockStrength / digSpeed));
        if (breakingSpeed <= 4 && breakingSpeed >= 1)  {
            breakingSpeed = 4;
        }
        breakingSpeed = 1 / breakingSpeed;
        cir.setReturnValue(breakingSpeed);
    }


    @Unique
    private float getMiningSpeed(PlayerEntity player, BlockState state) {
        ItemStack activeItem = player.getInventory().getSelectedStack();
        if (activeItem.isOf(ModItems.MOD_PICKAXE)) {
            ModToolComponent toolComponent = activeItem.get(ModDataComponentTypes.MOD_TOOL);
            assert toolComponent != null;
            return toolComponent.getSpeed(state);
        }
        return 100;

    }
}
