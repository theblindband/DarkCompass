package net.theblindbandit6.darkcompass;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.item.CompassAnglePredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;
import net.theblindbandit6.darkcompass.item.ModItems;
import net.theblindbandit6.darkcompass.item.custom.DarkCompassItem;

public class DarkCompassClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        //Client render code for the dark compass
        ModelPredicateProviderRegistry.register(ModItems.DARK_COMPASS, new Identifier("angle"), new CompassAnglePredicateProvider((world, stack, entity) -> DarkCompassItem.createDarkPos(world, stack.getOrCreateNbt())));
    }
}
