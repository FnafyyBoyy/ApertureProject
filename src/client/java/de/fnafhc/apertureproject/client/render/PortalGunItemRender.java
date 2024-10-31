package de.fnafhc.apertureproject.client.render;

import de.fnafhc.apertureproject.init.ItemInit;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class PortalGunItemRender extends PlayerEntityRenderer {
    public PortalGunItemRender(EntityRendererFactory.Context ctx) {
        super(ctx, false);
    }

    @Override
    public void render(AbstractClientPlayerEntity player, float yaw, float pitch, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        if (player.getMainHandStack().getItem() == ItemInit.PORTAL_GUN) {
            this.getModel().leftArmPose = BipedEntityModel.ArmPose.BOW_AND_ARROW;
            this.getModel().rightArmPose = BipedEntityModel.ArmPose.BOW_AND_ARROW;
        }
        super.render(player, yaw, pitch, matrices, vertexConsumers, light);
    }
}
