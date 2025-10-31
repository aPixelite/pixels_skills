package net.apixelite.skills.skills.book;

import net.apixelite.skills.PixelSkillsClient;
import net.apixelite.skills.PixelsSkills;
import net.apixelite.skills.skills.icons.Icons;
import net.apixelite.skills.util.SkillData;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.ThreePartsLayoutWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SkillBookScreen extends Screen {
    private static final Identifier WINDOW_TEXTURE = Identifier.of(PixelsSkills.MOD_ID, "textures/gui/skill_book.png");
    private static final Identifier SKILL_BG = Identifier.of(PixelsSkills.MOD_ID, "textures/gui/skill_background.png");
    private static final Identifier SKILL_BAR_PROGRESS = Identifier.of(PixelsSkills.MOD_ID, "textures/gui/skill_bar_progress.png");
    private static final Identifier SKILL_ICON_MAXED = Identifier.of(PixelsSkills.MOD_ID, "textures/gui/skill_icon_maxed.png");


    public static final int WINDOW_WIDTH = 351;
    public static final int WINDOW_HEIGHT = 140;
    private static final int PAGE_OFFSET_X = 108;
    private static final int PAGE_OFFSET_Y = 18;
    public static final int PAGE_WIDTH = 234;
    public static final int PAGE_HEIGHT = 113;
    private static final int TITLE_PLAYER_OFFSET_X = 8;
    private static final int TITLE_BOOK_OFFSET_X = 107;
    private static final int TITLE_OFFSET_Y = 6;
    private static final int TEXTURE_WIDTH = 352;
    private static final int TEXTURE_HEIGHT = 256;
    private static final Text SKILL_BOOK_TEXT = Text.translatable("gui.pixelskills.skill_book.book");
    private static final Text PLAYER_TEXT = Text.translatable("gui.pixelskills.skill_book.player");
    private final ThreePartsLayoutWidget layout = new ThreePartsLayoutWidget(this);

    @Nullable
    private final Screen parent;
    private float mouseX;
    private float mouseY;

    private final List<ItemStack> ICON_ITEMS = new ArrayList<>();
    private List<Integer> skill_levels;
    private List<Integer> skill_experience;

    public final int max_level = 50;

    public SkillBookScreen(@Nullable Screen parent) {
        super(SKILL_BOOK_TEXT);
        this.parent = parent;
        this.ICON_ITEMS.add(Icons.COMBAT_SKILL_ICON.getDefaultStack());
        this.ICON_ITEMS.add(Icons.MINING_SKILL_ICON.getDefaultStack());
        this.ICON_ITEMS.add(Icons.FORAGING_SKILL_ICON.getDefaultStack());
        this.ICON_ITEMS.add(Icons.FARMING_SKILL_ICON.getDefaultStack());
        this.ICON_ITEMS.add(Icons.FISHING_SKILL_ICON.getDefaultStack());
        this.ICON_ITEMS.add(Icons.EXPLORING_SKILL_ICON.getDefaultStack());
    }

    @Override
    protected void init() {
        this.layout.addHeader(SKILL_BOOK_TEXT, this.textRenderer);
    }

    @Override
    public void close() {
        this.client.setScreen(this.parent);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        super.render(context, mouseX, mouseY, deltaTicks);
        int x = (this.width - WINDOW_WIDTH) / 2;
        int y = (this.height - WINDOW_HEIGHT) / 2;

        this.mouseX = mouseX;
        this.mouseY = mouseY;

        this.updateSkillData();

        this.drawWindow(context, x, y);
        this.drawSkills(context, x, y);
    }

    public void drawWindow(DrawContext context, int x, int y) {
        context.drawTexture(RenderLayer::getGuiTextured, WINDOW_TEXTURE, x, y, 0.0F, 0.0F, WINDOW_WIDTH, WINDOW_HEIGHT, TEXTURE_WIDTH, TEXTURE_HEIGHT);
        context.drawText(this.textRenderer, PLAYER_TEXT, x + TITLE_PLAYER_OFFSET_X, y + TITLE_OFFSET_Y, 4210752, false);
        context.drawText(this.textRenderer, SKILL_BOOK_TEXT, x + TITLE_BOOK_OFFSET_X, y + TITLE_OFFSET_Y, 4210752, false);

        InventoryScreen.drawEntity(context, x + 9, y + 18, x + 88, y + 131, 45, 0.0625F, this.mouseX, this.mouseY, this.client.player);
    }

    public void drawSkills(DrawContext context, int x, int y) {
        // Icons
        int k = 0;
        for (int i = 0; i < 2; i += 1) {
            for (int j = 0; j < 3; j += 1) {
                context.drawTexture(RenderLayer::getGuiTextured, SKILL_BG, x + 114, y + 27, 0.0F, 0.0F, 105, 24, 105, 24);

                if (skill_levels.get(k) >= max_level) {
                    context.drawTexture(RenderLayer::getGuiTextured, SKILL_ICON_MAXED, x + 114, y + 27, 0.0F, 0.0F, 24, 24, 24, 24);
                    this.drawProgressBar(context, x, y, k, true);
                }
                this.drawProgressBar(context, x, y, k, false);

                context.drawItem(this.ICON_ITEMS.get(k), x + 118, y + 31);
                context.drawText(this.textRenderer, Text.literal("Level: " + skill_levels.get(k)), x + 140, y + 30, 0xFA5F6061, false);

                this.drawTooltip(context, x + 114, y + 27, k);

                y += 35;
                k += 1;
            }
            x += 113;
            y -= (35 * 3);
        }
    }

    public void drawProgressBar(DrawContext context, int x, int y, int k, boolean max) {
        int percentage = 81;

        if (!max) {
            int skill_exp = skill_experience.get(k);
            int exp_to_next_level = SkillData.getExpToNextLevel(skill_levels.get(k));

            percentage = (int) (81 * ((double) skill_exp / exp_to_next_level));
        }

        context.drawTexture(RenderLayer::getGuiTextured, SKILL_BAR_PROGRESS, x + 138, y + 39, 0.0F, 0.0F, percentage, 5, 81, 5);

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

    private void drawTooltip(DrawContext context, int x, int y, int k) {
        if (mouseOnIcon(x, y)) {
            context.drawItemTooltip(this.textRenderer, this.ICON_ITEMS.get(k), (int) this.mouseX, (int) this.mouseY);

        }
    }

    private boolean mouseOnIcon(int x, int y) {
        int mouseX = ((int) this.mouseX);
        int mouseY = ((int) this.mouseY);

        return x <= mouseX && mouseX <= (x + 24) &&
                y <= mouseY && mouseY <= (y + 24);
    }

    private void updateSkillData() {
        skill_levels = new ArrayList<>();
        skill_levels.add(SkillData.combat_level);
        skill_levels.add(SkillData.mining_level);
        skill_levels.add(SkillData.foraging_level);
        skill_levels.add(SkillData.farming_level);
        skill_levels.add(SkillData.fishing_level);
        skill_levels.add(SkillData.exploring_level);

        skill_experience = new ArrayList<>();
        skill_experience.add(SkillData.combat_exp);
        skill_experience.add(SkillData.mining_exp);
        skill_experience.add(SkillData.foraging_exp);
        skill_experience.add(SkillData.farming_exp);
        skill_experience.add(SkillData.fishing_exp);
        skill_experience.add(SkillData.exploring_exp);

    }
}
