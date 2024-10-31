package de.fnafhc.apertureproject.utils;

import de.fnafhc.apertureproject.Apertureproject;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class Location {

    private int x;
    private int y;
    private int z;
    private int yaw;
    private ServerWorld world;

    public Location(int x, int y, int z, ServerWorld world) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Location(Vec3d vec, ServerWorld world) {
        this.x = (int) vec.x;
        this.y = (int) vec.y;
        this.z = (int) vec.z;
    }

    public Vec3d getVec(){
        return new Vec3d(x, y, z);
    }

    public Vec3i getVec2(){
        return new Vec3i(x, y, z);
    }

    public int x(){
        return this.x;
    }
    public int y(){
        return this.y;
    }
    public int z(){
        return this.z;
    }
    public ServerWorld world(){
        return this.world;
    }

    public BlockPos toBlockPos(){
        return new BlockPos(getVec2());
    }

    public NbtCompound toNbt() {
        NbtCompound nbt = new NbtCompound();
        nbt.putInt("X", this.x);
        nbt.putInt("Y", this.y);
        nbt.putInt("Z", this.z);
        return nbt;
    }

    // Laden aus NBT
    public static Location fromNbt(NbtCompound nbt) {
        int x = nbt.getInt("X");
        int y = nbt.getInt("Y");
        int z = nbt.getInt("Z");
        ServerWorld world = Apertureproject.world;
        return new Location(x, y, z, world);
    }
}
