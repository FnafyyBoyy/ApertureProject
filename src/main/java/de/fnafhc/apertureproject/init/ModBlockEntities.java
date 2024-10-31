package de.fnafhc.apertureproject.init;

import de.fnafhc.apertureproject.Apertureproject;
import de.fnafhc.apertureproject.blocks.DoorBlockEntity;
import de.fnafhc.apertureproject.blocks.DropperBlockEntity;
import de.fnafhc.apertureproject.blocks.WireBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static BlockEntityType<WireBlockEntity> WIRE_BLOCK_ENTITY = null;
    public static BlockEntityType<DropperBlockEntity> DROPPER_BLOCK_ENTITY = null;
    public static BlockEntityType<DoorBlockEntity> DOOR_BLOCK_ENTITY = null;

    public static void register(){
        WIRE_BLOCK_ENTITY = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                new Identifier(Apertureproject.modid, "wire_block_entity"),
                BlockEntityType.Builder.create(WireBlockEntity::new, BlockInit.wire).build(null));

        DROPPER_BLOCK_ENTITY = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                new Identifier(Apertureproject.modid, "dropper_block_entity"),
                BlockEntityType.Builder.create(DropperBlockEntity::new, BlockInit.dropper).build(null));

        DOOR_BLOCK_ENTITY = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                new Identifier(Apertureproject.modid, "door_block_entity"),
                BlockEntityType.Builder.create(DoorBlockEntity::new, BlockInit.door).build(null));
    }
}
