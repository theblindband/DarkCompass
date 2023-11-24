package net.theblindbandit6.darkcompass;

import net.fabricmc.api.ModInitializer;

import net.theblindbandit6.darkcompass.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DarkCompass implements ModInitializer {
	public static final String MOD_ID = "darkcompass";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		LOGGER.info("Initializing Dark Compass");
	}
}