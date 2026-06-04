package net.apixelite.skills.mixin.skills;

import net.apixelite.skills.util.IEntityDataSaver;
import net.apixelite.skills.skills.SkillData;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class ModEntityDataSaverMixin implements IEntityDataSaver {
    @Unique
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

            for (SkillData.ESkillDataKeys key : SkillData.ESkillDataKeys.values()) {
                key.setExp(persistentData.getInt(key.type_exp, 0));
                key.setLevel(persistentData.getInt(key.type_level, 0));
            }
        }
    }
}
