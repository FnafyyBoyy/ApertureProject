package de.fnafhc.apertureproject.utils;

import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class NeighbourUtils {
    public static List<BlockPos> getNeighbours(BlockPos pos) {
        List<BlockPos> posList = new ArrayList<>();
        posList.add(new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ()));
        posList.add(new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ()));
        posList.add(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()));
        posList.add(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ()));
        posList.add(new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1));
        posList.add(new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 1));
        posList.add(new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ() + 1));
        posList.add(new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ() - 1));
        posList.add(new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ() + 1));
        posList.add(new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ() - 1));
        posList.add(new BlockPos(pos.getX() + 1, pos.getY() + 1, pos.getZ()));
        posList.add(new BlockPos(pos.getX() + 1, pos.getY() - 1, pos.getZ()));
        posList.add(new BlockPos(pos.getX() - 1, pos.getY() + 1, pos.getZ()));
        posList.add(new BlockPos(pos.getX() - 1, pos.getY() - 1, pos.getZ()));
        posList.add(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ() + 1));
        posList.add(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ() - 1));
        posList.add(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ() + 1));
        posList.add(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ() - 1));
        return posList;
    }
}
