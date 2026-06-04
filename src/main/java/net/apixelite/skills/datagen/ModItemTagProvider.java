package net.apixelite.skills.datagen;

import net.apixelite.skills.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ModTags.Items.FISHING_JUNK)
                .add(Items.LILY_PAD)
                .add(Items.BAMBOO)
                .add(Items.BONE)
                .add(Items.BOWL)
                .add(Items.LEATHER)
                .add(Items.LEATHER_BOOTS)
                .add(Items.ROTTEN_FLESH)
                .add(Items.TRIPWIRE_HOOK)
                .add(Items.STICK)
                .add(Items.STRING)
                .add(Items.INK_SAC)
        ;

        getOrCreateTagBuilder(ModTags.Items.FISHING_TREASURE)
                .add(Items.BOW)
                .add(Items.ENCHANTED_BOOK)
                .add(Items.NAME_TAG)
                .add(Items.NAUTILUS_SHELL)
                .add(Items.SADDLE)
        ;
    }
}
