package de.fnafhc.apertureproject.client.models;

import de.fnafhc.apertureproject.entities.Cube;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class CubeModel<T extends Cube> extends SinglePartEntityModel<T> {
    private final ModelPart main;
    private final ModelPart off;
    private final ModelPart on;
    public CubeModel(ModelPart root) {
        this.main = root.getChild("main");
        this.off = root.getChild("off");
        this.on = root.getChild("on");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create().uv(0, 0).cuboid(-6.5F, -13.5F, -6.5F, 13.0F, 13.0F, 13.0F, new Dilation(0.4F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData off = modelPartData.addChild("off", ModelPartBuilder.create().uv(0, 26).cuboid(-6.0F, -13.0F, -6.0F, 12.0F, 12.0F, 12.0F, new Dilation(0.4F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData on = modelPartData.addChild("on", ModelPartBuilder.create().uv(0, 50).cuboid(-6.0F, -13.0F, -6.0F, 12.0F, 12.0F, 12.0F, new Dilation(0.4F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 79, 79);
    }

    @Override
    public void setAngles(Cube cube, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        Entity entity = cube.getWorld().getEntityById(cube.getId());
        Cube cub = (Cube) entity;

        on.visible = cub.isOn;
        on.hidden = !cub.isOn;

        off.visible = !cub.isOn;
        off.hidden = cub.isOn;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        off.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        on.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart getPart() {
        return null;
    }
}
