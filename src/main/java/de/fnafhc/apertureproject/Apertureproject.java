package de.fnafhc.apertureproject;

import de.fnafhc.apertureproject.datagen.*;
import de.fnafhc.apertureproject.entities.Cube;
import de.fnafhc.apertureproject.entities.Cube2;
import de.fnafhc.apertureproject.init.*;
import de.fnafhc.apertureproject.utils.Location;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import qouteall.imm_ptl.core.api.PortalAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Apertureproject implements ModInitializer, DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(ModBlockTagProvider::new);
        pack.addProvider(ModItemTagProvider::new);
        pack.addProvider(ModLootTableProvider::new);
        pack.addProvider(ModModelProvider::new);
    }

    public static String modid = "apertureproject";

    public static final Identifier G_PACKET = new Identifier(modid, "gpacket");

    public static final Identifier HOLD_PACKET = new Identifier(modid, "hold_packet");

    public static ServerWorld world;
    public static MinecraftServer server;

    public static Map<ServerPlayerEntity, Location> portalLoc1 = new HashMap<>();
    public static Map<ServerPlayerEntity, Location> portalLoc2 = new HashMap<>();

    public static final GameRules.Key<GameRules.BooleanRule> PICKUP_RULE = GameRuleRegistry.register("allowEveryBlockPickup", GameRules.Category.PLAYER, GameRuleFactory.createBooleanRule(false));

    @Override
    public void onInitialize() {
        //#apertureproject:allowedblocks
        ServerLifecycleEvents.SERVER_STARTED.register(this::onServerStarted);
        FluidInit.register();
        BlockInit.register();
        ItemInit.register();
        GroupInit.register();
        SoundInit.register();
        EntityInit.register();
        ModBlockEntities.register();
        ServerTickEvents.END_SERVER_TICK.register(this::onEndServerTick);

        ServerPlayNetworking.registerGlobalReceiver(G_PACKET, (server, player, handler, buf, responseSender) -> {
            int entityId = buf.readInt();
            server.execute(() -> {
                isHolding.put(player, true);
                ppe(entityId, player);
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(HOLD_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                isHolding.put(player, false);
            });
        });
    }

    private void onServerStarted(MinecraftServer server) {
        world = server.getWorld(server.getOverworld().getRegistryKey());
        System.out.println("Server Method Good!!");
        this.server = server;
    }

    private void onEndServerTick(MinecraftServer server) {
        Apertureproject.server = server;
        List<PlayerEntity> playersToRemove = new ArrayList<>();

        for (PlayerEntity p : isRunning) {
            Entity entity = fb.get(p);
            if(entity instanceof Cube){
                Cube cube = (Cube) entity;
                cube.setHeld(true);
            }
            if(entity instanceof Cube2){
                Cube2 cube = (Cube2) entity;
                cube.setHeld(true);
            }
            if(!isHolding.get(p)){
                playersToRemove.add(p);
            }else {

                ServerWorld serverWorld = (ServerWorld) entity.getWorld();
                Vec3d pos = getPositionInFrontOfPlayer(p, 2.7);
                if (entity != null) {
                    moveEntityTowards(entity, pos);
                    //entity.setPosition(pos.x, pos.y, pos.z);
                    //entity.refreshPositionAndAngles(pos.x, pos.y, pos.z, entity.getYaw(), entity.getPitch());
                }
            }
        }

        for (PlayerEntity p : playersToRemove) {
            if(fb.get(p) instanceof Cube){
                Cube cube = (Cube) fb.get(p);
                cube.setHeld(false);
            }
            if(fb.get(p) instanceof Cube2){
                Cube2 cube = (Cube2) fb.get(p);
                cube.setHeld(false);
            }
            isRunning.remove(p);
            isHolding.remove(p);
            fb.remove(p);
        }
    }

    public void moveEntityTowards(Entity entity, Vec3d targetPosition) {
        Vec3d currentPosition = entity.getPos();
        Vec3d direction = targetPosition.subtract(currentPosition);
        double length = direction.length();
        double stepSize = 0.1;
        if(length < 0.5){
            stepSize = 0;
        }
        if(length < 1){
            stepSize = 0.2;
        }
        else if(length < 3){
            stepSize = 1;
        }
        else if(length < 5){
            stepSize = 2;
        }else stepSize = 10;
        if (length > 0) {
            Vec3d normalizedDirection = direction.multiply(1.0 / length);
            Vec3d movement = normalizedDirection.multiply(stepSize);
            entity.move(MovementType.SELF, movement);
        }
    }

    public static void ppe(int entityID, PlayerEntity p){
        isRunning.add(p);
        isHolding.put(p, true);
        Entity entity = p.getWorld().getEntityById(entityID);
        fb.put(p, entity);
    }

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static List<PlayerEntity> isRunning = new ArrayList<>();
    public static Map<PlayerEntity, Boolean> isHolding = new HashMap<>();
    public static Map<PlayerEntity, Entity> fb = new HashMap<>();

    public static Vec3d getPositionInFrontOfPlayer(PlayerEntity player, double distance) {
        Vec3d lookVec = player.getRotationVec(1.0F);
        double x = player.getX() + lookVec.x * distance;
        double y = player.getY() + lookVec.y * distance + 1.2;
        double z = player.getZ() + lookVec.z * distance;

        return new Vec3d(x, y, z);
    }

    public static boolean isBlockInCustomTag(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        TagKey<Block> customTag = TagKey.of(Registries.BLOCK.getKey(), new Identifier(modid, "allowedblocks"));
        return state.isIn(customTag);
    }

    public static void playSoundAtPlayerLocation(Entity player, SoundEvent sound) {
        World world = player.getWorld();
        ServerWorld  world2 = server.getWorld(world.getRegistryKey());
        BlockPos playerPos = player.getBlockPos();
        world2.playSound(null, playerPos, sound, SoundCategory.MASTER, 1.0F, 1.0F);
    }

    public static void playSoundAtPlayerLocation(BlockPos player, SoundEvent sound, ServerWorld world) {
        BlockPos playerPos = player;
        world.playSound(null, playerPos, sound, SoundCategory.MASTER, 0.6F, 1.0F);
    }

    public static Cube getCubeByID(World world, int id){
        Box box = new Box(world.getBottomY(), world.getBottomY(), world.getBottomY(),
                world.getTopY(), world.getTopY(), world.getTopY());
        for (Cube cube : world.getEntitiesByType(EntityInit.CUBE, box, entity -> entity instanceof Cube)) {
            int id2 = cube.getID();
            if(id == id2){
                return cube;
            }
        }
        return null;
    }

    public static Cube2 getCubeByID2(World world, int id){
        Box box = new Box(world.getBottomY(), world.getBottomY(), world.getBottomY(),
                world.getTopY(), world.getTopY(), world.getTopY());
        for (Cube2 cube : world.getEntitiesByType(EntityInit.CUBE2, box, entity -> entity instanceof Cube2)) {
            int id2 = cube.getID();
            if(id == id2){
                return cube;
            }
        }
        return null;
    }
}
