package de.fnafhc.apertureproject.client.models;

import de.fnafhc.apertureproject.entities.Cube2;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class Cube2Model<T extends Cube2> extends SinglePartEntityModel<T> {
    private final ModelPart main;
    private final ModelPart off;
    private final ModelPart on;
    public Cube2Model(ModelPart root) {
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
    public void setAngles(Cube2 entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        on.visible = entity.isOn();
        if(entity.isOn()){
            System.out.println("Onn!!");
        }
        off.visible = !entity.isOn();
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
