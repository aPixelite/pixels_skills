package net.apixelite.skills.util;

import net.apixelite.skills.network.payload.S2C.*;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Arrays;

public class SkillData {

    public static int max_level = 50;

    public static int combat_exp = 0;
    public static int combat_level = 0;
    public static int mining_exp = 0;
    public static int mining_level = 0;
    public static int foraging_exp = 0;
    public static int foraging_level = 0;
    public static int farming_exp = 0;
    public static int farming_level = 0;
    public static int fishing_exp = 0;
    public static int fishing_level = 0;
    public static int exploring_exp = 0;
    public static int exploring_level = 0;

    public static void addExp(ServerPlayerEntity player, int amount, SkillDataKeys key) {
        NbtCompound nbt = ((IEntityDataSaver) player).getPersistentData();
        int exp = nbt.getInt(key.exp, 0);
        int level = nbt.getInt(key.level, 0);

        while (exp + amount >= getExpToNextLevel(level) && level < max_level) {
            amount -= getExpToNextLevel(level);
            level += 1;
            levelUp(player, level, key);
        }

        exp += amount;

        nbt.putInt(key.exp, exp);
        nbt.putInt(key.level, level);
        syncSkillData(exp, level, player, key);

        double percentage = ((float) exp / getExpToNextLevel(level)) * 100F;

        // Text: "0/50 (0.0%)"
        player.sendMessage(Text.literal(exp + "/" + getExpToNextLevel(level) + " (" + String.format("%.1f", percentage) + "%)").formatted(Formatting.DARK_AQUA), true);
    }

    public static void setExp(ServerPlayerEntity player, int amount, SkillDataKeys key) {
        NbtCompound nbt = ((IEntityDataSaver) player).getPersistentData();
        nbt.putInt(key.exp, amount);
        int level = nbt.getInt(key.level, 0);
        syncSkillData(amount, level, player, key);
    }
    public static void setLevel(ServerPlayerEntity player, int amount, SkillDataKeys key) {
        NbtCompound nbt = ((IEntityDataSaver) player).getPersistentData();
        int exp = nbt.getInt(key.exp, 0);
        nbt.putInt(key.level, amount);
        syncSkillData(exp, amount, player, key);
    }

    public static void levelUp(ServerPlayerEntity player, int level, SkillDataKeys key) {
        float f = level > 30 ? 1.0F : level / 30.0F;
        // Text: "SKILL LEVEL UP Combat 0 → 1"
        player.sendMessage(Text.literal("§bSKILL LEVEL UP §3" + getNameFromKey(key) + " §8" + (level - 1) + " → §r§3" + level));
        player.playSoundToPlayer(SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.PLAYERS, f * 0.75F, 1.0F);
    }

    public static void resetAllData(ServerPlayerEntity player) {
        for (SkillDataKeys key : SkillDataKeys.values()) {
            resetData(player, key);
        }
    }

    public static void resetData(ServerPlayerEntity player, SkillDataKeys key) {
        NbtCompound nbt = ((IEntityDataSaver) player).getPersistentData();
        nbt.putInt(key.exp, 0);
        nbt.putInt(key.level, 0);
        syncSkillData(0, 0, player, key);
    }

    public static void syncSkillData(int exp, int level, ServerPlayerEntity player, SkillDataKeys key) {
        switch (key) {
            case COMBAT -> {
                combat_exp = exp;
                combat_level = level;
                CombatSkillS2CPayload payload = new CombatSkillS2CPayload(mining_exp, mining_level);
                ServerPlayNetworking.send(player, payload);
            }
            case MINING -> {
                mining_exp = exp;
                mining_level = level;
                MiningSkillS2CPayload payload = new MiningSkillS2CPayload(mining_exp, mining_level);
                ServerPlayNetworking.send(player, payload);
            }
            case FORAGING -> {
                foraging_exp = exp;
                foraging_level = level;
                ForagingSkillS2CPayload payload = new ForagingSkillS2CPayload(mining_exp, mining_level);
                ServerPlayNetworking.send(player, payload);
            }
            case FARMING -> {
                farming_exp = exp;
                farming_level = level;
                FarmingSkillS2CPayload payload = new FarmingSkillS2CPayload(mining_exp, mining_level);
                ServerPlayNetworking.send(player, payload);
            }
            case FISHING -> {
                fishing_exp = exp;
                fishing_level = level;
                FishingSkillS2CPayload payload = new FishingSkillS2CPayload(mining_exp, mining_level);
                ServerPlayNetworking.send(player, payload);
            }
            case EXPLORING -> {
                exploring_exp = exp;
                exploring_level = level;
                ExploringSkillS2CPayload payload = new ExploringSkillS2CPayload(mining_exp, mining_level);
                ServerPlayNetworking.send(player, payload);
            }
        }
    }

