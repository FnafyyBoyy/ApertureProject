package de.fnafhc.apertureproject.items;

import de.fnafhc.apertureproject.utils.RandomUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import qouteall.imm_ptl.core.api.PortalAPI;
import qouteall.imm_ptl.core.portal.Portal;

public class TestItem extends Item {
    public TestItem() {
        super(new Item.Settings().maxCount(1));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        Portal portal = RandomUtils.isPortalAtPosition(world, user.getBlockPos());
        if(portal != null){
            System.out.println("YEEE");
        }else System.out.println("NOOO");
        return super.use(world, user, hand);
    }
}
