package net.apixelite.skills;
import net.apixelite.skills.attributes.gui.AttributeScreen;
import net.apixelite.skills.network.payload.S2C.*;
import net.apixelite.skills.skills.book.SkillBookScreen;
import net.apixelite.skills.skills.SkillData;
import net.apixelite.skills.util.TooltipHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class PixelSkillsClient implements ClientModInitializer {

    public static final KeyBinding OPEN_SKILL_BOOK_KB = new KeyBinding(
            "key.pixelskills.open.skills",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_K,
            "category.pixelskills.skills"
    );
    public static final KeyBinding OPEN_STATS_MENU_KB = new KeyBinding(
            "key.pixelskills.open.stats",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_J,
            "category.pixelskills.stats"
    );

    private void onOpenKeyPress(Screen menu) {
        if (MinecraftClient.getInstance().currentScreen instanceof Screen screen) {
            screen.close();
        } else {
            MinecraftClient.getInstance().setScreen(menu);
        }
    }

    @Override
    public void onInitializeClient() {

        ItemTooltipCallback.EVENT.register((stack, context, tooltipType, list) -> TooltipHelper.appendTooltip(stack, list));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (OPEN_SKILL_BOOK_KB.wasPressed()) {
                onOpenKeyPress(new SkillBookScreen(null));
            }
            else if (OPEN_STATS_MENU_KB.wasPressed()) {
                onOpenKeyPress(new AttributeScreen(null, SkillData.ESkillDataKeys.MINING));
            }
        });

        ClientPlayNetworking.registerGlobalReceiver(CombatSkillS2CPayload.ID, (payload, context) -> {});
        ClientPlayNetworking.registerGlobalReceiver(MiningSkillS2CPayload.ID, (payload, context) -> {});
        ClientPlayNetworking.registerGlobalReceiver(ForagingSkillS2CPayload.ID, (payload, context) -> {});
        ClientPlayNetworking.registerGlobalReceiver(FarmingSkillS2CPayload.ID, (payload, context) -> {});
        ClientPlayNetworking.registerGlobalReceiver(FishingSkillS2CPayload.ID, (payload, context) -> {});
        ClientPlayNetworking.registerGlobalReceiver(ExploringSkillS2CPayload.ID, (payload, context) -> {});


    }
}
