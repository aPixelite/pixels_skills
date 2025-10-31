package net.apixelite.skills.network.payload.S2C;

import net.apixelite.skills.PixelsSkills;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record ForagingSkillS2CPayload(int exp, int level) implements CustomPayload {
    public static final Identifier FORAGING_SKILL_PAYLOAD_ID = Identifier.of(PixelsSkills.MOD_ID, "foraging_skill_payload");
    public static final CustomPayload.Id<ForagingSkillS2CPayload> ID = new CustomPayload.Id<>(FORAGING_SKILL_PAYLOAD_ID);
    public static final PacketCodec<RegistryByteBuf, ForagingSkillS2CPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, ForagingSkillS2CPayload::exp,
            PacketCodecs.INTEGER, ForagingSkillS2CPayload::level,
            ForagingSkillS2CPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