    public static int getExpToNextLevel(int level) {
        // y = 50 + 25 * (x)^2
        return (int) (50 + (25 * Math.pow(level, 2)));
    }

    public static void giveFarmingExp(World world, BlockState state, BlockPos pos, PlayerEntity player) {
        ServerPlayerEntity serverPlayer = ((ServerPlayerEntity) world.getPlayerByUuid(player.getUuid()));
        int amount = 0;

        if (state.isIn(ModTags.Blocks.CROPS)) {
            int[] age = getAge(state);
            if (age[0] == age[1]) {
                amount = 2;
            }
        }
        else if (state.isIn(ModTags.Blocks.TALL_CROPS)) {
            if (state.getBlock() instanceof WeepingVinesBlock) {
                return;
            }
            amount = getBlocksAbove(world, state, pos) * 2;
        } else if (state.isOf(Blocks.WILDFLOWERS) || state.isOf(Blocks.PINK_PETALS)) {
            amount = state.get(FlowerbedBlock.FLOWER_AMOUNT);
        } else if (state.isIn(ModTags.Blocks.FLOWERS)) {
            amount = 2;
        } else {
            int i = ((int) state.getBlock().getHardness());
            amount = i > 0 ? i * 4 : 1;
        }

        addExp(serverPlayer, amount, SkillDataKeys.FARMING);

    }

    public static int getBlocksAbove(World world, BlockState state, BlockPos initialBlockPos) {
        BlockPos pos = new BlockPos(initialBlockPos.getX(), initialBlockPos.getY() + 1, initialBlockPos.getZ());
        int i = 1;
        while (world.getBlockState(pos).getBlock().equals(state.getBlock())) {
            i += 1;
            pos = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ());
        }

        return i;

    }

    public static int[] getAge(BlockState state) {
        int[] age = new int[2];

         if (state.getBlock() instanceof BeetrootsBlock) {
             age[0] = ((BeetrootsBlock) state.getBlock()).getAge(state);
             age[1] = 3;
         }
         else if (state.getBlock() instanceof NetherWartBlock) {
             age[0] = state.get(NetherWartBlock.AGE);
             age[1] = 3;
         }
         else if (state.getBlock() instanceof SweetBerryBushBlock) {
             age[0] = state.get(SweetBerryBushBlock.AGE);
             age[1] = 3;
         }
         else if (state.getBlock() instanceof PitcherCropBlock) {
             age[0] = state.get(PitcherCropBlock.AGE);
             age[1] = 4;
         }
         else if (state.getBlock() instanceof CropBlock) {
             age[0] = ((CropBlock) state.getBlock()).getAge(state);
             age[1] = 7;
         }

        return age;
    }

    public static void giveCombatExp(ServerPlayerEntity player, ServerWorld world, LivingEntity entity) {
        double mod = 0.5;
        int difficulty = world.getLevelProperties().getDifficulty().getId();
        if (entity instanceof HostileEntity) {
            mod = 0.75 * difficulty;
        }

        int amount = (int) (entity.getMaxHealth() * mod);
        addExp(player, amount, SkillDataKeys.COMBAT);

    }

    public static String getNameFromKey(SkillDataKeys key) {
        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = key.name().toCharArray();

        char firstChar = chars[0];
        chars = Arrays.toString(chars).toLowerCase().toCharArray();
        stringBuilder.append(firstChar);
        for (int i = 4; i < chars.length; i += 3) {
            stringBuilder.append(chars[i]);
        }
        return stringBuilder.toString();
    }

}
