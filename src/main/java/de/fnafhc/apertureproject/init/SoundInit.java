package de.fnafhc.apertureproject.init;

import de.fnafhc.apertureproject.Apertureproject;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class SoundInit {

    public static SoundEvent portal1_shoot = createSound("portal1_shoot");
    public static SoundEvent portal2_shoot = createSound("portal2_shoot");
    public static SoundEvent portal_open = createSound("portal_open");
    public static SoundEvent portal_close = createSound("portal_close");
    public static SoundEvent super_button_down = createSound("super_button_down");
    public static SoundEvent super_button_up = createSound("super_button_up");
    public static SoundEvent button_press = createSound("button_press");
    public static SoundEvent button_release = createSound("button_release");
    public static SoundEvent door_open = createSound("door_open");
    public static SoundEvent door_close = createSound("door_close");

    public static void register(){
        registerSound(portal1_shoot);
        registerSound(portal2_shoot);
        registerSound(portal_open);
        registerSound(portal_close);
        registerSound(super_button_down);
        registerSound(super_button_up);
        registerSound(button_press);
        registerSound(button_release);
        registerSound(door_open);
        registerSound(door_close);
    }

    private static void registerSound(SoundEvent sound){
        Registry.register(Registries.SOUND_EVENT, sound.getId(), sound);
    }

    private  static SoundEvent createSound(String id){
        return SoundEvent.of(Identifier.of(Apertureproject.modid, id));
    }
}
