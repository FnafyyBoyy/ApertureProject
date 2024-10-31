package de.fnafhc.apertureproject.fluids;

import de.fnafhc.apertureproject.init.BlockInit;
import de.fnafhc.apertureproject.init.FluidInit;
import de.fnafhc.apertureproject.init.ItemInit;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;

public class GooFluid extends Goo{
    @Override
    public Fluid getStill() {
        return FluidInit.STILL_GOO;
    }

    @Override
    public Fluid getFlowing() {
        return FluidInit.FLOWING_GOO;
    }

    @Override
    public Item getBucketItem() {
        return ItemInit.GOO_BUCKET;
    }

    @Override
    public BlockState toBlockState(FluidState fluidState) {
        return BlockInit.GOO.getDefaultState().with(Properties.LEVEL_15, getBlockStateLevel(fluidState));
    }

    public static class Flowing extends GooFluid {
        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getLevel(FluidState fluidState) {
            return fluidState.get(LEVEL);
        }

        @Override
        public boolean isStill(FluidState fluidState) {
            return false;
        }
    }

    public static class Still extends GooFluid {
        @Override
        public int getLevel(FluidState fluidState) {
            return 8;
        }

        @Override
        public boolean isStill(FluidState fluidState) {
            return true;
        }
    }
}
