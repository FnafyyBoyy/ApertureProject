package de.fnafhc.apertureproject.blocks;

import com.mojang.serialization.MapCodec;
import de.fnafhc.apertureproject.entities.Cube;
import de.fnafhc.apertureproject.entities.Cube2;
import de.fnafhc.apertureproject.init.EntityInit;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static de.fnafhc.apertureproject.Apertureproject.getPositionInFrontOfPlayer;
import static de.fnafhc.apertureproject.Apertureproject.server;

public class Dropper extends BlockWithEntity {

    public static final BooleanProperty OPEN = BooleanProperty.of("open");
    public static final IntProperty CUBE_TYPE = IntProperty.of("cubetype", 0, 2);

    public Dropper() {
        super(AbstractBlock.Settings.create().strength(2));
        this.setDefaultState(this.getStateManager().getDefaultState().with(OPEN, false).with(CUBE_TYPE, 0));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(OPEN, CUBE_TYPE);
    }

    public void onPlaced(World world2, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if(!world2.isClient){
            ServerWorld world = server.getWorld(world2.getRegistryKey());
            super.onPlaced(world, pos, state, placer, itemStack);
            try {
                int type = itemStack.getNbt().getInt("type");
                world.setBlockState(pos, state.with(CUBE_TYPE, type));
            } catch (Exception ex) {
            }
        }
    }

    private boolean isCooldown = false;
    private boolean firstDrop = false;

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if(!world.isClient){
            for (Direction direction : Direction.values()) {
                int power = world.getEmittedRedstonePower(sourcePos, direction);
                BlockEntity blockEntity = world.getBlockEntity(pos);
                boolean e = false;
                if (blockEntity instanceof DropperBlockEntity dropperBlockEntity) {
                    if (power > 0) {
                        e = true;
                        if(isCooldown){
                            return;
                        }
                        isCooldown = true;
                        world.setBlockState(pos.add(0,1,0), Blocks.AIR.getDefaultState());
                        world.setBlockState(pos, state.with(OPEN, true));
                        if (dropperBlockEntity.getStoredEntity(world) != null) {
                            dropperBlockEntity.getStoredEntity(world).kill();
                        }
                        if(state.get(CUBE_TYPE) == 0){
                            if (dropperBlockEntity.getStoredEntity(world) != null) {
                                dropperBlockEntity.getStoredEntity(world).kill();
                            }
                        }else if(state.get(CUBE_TYPE) == 1){
                            if (dropperBlockEntity.getStoredEntity2(world) != null) {
                                dropperBlockEntity.getStoredEntity2(world).kill();
                            }
                        }
                        firstDrop = true;
                        world.scheduleBlockTick(pos, this, 7);
                    }
                }
            }
        }
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if(state.get(OPEN)){
            if(firstDrop){
                firstDrop = false;
                BlockEntity blockEntity = world.getBlockEntity(pos);
                if (blockEntity instanceof DropperBlockEntity dropperBlockEntity) {
                    if(state.get(CUBE_TYPE) == 0){
                        Cube entity = EntityInit.CUBE.create(world);
                        entity.refreshPositionAndAngles(pos.getX()+0.5, pos.getY() + 1, pos.getZ()+0.5, world.random.nextFloat() * 360F, 0);
                        world.spawnEntity(entity);
                        dropperBlockEntity.setStoredEntity(entity);
                    }else if(state.get(CUBE_TYPE) == 1){
                        Cube2 entity = EntityInit.CUBE2.create(world);
                        entity.refreshPositionAndAngles(pos.getX()+0.5, pos.getY() + 1, pos.getZ()+0.5, world.random.nextFloat() * 360F, 0);
                        world.spawnEntity(entity);
                        dropperBlockEntity.setStoredEntity(entity);
                    }
                    world.scheduleBlockTick(pos, this, 10);
                }
            }else {
                isCooldown = false;
                world.setBlockState(pos, state.with(OPEN, false));
            }
        }
        super.scheduledTick(state, world, pos, random);
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if(!world.isClient){
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if(state.get(CUBE_TYPE) == 0){
                if (blockEntity instanceof DropperBlockEntity dropperBlockEntity) {
                    if (dropperBlockEntity.getStoredEntity(world) != null) {
                        dropperBlockEntity.getStoredEntity(world).kill();
                    }
                }
            }
            if(state.get(CUBE_TYPE) == 1){
                if (blockEntity instanceof DropperBlockEntity dropperBlockEntity) {
                    if (dropperBlockEntity.getStoredEntity2(world) != null) {
                        dropperBlockEntity.getStoredEntity2(world).kill();
                    }
                }
            }
        }
        return super.onBreak(world, pos, state, player);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if(state.get(OPEN)){
            return box();
        }
        return box2();
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return null;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public VoxelShape box(){
        return VoxelShapes.union(
                VoxelShapes.cuboid(-0.2, -0.3, 0.0, 0.0, 1.0, 1.0),
                VoxelShapes.cuboid(1.0, -0.3, 0.0, 1.2, 1.0, 1.0),
                VoxelShapes.cuboid(0.0, -0.3, -0.2, 1.0, 1.0, 0.0),
                VoxelShapes.cuboid(0.0, -0.3, 1.0, 1.0, 1.0, 1.2)
        );
    }

    public VoxelShape box2(){
        return VoxelShapes.union(
                VoxelShapes.cuboid(0, -0.3, 0, 1, -0.2, 1),
                VoxelShapes.cuboid(-0.2, -0.3, 0.0, 0.0, 1.0, 1.0),
                VoxelShapes.cuboid(1.0, -0.3, 0.0, 1.2, 1.0, 1.0),
                VoxelShapes.cuboid(0.0, -0.3, -0.2, 1.0, 1.0, 0.0),
                VoxelShapes.cuboid(0.0, -0.3, 1.0, 1.0, 1.0, 1.2)
        );
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DropperBlockEntity(pos, state);
    }
}
