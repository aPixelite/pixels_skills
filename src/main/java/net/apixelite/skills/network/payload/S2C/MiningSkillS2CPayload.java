package net.apixelite.skills.network.payload.S2C;

import net.apixelite.skills.PixelsSkills;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record MiningSkillS2CPayload(int exp, int level) implements CustomPayload {
    public static final Identifier MINING_SKILL_PAYLOAD_ID = Identifier.of(PixelsSkills.MOD_ID, "mining_skill_payload");
    public static final CustomPayload.Id<MiningSkillS2CPayload> ID = new CustomPayload.Id<>(MINING_SKILL_PAYLOAD_ID);
    public static final PacketCodec<RegistryByteBuf, MiningSkillS2CPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, MiningSkillS2CPayload::exp,
            PacketCodecs.INTEGER, MiningSkillS2CPayload::level,
            MiningSkillS2CPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
