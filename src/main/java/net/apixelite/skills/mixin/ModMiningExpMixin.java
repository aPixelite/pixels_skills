package net.apixelite.skills.mixin;

import net.apixelite.skills.util.ModTags;
import net.apixelite.skills.util.SkillData;
import net.apixelite.skills.util.SkillDataKeys;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class ModMiningExpMixin {

    @Inject(method = "onBreak", at = @At("HEAD"))
    private void injectedMiningExp(World world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfoReturnable<BlockState> cir) {

        if (world instanceof ServerWorld) {
            ServerPlayerEntity serverPlayer = ((ServerPlayerEntity) world.getPlayerByUuid(player.getUuid()));
            int amount = (int) ( state.getBlock().getHardness() * 4);

            if (state.isIn(ModTags.Blocks.MINING_SKILL_BLOCKS)) {
                SkillData.addExp(serverPlayer, amount, SkillDataKeys.MINING);
            }
            else if (state.isIn(ModTags.Blocks.FORAGING_SKILL_BLOCKS)) {
                if (state.isIn(BlockTags.LOGS)) {
                    SkillData.addExp(serverPlayer, amount, SkillDataKeys.FORAGING);
                } else {
                    SkillData.addExp(serverPlayer, 2, SkillDataKeys.FORAGING);
                }
            }
            else if (state.isIn(ModTags.Blocks.FARMING_SKILL_BLOCKS)) {
                SkillData.giveFarmingExp(world, state, pos, player);
//                if (state.isIn(BlockTags.SMALL_FLOWERS)) {
//                    SkillData.addExp(serverPlayer, 1, SkillDataKeys.FARMING);
//                } else if (amount > 0) {
//                    SkillData.addExp(serverPlayer, amount, SkillDataKeys.FARMING);
//                } else {
//                    SkillData.addExp(serverPlayer, 2, SkillDataKeys.FARMING);
//                }
            }
        }
    }

}
