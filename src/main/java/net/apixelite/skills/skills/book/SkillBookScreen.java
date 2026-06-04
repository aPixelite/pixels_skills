package net.apixelite.skills.skills.book;

import net.apixelite.skills.PixelSkillsClient;
import net.apixelite.skills.PixelsSkills;
import net.apixelite.skills.attributes.gui.AttributeScreen;
import net.apixelite.skills.skills.SkillData;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ThreePartsLayoutWidget;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class SkillBookScreen extends Screen {
    private static final Identifier WINDOW_TEXTURE = Identifier.of(PixelsSkills.MOD_ID, "textures/gui/skill_menu.png");
    private static final Identifier SKILL_BG = Identifier.of(PixelsSkills.MOD_ID, "textures/gui/skill_icon_bg.png");
    private static final Identifier SKILL_BAR_BG = Identifier.of(PixelsSkills.MOD_ID, "textures/gui/skill_bar_bg.png");
    private static final Identifier SKILL_BAR_PROGRESS = Identifier.of(PixelsSkills.MOD_ID, "textures/gui/skill_bar_full.png");
    private static final Identifier SKILL_ICON_MAXED = Identifier.of(PixelsSkills.MOD_ID, "textures/gui/skill_icon_max.png");


    public static final int WINDOW_WIDTH = 286;
    public static final int WINDOW_HEIGHT = 138;
    private static final int TITLE_PLAYER_OFFSET_X = 8;
    private static final int TITLE_BOOK_OFFSET_X = 107;
    private static final int TITLE_OFFSET_Y = 6;
    private static final int TEXTURE_WIDTH = 286;
    private static final int TEXTURE_HEIGHT = 138;
    private static final Text SKILL_BOOK_TEXT = Text.translatable("gui.pixelskills.skill_book.book");
    private static final Text PLAYER_TEXT = Text.translatable("gui.pixelskills.skill_book.player");
    private final ThreePartsLayoutWidget layout = new ThreePartsLayoutWidget(this);

    @Nullable
    private final Screen parent;
    private float mouseX;
    private float mouseY;
    private ClientPlayerEntity player;

    private static SkillData.ESkillDataKeys[] skills;

    public SkillBookScreen(@Nullable Screen parent) {
        super(SKILL_BOOK_TEXT);
        this.parent = parent;
    }

    @Override
    protected void init() {
        this.layout.addHeader(SKILL_BOOK_TEXT, this.textRenderer);
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

        updateSkillData();

        this.drawWindow(context, x, y);
        this.drawSkills(context, x, y);
    }

    public void drawWindow(DrawContext context, int x, int y) {
        context.drawTexture(RenderLayer::getGuiTextured, WINDOW_TEXTURE, x, y, 0.0F, 0.0F, WINDOW_WIDTH, WINDOW_HEIGHT, TEXTURE_WIDTH, TEXTURE_HEIGHT);
        context.drawText(this.textRenderer, PLAYER_TEXT, x + TITLE_PLAYER_OFFSET_X, y + TITLE_OFFSET_Y, 4210752, false);
        context.drawText(this.textRenderer, SKILL_BOOK_TEXT, x + TITLE_BOOK_OFFSET_X, y + TITLE_OFFSET_Y, 4210752, false);

        InventoryScreen.drawEntity(context, x + 9, y + 18, x + 88, y + 131, 45, 0.0625F, this.mouseX, this.mouseY, this.player);
            }

    public void drawSkills(DrawContext context, int x, int y) {
        // Icons
        for (int i = 0; i < 2; i += 1) {
            for (int j = 0; j < 3; j += 1) {
                drawSkill(context, x + 82 * i, y + 36 * j, skills[i * 3 + j]);
            }
        }
    }

    public void drawSkill(DrawContext context, int x, int y, SkillData.ESkillDataKeys skill) {
        this.drawButton(x + 115, y + 24, skill);
        context.drawTexture(RenderLayer::getGuiTextured, SKILL_BG, x + 113, y + 22, 0.0F, 0.0F, 24, 24, 24, 24);
        drawProgressBar(context, SKILL_BAR_BG, x + 112, y + 49, skill, true);

        if (skill.level >= SkillData.MAX_LEVEL) {
            context.drawTexture(RenderLayer::getGuiTextured, SKILL_ICON_MAXED, x + 114, y + 27, 0.0F, 0.0F, 24, 24, 24, 24);
            drawProgressBar(context, SKILL_BAR_PROGRESS, x + 112, y + 49, skill, true);
        }
        drawProgressBar(context, SKILL_BAR_PROGRESS, x + 112, y + 49, skill, false);

        drawSkillIcon(context, this.textRenderer, x + 117, y + 26, skill);

        this.drawTooltip(context, x + 114, y + 27, skill);
    }

    private void drawButton(int x, int y, SkillData.ESkillDataKeys skill) {
        this.addDrawableChild(ButtonWidget.builder(
                        Text.literal(""),
                        button -> {
                            assert this.client != null;
                            this.client.setScreen(new AttributeScreen(this, skill));
                        })
                .dimensions(x, y, 20, 20)
                .build());
    }

    public static void drawSkillIcon(DrawContext context, TextRenderer textRenderer, int x, int y, SkillData.ESkillDataKeys skill) {
        updateSkillData();
        context.drawItem(skill.icon, x, y);
        context.drawText(textRenderer, Text.literal("Level: " + skill.level), x + 23, y + 8, 0xFA5F6061, false);
    }

    public static void drawProgressBar(DrawContext context, Identifier sprite, int x, int y, SkillData.ESkillDataKeys skill, boolean max) {
        updateSkillData();
        int percentage = 78;

        if (!max) {
            int skill_exp = skill.exp;
            int exp_to_next_level = SkillData.getExpToNextLevel(skill.level);

            percentage = (int) (percentage * ((double) skill_exp / exp_to_next_level));
        }

        context.drawTexture(RenderLayer::getGuiTextured, sprite, x, y, 0.0F, 0.0F, percentage, 5, 78, 5);

    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (PixelSkillsClient.OPEN_SKILL_BOOK_KB.matchesKey(keyCode, scanCode)) {
            this.close();
            return true;
        } else {
            return super.keyPressed(keyCode, scanCode, modifiers);
        }
    }

    private void drawTooltip(DrawContext context, int x, int y, SkillData.ESkillDataKeys skill) {
        if (mouseOnIcon(x, y)) {
            context.drawItemTooltip(this.textRenderer, skill.icon, (int) this.mouseX, (int) this.mouseY);
        }
    }

    private boolean mouseOnIcon(int x, int y) {
        int mouseX = ((int) this.mouseX);
        int mouseY = ((int) this.mouseY);

        return x <= mouseX && mouseX <= (x + 24) &&
                y <= mouseY && mouseY <= (y + 24);
    }

    private static void updateSkillData() {
        skills = SkillData.ESkillDataKeys.values();
    }
}
