package de.fnafhc.apertureproject.blocks;

import de.fnafhc.apertureproject.Apertureproject;
import de.fnafhc.apertureproject.entities.Cube;
import de.fnafhc.apertureproject.entities.Cube2;
import de.fnafhc.apertureproject.init.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DropperBlockEntity extends BlockEntity {
    private int storedEntity = -1;

    public DropperBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DROPPER_BLOCK_ENTITY, pos, state);
    }

    public Cube getStoredEntity(World world) {
        if(storedEntity != -1) {
            return Apertureproject.getCubeByID(world, storedEntity);
        }
        return null;
    }

    public Cube2 getStoredEntity2(World world) {
        if(storedEntity != -1) {
            return Apertureproject.getCubeByID2(world, storedEntity);
        }
        return null;
    }

    public void setStoredEntity(Cube cube) {
        this.storedEntity = cube.getID();
    }

    public void setStoredEntity(Cube2 cube) {
        this.storedEntity = cube.getID();
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("storedEntity", storedEntity);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if (nbt.contains("storedEntity")) {
            this.storedEntity = nbt.getInt("storedEntity");
        }
    }
}

