package net.apixelite.skills.skills;

import net.apixelite.skills.attributes.stats.ModAttributes;
import net.apixelite.skills.network.payload.S2C.*;
import net.apixelite.skills.skills.icons.Icons;
import net.apixelite.skills.util.IEntityDataSaver;
import net.apixelite.skills.util.ModTags;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Objects;

public class SkillData {

    public enum ESkillDataKeys {

        COMBAT(0, 0, Icons.COMBAT_SKILL_ICON, ModAttributes.COMBAT),
        MINING(0, 0 ,Icons.MINING_SKILL_ICON, ModAttributes.MINING),
        FORAGING(0, 0, Icons.FORAGING_SKILL_ICON, ModAttributes.FORAGING),
        FARMING(0, 0, Icons.FARMING_SKILL_ICON, ModAttributes.FARMING),
        FISHING(0, 0, Icons.FISHING_SKILL_ICON, ModAttributes.FISHING),
        EXPLORING(0, 0, Icons.EXPLORING_SKILL_ICON, ModAttributes.EXPLORING);

        public int exp;
        public int level;
        public final String type_exp;
        public final String type_level;
        public final ItemStack icon;
        public final ModAttributes.Category category;

        ESkillDataKeys(int exp, int level, Item icon, ModAttributes.Category category) {
            this.exp = exp;
            this.level = level;
            this.type_exp = this.name().toLowerCase() + "_exp";
            this.type_level = this.name().toLowerCase() + "_level";
            this.icon = icon.getDefaultStack();
            this.category = category;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public void setExp(int exp) {
            this.exp = exp;
        }
    }

    public static final int MAX_LEVEL = 50;


    public static void addExp(ServerPlayerEntity player, int amount, ESkillDataKeys key) {
        NbtCompound nbt = ((IEntityDataSaver) player).getPersistentData();
        int exp = nbt.getInt(key.type_exp, 0);
        int level = nbt.getInt(key.type_level, 0);

        while (exp + amount >= getExpToNextLevel(level) && level < MAX_LEVEL) {
            amount -= getExpToNextLevel(level);
            level += 1;
            levelUp(player, level, key);
        }

        exp += amount;

        nbt.putInt(key.type_exp, exp);
        nbt.putInt(key.type_level, level);
        syncSkillData(exp, level, player, key);

        double percentage = ((float) exp / getExpToNextLevel(level)) * 100F;

        // Text: "0/50 (0.0%)"
        player.sendMessage(Text.literal(exp + "/" + getExpToNextLevel(level) + " (" + String.format("%.1f", percentage) + "%)").formatted(Formatting.DARK_AQUA), true);
    }

    public static void setExp(ServerPlayerEntity player, int amount, ESkillDataKeys key) {
        NbtCompound nbt = ((IEntityDataSaver) player).getPersistentData();
        nbt.putInt(key.type_exp, amount);
        int level = nbt.getInt(key.type_level, 0);
        syncSkillData(amount, level, player, key);
    }
    public static void setLevel(ServerPlayerEntity player, int amount, ESkillDataKeys key) {
        NbtCompound nbt = ((IEntityDataSaver) player).getPersistentData();
        int exp = nbt.getInt(key.type_exp, 0);
        nbt.putInt(key.type_level, amount);
        syncSkillData(exp, amount, player, key);
    }

    public static void levelUp(ServerPlayerEntity player, int level, ESkillDataKeys key) {
        float f = level > 30 ? 1.0F : level / 30.0F;
        // Text: "SKILL LEVEL UP Combat 0 → 1"
        player.sendMessage(Text.literal("§bSKILL LEVEL UP §3" + key.name() + " §8" + (level - 1) + " → §r§3" + level));
        player.playSoundToPlayer(SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.PLAYERS, f * 0.75F, 1.0F);
    }

    public static void resetAllData(ServerPlayerEntity player) {
        for (ESkillDataKeys key : ESkillDataKeys.values()) {
            resetData(player, key);
        }
    }

    public static void resetData(ServerPlayerEntity player, ESkillDataKeys key) {
        NbtCompound nbt = ((IEntityDataSaver) player).getPersistentData();
        nbt.putInt(key.type_exp, 0);
        nbt.putInt(key.type_level, 0);
        syncSkillData(0, 0, player, key);
    }

    public static void syncSkillData(int exp, int level, ServerPlayerEntity player, ESkillDataKeys key) {
        key.setExp(exp);
        key.setLevel(level);
        ServerPlayNetworking.send(player, Objects.requireNonNull(getPayload(key, exp, level)));

    }

    private static CustomPayload getPayload(ESkillDataKeys key, int exp, int level) {
        switch (key) {
            case COMBAT -> {
                return new CombatSkillS2CPayload(exp, level);
            }
            case MINING -> {
                return new MiningSkillS2CPayload(exp, level);
            }
            case FORAGING -> {
                return new ForagingSkillS2CPayload(exp, level);
            }
            case FARMING -> {
                return new FarmingSkillS2CPayload(exp, level);
            }
            case FISHING -> {
                return new FishingSkillS2CPayload(exp, level);
            }
            case EXPLORING -> {
                return new ExploringSkillS2CPayload(exp, level);
            }
        }
        return null;
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

        addExp(serverPlayer, amount, ESkillDataKeys.FARMING);

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
        addExp(player, amount, ESkillDataKeys.COMBAT);

    }
}
