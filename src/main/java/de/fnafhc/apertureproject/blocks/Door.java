package de.fnafhc.apertureproject.blocks;

import com.mojang.serialization.MapCodec;
import de.fnafhc.apertureproject.Apertureproject;
import de.fnafhc.apertureproject.init.SoundInit;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
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

public class Door extends BlockWithEntity {

    public static final BooleanProperty OPEN = BooleanProperty.of("open");
    public static final IntProperty TYPE = IntProperty.of("type", 0, 3);
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    public Door() {
        super(AbstractBlock.Settings.create().strength(2));
        this.setDefaultState(this.getStateManager().getDefaultState().with(OPEN, false).with(TYPE, 0).with(FACING, Direction.NORTH));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(OPEN, TYPE, FACING);
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
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction facing = ctx.getHorizontalPlayerFacing().getOpposite();
        return getDefaultState().with(FACING, facing);
    }

    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        Direction direction = state.get(FACING);
        if(direction == Direction.NORTH){
            BlockPos neben = pos.add(-1, 0, 0);
            BlockPos oben = pos.add(0, 1, 0);
            BlockPos nebenoben = pos.add(-1, 1, 0);
            world.setBlockState(neben, state.with(FACING, direction).with(TYPE, 1));
            world.setBlockState(oben, state.with(FACING, direction).with(TYPE, 2));
            world.setBlockState(nebenoben, state.with(FACING, direction).with(TYPE, 3));
        }
        if(direction == Direction.SOUTH){
            BlockPos neben = pos.add(1, 0, 0);
            BlockPos oben = pos.add(0, 1, 0);
            BlockPos nebenoben = pos.add(1, 1, 0);
            world.setBlockState(neben, state.with(FACING, direction).with(TYPE, 1));
            world.setBlockState(oben, state.with(FACING, direction).with(TYPE, 2));
            world.setBlockState(nebenoben, state.with(FACING, direction).with(TYPE, 3));
        }
        if(direction == Direction.EAST){
            BlockPos neben = pos.add(0, 0, -1);
            BlockPos oben = pos.add(0, 1, 0);
            BlockPos nebenoben = pos.add(0, 1, -1);
            world.setBlockState(neben, state.with(FACING, direction).with(TYPE, 1));
            world.setBlockState(oben, state.with(FACING, direction).with(TYPE, 2));
            world.setBlockState(nebenoben, state.with(FACING, direction).with(TYPE, 3));
        }
        if(direction == Direction.WEST){
            BlockPos neben = pos.add(0, 0, 1);
            BlockPos oben = pos.add(0, 1, 0);
            BlockPos nebenoben = pos.add(0, 1, 1);
            world.setBlockState(neben, state.with(FACING, direction).with(TYPE, 1));
            world.setBlockState(oben, state.with(FACING, direction).with(TYPE, 2));
            world.setBlockState(nebenoben, state.with(FACING, direction).with(TYPE, 3));
        }
        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        Direction direction = state.get(FACING);
        int type = state.get(TYPE);
        if(direction == Direction.NORTH){
            if(type == 0){
                BlockPos neben = pos.add(-1, 0, 0);
                BlockPos oben = pos.add(0, 1, 0);
                BlockPos nebenoben = pos.add(-1, 1, 0);
                world.breakBlock(neben, false);
                world.breakBlock(oben, false);
                world.breakBlock(nebenoben, false);
            }
            if(type == 1){
                BlockPos neben = pos.add(1, 0, 0);
                BlockPos oben = pos.add(0, 1, 0);
                BlockPos nebenoben = pos.add(1, 1, 0);
                world.breakBlock(neben, false);
                world.breakBlock(oben, false);
                world.breakBlock(nebenoben, false);
            }
            if(type == 2){
                BlockPos neben = pos.add(-1, 0, 0);
                BlockPos oben = pos.add(0, -1, 0);
                BlockPos nebenoben = pos.add(-1, -1, 0);
                world.breakBlock(neben, false);
                world.breakBlock(oben, false);
                world.breakBlock(nebenoben, false);
            }
            if(type == 3){
                BlockPos neben = pos.add(1, 0, 0);
                BlockPos oben = pos.add(0, -1, 0);
                BlockPos nebenoben = pos.add(1, -1, 0);
                world.breakBlock(neben, false);
                world.breakBlock(oben, false);
                world.breakBlock(nebenoben, false);
            }
        }

