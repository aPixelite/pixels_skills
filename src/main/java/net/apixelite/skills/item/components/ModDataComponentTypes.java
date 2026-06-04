package net.apixelite.skills.item.components;

import net.apixelite.skills.PixelsSkills;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.function.UnaryOperator;

public class ModDataComponentTypes {
    public static final ComponentType<ModToolComponent> MOD_TOOL = register(
            "mod_tool", builder -> builder.codec(ModToolComponent.CODEC).packetCodec(ModToolComponent.PACKET_CODEC).cache()
    );

    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, id, builderOperator.apply( ComponentType.builder()).build());
    }

    public static void registerModDataComponentTypes() {
        PixelsSkills.LOGGER.info("Registering Mod Data Component Types for " + PixelsSkills.MOD_ID);
    }
}
