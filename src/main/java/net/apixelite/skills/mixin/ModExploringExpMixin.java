package net.apixelite.skills.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.apixelite.skills.util.SkillData;
import net.apixelite.skills.util.SkillDataKeys;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.VehicleMoveC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class ModExploringExpMixin {

    @Shadow public ServerPlayerEntity player;
    @Unique
    private int i = 0;


    @Inject(method = "onPlayerMove", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;increaseTravelMotionStats(DDD)V", shift = At.Shift.AFTER))
    protected void injectedTravelMotionMethod(PlayerMoveC2SPacket packet, CallbackInfo ci, @Local(ordinal = 3) double i, @Local(ordinal = 4) double j, @Local(ordinal = 5) double k) {
        if (!isZero(this.player.getX() - i, this.player.getY() - j, this.player.getZ() - k)) {
            this.i += 1;
            if (this.i > 20) {
            SkillData.addExp(this.player, 1, SkillDataKeys.EXPLORING);
            this.i = 0;
            }
        }
    }

    @Inject(method = "onVehicleMove", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;increaseTravelMotionStats(DDD)V", shift = At.Shift.AFTER))
    protected void injectedRidingMotionMethod(VehicleMoveC2SPacket packet, CallbackInfo ci, @Local(ordinal = 4) double i, @Local(ordinal = 5) double j, @Local(ordinal = 6) double k) {
        if (!isZero(this.player.getX() - i, this.player.getY() - j, this.player.getZ() - k)) {
            this.i += 1;
            if (this.i > 20) {
                SkillData.addExp(this.player, 1, SkillDataKeys.EXPLORING);
                this.i = 0;
            }
        }
    }

    @Unique
    private static boolean isZero(double deltaX, double deltaY, double deltaZ) {
        return deltaX == 0.0 && deltaY == 0.0 && deltaZ == 0.0;
    }

}
