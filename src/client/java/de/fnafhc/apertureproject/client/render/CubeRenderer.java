package de.fnafhc.apertureproject.client.render;

import de.fnafhc.apertureproject.Apertureproject;
import de.fnafhc.apertureproject.client.ModModelLayers;
import de.fnafhc.apertureproject.client.models.CubeModel;
import de.fnafhc.apertureproject.entities.Cube;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class CubeRenderer extends MobEntityRenderer<Cube, CubeModel<Cube>> {
    private static final Identifier TEXTURE = new Identifier(Apertureproject.modid, "textures/block/cube.png");

    public CubeRenderer(EntityRendererFactory.Context context) {
        super(context, new CubeModel(context.getPart(ModModelLayers.CUBE)), 0.2f);
    }

    @Override
    public Identifier getTexture(Cube entity) {
        return TEXTURE;
    }

    @Override
    public void render(Cube mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
