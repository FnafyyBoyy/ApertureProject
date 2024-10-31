package de.fnafhc.apertureproject.datagen;

import de.fnafhc.apertureproject.init.BlockInit;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        BlockStateModelGenerator.BlockTexturePool p1 = blockStateModelGenerator.registerCubeAllModelTexturePool(BlockInit.panel_white);
        BlockStateModelGenerator.BlockTexturePool p2 = blockStateModelGenerator.registerCubeAllModelTexturePool(BlockInit.panel_white_small);
        BlockStateModelGenerator.BlockTexturePool p3 = blockStateModelGenerator.registerCubeAllModelTexturePool(BlockInit.panel_black);
        BlockStateModelGenerator.BlockTexturePool p4 = blockStateModelGenerator.registerCubeAllModelTexturePool(BlockInit.panel_black_small);

        p1.stairs(BlockInit.panel_white_stairs);
        p2.stairs(BlockInit.panel_white_small_stairs);
        p3.stairs(BlockInit.panel_black_stairs);
        p4.stairs(BlockInit.panel_black_small_stairs);

        p1.slab(BlockInit.panel_white_slab);
        p2.slab(BlockInit.panel_white_small_slab);
        p3.slab(BlockInit.panel_black_slab);
        p4.slab(BlockInit.panel_black_small_slab);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
    }
}
