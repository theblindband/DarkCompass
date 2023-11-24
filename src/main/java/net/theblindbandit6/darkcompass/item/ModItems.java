package net.theblindbandit6.darkcompass.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.theblindbandit6.darkcompass.DarkCompass;
import net.theblindbandit6.darkcompass.item.custom.DarkCompassItem;

public class ModItems {
    //Dark Compass
    public static final Item DARK_COMPASS = registerItem("dark_compass", new DarkCompassItem(new FabricItemSettings()));
    public static final Item SPAWNER_FRAGMENT = registerItem("spawner_fragment", new Item(new FabricItemSettings()));
    //Add dark compass to tools item group
    private static void addItemsToToolsItemGroup(FabricItemGroupEntries entries) {
        entries.add(DARK_COMPASS);
    }
    //Add spawner fragment to ingredients item group
    private static void addItemsToIngredientsItemGroup(FabricItemGroupEntries entries) {
        entries.add(SPAWNER_FRAGMENT);
    }
    //Register Item Methods
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(DarkCompass.MOD_ID, name), item);
    }
    public static void registerModItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(ModItems::addItemsToToolsItemGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientsItemGroup);
    }

}
