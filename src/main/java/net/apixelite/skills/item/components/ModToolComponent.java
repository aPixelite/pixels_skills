package net.apixelite.skills.item.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.RegistryCodecs;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.util.dynamic.Codecs;

import java.util.List;
import java.util.Optional;

public record ModToolComponent(List<ModToolComponent.Rule> rules, float defaultMiningSpeed, int damagePerBlock, boolean canDestroyBlocksInCreative) {
    public static final Codec<ModToolComponent> CODEC = RecordCodecBuilder.create(
            (instance) -> instance.group(
                    ModToolComponent.Rule.CODEC.listOf().fieldOf("rules").forGetter(ModToolComponent::rules),
                    Codec.FLOAT.optionalFieldOf("default_mining_speed", 1.0F).forGetter(ModToolComponent::defaultMiningSpeed),
                    Codecs.NON_NEGATIVE_INT.optionalFieldOf("damage_per_block", 1).forGetter(ModToolComponent::damagePerBlock),
                    Codec.BOOL.optionalFieldOf("can_destroy_blocks_in_creative", true).forGetter(ModToolComponent::canDestroyBlocksInCreative)
            ).apply(instance, ModToolComponent::new));
    public static final PacketCodec<RegistryByteBuf, ModToolComponent> PACKET_CODEC;

    public ModToolComponent(List<ModToolComponent.Rule> rules, float defaultMiningSpeed, int damagePerBlock, boolean canDestroyBlocksInCreative) {
        this.rules = rules;
        this.defaultMiningSpeed = defaultMiningSpeed;
        this.damagePerBlock = damagePerBlock;
        this.canDestroyBlocksInCreative = canDestroyBlocksInCreative;
    }

    public float getSpeed(BlockState state) {
        for (ModToolComponent.Rule rule : this.rules) {
            if (rule.speed.isPresent() && state.isIn(rule.blocks)) {
                return rule.speed.get();
            }
        }

        return this.defaultMiningSpeed;
    }

    public float getSpeedRaw() {
        for (ModToolComponent.Rule rule : this.rules) {
            if (rule.speed.isPresent()) {
                return rule.speed.get();
            }
        }

        return this.defaultMiningSpeed;
    }

    public boolean isCorrectForDrops(BlockState state) {
        for (ModToolComponent.Rule rule : this.rules) {
            if (rule.correctForDrops.isPresent() && state.isIn(rule.blocks)) {
                return (Boolean)rule.correctForDrops.get();
            }
        }

        return false;
    }

    public List<ModToolComponent.Rule> rules() {
        return this.rules;
    }

    public float defaultMiningSpeed() {
        return this.defaultMiningSpeed;
    }

    public int damagePerBlock() {
        return this.damagePerBlock;
    }

    public boolean canDestroyBlocksInCreative() {
        return this.canDestroyBlocksInCreative;
    }

    static {
        PACKET_CODEC = PacketCodec.tuple(
                ModToolComponent.Rule.PACKET_CODEC.collect(PacketCodecs.toList()),
                ModToolComponent::rules,
                PacketCodecs.FLOAT,
                ModToolComponent::defaultMiningSpeed,
                PacketCodecs.VAR_INT,
                ModToolComponent::damagePerBlock,
                PacketCodecs.BOOLEAN,
                ModToolComponent::canDestroyBlocksInCreative,
                ModToolComponent::new);
    }

    public record Rule(RegistryEntryList<net.minecraft.block.Block> blocks, Optional<Float> speed, Optional<Boolean> correctForDrops) {
        public static final Codec<ModToolComponent.Rule> CODEC = RecordCodecBuilder.create((instance) ->
             instance.group(
                    RegistryCodecs.entryList(RegistryKeys.BLOCK).fieldOf("blocks").forGetter(ModToolComponent.Rule::blocks),
                    Codecs.POSITIVE_FLOAT.optionalFieldOf("speed").forGetter(ModToolComponent.Rule::speed),
                    Codec.BOOL.optionalFieldOf("correct_for_drops").forGetter(ModToolComponent.Rule::correctForDrops)
            ).apply(instance, ModToolComponent.Rule::new));
        public static final PacketCodec<RegistryByteBuf, ModToolComponent.Rule> PACKET_CODEC;

        public Rule(RegistryEntryList<net.minecraft.block.Block> blocks, Optional<Float> speed, Optional<Boolean> correctForDrops) {
            this.blocks = blocks;
            this.speed = speed;
            this.correctForDrops = correctForDrops;
        }

        public static ModToolComponent.Rule ofAlwaysDropping(RegistryEntryList<net.minecraft.block.Block> blocks, float speed) {
            return new ModToolComponent.Rule(blocks, Optional.of(speed), Optional.of(true));
        }

        public static ModToolComponent.Rule ofNeverDropping(RegistryEntryList<net.minecraft.block.Block> blocks) {
            return new ModToolComponent.Rule(blocks, Optional.empty(), Optional.of(false));
        }

        public static ModToolComponent.Rule of(RegistryEntryList<net.minecraft.block.Block> blocks, float speed) {
            return new ModToolComponent.Rule(blocks, Optional.of(speed), Optional.empty());
        }

        public RegistryEntryList<Block> blocks() {
            return this.blocks;
        }

        public Optional<Float> speed() {
            return this.speed;
        }

        public Optional<Boolean> correctForDrops() {
            return this.correctForDrops;
        }

        static {
            PACKET_CODEC = PacketCodec.tuple(
                    PacketCodecs.registryEntryList(RegistryKeys.BLOCK),
                    ModToolComponent.Rule::blocks,
                    PacketCodecs.FLOAT.collect(PacketCodecs::optional),
                    ModToolComponent.Rule::speed,
                    PacketCodecs.BOOLEAN.collect(PacketCodecs::optional),
                    ModToolComponent.Rule::correctForDrops, ModToolComponent.Rule::new);
        }
    }
}
