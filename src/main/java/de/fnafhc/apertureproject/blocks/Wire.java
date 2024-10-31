package de.fnafhc.apertureproject.blocks;

import com.mojang.serialization.MapCodec;
import de.fnafhc.apertureproject.init.BlockInit;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Wire extends BlockWithEntity {

    public static final BooleanProperty POWERED = BooleanProperty.of("powered");
    public static final DirectionProperty FACING = Properties.FACING;
    public static final IntProperty TYPE = IntProperty.of("type", 0, 1);

    public Wire() {
        super(AbstractBlock.Settings.create().notSolid().breakInstantly());
        this.setDefaultState(this.getStateManager().getDefaultState().with(POWERED, false).with(FACING, Direction.DOWN).with(TYPE, 0));
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POWERED, FACING, TYPE);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new WireBlockEntity(pos, state);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return null;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if (world.getBlockEntity(pos) instanceof WireBlockEntity wireBlockEntity) {
            wireBlockEntity.setStoredPos(pos);
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction facing = ctx.getSide().getOpposite();
        return getDefaultState().with(FACING, facing);
    }



    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (!world.isClient) {
            Direction direction2 = state.get(FACING);
            BlockPos onBlock = pos.offset(direction2);
            BlockState blockState = world.getBlockState(onBlock);
            if(blockState.isAir()) {
                world.breakBlock(pos, true);
                return;
            }
            BlockPos[] diagonalPositions = new BlockPos[] {
                    pos.add(1, 1, 0),
                    pos.add(1, -1, 0),
                    pos.add(-1, 1, 0),
                    pos.add(-1, -1, 0),
                    pos.add(1, 0, 1),
                    pos.add(1, 0, -1),
                    pos.add(-1, 0, 1),
                    pos.add(-1, 0, -1),
                    pos.add(0, 1, 1),
                    pos.add(0, 1, -1),
                    pos.add(0, -1, 1),
                    pos.add(0, -1, -1),
                    pos.add(1, 1, 1),
                    pos.add(1, 1, -1),
                    pos.add(1, -1, 1),
                    pos.add(1, -1, -1),
                    pos.add(-1, 1, 1),
                    pos.add(-1, 1, -1),
                    pos.add(-1, -1, 1),
                    pos.add(-1, -1, -1)
            };
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof WireBlockEntity wireBlockEntity) {
                boolean isPowered = state.get(POWERED);
                if(!isPowered){
                    int power = 0;
                    for (Direction direction : Direction.values()) {
                        int powerr = world.getEmittedRedstonePower(fromPos, direction);
                        if(powerr > 0){
                            power = powerr;
                        }
                    }
                    if(power > 0){
                        world.setBlockState(pos, state.with(POWERED, true), Block.NOTIFY_ALL);
                        wireBlockEntity.setStoredPos(fromPos);
//                            for (BlockPos diagonalPos : diagonalPositions) {
//                                world.updateNeighborsAlways(diagonalPos, block);
//                            }
                        world.updateNeighbors(pos.offset(state.get(FACING)), this);
                    }
                }else {
                    boolean is = false;
                    for (Direction direction : Direction.values()) {
                        BlockPos neighborPos = wireBlockEntity.getStoredPos();
                        int power = world.getEmittedRedstonePower(neighborPos, direction);
                        if(power > 0){
                            is = true;
                        }
                    }
                    if(!is){
                        for (Direction direction : Direction.values()) {
                            world.setBlockState(pos, state.with(POWERED, false), Block.NOTIFY_ALL);
//                        for (BlockPos diagonalPos : diagonalPositions) {
//                            world.updateNeighborsAlways(diagonalPos, block);
//                        }
                            world.updateNeighbors(pos.offset(state.get(FACING)), this);
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return true;
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return state.get(POWERED) ? 15 : 0;
    }

    @Override
    public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return state.get(POWERED) ? 15 : 0;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction facing = state.get(FACING);

        switch (facing) {
            case DOWN:
                return VoxelShapes.cuboid(0.2, 0.001, 0.2, 0.8, 0.01, 0.8);
            case UP:
                return VoxelShapes.cuboid(0.2, 0.99, 0.2, 0.8, 0.999, 0.8);
            case NORTH:
                return VoxelShapes.cuboid(0.2, 0.2, 0.001, 0.8, 0.8, 0.01);
            case SOUTH:
                return VoxelShapes.cuboid(0.2, 0.2, 0.99, 0.8, 0.8, 0.999);
            case WEST:
                return VoxelShapes.cuboid(0.001, 0.2, 0.2, 0.01, 0.8, 0.8);
            case EAST:
                return VoxelShapes.cuboid(0.99, 0.2, 0.2, 0.999, 0.8, 0.8);
            default:
                return VoxelShapes.cuboid(0.2, 0.001, 0.2, 0.8, 0.01, 0.8);
        }
    }
}

