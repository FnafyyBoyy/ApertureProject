package de.fnafhc.apertureproject.items;

import de.fnafhc.apertureproject.Apertureproject;
import de.fnafhc.apertureproject.init.SoundInit;
import de.fnafhc.apertureproject.utils.Location;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Rarity;
import net.minecraft.util.UseAction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class PortalGunItem extends Item {
    public PortalGunItem() {
        super(new Item.Settings()
                .rarity(Rarity.EPIC)
                .maxCount(1));
    }

    public static void left(PlayerEntity p, HitResult result) {
        if(result.getType() == HitResult.Type.BLOCK){
            placePortal(0, p, result);
        }
    }

    public static void right(PlayerEntity p, HitResult result) {
        if(result.getType() == HitResult.Type.BLOCK){
            placePortal(1, p, result);
        }
    }

    private static void placePortal(int leftorright, PlayerEntity p, HitResult result){
        Vec3d oldblockPos = result.getPos();
        BlockHitResult blockHitResult = (BlockHitResult) result;
        Direction direction = blockHitResult.getSide();
        float pDirection2 = p.getYaw();
        Direction pDir = Direction.fromRotation(pDirection2);
        Vec3d blockPos;
        if(direction == Direction.UP){
            blockPos = oldblockPos;
        }else if(direction == Direction.DOWN) {
            blockPos = oldblockPos;
        }else if(direction == Direction.NORTH) {
            blockPos = oldblockPos;
        }else if(direction == Direction.WEST) {
            blockPos = oldblockPos;
        }else if(direction == Direction.SOUTH) {
            blockPos = oldblockPos;
        }else if(direction == Direction.EAST) {
            blockPos = oldblockPos;
        }else {
            blockPos = oldblockPos;
        }

        BlockPos blockPos1 = new BlockPos((int) blockPos.x, (int) blockPos.y, (int) blockPos.z);
        ServerPlayerEntity sp = Apertureproject.server.getPlayerManager().getPlayer(p.getUuid());
        Location loc = new Location(blockPos, sp.getServerWorld());
        if(Apertureproject.portalLoc1.get(p) == loc || Apertureproject.portalLoc2.get(p) == loc){
            return;
        }
        if(leftorright == 0){
            if(Apertureproject.portalLoc1.get(p) != null){
                Apertureproject.world.setBlockState(Apertureproject.portalLoc1.get(p).toBlockPos(), Blocks.AIR.getDefaultState());
            }
            Apertureproject.world.setBlockState(blockPos1, Blocks.BLUE_WOOL.getDefaultState());
            Apertureproject.playSoundAtPlayerLocation(p, SoundInit.portal1_shoot);
            if(Apertureproject.portalLoc2.get(p) != null){
                placePortal2(new Location(blockPos, sp.getServerWorld()), Apertureproject.portalLoc2.get(p), p);
            }
            Apertureproject.portalLoc1.put(sp, loc);
        }else if(leftorright == 1){
            if(Apertureproject.portalLoc2.get(p) != null){
                Apertureproject.world.setBlockState(Apertureproject.portalLoc2.get(p).toBlockPos(), Blocks.AIR.getDefaultState());
            }
            Apertureproject.playSoundAtPlayerLocation(p, SoundInit.portal2_shoot);
            Apertureproject.world.setBlockState(blockPos1, Blocks.YELLOW_WOOL.getDefaultState());
            if(Apertureproject.portalLoc1.get(p) != null){
                placePortal2(Apertureproject.portalLoc1.get(p), new Location(blockPos, sp.getServerWorld()), p);
            }
            Apertureproject.portalLoc2.put(sp, loc);
        }
    }

    private static void placePortal2(Location loc1, Location loc2, PlayerEntity p){
        Apertureproject.playSoundAtPlayerLocation(p, SoundInit.portal_open);
        BlockPos blockPos1 = new BlockPos(loc1.getVec2());
        BlockPos blockPos2 = new BlockPos(loc2.getVec2());
        Apertureproject.world.setBlockState(blockPos1, Blocks.BLUE_TERRACOTTA.getDefaultState());
        Apertureproject.world.setBlockState(blockPos2, Blocks.YELLOW_TERRACOTTA.getDefaultState());
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    public static void removePortals(PlayerEntity p){
        if(Apertureproject.portalLoc1.get(p) == null && Apertureproject.portalLoc2.get(p) == null){
            return;
        }
        Apertureproject.playSoundAtPlayerLocation(p, SoundInit.portal_close);
        if(Apertureproject.portalLoc1.get(p) != null) Apertureproject.world.setBlockState(Apertureproject.portalLoc1.get(p).toBlockPos(), Blocks.AIR.getDefaultState());
        if(Apertureproject.portalLoc2.get(p) != null) Apertureproject.world.setBlockState(Apertureproject.portalLoc2.get(p).toBlockPos(), Blocks.AIR.getDefaultState());
        Apertureproject.portalLoc1.remove(p);
        Apertureproject.portalLoc2.remove(p);
    }
}
