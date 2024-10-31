package de.fnafhc.apertureproject.blocks;

import de.fnafhc.apertureproject.Apertureproject;
import de.fnafhc.apertureproject.init.SoundInit;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
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
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static de.fnafhc.apertureproject.Apertureproject.server;

public class Button extends Block {

    public static final BooleanProperty PRESSED = BooleanProperty.of("pressed");
    public static final IntProperty COOLDOWN = IntProperty.of("cooldown", 0, 60);
    public static final IntProperty TIMER = IntProperty.of("timer", 0, 60);
    public static final DirectionProperty FACING = Properties.FACING;
    public static final IntProperty MODEL_ROTATION = IntProperty.of("model_rotation", 0, 3);

    public Button() {
        super(AbstractBlock.Settings.create().strength(2));
        this.setDefaultState(this.getStateManager().getDefaultState()
                .with(PRESSED, false)
                .with(COOLDOWN, 0)
                .with(TIMER, 1)
                .with(FACING, Direction.NORTH)
                .with(MODEL_ROTATION, 0));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PRESSED, COOLDOWN, TIMER, FACING, MODEL_ROTATION);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction facing = ctx.getPlayerLookDirection().getOpposite();
        int rotation = 0;

        if (facing == Direction.DOWN) {
            rotation = (int)(ctx.getPlayer().getY() / 90) % 4;
        }

        return getDefaultState().with(FACING, facing).with(MODEL_ROTATION, rotation);
    }

    public void onPlaced(World world2, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if(!world2.isClient){
            ServerWorld world = server.getWorld(world2.getRegistryKey());
            super.onPlaced(world, pos, state, placer, itemStack);
            try {
                int timer = itemStack.getNbt().getInt("time");
                world.setBlockState(pos, state.with(TIMER, timer).with(PRESSED, false));
            } catch (Exception ex) {
                world.setBlockState(pos, state.with(TIMER, 1).with(PRESSED, false));
            }
        }
    }

    public ActionResult onUse(BlockState state, World world2, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world2.isClient){
            ServerWorld world = server.getWorld(world2.getRegistryKey());
            if (!world.isClient) {
                int timer = state.get(TIMER);
                world.setBlockState(pos, state.with(PRESSED, true).with(COOLDOWN, timer));
                Apertureproject.playSoundAtPlayerLocation(player, SoundInit.button_press);
                world.scheduleBlockTick(pos, this, 20);
                world.updateNeighborsAlways(pos, this);
            }
        }
        return ActionResult.SUCCESS;
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int cooldown = state.get(COOLDOWN);
        world.setBlockState(pos, state.with(PRESSED, false));
        if (cooldown > 1) {
            world.setBlockState(pos, state.with(PRESSED, true).with(COOLDOWN, cooldown - 1));
            world.scheduleBlockTick(pos, this, 20);
            world.updateNeighborsAlways(pos, this);
        } else {
            if (state.get(PRESSED)) {
                world.setBlockState(pos, state.with(PRESSED, false).with(COOLDOWN, 0));
                Apertureproject.playSoundAtPlayerLocation(pos, SoundInit.button_release, world);
                world.updateNeighborsAlways(pos, this);
            }
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

    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return state.get(PRESSED) ? 15 : 0;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction facing = state.get(FACING);
        int rotation = state.get(MODEL_ROTATION);

        if (state.get(PRESSED)) {
            switch (facing) {
                case UP:
                    return VoxelShapes.cuboid(0.35, 0.35, 0.35, 0.65, 1, 0.65);
                case DOWN:
                    return getRotatedShape(rotation);
                default:
                    return VoxelShapes.cuboid(0.35, 0, 0.35, 0.65, 1.2, 0.65);
            }
        } else {
            switch (facing) {
                case UP:
                    return VoxelShapes.cuboid(0.35, 0.35, 0.35, 0.65, 1.3, 0.65);
                case DOWN:
                    return getRotatedShape(rotation);
                default:
                    return VoxelShapes.cuboid(0.35, 0, 0.35, 0.65, 1.3, 0.65);
            }
        }
    }

    private VoxelShape getRotatedShape(int rotation) {
        switch (rotation) {
            case 1:
                return VoxelShapes.cuboid(0.35, 0, 0.35, 0.65, 0.65, 0.65); // 90° Rotation
            case 2:
                return VoxelShapes.cuboid(0.35, 0, 0.35, 0.65, 0.65, 0.65); // 180° Rotation
            case 3:
                return VoxelShapes.cuboid(0.35, 0, 0.35, 0.65, 0.65, 0.65); // 270° Rotation
            default:
                return VoxelShapes.cuboid(0.35, 0, 0.35, 0.65, 0.65, 0.65); // Keine Rotation
        }
    }
}
