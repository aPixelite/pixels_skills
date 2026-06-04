package net.apixelite.skills;

import net.apixelite.skills.attributes.stats.ModAttributes;
import net.apixelite.skills.block.ModBlocks;
import net.apixelite.skills.command.ModArgumentTypes;
import net.apixelite.skills.command.ModCommands;
import net.apixelite.skills.item.components.ModDataComponentTypes;
import net.apixelite.skills.item.ModItemGroups;
import net.apixelite.skills.item.ModItems;
import net.apixelite.skills.network.payload.ModPayloads;
import net.apixelite.skills.skills.icons.Icons;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PixelsSkills implements ModInitializer {
	public static final String MOD_ID = "pixels_skills";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		LOGGER.info("Loading Pixels skills mod!");

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		Icons.registerSkillIcons();
		ModItemGroups.registerItemGroups();

		ModArgumentTypes.registerArgumentTypes();
		ModCommands.registerModCommands();

		ModPayloads.registerS2CPayloads();

		ModAttributes.registerModAttributes();

		ModDataComponentTypes.registerModDataComponentTypes();

	}
}