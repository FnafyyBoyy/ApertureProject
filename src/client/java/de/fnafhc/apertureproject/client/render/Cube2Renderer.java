package de.fnafhc.apertureproject.client.render;

import de.fnafhc.apertureproject.Apertureproject;
import de.fnafhc.apertureproject.client.ModModelLayers;
import de.fnafhc.apertureproject.client.models.Cube2Model;
import de.fnafhc.apertureproject.entities.Cube2;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class Cube2Renderer extends MobEntityRenderer<Cube2, Cube2Model<Cube2>> {
    private static final Identifier TEXTURE = new Identifier(Apertureproject.modid, "textures/block/cube2.png");

    public Cube2Renderer(EntityRendererFactory.Context context) {
        super(context, new Cube2Model(context.getPart(ModModelLayers.CUBE2)), 0.2f);
    }

    @Override
    public Identifier getTexture(Cube2 entity) {
        return TEXTURE;
    }

    @Override
    public void render(Cube2 mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