        if(direction == Direction.SOUTH){
            if(type == 0){
                BlockPos neben = pos.add(1, 0, 0);
                BlockPos oben = pos.add(0, 1, 0);
                BlockPos nebenoben = pos.add(1, 1, 0);
                world.breakBlock(neben, false);
                world.breakBlock(oben, false);
                world.breakBlock(nebenoben, false);
            }
            if(type == 1){
                BlockPos neben = pos.add(-1, 0, 0);
                BlockPos oben = pos.add(0, 1, 0);
                BlockPos nebenoben = pos.add(-1, 1, 0);
                world.breakBlock(neben, false);
                world.breakBlock(oben, false);
                world.breakBlock(nebenoben, false);
            }
            if(type == 2){
                BlockPos neben = pos.add(1, 0, 0);
                BlockPos oben = pos.add(0, -1, 0);
                BlockPos nebenoben = pos.add(1, -1, 0);
                world.breakBlock(neben, false);
                world.breakBlock(oben, false);
                world.breakBlock(nebenoben, false);
            }
            if(type == 3){
                BlockPos neben = pos.add(-1, 0, 0);
                BlockPos oben = pos.add(0, -1, 0);
                BlockPos nebenoben = pos.add(-1, -1, 0);
                world.breakBlock(neben, false);
                world.breakBlock(oben, false);
                world.breakBlock(nebenoben, false);
            }
        }

        if(direction == Direction.EAST){
            if(type == 0){
                BlockPos neben = pos.add(0, 0, -1);
                BlockPos oben = pos.add(0, 1, 0);
                BlockPos nebenoben = pos.add(0, 1, -1);
                world.breakBlock(neben, false);
                world.breakBlock(oben, false);
                world.breakBlock(nebenoben, false);
            }
            if(type == 1){
                BlockPos neben = pos.add(0, 0, 1);
                BlockPos oben = pos.add(0, 1, 0);
                BlockPos nebenoben = pos.add(0, 1, 1);
                world.breakBlock(neben, false);
                world.breakBlock(oben, false);
                world.breakBlock(nebenoben, false);
            }
            if(type == 2){
                BlockPos neben = pos.add(0, 0, -1);
                BlockPos oben = pos.add(0, -1, 0);
                BlockPos nebenoben = pos.add(0, -1, -1);
                world.breakBlock(neben, false);
                world.breakBlock(oben, false);
                world.breakBlock(nebenoben, false);
            }
            if(type == 3){
                BlockPos neben = pos.add(0, 0, 1);
                BlockPos oben = pos.add(0, -1, 0);
                BlockPos nebenoben = pos.add(0, -1, 1);
                world.breakBlock(neben, false);
                world.breakBlock(oben, false);
                world.breakBlock(nebenoben, false);
            }
        }

