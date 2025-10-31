package net.apixelite.skills;

import net.apixelite.skills.command.ModArgumentTypes;
import net.apixelite.skills.command.ModCommands;
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

		ModArgumentTypes.registerArgumentTypes();
		ModCommands.registerModCommands();

		ModPayloads.registerS2CPayloads();

		Icons.registerSkillIcons();

	}
}