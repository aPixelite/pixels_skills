package net.apixelite.skills.mixin.skills;

import com.mojang.authlib.GameProfile;
import net.apixelite.skills.skills.SkillData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class ModCombatExpMixin {

    @Shadow @Final private GameProfile gameProfile;

    @Inject(method = "onKilledOther", at = @At("HEAD"))
    protected void injectedCombatSkillMethod(ServerWorld world, LivingEntity other, CallbackInfoReturnable<Boolean> cir) {
        if (!(other instanceof PlayerEntity)) {
            SkillData.giveCombatExp((ServerPlayerEntity) world.getPlayerByUuid(this.gameProfile.getId()), world, other);
        }
    }
}
