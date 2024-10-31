package de.fnafhc.apertureproject.init;

import de.fnafhc.apertureproject.Apertureproject;
import de.fnafhc.apertureproject.blocks.*;
import de.fnafhc.apertureproject.items.ButtonItem;
import de.fnafhc.apertureproject.items.DropperItem;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BlockInit {
    public static Block panel_black = createBlock(2);
    public static Block panel_black_small = createBlock(2);
    public static Block panel_black_large = new LargeBlackPanel();
    public static Block panel_white = createBlock(2);
    public static Block panel_white_small = createBlock(2);
    public static Block panel_white_large = new LargeWhitePanel();

    public static StairsBlock panel_black_stairs = createStairsBlock(2, panel_black);
    public static StairsBlock panel_black_small_stairs = createStairsBlock(2, panel_black_small);
    public static StairsBlock panel_white_stairs = createStairsBlock(2, panel_white);
    public static StairsBlock panel_white_small_stairs = createStairsBlock(2, panel_white_small);

    public static SlabBlock panel_black_slab = createSlabBlock(2, panel_black);
    public static SlabBlock panel_black_small_slab = createSlabBlock(2, panel_black_small);
    public static SlabBlock panel_white_slab = createSlabBlock(2, panel_white);
    public static SlabBlock panel_white_small_slab = createSlabBlock(2, panel_white_small);

    public static Block super_button = new SuperButton();
    public static Block button = new Button();
    public static Block wire = new Wire();
    public static Block dropper = new Dropper();
    public static Block door = new Door();
    public static Block LIGHT_BRIDGE = new LightBridgeBlock();
    public static Block ELEVATOR = new Elevator();
    public static Block ELEVATORHOLDER = new ElevatorHolder();

    public static Block GOO;

    public static void register(){
        registerBlock("panel_black", panel_black, true);
        registerBlock("panel_black_small", panel_black_small, true);
        registerBlock("panel_black_large", panel_black_large, true);
        registerBlock("panel_white", panel_white, true);
        registerBlock("panel_white_small", panel_white_small, true);
        registerBlock("panel_white_large", panel_white_large, true);

        registerBlock("panel_black_stairs", panel_black_stairs, true);
        registerBlock("panel_black_small_stairs", panel_black_small_stairs, true);
        registerBlock("panel_white_stairs", panel_white_stairs, true);
        registerBlock("panel_white_small_stairs", panel_white_small_stairs, true);

        registerBlock("panel_black_slab", panel_black_slab, true);
        registerBlock("panel_black_small_slab", panel_black_small_slab, true);
        registerBlock("panel_white_slab", panel_white_slab, true);
        registerBlock("panel_white_small_slab", panel_white_small_slab, true);

        registerBlock("super_button", super_button, true);
        registerBlock("button", button, new ButtonItem(button, new Item.Settings()));
        registerBlock("wire", wire, true);
        registerBlock("dropper", dropper, new DropperItem(dropper, new Item.Settings()));
        registerBlock("door", door, true);
        registerBlock("lightbridge", LIGHT_BRIDGE, true);
        registerBlock("elevator", ELEVATOR, true);
        registerBlock("elevatorholder", ELEVATORHOLDER, true);

        GOO = Registry.register(Registries.BLOCK, new Identifier(Apertureproject.modid, "gooo"), new GooFBlock(FluidInit.FLOWING_GOO, FabricBlockSettings.copy(Blocks.LAVA).liquid()));
    }

    public static void registerBlock(String id, Block block, boolean alsoItem){
        Registry.register(Registries.BLOCK, new Identifier(Apertureproject.modid, id), block);
        if(alsoItem){
            Registry.register(Registries.ITEM, new Identifier(Apertureproject.modid, id), new BlockItem(block, new Item.Settings()));
        }
    }

    public static void registerBlock(String id, Block block, BlockItem blockItem){
        Registry.register(Registries.BLOCK, new Identifier(Apertureproject.modid, id), block);
        Registry.register(Registries.ITEM, new Identifier(Apertureproject.modid, id), blockItem);
    }

    public static Block createBlock(int strength){
        return new NormalBlock(AbstractBlock.Settings.create().strength(strength));
    }

    public static StairsBlock createStairsBlock(int strength, Block block){
        return new StairsBlock(block.getDefaultState(), AbstractBlock.Settings.create().strength(strength));
    }

    public static SlabBlock createSlabBlock(int strength, Block block){
        return new SlabBlock(AbstractBlock.Settings.create().strength(strength));
    }
}
