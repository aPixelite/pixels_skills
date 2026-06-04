package net.apixelite.skills.network.payload;

import net.apixelite.skills.network.payload.S2C.*;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public class ModPayloads {

    public static void registerS2CPayloads() {
        PayloadTypeRegistry.playS2C().register(CombatSkillS2CPayload.ID, CombatSkillS2CPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(MiningSkillS2CPayload.ID, MiningSkillS2CPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(ForagingSkillS2CPayload.ID, ForagingSkillS2CPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(FarmingSkillS2CPayload.ID, FarmingSkillS2CPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(FishingSkillS2CPayload.ID, FishingSkillS2CPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(ExploringSkillS2CPayload.ID, ExploringSkillS2CPayload.CODEC);
    }

}
