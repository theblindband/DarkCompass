package net.theblindbandit6.darkcompass;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.theblindbandit6.darkcompass.datagen.ModModelProvider;

public class DarkCompassDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		//Only a model generator was used for this mod to generate the models for the dark compass item
		pack.addProvider(ModModelProvider::new);

	}
}
