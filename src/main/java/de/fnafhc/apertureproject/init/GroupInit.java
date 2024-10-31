package de.fnafhc.apertureproject.init;

import de.fnafhc.apertureproject.Apertureproject;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class GroupInit {
    public static ItemGroup bblocksGroup = FabricItemGroup.builder()
            .icon(() -> new ItemStack(BlockInit.panel_white))
            .displayName(Text.translatable("itemGroup.apbblocks"))
            .entries((context, entries) -> {
                entries.add(BlockInit.panel_white);
                entries.add(BlockInit.panel_white_stairs);
                entries.add(BlockInit.panel_white_slab);
                entries.add(BlockInit.panel_white_large);
                entries.add(BlockInit.panel_white_small);
                entries.add(BlockInit.panel_white_small_stairs);
                entries.add(BlockInit.panel_white_small_slab);
                entries.add(BlockInit.panel_black);
                entries.add(BlockInit.panel_black_stairs);
                entries.add(BlockInit.panel_black_slab);
                entries.add(BlockInit.panel_black_large);
                entries.add(BlockInit.panel_black_small);
                entries.add(BlockInit.panel_black_small_stairs);
                entries.add(BlockInit.panel_black_small_slab);
                entries.add(BlockInit.ELEVATORHOLDER);
            })
            .build();

    public static ItemGroup blocksGroup = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ItemInit.CUBE_SPAWN))
            .displayName(Text.translatable("itemGroup.apblocks"))
            .entries((context, entries) -> {
                entries.add(BlockInit.super_button);
                entries.add(BlockInit.button);
                entries.add(BlockInit.wire);
                entries.add(ItemInit.CUBE_SPAWN);
                entries.add(ItemInit.CUBE_SPAWN2);
                entries.add(BlockInit.dropper);
                entries.add(dropper2());
                entries.add(dropper3());
                entries.add(BlockInit.door);
                entries.add(BlockInit.LIGHT_BRIDGE);
                entries.add(BlockInit.ELEVATOR);
            })
            .build();

    public static ItemGroup itemsGroup = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ItemInit.PORTAL_GUN))
            .displayName(Text.translatable("itemGroup.apitems"))
            .entries((context, entries) -> {
                entries.add(ItemInit.PORTAL_GUN);
                entries.add(ItemInit.TESTER);
                try {
                    entries.add(portalgun());
                }catch (Exception ex) {}
            })
            .build();

    public static void register(){
        Registry.register(Registries.ITEM_GROUP, new Identifier(Apertureproject.modid, "bblockgroup"), bblocksGroup);

        Registry.register(Registries.ITEM_GROUP, new Identifier(Apertureproject.modid, "blockgroup"), blocksGroup);

        Registry.register(Registries.ITEM_GROUP, new Identifier(Apertureproject.modid, "itemgroup"), itemsGroup);
    }

    public static ItemStack portalgun() {
        ItemStack portalGunStack = new ItemStack(Registries.ITEM.get(new Identifier("portalgun", "portal_gun")));
        portalGunStack.getOrCreateNbt().putString("allowedBlocks", "[\"#apertureproject:allowedblocks\"]");
        return portalGunStack;
    }

    public static ItemStack dropper2(){
        ItemStack item = new ItemStack(BlockInit.dropper);
        item.getOrCreateNbt().putInt("type", 1);
        return item;
    }

    public static ItemStack dropper3(){
        ItemStack item = new ItemStack(BlockInit.dropper);
        item.getOrCreateNbt().putInt("type", 2);
        return item;
    }
}
