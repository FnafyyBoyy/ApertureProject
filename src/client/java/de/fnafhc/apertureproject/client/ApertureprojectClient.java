package de.fnafhc.apertureproject.client;

import de.fnafhc.apertureproject.Apertureproject;
import de.fnafhc.apertureproject.client.models.Cube2Model;
import de.fnafhc.apertureproject.client.models.CubeModel;
import de.fnafhc.apertureproject.client.render.Cube2Renderer;
import de.fnafhc.apertureproject.client.render.CubeRenderer;
import de.fnafhc.apertureproject.client.render.WireBlockEntityRenderer;
import de.fnafhc.apertureproject.entities.Cube;
import de.fnafhc.apertureproject.entities.Cube2;
import de.fnafhc.apertureproject.init.BlockInit;
import de.fnafhc.apertureproject.init.EntityInit;
import de.fnafhc.apertureproject.init.FluidInit;
import de.fnafhc.apertureproject.init.ModBlockEntities;
import de.fnafhc.apertureproject.items.ButtonItem;
import de.fnafhc.apertureproject.items.DropperItem;
import de.fnafhc.apertureproject.items.PortalGunItem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screen.v1.ScreenMouseEvents;
import net.fabricmc.fabric.api.event.client.ItemTooltipCallback;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

import static de.fnafhc.apertureproject.Apertureproject.isHolding;
import static de.fnafhc.apertureproject.Apertureproject.modid;

public class ApertureprojectClient implements ClientModInitializer {

    public static KeyBinding key;
    public static KeyBinding key2;
    private static boolean isLeftClickPressed = false;
    private static boolean isRightClickPressed = false;
    private static boolean isInBlock = false;
    private static boolean isHolding = false;

    public static int LEFT_CLICK = 0;
    public static int RIGHT_CLICK = 1;

    @Override
    public void onInitializeClient() {

        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.CUBE, CubeModel::getTexturedModelData);
        EntityRendererRegistry.register(EntityInit.CUBE, CubeRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.CUBE2, Cube2Model::getTexturedModelData);
        EntityRendererRegistry.register(EntityInit.CUBE2, Cube2Renderer::new);

        BlockEntityRendererRegistry.register(ModBlockEntities.WIRE_BLOCK_ENTITY, WireBlockEntityRenderer::new);

