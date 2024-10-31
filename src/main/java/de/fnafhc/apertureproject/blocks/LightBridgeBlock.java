package de.fnafhc.apertureproject.blocks;

import de.fnafhc.apertureproject.init.BlockInit;
import de.fnafhc.apertureproject.utils.RandomUtils;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import qouteall.imm_ptl.core.portal.Portal;
import qouteall.q_misc_util.my_util.DQuaternion;

public class LightBridgeBlock extends Block {

    public static final IntProperty TYPE = IntProperty.of("type", 0, 3);
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    public LightBridgeBlock() {
        super(AbstractBlock.Settings.create().strength(10));
        this.setDefaultState(this.getStateManager().getDefaultState().with(FACING, Direction.NORTH).with(TYPE,0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, TYPE);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction facing = ctx.getHorizontalPlayerFacing().getOpposite();
        return getDefaultState().with(FACING, facing);
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        int type = state.get(TYPE);
        if(type == 1 || type == 3){
            return state;
        }
        return super.onBreak(world, pos, state, player);
    }

    private boolean cooldown = false;

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        int power = 0;
        int type = state.get(TYPE);
        if(type == 0 || type == 2){
            if(cooldown) return;
            cooldown = true;
            if(world.isReceivingRedstonePower(pos)){
                power = 1;
            }
            for (Direction direction : Direction.values()){
                int power2 = world.getEmittedRedstonePower(sourcePos, direction);
                if(power2 > 0 || DoorUtils.isNeighbourRecievingPower(pos, world)){
                    power = power2;
                }
            }
            if(power > 0){
                world.setBlockState(pos, state.with(TYPE, 2));
                Direction dir = state.get(FACING);
                nextBlock(world, pos, dir, true);
            }
            else {
                world.setBlockState(pos, state.with(TYPE, 0));
                Direction dir = state.get(FACING);
                nextBlock(world, pos, dir, false);
            }
            world.scheduleBlockTick(pos, this, 5);
        }
        if(type == 3){
            Direction dir = state.get(FACING);
            BlockPos testpos = pos.offset(dir);
            if(world.getBlockState(testpos).getBlock() == Blocks.AIR){
                world.setBlockState(pos, state.with(TYPE, 1));
                nextBlock(world, pos, dir, true);
            }
        }
        if(type == 1){
            Direction dir = state.get(FACING).getOpposite();
            BlockPos testpos = pos.offset(dir);
            if(testpos == sourcePos){
                if(world.getBlockState(testpos).getBlock() == BlockInit.LIGHT_BRIDGE){
                    world.breakBlock(pos, false);
                    nextBlock(world, pos, dir, false);
                }
            }
        }
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int type = state.get(TYPE);
        if(type == 1){
            Direction direction = state.get(FACING).getOpposite();
            BlockPos behind = pos.offset(direction);
            if(world.getBlockState(behind).getBlock() != BlockInit.LIGHT_BRIDGE){
                nextBlock(world, pos, direction.getOpposite(), false);
            }else {
                world.scheduleBlockTick(pos, this, 10);
            }
        }
        cooldown = false;
        super.scheduledTick(state, world, pos, random);
    }

    private boolean warSchon = false;

    private void nextBlock(World world, BlockPos currentBlockPos, Direction direction, boolean bool){
        BlockPos testpos = currentBlockPos.offset(direction);
        if(bool){
            if(world.getBlockState(testpos).getBlock() != Blocks.AIR){
                if(world.getBlockState(currentBlockPos).get(TYPE) != 0){
                    if(world.getBlockState(currentBlockPos).get(TYPE) != 2){
                        world.setBlockState(currentBlockPos, world.getBlockState(currentBlockPos).with(TYPE, 3));
                    }
                }
                return;
            }
        }else {
            if(world.getBlockState(testpos).getBlock() != BlockInit.LIGHT_BRIDGE){
                return;
            }
        }
        Portal portal = RandomUtils.isPortalAtPosition(world, currentBlockPos);
        if(portal != null){
            if(warSchon){
                BlockPos nextBlockPos = currentBlockPos.offset(direction);
                if(bool) world.setBlockState(nextBlockPos, this.getDefaultState().with(TYPE, 1).with(FACING, direction));
                else world.setBlockState(nextBlockPos, Blocks.AIR.getDefaultState());
                nextBlock(world, nextBlockPos, direction, bool);
                world.scheduleBlockTick(nextBlockPos, world.getBlockState(nextBlockPos).getBlock(), 10);
                warSchon = false;
                return;
            }
            warSchon = true;
            Vec3d vec = portal.getDestination();
            World world2 = portal.getDestinationWorld();
            BlockPos nextBlockPos = null;
            if(portal.getOtherSideState().height() > 1){
                nextBlockPos = new BlockPos((int)vec.x, (int)vec.y-1, (int)vec.z);
            }else nextBlockPos = new BlockPos((int)vec.x, (int)vec.y, (int)vec.z);
            DQuaternion portalQ = portal.getOrientationRotation();
            Direction nextDirection = RandomUtils.getDirectionFromQuaternion(portalQ).getOpposite();
            if(bool) world.setBlockState(nextBlockPos, this.getDefaultState().with(TYPE, 1).with(FACING, nextDirection));
            else world.setBlockState(nextBlockPos, Blocks.AIR.getDefaultState());
            nextBlock(world2, nextBlockPos, nextDirection, bool);
            world.scheduleBlockTick(nextBlockPos, world.getBlockState(nextBlockPos).getBlock(), 10);
        }else {
            BlockPos nextBlockPos = currentBlockPos.offset(direction);
            if(bool) world.setBlockState(nextBlockPos, this.getDefaultState().with(TYPE, 1).with(FACING, direction));
            else world.setBlockState(nextBlockPos, Blocks.AIR.getDefaultState());
            nextBlock(world, nextBlockPos, direction, bool);
            world.scheduleBlockTick(nextBlockPos, world.getBlockState(nextBlockPos).getBlock(), 10);
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0, 0.4, 0, 1, 0.6, 1);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
