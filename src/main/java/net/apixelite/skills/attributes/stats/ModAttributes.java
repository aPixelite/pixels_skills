package net.apixelite.skills.attributes.stats;

import net.apixelite.skills.PixelsSkills;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class ModAttributes {

    public static final Category attributes = new Category();

    public static final Category MINING = new Category();
    public static final Category COMBAT = new Category();
    public static final Category FARMING = new Category();
    public static final Category FORAGING = new Category();
    public static final Category FISHING = new Category();
    public static final Category EXPLORING = new Category();

    // Combat
    public static final RegistryEntry<EntityAttribute> HEALTH = register("health", 100.0d, COMBAT);
    public static final RegistryEntry<EntityAttribute> DEFENCE = register("defence", 0.0d, COMBAT);
    public static final RegistryEntry<EntityAttribute> DAMAGE = register("damage", 0.0d, COMBAT);
    public static final RegistryEntry<EntityAttribute> STRENGTH = register("strength", 0.0d, COMBAT);
    public static final RegistryEntry<EntityAttribute> CRIT_DAMAGE = register("crit_damage", 50.0d, COMBAT);
    public static final RegistryEntry<EntityAttribute> CRIT_CHANCE = register("crit_chance", 30.0d, COMBAT);
    public static final RegistryEntry<EntityAttribute> ATTACK_SPEED = register("attack_speed", 0.0d, COMBAT);

    // Mining
    public static final RegistryEntry<EntityAttribute> MINING_SPEED = register("mining_speed", 0.0d, MINING);
    public static final RegistryEntry<EntityAttribute> MINING_FORTUNE = register("mining_fortune", 0.0d, MINING);
    public static final RegistryEntry<EntityAttribute> BLOCK_MINING_SPREAD = register("block_mining_spread", 0.0d, MINING);
    public static final RegistryEntry<EntityAttribute> ORE_MINING_SPREAD = register("ore_mining_spread", 0.0d, MINING);
    public static final RegistryEntry<EntityAttribute> BREAKING_POWER = register("breaking_power", 0.0d, MINING);

    // Farming
    public static final RegistryEntry<EntityAttribute> FARMING_FORTUNE = register("farming_fortune", 0.0d, FARMING);

    // Foraging
    public static final RegistryEntry<EntityAttribute> FORAGING_FORTUNE = register("foraging_fortune", 0.0d, FORAGING);
    public static final RegistryEntry<EntityAttribute> SWEEP = register("sweep", 0.0d, FORAGING);

    // Fishing
    public static final RegistryEntry<EntityAttribute> FISHING_SPEED = register("fishing_speed", 0.0d, FISHING);
    public static final RegistryEntry<EntityAttribute> TREASURE_CHANCE = register("treasure_chance", 0.0d, FISHING);
    public static final RegistryEntry<EntityAttribute> SEA_CREATURE_CHANCE = register("sea_creature_chance", 0.0d, FISHING);

    public static final RegistryEntry<EntityAttribute> RESPIRATION = register("respiration", 0.0d, EXPLORING);
    public static final RegistryEntry<EntityAttribute> SPEED = register("speed", 100.0d, EXPLORING);

    private static RegistryEntry<EntityAttribute> register(String id, double min, Category category) {
        RegistryEntry<EntityAttribute> attribute = Registry.registerReference(Registries.ATTRIBUTE, Identifier.of(PixelsSkills.MOD_ID, id),
                new ClampedEntityAttribute("attribute." + id, min, min, 1000000.0d));
        attributes.add(attribute);
        category.add(attribute);
        return attribute;
    }

    public static void registerModAttributes() {
        PixelsSkills.LOGGER.info("Registering Mod Attributes for " + PixelsSkills.MOD_ID);
    }

    public static class Category {
        public ArrayList<RegistryEntry<EntityAttribute>> attributes;

        public Category() {
            this.attributes = new ArrayList<>();
        }

        public void add(RegistryEntry<EntityAttribute> attribute) {
            this.attributes.add(attribute);
        }

        public ArrayList<RegistryEntry<EntityAttribute>> getList() {
            return this.attributes;
        }
    }
}
