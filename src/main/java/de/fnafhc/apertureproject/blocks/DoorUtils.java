package de.fnafhc.apertureproject.blocks;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class DoorUtils {

    public static List<BlockPos> getNorth0(BlockPos pos){
        List<BlockPos> list = new ArrayList<>();
        BlockPos neben = pos.add(-1, 0, 0);
        BlockPos oben = pos.add(0, 1, 0);
        BlockPos nebenoben = pos.add(-1, 1, 0);
        list.add(neben);
        list.add(oben);
        list.add(nebenoben);
        return list;
    }

    public static List<BlockPos> getNorth1(BlockPos pos){
        List<BlockPos> list = new ArrayList<>();
        BlockPos neben = pos.add(1, 0, 0);
        BlockPos oben = pos.add(0, 1, 0);
        BlockPos nebenoben = pos.add(1, 1, 0);
        list.add(neben);
        list.add(oben);
        list.add(nebenoben);
        return list;
    }

    public static List<BlockPos> getNorth2(BlockPos pos){
        List<BlockPos> list = new ArrayList<>();
        BlockPos neben = pos.add(-1, 0, 0);
        BlockPos oben = pos.add(0, -1, 0);
        BlockPos nebenoben = pos.add(-1, -1, 0);
        list.add(neben);
        list.add(oben);
        list.add(nebenoben);
        return list;
    }

    public static List<BlockPos> getNorth3(BlockPos pos){
        List<BlockPos> list = new ArrayList<>();
        BlockPos neben = pos.add(1, 0, 0);
        BlockPos oben = pos.add(0, -1, 0);
        BlockPos nebenoben = pos.add(1, -1, 0);
        list.add(neben);
        list.add(oben);
        list.add(nebenoben);
        return list;
    }


    public static List<BlockPos> getSouth0(BlockPos pos){
        List<BlockPos> list = new ArrayList<>();
        BlockPos neben = pos.add(1, 0, 0);
        BlockPos oben = pos.add(0, 1, 0);
        BlockPos nebenoben = pos.add(1, 1, 0);
        list.add(neben);
        list.add(oben);
        list.add(nebenoben);
        return list;
    }

    public static List<BlockPos> getSouth1(BlockPos pos){
        List<BlockPos> list = new ArrayList<>();
        BlockPos neben = pos.add(-1, 0, 0);
        BlockPos oben = pos.add(0, 1, 0);
        BlockPos nebenoben = pos.add(-1, 1, 0);
        list.add(neben);
        list.add(oben);
        list.add(nebenoben);
        return list;
    }

    public static List<BlockPos> getSouth2(BlockPos pos){
        List<BlockPos> list = new ArrayList<>();
        BlockPos neben = pos.add(1, 0, 0);
        BlockPos oben = pos.add(0, -1, 0);
        BlockPos nebenoben = pos.add(1, -1, 0);
        list.add(neben);
        list.add(oben);
        list.add(nebenoben);
        return list;
    }

    public static List<BlockPos> getSouth3(BlockPos pos){
        List<BlockPos> list = new ArrayList<>();
        BlockPos neben = pos.add(-1, 0, 0);
        BlockPos oben = pos.add(0, -1, 0);
        BlockPos nebenoben = pos.add(-1, -1, 0);
        list.add(neben);
        list.add(oben);
        list.add(nebenoben);
        return list;
    }


    public static List<BlockPos> getEast0(BlockPos pos){
        List<BlockPos> list = new ArrayList<>();
        BlockPos neben = pos.add(0, 0, -1);
        BlockPos oben = pos.add(0, 1, 0);
        BlockPos nebenoben = pos.add(0, 1, -1);
        list.add(neben);
        list.add(oben);
        list.add(nebenoben);
        return list;
    }

    public static List<BlockPos> getEast1(BlockPos pos){
        List<BlockPos> list = new ArrayList<>();
        BlockPos neben = pos.add(0, 0, 1);
        BlockPos oben = pos.add(0, 1, 0);
        BlockPos nebenoben = pos.add(0, 1, 1);
        list.add(neben);
        list.add(oben);
        list.add(nebenoben);
        return list;
    }

    public static List<BlockPos> getEast2(BlockPos pos){
        List<BlockPos> list = new ArrayList<>();
        BlockPos neben = pos.add(0, 0, -1);
        BlockPos oben = pos.add(0, -1, 0);
        BlockPos nebenoben = pos.add(0, -1, -1);
        list.add(neben);
        list.add(oben);
        list.add(nebenoben);
        return list;
    }

    public static List<BlockPos> getEast3(BlockPos pos){
        List<BlockPos> list = new ArrayList<>();
        BlockPos neben = pos.add(0, 0, 1);
        BlockPos oben = pos.add(0, -1, 0);
        BlockPos nebenoben = pos.add(0, -1, 1);
        list.add(neben);
        list.add(oben);
        list.add(nebenoben);
        return list;
    }


    public static List<BlockPos> getWest0(BlockPos pos){
        List<BlockPos> list = new ArrayList<>();
        BlockPos neben = pos.add(0, 0, 1);
        BlockPos oben = pos.add(0, 1, 0);
        BlockPos nebenoben = pos.add(0, 1, 1);
        list.add(neben);
        list.add(oben);
        list.add(nebenoben);
        return list;
    }

    public static List<BlockPos> getWest1(BlockPos pos){
        List<BlockPos> list = new ArrayList<>();
        BlockPos neben = pos.add(0, 0, -1);
        BlockPos oben = pos.add(0, 1, 0);
        BlockPos nebenoben = pos.add(0, 1, -1);
        list.add(neben);
        list.add(oben);
        list.add(nebenoben);
        return list;
    }

    public static List<BlockPos> getWest2(BlockPos pos){
        List<BlockPos> list = new ArrayList<>();
        BlockPos neben = pos.add(0, 0, 1);
        BlockPos oben = pos.add(0, -1, 0);
        BlockPos nebenoben = pos.add(0, -1, 1);
        list.add(neben);
        list.add(oben);
        list.add(nebenoben);
        return list;
    }

    public static List<BlockPos> getWest3(BlockPos pos){
        List<BlockPos> list = new ArrayList<>();
        BlockPos neben = pos.add(0, 0, -1);
        BlockPos oben = pos.add(0, -1, 0);
        BlockPos nebenoben = pos.add(0, -1, -1);
        list.add(neben);
        list.add(oben);
        list.add(nebenoben);
        return list;
    }

    public static boolean isNeighbourRecievingPower(BlockPos pos, World world){
        BlockPos pos1 = pos.add(1, 0, 0);
        BlockPos pos2 = pos.add(0, 1, 0);
        BlockPos pos3 = pos.add(0, 0, 1);
        BlockPos pos4 = pos.add(0, -1, 0);
        BlockPos pos5 = pos.add(-1, 0, 0);
        BlockPos pos6 = pos.add(0, -1, -1);
        if(world.isReceivingRedstonePower(pos1)) return true;
        if(world.isReceivingRedstonePower(pos2)) return true;
        if(world.isReceivingRedstonePower(pos3)) return true;
        if(world.isReceivingRedstonePower(pos4)) return true;
        if(world.isReceivingRedstonePower(pos5)) return true;
        if(world.isReceivingRedstonePower(pos6)) return true;
        return false;
    }
}