        FluidRenderHandlerRegistry.INSTANCE.register(FluidInit.STILL_GOO, FluidInit.FLOWING_GOO, new SimpleFluidRenderHandler(
                new Identifier(modid + ":block/goo"),
                new Identifier(modid + ":block/goo")
        ));

        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), FluidInit.STILL_GOO, FluidInit.FLOWING_GOO);

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.dropper, RenderLayer.getTranslucent());

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.LIGHT_BRIDGE, RenderLayer.getTranslucent());

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.ELEVATOR, RenderLayer.getTranslucent());

        ItemTooltipCallback.EVENT.register((stack, world, tooltip) -> {
            if(stack.getItem() instanceof ButtonItem){
                if (stack.hasNbt() && stack.getNbt().contains("time")) {
                    int yourValue = stack.getNbt().getInt("time");
                    if(yourValue == 0){
                        yourValue = 1;
                    }
                    tooltip.add(Text.literal("Time: " + yourValue + " Seconds").formatted(Formatting.GRAY));
                }else {
                    tooltip.add(Text.literal("Time: 1 Seconds").formatted(Formatting.GRAY));
                }
            }
            if(stack.getItem() instanceof DropperItem){
                if (stack.hasNbt() && stack.getNbt().contains("type")) {
                    int type = stack.getNbt().getInt("type");
                    if(type == 0){
                        tooltip.add(Text.literal("Type: Normal").formatted(Formatting.GRAY));
                    }else if(type == 1){
                        tooltip.add(Text.literal("Type: Companion").formatted(Formatting.GRAY));
                    }else if(type == 2){
                        tooltip.add(Text.literal("Type: Empty").formatted(Formatting.GRAY));
                    }else {
                        tooltip.add(Text.literal("Type: Normal").formatted(Formatting.GRAY));
                    }
                }else {
                    tooltip.add(Text.literal("Type: Normal").formatted(Formatting.GRAY));
                }
            }
        });

        key = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key." + modid + ".pickup",
                GLFW.GLFW_KEY_G,
                "category." + modid
        ));

        key2 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key." + modid + ".resetPortals",
                GLFW.GLFW_KEY_R,
                "category." + modid
        ));

        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
            if(client.currentScreen == null && player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof PortalGunItem){
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });

        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if(client.currentScreen == null && player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof PortalGunItem){
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            while (key.wasPressed()){
                onKeyPressed();
            }
            while (key2.wasPressed()){
                if(player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof PortalGunItem){
                    PortalGunItem.removePortals(player);
                }
            }
            tpChecker(client, player);
            clickChecker(client, player);
        });

        if(MinecraftClient.getInstance().currentScreen != null){
            ScreenMouseEvents.allowMouseScroll(MinecraftClient.getInstance().currentScreen).register((screen, mouseX, mouseY, horizontalAmount, verticalAmount) -> {
                if(MinecraftClient.getInstance().world != null){
                    if (screen instanceof HandledScreen<?> handledScreen) {
                        onScroll(handledScreen, screen, mouseX, mouseY, horizontalAmount, verticalAmount);
                    }
                }
                return false;
            });
        }
    }

    public void tpChecker(MinecraftClient client, ClientPlayerEntity player){
        if(Apertureproject.server != null && player != null){
            ServerPlayerEntity sp = Apertureproject.server.getPlayerManager().getPlayer(player.getUuid());
            if(Apertureproject.portalLoc1.get(sp) != null && Apertureproject.portalLoc2.get(sp) != null){
                BlockPos playerPos = player.getBlockPos();
                Vec3d playerPos2 = playerPos.toCenterPos();
                Vec3d loc1 = Apertureproject.portalLoc1.get(sp).getVec().add(0, 1, 0);;
                Vec3d loc2 = Apertureproject.portalLoc2.get(sp).getVec().add(0, 1, 0);;
                if((int) loc1.x == (int) playerPos2.x && (int) loc1.y == (int) playerPos2.y && (int) loc1.z == (int) playerPos2.z){
                    if(!isInBlock){
                        isInBlock = true;
                        Vec3d newPos = Apertureproject.portalLoc2.get(sp).getVec().add(0.5, 1, 0.5);
                        sp.teleport(Apertureproject.world, newPos.x, newPos.y, newPos.z, sp.getYaw(), sp.getPitch());
                    }
                }
                else if((int)loc2.x == (int) playerPos2.x && (int) loc2.y == (int) playerPos2.y && (int) loc2.z == (int) playerPos2.z){
                    if(!isInBlock){
                        isInBlock = true;
                        Vec3d newPos = Apertureproject.portalLoc1.get(sp).getVec().add(0.5, 1, 0.5);
                        sp.teleport(Apertureproject.world, newPos.x, newPos.y, newPos.z, sp.getYaw(), sp.getPitch());
                    }
                }else {
                    if(isInBlock) {
                        isInBlock = false;
                    }
                }
            }
        }
    }

    private boolean isLeftClickPressed() {
        return GLFW.glfwGetMouseButton(MinecraftClient.getInstance().getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS;
    }

    private boolean isRightClickPressed() {
        return GLFW.glfwGetMouseButton(MinecraftClient.getInstance().getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS;
    }

    public void clickChecker(MinecraftClient client, ClientPlayerEntity player){
        if(client.player != null){
            if(client.currentScreen != null){
                return;
            }
            if(isLeftClickPressed() && !isLeftClickPressed){
                isLeftClickPressed = true;
                if(player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof PortalGunItem){
                    HitResult hitResult = client.player.raycast(200, 0, false);
                    PortalGunItem.left(client.player, hitResult);
                }
            }
            if(!isLeftClickPressed() && isLeftClickPressed){
                isLeftClickPressed = false;
            }

            if(isRightClickPressed() && !isRightClickPressed){
                isRightClickPressed = true;
                if(player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof PortalGunItem){
                    HitResult hitResult = client.player.raycast(1000, 0, false);
                    PortalGunItem.right(client.player, hitResult);
                }
            }
            if(!isRightClickPressed() && isRightClickPressed){
                isRightClickPressed = false;
            }
        }
    }

    public  MinecraftClient client = MinecraftClient.getInstance();

    private void onKeyPressed() {
        ClientPlayerEntity player = client.player;
        if(isHolding){
            isHolding = false;
            ClientPlayNetworking.send(Apertureproject.HOLD_PACKET, PacketByteBufs.create());
        }
        else if(player != null){
            isHolding = true;
            Entity entity = raycastEntity();
            if(entity != null){
                if(entity instanceof Cube || entity instanceof Cube2){
                    int id = entity.getId();
                    PacketByteBuf buf = PacketByteBufs.create();
                    buf.writeInt(id);
                    ClientPlayNetworking.send(Apertureproject.G_PACKET, buf);
                }
            }
        }
    }

    public static Entity raycastEntity() {
        MinecraftClient client = MinecraftClient.getInstance();
        HitResult hitResult = client.crosshairTarget;
        if (hitResult instanceof EntityHitResult) {
            return ((EntityHitResult) hitResult).getEntity();
        }
        return null;
    }

    public static void onScroll(HandledScreen<?> handledScreen, Screen screen, double mouseX, double mouseY, double horizontalAmount, double verticalAmount){
        if(verticalAmount != 0){
            Slot slot = getSlotAtPosition(handledScreen, (int) mouseX, (int) mouseY);
            if(slot != null){
                ItemStack stack = slot.getStack();
                if(stack != null){
                    if(stack.getItem() instanceof ButtonItem){
                    }
                }
            }
        }
    }

    private static Slot getSlotAtPosition(HandledScreen<?> screen, int mouseX, int mouseY) {
        for (Slot slot : screen.getScreenHandler().slots) {
            if (isMouseOverSlot(slot, screen, mouseX, mouseY)) {
                return slot;
            }
        }
        return null;
    }

    private static boolean isMouseOverSlot(Slot slot, HandledScreen<?> screen, int mouseX, int mouseY) {
        int slotX = screen.width + slot.x;
        int slotY = screen.height + slot.y;
        return mouseX >= slotX && mouseX < slotX + 16 && mouseY >= slotY && mouseY < slotY + 16;
    }
}
