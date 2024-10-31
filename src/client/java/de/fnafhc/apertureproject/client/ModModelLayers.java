package de.fnafhc.apertureproject.client;

import de.fnafhc.apertureproject.Apertureproject;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModModelLayers {
    public static final EntityModelLayer CUBE =
            new EntityModelLayer(new Identifier(Apertureproject.modid, "cube"), "bb_main");

    public static final EntityModelLayer CUBE2 =
            new EntityModelLayer(new Identifier(Apertureproject.modid, "cube2"), "bb_main");
}
