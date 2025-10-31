package net.apixelite.skills.mixin;

import net.apixelite.skills.util.IEntityDataSaver;
import net.apixelite.skills.util.SkillData;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.apixelite.skills.util.SkillDataKeys.*;

@Mixin(Entity.class)
public class ModEntityDataSaverMixin implements IEntityDataSaver {
    private NbtCompound persistentData;

    @Override
    public NbtCompound getPersistentData() {
        if (this.persistentData == null) {
            this.persistentData = new NbtCompound();
        }
        return persistentData;
    }

    @Inject(method = "writeNbt", at = @At("HEAD"))
    protected void injectedWriteMethod(NbtCompound nbt, CallbackInfoReturnable info) {
        if (persistentData != null) {
            nbt.put("pixelskills.skill_data", persistentData);
        }
    }

    @Inject(method = "readNbt", at = @At("HEAD"))
    protected void injectedReadMethod(NbtCompound nbt, CallbackInfo info) {
        if (nbt.contains("pixelskills.skill_data")) {
            persistentData = nbt.getCompoundOrEmpty("pixelskills.skill_data");

            SkillData.combat_exp = persistentData.getInt(COMBAT.exp, 0);
            SkillData.combat_level = persistentData.getInt(COMBAT.level, 0);

            SkillData.mining_exp = persistentData.getInt(MINING.exp, 0);
            SkillData.mining_level = persistentData.getInt(MINING.level, 0);

            SkillData.foraging_exp = persistentData.getInt(FORAGING.exp, 0);
            SkillData.foraging_level = persistentData.getInt(FORAGING.level, 0);

            SkillData.farming_exp = persistentData.getInt(FARMING.exp, 0);
            SkillData.farming_level = persistentData.getInt(FARMING.level, 0);

            SkillData.fishing_exp = persistentData.getInt(FISHING.exp, 0);
            SkillData.fishing_level = persistentData.getInt(FISHING.level, 0);

            SkillData.exploring_exp = persistentData.getInt(EXPLORING.exp, 0);
            SkillData.exploring_level = persistentData.getInt(EXPLORING.level, 0);
        }


    }
}
