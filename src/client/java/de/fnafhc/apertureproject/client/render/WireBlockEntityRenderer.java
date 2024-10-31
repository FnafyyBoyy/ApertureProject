package de.fnafhc.apertureproject.client.render;

import de.fnafhc.apertureproject.blocks.WireBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Quaternionf;

public class WireBlockEntityRenderer implements BlockEntityRenderer<WireBlockEntity> {

    public WireBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
    }

    @Override
    public void render(WireBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();

        matrices.translate(0.5, 0.5, 0.5);
        Quaternionf rotation = new Quaternionf().rotateY((float) Math.toRadians(45));
        matrices.multiply(rotation);
        matrices.pop();
    }

    @Override
    public boolean rendersOutsideBoundingBox(WireBlockEntity blockEntity) {
        return false;
    }
}

