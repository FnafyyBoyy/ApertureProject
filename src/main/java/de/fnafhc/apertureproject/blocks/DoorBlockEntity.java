package de.fnafhc.apertureproject.blocks;

import de.fnafhc.apertureproject.init.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class DoorBlockEntity extends BlockEntity {
    public DoorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DOOR_BLOCK_ENTITY, pos, state);
    }
}
