package net.apixelite.skills.network.payload.S2C;

import net.apixelite.skills.PixelsSkills;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record FishingSkillS2CPayload(int exp, int level) implements CustomPayload {
    public static final Identifier FISHING_SKILL_PAYLOAD_ID = Identifier.of(PixelsSkills.MOD_ID, "fishing_skill_payload");
    public static final CustomPayload.Id<FishingSkillS2CPayload> ID = new CustomPayload.Id<>(FISHING_SKILL_PAYLOAD_ID);
    public static final PacketCodec<RegistryByteBuf, FishingSkillS2CPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, FishingSkillS2CPayload::exp,
            PacketCodecs.INTEGER, FishingSkillS2CPayload::level,
            FishingSkillS2CPayload::new
    );

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }
}
