package net.apixelite.skills.skills.icons;

import net.apixelite.skills.util.SkillData;
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
        switch (this.type) {
            case "combat" -> {
                return SkillData.combat_exp;
            }
            case "mining" -> {
                return SkillData.mining_exp;
            }
            case "foraging" -> {
                return SkillData.foraging_exp;
            }
            case "farming" -> {
                return SkillData.farming_exp;
            }
            case "fishing" -> {
                return SkillData.fishing_exp;
            }
            case "exploring" -> {
                return SkillData.exploring_exp;
            }
            default -> {
                return 0;
            }
        }
    }

    public int getLevel() {
        switch (this.type) {
            case "combat" -> {
                return SkillData.combat_level;
            }
            case "mining" -> {
                return SkillData.mining_level;
            }
            case "foraging" -> {
                return SkillData.foraging_level;
            }
            case "farming" -> {
                return SkillData.farming_level;
            }
            case "fishing" -> {
                return SkillData.fishing_level;
            }
            case "exploring" -> {
                return SkillData.exploring_level;
            }
            default -> {
                return 0;
            }
        }
    }
}
