package net.apixelite.skills.skills.icons;

import net.apixelite.skills.skills.SkillData;
import net.minecraft.item.Item;

public class SkillIcon extends Item {

    private final String type;

    public SkillIcon(String type, Settings settings) {
        super(settings);

        this.type = type;

    }

    public String getType() {
        return this.type;
    }

    public int getExp() {
        return SkillData.ESkillDataKeys.valueOf(this.type.toUpperCase()).exp;
    }

    public int getLevel() {
        return SkillData.ESkillDataKeys.valueOf(this.type.toUpperCase()).level;
    }
}
