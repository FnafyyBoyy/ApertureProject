package de.fnafhc.apertureproject.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GooFBlock extends FluidBlock {
    public GooFBlock(FlowableFluid fluid, Settings settings) {
        super(fluid, settings);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if(entity instanceof PlayerEntity){
            PlayerEntity p = (PlayerEntity) entity;
            if(p.isSpectator() || p.isCreative()){
            }else entity.kill();
        }else entity.kill();
        super.onEntityCollision(state, world, pos, entity);
    }
}
