package net.apixelite.skills.datagen;

import net.apixelite.skills.PixelsSkills;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.ConsumeItemCriterion;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementsProvider extends FabricAdvancementProvider {
    public ModAdvancementsProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generateAdvancement(RegistryWrapper.WrapperLookup wrapperLookup, Consumer<AdvancementEntry> consumer) {
        AdvancementEntry getDirt = Advancement.Builder.create()
                .display(
                        Items.DIRT, // The display icon
                        Text.literal("Your First Dirt Block"), // The title
                        Text.literal("Now make a house from it"), // The description
                        Identifier.ofVanilla("gui/advancements/backgrounds/stone"), // Background image for the tab in the advancements page, if this is a root advancement (has no parent)
                        AdvancementFrame.TASK, // TASK, CHALLENGE, or GOAL
                        true, // Show the toast when completing it
                        true, // Announce it to chat
                        false // Hide it in the advancement tab until it's achieved
                )
                // "got_dirt" is the name referenced by other advancements when they want to have "requirements."
                .criterion("got_dirt", InventoryChangedCriterion.Conditions.items(Items.DIRT))
                // Give the advancement an id
                .build(consumer, PixelsSkills.MOD_ID + ":get_dirt");
        final RegistryWrapper.Impl<Item> itemLookup = wrapperLookup.getOrThrow(RegistryKeys.ITEM);
        AdvancementEntry appleAndBeef = Advancement.Builder.create()
                .parent(getDirt)
                .display(
                        Items.APPLE,
                        Text.literal("Apple and Beef"),
                        Text.literal("Ate an apple and beef"),
                        null, // Children don't need a background, the root advancement takes care of that
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        false
                )
                .criterion("ate_apple", ConsumeItemCriterion.Conditions.item(itemLookup, Items.APPLE))
                .criterion("ate_cooked_beef", ConsumeItemCriterion.Conditions.item(itemLookup, Items.COOKED_BEEF))
                .build(consumer, PixelsSkills.MOD_ID + ":apple_and_beef");
    }
}
