package de.fnafhc.apertureproject.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

public class NormalBlock extends Block {
    public NormalBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return true;
    }
}
