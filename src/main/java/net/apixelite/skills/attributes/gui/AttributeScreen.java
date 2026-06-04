package net.apixelite.skills.attributes.gui;

import net.apixelite.skills.PixelSkillsClient;
import net.apixelite.skills.PixelsSkills;
import net.apixelite.skills.attributes.stats.ModAttributes;
import net.apixelite.skills.item.components.ModDataComponentTypes;
import net.apixelite.skills.item.ModItems;
import net.apixelite.skills.skills.book.SkillBookScreen;
import net.apixelite.skills.skills.SkillData;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.ThreePartsLayoutWidget;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Objects;

public class AttributeScreen extends Screen {
    private static final Identifier WINDOW_TEXTURE = Identifier.of(PixelsSkills.MOD_ID, "textures/gui/attribute_menu_v4.png");
    private static final Identifier SKILL_BAR_FULL = Identifier.of(PixelsSkills.MOD_ID, "textures/gui/skill_bar_full.png");

    public static final int WINDOW_WIDTH = 371;
    public static final int WINDOW_HEIGHT = 151;
    private static final int TITLE_BOOK_OFFSET_X = 6;
    private static final int TITLE_OFFSET_Y = 5;
    private static final int TEXTURE_WIDTH = 370;
    private static final int TEXTURE_HEIGHT = 150;
    private static final Text PLAYER_TITLE = Text.translatable("gui.pixelskills.skill_book.player");
    private static final Text SKIll_TITLE = Text.translatable("gui.pixelskills.attribute.mining");
    private static final Text ATTRIBUTES_TITLE = Text.translatable("gui.pixelskills.attribute.attributes");
    private static final Text REWARDS_TITLE = Text.translatable("gui.pixelskills.attribute.rewards");
    private final ThreePartsLayoutWidget layout = new ThreePartsLayoutWidget(this);

    @Nullable
    private final Screen parent;
    private float mouseX;
    private float mouseY;
    private ClientPlayerEntity player;

    private final SkillData.ESkillDataKeys skill;

    public AttributeScreen(@Nullable Screen parent, SkillData.ESkillDataKeys skill) {
        super(ATTRIBUTES_TITLE);
        this.parent = parent;
        this.skill = skill;
    }

    @Override
    public void init() {
        this.layout.addHeader(ATTRIBUTES_TITLE, this.textRenderer);
    }

    @Override
    public void close() {
        assert this.client != null;
        this.client.setScreen(this.parent);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        super.render(context, mouseX, mouseY, deltaTicks);
        int x = (this.width - WINDOW_WIDTH) / 2;
        int y = (this.height - WINDOW_HEIGHT) / 2;

        this.mouseX = mouseX;
        this.mouseY = mouseY;

        assert this.client != null;
        this.player = this.client.player;

        this.drawWindow(context, x, y);

        this.drawSkill(context, x, y);

        this.drawAttributes(context, x + 218, y + 20);
    }

    private void drawWindow(DrawContext context, int x, int y) {
        InventoryScreen.drawEntity(context, x + 9, y + 17, x + 87, y + 129, 45, 0.0625F, this.mouseX, this.mouseY, this.player);
        context.drawTexture(RenderLayer::getGuiTextured, WINDOW_TEXTURE, x, y, 0.0F, 0.0F, WINDOW_WIDTH, WINDOW_HEIGHT, TEXTURE_WIDTH, TEXTURE_HEIGHT);
        this.drawTitles(context, x, y);
    }

    private void drawTitles(DrawContext context, int x, int y) {
        context.drawText(this.textRenderer, PLAYER_TITLE, x + TITLE_BOOK_OFFSET_X, y + TITLE_OFFSET_Y, 4210752, false);
        context.drawText(this.textRenderer, SKIll_TITLE, x + TITLE_BOOK_OFFSET_X + 99, y + TITLE_OFFSET_Y, 4210752, false);
        context.drawText(this.textRenderer, ATTRIBUTES_TITLE, x + TITLE_BOOK_OFFSET_X + 206, y + TITLE_OFFSET_Y, 4210752, false);
        context.drawText(this.textRenderer, REWARDS_TITLE, x + TITLE_BOOK_OFFSET_X + 98, y + TITLE_OFFSET_Y + 66, 4210752, false);
    }

    private void drawSkill(DrawContext context, int x, int y) {
        SkillBookScreen.drawSkillIcon(context, this.textRenderer, x + 117, y + 26, this.skill);
        SkillBookScreen.drawProgressBar(context, SKILL_BAR_FULL, x + 112, y + 48, this.skill, false);
        this.drawSkillRewards(context, x + 111, y + 85);
    }

    private void drawSkillRewards(DrawContext context, int x, int y) {
        int level = this.skill.level;
        String text = "§eSpelunker " +  (level + 1) + "\n" +
                "  §6+§8" + (4 * level) + "➡§6" + (4 * (level + 1))  + " ☘ \n" +
                "§8+§a1 ❇ Defence";

        drawText(context, x, y, text.split("\n"));
    }

    private void drawText(DrawContext context, int x, int y, String[] text) {
        for (int i = 0; i < text.length; i++) {
            context.drawText(this.textRenderer, Text.literal(text[i]), x, y + (12 * i), 0, false);
        }
    }

    private void drawAttributes(DrawContext context, int x, int y) {
        AttributeContainer attributes = this.player.getAttributes(); Text text;
        ArrayList<RegistryEntry<EntityAttribute>> attribute = this.skill.category.attributes;
        for (int i = 0; i < attribute.size(); i++) {
            if (attribute.get(i).equals(ModAttributes.MINING_SPEED)) {
                text = Text.literal(getTranslation(attribute.get(i)) + " §f" + Math.round(attributes.getValue(attribute.get(i)) + getMiningSpeed()));
            } else {
                text = Text.literal(getTranslation(attribute.get(i)) + " §f" + Math.round(attributes.getValue(attribute.get(i))));
            }
            context.drawText(this.textRenderer, text, x, y + (12 * i), 16777215, true);
        }
    }

    private int getAddition(String type) {
        if (type.equals("speed")) {
            return getMiningSpeed();
        }
        return 0;
    }

    private int getMiningSpeed() {
        ItemStack stack = this.player.getInventory().getSelectedStack();
        if (stack.isOf(ModItems.MOD_PICKAXE)) {
            return (int) Objects.requireNonNull(stack.get(ModDataComponentTypes.MOD_TOOL)).getSpeedRaw();
        }
        return 0;
    }

    private String getTranslation(RegistryEntry<EntityAttribute> attribute) {
        return Text.translatable(attribute.value().getTranslationKey()).getString();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (PixelSkillsClient.OPEN_STATS_MENU_KB.matchesKey(keyCode, scanCode)) {
            this.close();
            return true;
        } else {
            return super.keyPressed(keyCode, scanCode, modifiers);
        }
    }
}