        if(direction == Direction.WEST){
            if(type == 0){
                BlockPos neben = pos.add(0, 0, 1);
                BlockPos oben = pos.add(0, 1, 0);
                BlockPos nebenoben = pos.add(0, 1, 1);
                world.breakBlock(neben, false);
                world.breakBlock(oben, false);
                world.breakBlock(nebenoben, false);
            }
            if(type == 1){
                BlockPos neben = pos.add(0, 0, -1);
                BlockPos oben = pos.add(0, 1, 0);
                BlockPos nebenoben = pos.add(0, 1, -1);
                world.breakBlock(neben, false);
                world.breakBlock(oben, false);
                world.breakBlock(nebenoben, false);
            }
            if(type == 2){
                BlockPos neben = pos.add(0, 0, 1);
                BlockPos oben = pos.add(0, -1, 0);
                BlockPos nebenoben = pos.add(0, -1, 1);
                world.breakBlock(neben, false);
                world.breakBlock(oben, false);
                world.breakBlock(nebenoben, false);
            }
            if(type == 3){
                BlockPos neben = pos.add(0, 0, -1);
                BlockPos oben = pos.add(0, -1, 0);
                BlockPos nebenoben = pos.add(0, -1, -1);
                world.breakBlock(neben, false);
                world.breakBlock(oben, false);
                world.breakBlock(nebenoben, false);
            }
        }
        return super.onBreak(world, pos, state, player);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if(!world.isClient){
            int power = 0;
            for (Direction direction : Direction.values()){
                int power2 = world.getEmittedRedstonePower(sourcePos, direction);
                if(power2 > 0 || DoorUtils.isNeighbourRecievingPower(pos, world)){
                    power = power2;
                }
            }
            if(power > 0){
                ServerWorld serverWorld = (ServerWorld) world;
                if(!state.get(OPEN)){
                    Apertureproject.playSoundAtPlayerLocation(pos, SoundInit.door_open, serverWorld);
                    world.setBlockState(pos, state.with(OPEN, true));
                    Direction direction = state.get(FACING);
                    int type = state.get(TYPE);
                    if(direction == Direction.NORTH){
                        if(type == 0){
                            List<BlockPos> blockPoses = DoorUtils.getNorth0(pos);
                            for(BlockPos bp : blockPoses){
                                BlockState state2 = world.getBlockState(bp);
                                world.setBlockState(bp, state2.with(OPEN, true));
                            }
                        }
                        if(type == 1){
                            List<BlockPos> blockPoses = DoorUtils.getNorth1(pos);
                            for(BlockPos bp : blockPoses){
                                BlockState state2 = world.getBlockState(bp);
                                world.setBlockState(bp, state2.with(OPEN, true));
                            }
                        }
                        if(type == 2){
                            List<BlockPos> blockPoses = DoorUtils.getNorth2(pos);
                            for(BlockPos bp : blockPoses){
                                BlockState state2 = world.getBlockState(bp);
                                world.setBlockState(bp, state2.with(OPEN, true));
                            }
                        }
                        if(type == 3){
                            List<BlockPos> blockPoses = DoorUtils.getNorth3(pos);
                            for(BlockPos bp : blockPoses){
                                BlockState state2 = world.getBlockState(bp);
                                world.setBlockState(bp, state2.with(OPEN, true));
                            }
                        }
                    }
                    if(direction == Direction.SOUTH){
                        if(type == 0){
                            List<BlockPos> blockPoses = DoorUtils.getSouth0(pos);
                            for(BlockPos bp : blockPoses){
                                BlockState state2 = world.getBlockState(bp);
                                world.setBlockState(bp, state2.with(OPEN, true));
                            }
                        }
                        if(type == 1){
                            List<BlockPos> blockPoses = DoorUtils.getSouth1(pos);
                            for(BlockPos bp : blockPoses){
                                BlockState state2 = world.getBlockState(bp);
                                world.setBlockState(bp, state2.with(OPEN, true));
                            }
                        }
                        if(type == 2){
                            List<BlockPos> blockPoses = DoorUtils.getSouth2(pos);
                            for(BlockPos bp : blockPoses){
                                BlockState state2 = world.getBlockState(bp);
                                world.setBlockState(bp, state2.with(OPEN, true));
                            }
                        }
                        if(type == 3){
                            List<BlockPos> blockPoses = DoorUtils.getSouth3(pos);
                            for(BlockPos bp : blockPoses){
                                BlockState state2 = world.getBlockState(bp);
                                world.setBlockState(bp, state2.with(OPEN, true));
                            }
                        }
                    }
                    if(direction == Direction.EAST){
                        if(type == 0){
                            List<BlockPos> blockPoses = DoorUtils.getEast0(pos);
                            for(BlockPos bp : blockPoses){
                                BlockState state2 = world.getBlockState(bp);
                                world.setBlockState(bp, state2.with(OPEN, true));
                            }
                        }
                        if(type == 1){
                            List<BlockPos> blockPoses = DoorUtils.getEast1(pos);
                            for(BlockPos bp : blockPoses){
                                BlockState state2 = world.getBlockState(bp);
                                world.setBlockState(bp, state2.with(OPEN, true));
                            }
                        }
                        if(type == 2){
                            List<BlockPos> blockPoses = DoorUtils.getEast2(pos);
                            for(BlockPos bp : blockPoses){
                                BlockState state2 = world.getBlockState(bp);
                                world.setBlockState(bp, state2.with(OPEN, true));
                            }
                        }
                        if(type == 3){
                            List<BlockPos> blockPoses = DoorUtils.getEast3(pos);
                            for(BlockPos bp : blockPoses){
                                BlockState state2 = world.getBlockState(bp);
                                world.setBlockState(bp, state2.with(OPEN, true));
                            }
                        }
                    }
                    if(direction == Direction.WEST){
                        if(type == 0){
                            List<BlockPos> blockPoses = DoorUtils.getWest0(pos);
                            for(BlockPos bp : blockPoses){
                                BlockState state2 = world.getBlockState(bp);
                                world.setBlockState(bp, state2.with(OPEN, true));
                            }
                        }
                        if(type == 1){
                            List<BlockPos> blockPoses = DoorUtils.getWest1(pos);
                            for(BlockPos bp : blockPoses){
                                BlockState state2 = world.getBlockState(bp);
                                world.setBlockState(bp, state2.with(OPEN, true));
                            }
                        }
                        if(type == 2){
                            List<BlockPos> blockPoses = DoorUtils.getWest2(pos);
                            for(BlockPos bp : blockPoses){
                                BlockState state2 = world.getBlockState(bp);
                                world.setBlockState(bp, state2.with(OPEN, true));
                            }
                        }
                        if(type == 3){
                            List<BlockPos> blockPoses = DoorUtils.getWest3(pos);
                            for(BlockPos bp : blockPoses){
                                BlockState state2 = world.getBlockState(bp);
                                world.setBlockState(bp, state2.with(OPEN, true));
                            }
                        }
                    }
                }
            }else {
                if(state.get(OPEN)){
                    Direction direction = state.get(FACING);
                    int type = state.get(TYPE);
                    int isRec = 0;
                    if(direction == Direction.NORTH){
                        if(type == 0){
                            List<BlockPos> blockPoses = DoorUtils.getNorth0(pos);
                            for(BlockPos bp : blockPoses){
                                if(world.isReceivingRedstonePower(bp) || DoorUtils.isNeighbourRecievingPower(bp, world)){
                                    isRec = 1;
                                }
                            }
                        }
                        if(type == 1){
                            List<BlockPos> blockPoses = DoorUtils.getNorth1(pos);
                            for(BlockPos bp : blockPoses){
                                if(world.isReceivingRedstonePower(bp) || DoorUtils.isNeighbourRecievingPower(bp, world)){
                                    isRec = 1;
                                }
                            }
                        }
                        if(type == 2){
                            List<BlockPos> blockPoses = DoorUtils.getNorth2(pos);
                            for(BlockPos bp : blockPoses){
                                if(world.isReceivingRedstonePower(bp) || DoorUtils.isNeighbourRecievingPower(bp, world)){
                                    isRec = 1;
                                }
                            }
                        }
                        if(type == 3){
                            List<BlockPos> blockPoses = DoorUtils.getNorth3(pos);
                            for(BlockPos bp : blockPoses){
                                if(world.isReceivingRedstonePower(bp) || DoorUtils.isNeighbourRecievingPower(bp, world)){
                                    isRec = 1;
                                }
                            }
                        }
                    }
                    if(direction == Direction.SOUTH){
                        if(type == 0){
                            List<BlockPos> blockPoses = DoorUtils.getSouth0(pos);
                            for(BlockPos bp : blockPoses){
                                if(world.isReceivingRedstonePower(bp) || DoorUtils.isNeighbourRecievingPower(bp, world)){
                                    isRec = 1;
                                }
                            }
                        }
                        if(type == 1){
                            List<BlockPos> blockPoses = DoorUtils.getSouth1(pos);
                            for(BlockPos bp : blockPoses){
                                if(world.isReceivingRedstonePower(bp) || DoorUtils.isNeighbourRecievingPower(bp, world)){
                                    isRec = 1;
                                }
                            }
                        }
                        if(type == 2){
                            List<BlockPos> blockPoses = DoorUtils.getSouth2(pos);
                            for(BlockPos bp : blockPoses){
                                if(world.isReceivingRedstonePower(bp) || DoorUtils.isNeighbourRecievingPower(bp, world)){
                                    isRec = 1;
                                }
                            }
                        }
                        if(type == 3){
                            List<BlockPos> blockPoses = DoorUtils.getSouth3(pos);
                            for(BlockPos bp : blockPoses){
                                if(world.isReceivingRedstonePower(bp) || DoorUtils.isNeighbourRecievingPower(bp, world)){
                                    isRec = 1;
                                }
                            }
                        }
                    }
                    if(direction == Direction.EAST){
                        if(type == 0){
                            List<BlockPos> blockPoses = DoorUtils.getEast0(pos);
                            for(BlockPos bp : blockPoses){
                                if(world.isReceivingRedstonePower(bp) || DoorUtils.isNeighbourRecievingPower(bp, world)){
                                    isRec = 1;
                                }
                            }
                        }
                        if(type == 1){
                            List<BlockPos> blockPoses = DoorUtils.getEast1(pos);
                            for(BlockPos bp : blockPoses){
                                if(world.isReceivingRedstonePower(bp) || DoorUtils.isNeighbourRecievingPower(bp, world)){
                                    isRec = 1;
                                }
                            }
                        }
                        if(type == 2){
                            List<BlockPos> blockPoses = DoorUtils.getEast2(pos);
                            for(BlockPos bp : blockPoses){
                                if(world.isReceivingRedstonePower(bp) || DoorUtils.isNeighbourRecievingPower(bp, world)){
                                    isRec = 1;
                                }
                            }
                        }
                        if(type == 3){
                            List<BlockPos> blockPoses = DoorUtils.getEast3(pos);
                            for(BlockPos bp : blockPoses){
                                if(world.isReceivingRedstonePower(bp) || DoorUtils.isNeighbourRecievingPower(bp, world)){
                                    isRec = 1;
                                }
                            }
                        }
                    }
                    if(direction == Direction.WEST){
                        if(type == 0){
                            List<BlockPos> blockPoses = DoorUtils.getWest0(pos);
                            for(BlockPos bp : blockPoses){
                                if(world.isReceivingRedstonePower(bp) || DoorUtils.isNeighbourRecievingPower(bp, world)){
                                    isRec = 1;
                                }
                            }
                        }
                        if(type == 1){
                            List<BlockPos> blockPoses = DoorUtils.getWest1(pos);
                            for(BlockPos bp : blockPoses){
                                if(world.isReceivingRedstonePower(bp) || DoorUtils.isNeighbourRecievingPower(bp, world)){
                                    isRec = 1;
                                }
                            }
                        }
                        if(type == 2){
                            List<BlockPos> blockPoses = DoorUtils.getWest2(pos);
                            for(BlockPos bp : blockPoses){
                                if(world.isReceivingRedstonePower(bp) || DoorUtils.isNeighbourRecievingPower(bp, world)){
                                    isRec = 1;
                                }
                            }
                        }
                        if(type == 3){
                            List<BlockPos> blockPoses = DoorUtils.getWest3(pos);
                            for(BlockPos bp : blockPoses){
                                if(world.isReceivingRedstonePower(bp) || DoorUtils.isNeighbourRecievingPower(bp, world)){
                                    isRec = 1;
                                }
                            }
                        }
                    }
                    if(isRec == 0){
                        world.setBlockState(pos, state.with(OPEN, false));
                        ServerWorld serverWorld = (ServerWorld) world;
                        Apertureproject.playSoundAtPlayerLocation(pos, SoundInit.door_close, serverWorld);
                        if(direction == Direction.NORTH){
                            if(type == 0){
                                List<BlockPos> blockPoses = DoorUtils.getNorth0(pos);
                                for(BlockPos bp : blockPoses){
                                    BlockState state2 = world.getBlockState(bp);
                                    world.setBlockState(bp, state2.with(OPEN, false));
                                }
                            }
                            if(type == 1){
                                List<BlockPos> blockPoses = DoorUtils.getNorth1(pos);
                                for(BlockPos bp : blockPoses){
                                    BlockState state2 = world.getBlockState(bp);
                                    world.setBlockState(bp, state2.with(OPEN, false));
                                }
                            }
                            if(type == 2){
                                List<BlockPos> blockPoses = DoorUtils.getNorth2(pos);
                                for(BlockPos bp : blockPoses){
                                    BlockState state2 = world.getBlockState(bp);
                                    world.setBlockState(bp, state2.with(OPEN, false));
                                }
                            }
                            if(type == 3){
                                List<BlockPos> blockPoses = DoorUtils.getNorth3(pos);
                                for(BlockPos bp : blockPoses){
                                    BlockState state2 = world.getBlockState(bp);
                                    world.setBlockState(bp, state2.with(OPEN, false));
                                }
                            }
                        }
                        if(direction == Direction.SOUTH){
                            if(type == 0){
                                List<BlockPos> blockPoses = DoorUtils.getSouth0(pos);
                                for(BlockPos bp : blockPoses){
                                    BlockState state2 = world.getBlockState(bp);
                                    world.setBlockState(bp, state2.with(OPEN, false));
                                }
                            }
                            if(type == 1){
                                List<BlockPos> blockPoses = DoorUtils.getSouth1(pos);
                                for(BlockPos bp : blockPoses){
                                    BlockState state2 = world.getBlockState(bp);
                                    world.setBlockState(bp, state2.with(OPEN, false));
                                }
                            }
                            if(type == 2){
                                List<BlockPos> blockPoses = DoorUtils.getSouth2(pos);
                                for(BlockPos bp : blockPoses){
                                    BlockState state2 = world.getBlockState(bp);
                                    world.setBlockState(bp, state2.with(OPEN, false));
                                }
                            }
                            if(type == 3){
                                List<BlockPos> blockPoses = DoorUtils.getSouth3(pos);
                                for(BlockPos bp : blockPoses){
                                    BlockState state2 = world.getBlockState(bp);
                                    world.setBlockState(bp, state2.with(OPEN, false));
                                }
                            }
                        }
                        if(direction == Direction.EAST){
                            if(type == 0){
                                List<BlockPos> blockPoses = DoorUtils.getEast0(pos);
                                for(BlockPos bp : blockPoses){
                                    BlockState state2 = world.getBlockState(bp);
                                    world.setBlockState(bp, state2.with(OPEN, false));
                                }
                            }
                            if(type == 1){
                                List<BlockPos> blockPoses = DoorUtils.getEast1(pos);
                                for(BlockPos bp : blockPoses){
                                    BlockState state2 = world.getBlockState(bp);
                                    world.setBlockState(bp, state2.with(OPEN, false));
                                }
                            }
                            if(type == 2){
                                List<BlockPos> blockPoses = DoorUtils.getEast2(pos);
                                for(BlockPos bp : blockPoses){
                                    BlockState state2 = world.getBlockState(bp);
                                    world.setBlockState(bp, state2.with(OPEN, false));
                                }
                            }
                            if(type == 3){
                                List<BlockPos> blockPoses = DoorUtils.getEast3(pos);
                                for(BlockPos bp : blockPoses){
                                    BlockState state2 = world.getBlockState(bp);
                                    world.setBlockState(bp, state2.with(OPEN, false));
                                }
                            }
                        }
                        if(direction == Direction.WEST){
                            if(type == 0){
                                List<BlockPos> blockPoses = DoorUtils.getWest0(pos);
                                for(BlockPos bp : blockPoses){
                                    BlockState state2 = world.getBlockState(bp);
                                    world.setBlockState(bp, state2.with(OPEN, false));
                                }
                            }
                            if(type == 1){
                                List<BlockPos> blockPoses = DoorUtils.getWest1(pos);
                                for(BlockPos bp : blockPoses){
                                    BlockState state2 = world.getBlockState(bp);
                                    world.setBlockState(bp, state2.with(OPEN, false));
                                }
                            }
                            if(type == 2){
                                List<BlockPos> blockPoses = DoorUtils.getWest2(pos);
                                for(BlockPos bp : blockPoses){
                                    BlockState state2 = world.getBlockState(bp);
                                    world.setBlockState(bp, state2.with(OPEN, false));
                                }
                            }
                            if(type == 3){
                                List<BlockPos> blockPoses = DoorUtils.getWest3(pos);
                                for(BlockPos bp : blockPoses){
                                    BlockState state2 = world.getBlockState(bp);
                                    world.setBlockState(bp, state2.with(OPEN, false));
                                }
                            }
                        }
                    }
                }
            }
        }
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DoorBlockEntity(pos, state);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if(state.get(OPEN)){
            return VoxelShapes.cuboid(0, 0, 0, 0, 0, 0);
        }
        if(state.get(FACING) == Direction.NORTH){
            return VoxelShapes.cuboid(0, 0, 5 / 16.0, 1, 1, 11 / 16.0);
        }
        if(state.get(FACING) == Direction.SOUTH){
            return VoxelShapes.cuboid(0, 0, 5 / 16.0, 1, 1, 11 / 16.0);
        }
        if(state.get(FACING) == Direction.WEST){
            return VoxelShapes.cuboid(5 / 16.0, 0, 0 / 16.0, 11 / 16.0, 1, 1);
        }
        if(state.get(FACING) == Direction.EAST){
            return VoxelShapes.cuboid(5 / 16.0, 0, 0 / 16.0, 11 / 16.0, 1, 1);
        }
        return VoxelShapes.cuboid(0, 0, 5 / 16.0, 1, 1, 11 / 16.0);
    }
}
