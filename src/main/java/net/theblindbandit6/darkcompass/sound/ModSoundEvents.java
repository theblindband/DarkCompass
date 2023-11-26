package net.theblindbandit6.darkcompass.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.theblindbandit6.darkcompass.DarkCompass;

public class ModSoundEvents {

    //Mod sounds
    public static final SoundEvent ITEM_DARK_COMPASS_LOCK = registerSoundEvent("item.dark_compass.lock");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(DarkCompass.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSoundEvents() {
    }
}
