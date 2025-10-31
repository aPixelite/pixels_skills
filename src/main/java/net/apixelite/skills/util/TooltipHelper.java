package net.apixelite.skills.util;

import net.apixelite.skills.skills.icons.SkillIcon;
import net.apixelite.skills.util.tooltip.SkillTooltip;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Environment(EnvType.CLIENT)
public class TooltipHelper {

    public static void appendTooltip(@NotNull ItemStack stack, @NotNull List<Text> tooltip) {

        if (stack.getItem() instanceof SkillIcon) {
//            if (stack.isOf(Icons.MINING_SKILL_ICON)) {
//                MiningSkillTooltip.buildTooltip(tooltip, stack);
//            }
            SkillTooltip.buildTooltip(tooltip, stack);
        }

    }

    public static void add(List<Text> tooltip, List<String> text) {
        tooltip.clear();
        for (int i = 0; i < text.size(); i += 1) {
            tooltip.add(i, Text.literal(text.get(i)));
        }
    }

}
