package de.fnafhc.apertureproject.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class LargeWhitePanel extends Block {

    public static final BooleanProperty UPPER = BooleanProperty.of("upper");

    public LargeWhitePanel() {
        super(AbstractBlock.Settings.create().strength(2));
        this.setDefaultState(this.getStateManager().getDefaultState().with(UPPER, false));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(UPPER);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        world.setBlockState(pos.add(0, 1, 0), state.with(UPPER, true));
        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if(state.get(UPPER)){
            world.breakBlock(pos.add(0, -1, 0), false);
        }else {
            world.breakBlock(pos.add(0, 1, 0), false);
        }
        return super.onBreak(world, pos, state, player);
    }
}
