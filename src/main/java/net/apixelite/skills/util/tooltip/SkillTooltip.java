package net.apixelite.skills.util.tooltip;

import net.apixelite.skills.skills.icons.SkillIcon;
import net.apixelite.skills.util.SkillData;
import net.apixelite.skills.util.TooltipHelper;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class SkillTooltip {

    public static void buildTooltip(@NotNull List<Text> tooltip, @NotNull ItemStack stack) {
        List<String> tooltipText = new ArrayList<>();

        if (stack.getItem() instanceof SkillIcon icon) {
            int level = icon.getLevel();
            int exp = icon.getExp();
            String name = icon.getName().getString().split(" ")[0];

            double percentage = ((float) exp / SkillData.getExpToNextLevel(level)) * 100F;

            tooltipText.add(name + " Skill");
            tooltipText.add("");

            tooltipText.add("§6Level: §3" + level);
            tooltipText.add("§6Exp: §3" + exp + "/" + SkillData.getExpToNextLevel(level) + " §8(" + String.format("%.1f", percentage) + "%)");

            tooltipText.add("");
            tooltipText.add("§8pixels_skills:" + name.toLowerCase() + "_skill" );
        }
        TooltipHelper.add(tooltip, tooltipText);
    }

}
