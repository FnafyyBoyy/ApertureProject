package de.fnafhc.apertureproject.items;

import de.fnafhc.apertureproject.Apertureproject;
import de.fnafhc.apertureproject.init.EntityInit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class CubeSpawner2 extends Item {
    public CubeSpawner2(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(!context.getWorld().isClient){
            PlayerEntity player = context.getPlayer();
            if (player != null && !player.getItemCooldownManager().isCoolingDown(this)) {
                player.getItemCooldownManager().set(this, 10);
                BlockPos pos = context.getBlockPos();
                World world = context.getWorld();
                ServerWorld serverWorld = Apertureproject.server.getWorld(world.getRegistryKey());
                BlockPos newPos = pos.offset(context.getSide());
                EntityInit.CUBE2.spawn(serverWorld, newPos, SpawnReason.SPAWN_EGG);

                if (!player.isCreative()) {
                    ItemStack stack = context.getStack();
                    stack.setCount(stack.getCount() - 1);
                }

            }
        }


        return super.useOnBlock(context);
    }
}
