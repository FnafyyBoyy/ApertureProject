package de.fnafhc.apertureproject.init;

import de.fnafhc.apertureproject.Apertureproject;
import de.fnafhc.apertureproject.entities.Cube;
import de.fnafhc.apertureproject.entities.Cube2;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class EntityInit {

    public static final EntityType<Cube> CUBE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(Apertureproject.modid, "cube"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, Cube::new)
                    .dimensions(EntityDimensions.fixed(0.86f, 0.86f)).build());

    public static final EntityType<Cube2> CUBE2 = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(Apertureproject.modid, "cube2"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, Cube2::new)
                    .dimensions(EntityDimensions.fixed(0.86f, 0.86f)).build());

    public static void register(){
        FabricDefaultAttributeRegistry.register(CUBE, Cube.createNoAIEntityAttributes());
        FabricDefaultAttributeRegistry.register(CUBE2, Cube2.createNoAIEntityAttributes());
    }
}
