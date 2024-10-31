package de.fnafhc.apertureproject.blocks;

import de.fnafhc.apertureproject.init.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class WireBlockEntity extends BlockEntity {

    private BlockPos storedPos;

    public WireBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WIRE_BLOCK_ENTITY, pos, state);
        this.storedPos = pos;
    }

    public BlockPos getStoredPos() {
        return storedPos;
    }

    public void setStoredPos(BlockPos pos) {
        this.storedPos = pos;
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (storedPos != null) {
            nbt.putLong("storedPos", storedPos.asLong());
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if (nbt.contains("storedPos")) {
            this.storedPos = BlockPos.fromLong(nbt.getLong("storedPos"));
        }
    }
}

