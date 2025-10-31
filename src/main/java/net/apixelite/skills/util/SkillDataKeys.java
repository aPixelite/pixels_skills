package net.apixelite.skills.util;

public enum SkillDataKeys {

    COMBAT("combat_exp","combat_level"),
    MINING("mining_exp","mining_level"),
    FORAGING("foraging_exp","foraging_level"),
    FARMING("farming_exp","farming_level"),
    FISHING("fishing_exp","fishing_level"),
    EXPLORING("exploring_exp","exploring_level");

    public final String exp;
    public final String level;

    SkillDataKeys(final String exp, final String level) {
        this.exp = exp;
        this.level = level;
    }

}
