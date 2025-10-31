package net.apixelite.skills.network.payload.S2C;

import net.apixelite.skills.PixelsSkills;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record FarmingSkillS2CPayload(int exp, int level) implements CustomPayload {
    public static final Identifier FARMING_SKILL_PAYLOAD_ID = Identifier.of(PixelsSkills.MOD_ID, "farming_skill_payload");
    public static final CustomPayload.Id<FarmingSkillS2CPayload> ID = new CustomPayload.Id<>(FARMING_SKILL_PAYLOAD_ID);
    public static final PacketCodec<RegistryByteBuf, FarmingSkillS2CPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, FarmingSkillS2CPayload::exp,
            PacketCodecs.INTEGER, FarmingSkillS2CPayload::level,
            FarmingSkillS2CPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
