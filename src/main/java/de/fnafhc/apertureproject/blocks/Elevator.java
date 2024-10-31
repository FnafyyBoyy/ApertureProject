package de.fnafhc.apertureproject.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class Elevator extends Block {

    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    public Elevator() {
        super(AbstractBlock.Settings.create());
        this.setDefaultState(this.getStateManager().getDefaultState().with(FACING, Direction.NORTH));
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
        if(state.get(FACING) == Direction.WEST){
            return west();
        }else if(state.get(FACING) == Direction.EAST){
            return east();
        }else if(state.get(FACING) == Direction.NORTH){
            return north();
        }else {
            return south();
        }
    }

    public VoxelShape north(){
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(-0.1875, -1, -0.1875, 1.1875, -0.75, 1.1875), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(-0.1875, 1.4375, -0.1875, 1.1875, 1.6875, 1.1875), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(-0.3125, -0.75, -0.1875, -0.1875, 1.4375, 1.1875), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(1.1875, -0.75, -0.1875, 1.3125, 1.4375, 1.1875), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(-0.1875, -0.75, 1.1875, 1.1875, 1.4375, 1.3125), BooleanBiFunction.OR);
        return shape;
    }

    public VoxelShape south(){
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(-0.1875, -1, -0.1875, 1.1875, -0.75, 1.1875), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(-0.1875, 1.4375, -0.1875, 1.1875, 1.6875, 1.1875), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(-0.3125, -0.75, -0.1875, -0.1875, 1.4375, 1.1875), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(1.1875, -0.75, -0.1875, 1.3125, 1.4375, 1.1875), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(-0.1875, -0.75, -0.3125, 1.1875, 1.4375, -0.1875), BooleanBiFunction.OR);
        return shape;
    }

    public VoxelShape east(){
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(-0.1875, -1, -0.1875, 1.1875, -0.75, 1.1875), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(-0.1875, 1.4375, -0.1875, 1.1875, 1.6875, 1.1875), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(-0.3125, -0.75, -0.1875, -0.1875, 1.4375, 1.1875), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(-0.1875, -0.75, 1.1875, 1.1875, 1.4375, 1.3125), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(-0.1875, -0.75, -0.3125, 1.1875, 1.4375, -0.1875), BooleanBiFunction.OR);
        return shape;
    }

    public VoxelShape west(){
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(-0.1875, -1, -0.1875, 1.1875, -0.75, 1.1875), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(-0.1875, 1.4375, -0.1875, 1.1875, 1.6875, 1.1875), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(1.1875, -0.75, -0.1875, 1.3125, 1.4375, 1.1875), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(-0.1875, -0.75, 1.1875, 1.1875, 1.4375, 1.3125), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(-0.1875, -0.75, -0.3125, 1.1875, 1.4375, -0.1875), BooleanBiFunction.OR);
        return shape;
    }
}
