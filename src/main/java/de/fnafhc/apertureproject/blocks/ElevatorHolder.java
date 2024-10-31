package de.fnafhc.apertureproject.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class ElevatorHolder extends Block {

    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    public ElevatorHolder() {
        super(AbstractBlock.Settings.create());
        this.setDefaultState(getStateManager().getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction facing = ctx.getHorizontalPlayerFacing().getOpposite();
        return getDefaultState().with(FACING, facing);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if(state.get(FACING) == Direction.NORTH){
            return VoxelShapes.cuboid(0, 0, 8.0 / 16, 1, 1, 1);
        }else if(state.get(FACING) == Direction.EAST){
            return VoxelShapes.cuboid(0, 0, 0, 8.0 / 16.0, 1, 1);
        }else if(state.get(FACING) == Direction.SOUTH){
            return VoxelShapes.cuboid(0, 0, 0, 1, 1, 8.0 / 16.0);
        }else {
            return VoxelShapes.cuboid(8.0 / 16.0, 0, 0, 1, 1, 1);
        }
    }
}
