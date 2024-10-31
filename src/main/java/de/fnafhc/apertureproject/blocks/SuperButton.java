package de.fnafhc.apertureproject.blocks;

import de.fnafhc.apertureproject.Apertureproject;
import de.fnafhc.apertureproject.entities.Cube;
import de.fnafhc.apertureproject.init.SoundInit;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class SuperButton extends Block {

    public static final BooleanProperty PRESSED = BooleanProperty.of("pressed");
    public static final IntProperty CUBE_ID = IntProperty.of("cube_id", 100, 1000);

    public SuperButton() {
        super(AbstractBlock.Settings.create().strength(2));

        this.setDefaultState(this.getStateManager().getDefaultState().with(PRESSED, false).with(CUBE_ID, 100));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PRESSED, CUBE_ID);
    }

    private Cube cube;

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!world.isClient() && !(entity instanceof ItemEntity)) {
            boolean currentState = state.get(PRESSED);
            if (!currentState) {
                world.updateNeighborsAlways(pos, this);
                if(entity instanceof Cube){
                    Cube cube = (Cube) entity;
                    cube.setOn(true);
                    world.setBlockState(pos, state.with(CUBE_ID, cube.getID()).with(PRESSED, true));
                    this.cube = cube;
                }else {
                    world.setBlockState(pos, state.with(PRESSED, true));
                }
                Apertureproject.playSoundAtPlayerLocation(entity, SoundInit.super_button_down);
                world.scheduleBlockTick(pos, this, 1);
            }
        }
        super.onSteppedOn(world, pos, state, entity);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!world.isClient() && entity instanceof PlayerEntity) {
            world.scheduleBlockTick(pos, this, 1);
        }
        super.onEntityCollision(state, world, pos, entity);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        boolean isEntityOnBlock = !world.getEntitiesByClass(Entity.class, new Box(pos), entity -> true).isEmpty();

        if (!isEntityOnBlock && state.get(PRESSED)) {
            world.setBlockState(pos, state.with(PRESSED, false));
            if(this.cube != null){
                this.cube.setOn(false);
                this.cube = null;
            }
            Apertureproject.playSoundAtPlayerLocation(pos, SoundInit.super_button_up, world);
            world.updateNeighborsAlways(pos, this);
        }
        if(state.get(PRESSED)){
            world.scheduleBlockTick(pos, this, 1);
        }
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction side) {
        return state.get(PRESSED) ? 15 : 0;
    }

    @Override
    public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction side) {
        return state.get(PRESSED) ? 15 : 0;
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return state.get(PRESSED) ? 15 : 0;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if(state.get(PRESSED)) {
            return VoxelShapes.cuboid(-0.2, 0, -0.2, 1.2, 0.3, 1.2);
        }else return VoxelShapes.cuboid(-0.2, 0, -0.2, 1.2, 0.4, 1.2);
    }
}
