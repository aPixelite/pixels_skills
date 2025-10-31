package net.apixelite.skills.network.payload.S2C;

import net.apixelite.skills.PixelsSkills;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record CombatSkillS2CPayload(int exp, int level) implements CustomPayload {
    public static final Identifier COMBAT_SKILL_PAYLOAD_ID = Identifier.of(PixelsSkills.MOD_ID, "combat_skill_payload");
    public static final CustomPayload.Id<CombatSkillS2CPayload> ID = new CustomPayload.Id<>(COMBAT_SKILL_PAYLOAD_ID);
    public static final PacketCodec<RegistryByteBuf, CombatSkillS2CPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, CombatSkillS2CPayload::exp,
            PacketCodecs.INTEGER, CombatSkillS2CPayload::level,
            CombatSkillS2CPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
