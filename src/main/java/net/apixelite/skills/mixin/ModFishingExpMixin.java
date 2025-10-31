package net.apixelite.skills.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.apixelite.skills.util.ModTags;
import net.apixelite.skills.util.SkillData;
import net.apixelite.skills.util.SkillDataKeys;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.*;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Iterator;
import java.util.List;

@Mixin(FishingBobberEntity.class)
public class ModFishingExpMixin {
    @Unique
    int i = 0;

    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z", shift = At.Shift.AFTER))
    protected void injectedFishingSkillMethod(ItemStack usedItem, CallbackInfoReturnable<Integer> cir, @Local PlayerEntity playerEntity, @Local(ordinal = 1) ItemStack itemStack) {
        int amount = 0;

        if (this.i <= 0) {
            if (itemStack.isIn(ItemTags.FISHES)) {
                amount = 3;
            } else if (itemStack.isIn(ModTags.Items.FISHING_TREASURE) || itemStack.getItem().hasGlint(itemStack)) {
                amount = 10;
            } else if (itemStack.isIn(ModTags.Items.FISHING_JUNK) || itemStack.getItem() instanceof PotionItem || itemStack.getItem() instanceof FishingRodItem) {
                amount = 1;
            }
            this.i = 1;

            SkillData.addExp(((ServerPlayerEntity) playerEntity), amount, SkillDataKeys.FISHING);
        } else if (this.i == 1) {
            this.i = 0;
        }
    }

}
