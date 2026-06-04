package net.apixelite.skills.mixin;

import net.apixelite.skills.attributes.stats.ModAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class ModEntityAttributeMixin {

    @Inject(method = "createPlayerAttributes", at = @At("HEAD"), cancellable = true)
    private static void injectedAttributeMethod(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        cir.setReturnValue(
                LivingEntity.createLivingAttributes()
                        .add(ModAttributes.HEALTH, 100)
                        .add(ModAttributes.DEFENCE)
                        .add(ModAttributes.DAMAGE)
                        .add(ModAttributes.STRENGTH)
                        .add(ModAttributes.CRIT_DAMAGE, 50)
                        .add(ModAttributes.CRIT_CHANCE, 30)
                        .add(ModAttributes.ATTACK_SPEED)

                        .add(ModAttributes.MINING_SPEED)
                        .add(ModAttributes.MINING_FORTUNE)
                        .add(ModAttributes.BLOCK_MINING_SPREAD)
                        .add(ModAttributes.ORE_MINING_SPREAD)
                        .add(ModAttributes.BREAKING_POWER)

                        .add(ModAttributes.FARMING_FORTUNE)

                        .add(ModAttributes.FORAGING_FORTUNE)
                        .add(ModAttributes.SWEEP)

                        .add(ModAttributes.FISHING_SPEED)
                        .add(ModAttributes.TREASURE_CHANCE)
                        .add(ModAttributes.SEA_CREATURE_CHANCE)

                        .add(ModAttributes.RESPIRATION)
                        .add(ModAttributes.SPEED)

                        .add(EntityAttributes.ATTACK_DAMAGE, 1.0)
                        .add(EntityAttributes.MOVEMENT_SPEED, 0.1F)
                        .add(EntityAttributes.ATTACK_SPEED)
                        .add(EntityAttributes.LUCK)
                        .add(EntityAttributes.BLOCK_INTERACTION_RANGE, 4.5)
                        .add(EntityAttributes.ENTITY_INTERACTION_RANGE, 3.0)
                        .add(EntityAttributes.BLOCK_BREAK_SPEED)
                        .add(EntityAttributes.SUBMERGED_MINING_SPEED)
                        .add(EntityAttributes.SNEAKING_SPEED)
                        .add(EntityAttributes.MINING_EFFICIENCY)
                        .add(EntityAttributes.SWEEPING_DAMAGE_RATIO)
        );
    }
}
