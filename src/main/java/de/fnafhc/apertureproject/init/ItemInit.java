package de.fnafhc.apertureproject.init;

import de.fnafhc.apertureproject.Apertureproject;
import de.fnafhc.apertureproject.items.CubeSpawner;
import de.fnafhc.apertureproject.items.CubeSpawner2;
import de.fnafhc.apertureproject.items.PortalGunItem;
import de.fnafhc.apertureproject.items.TestItem;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ItemInit {

    public static Item PORTAL_GUN = new PortalGunItem();

    public static Item TESTER = new TestItem();

    public static Item CUBE_SPAWN = new CubeSpawner(new Item.Settings());
    public static Item CUBE_SPAWN2 = new CubeSpawner2(new Item.Settings());
    public static BucketItem GOO_BUCKET;

    public static void register() {
        registerItem("portalgun", PORTAL_GUN);
        registerItem("cube_spawner", CUBE_SPAWN);
        registerItem("cube_spawner2", CUBE_SPAWN2);
        registerItem("tester", TESTER);
        GOO_BUCKET = Registry.register(Registries.ITEM, new Identifier(Apertureproject.modid, "goo_bucket"), new BucketItem(FluidInit.STILL_GOO, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1)));
    }

    private static void registerItem(String id, Item item){
        Registry.register(Registries.ITEM, new Identifier(Apertureproject.modid, id), item);
    }

    private static Item createItem(){
        return new Item(new Item.Settings());
    }

    private static SpawnEggItem createItem(EntityType type){
        return new SpawnEggItem(type, 0, 0, new Item.Settings());
    }
}
