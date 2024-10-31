package de.fnafhc.apertureproject.init;

import de.fnafhc.apertureproject.Apertureproject;
import de.fnafhc.apertureproject.fluids.GooFluid;
import net.minecraft.block.Block;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class FluidInit {
    public static FlowableFluid STILL_GOO;
    public static FlowableFluid FLOWING_GOO;

    public static void register() {
        STILL_GOO = Registry.register(Registries.FLUID, new Identifier(Apertureproject.modid, "goo"), new GooFluid.Still());
        FLOWING_GOO = Registry.register(Registries.FLUID, new Identifier(Apertureproject.modid, "flowing_goo"), new GooFluid.Flowing());
    }
}
