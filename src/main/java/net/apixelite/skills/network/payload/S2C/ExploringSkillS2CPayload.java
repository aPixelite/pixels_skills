package net.apixelite.skills.network.payload.S2C;

import net.apixelite.skills.PixelsSkills;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record ExploringSkillS2CPayload(int exp, int level) implements CustomPayload {
    public static final Identifier EXPLORING_SKILL_PAYLOAD_ID = Identifier.of(PixelsSkills.MOD_ID, "exploring_skill_payload");
    public static final CustomPayload.Id<ExploringSkillS2CPayload> ID = new CustomPayload.Id<>(EXPLORING_SKILL_PAYLOAD_ID);
    public static final PacketCodec<RegistryByteBuf, ExploringSkillS2CPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, ExploringSkillS2CPayload::exp,
            PacketCodecs.INTEGER, ExploringSkillS2CPayload::level,
            ExploringSkillS2CPayload::new
    );


    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
