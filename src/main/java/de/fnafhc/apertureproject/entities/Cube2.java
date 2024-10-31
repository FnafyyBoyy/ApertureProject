package de.fnafhc.apertureproject.entities;

import de.fnafhc.apertureproject.Apertureproject;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.UUID;

public class Cube2 extends PathAwareEntity {

    private int id = -1;

    private boolean isHeld = false;

    private boolean isOn = false;

    public Cube2(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        id = generateRandomTenDigitInt();
    }

    public static int generateRandomTenDigitInt() {
        Random random = Random.create();
        long randomLong = 1000000000L + (long)(random.nextDouble() * 9000000000L);
        return (int) randomLong;
    }

    public void setHeld(boolean bool){
        isHeld = bool;
    }

    public boolean isOn(){
        return isOn;
    }

    public void setOn(boolean bool){
        isOn = bool;
        NbtCompound nbt = this.writeNbt(new NbtCompound());
        nbt.putBoolean("isOn", bool);
        this.readNbt(nbt);
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (!this.isOnGround()) {
            if(isHeld){
                this.setVelocity(this.getVelocity().x, 0, this.getVelocity().z);
            }else {
                this.setVelocity(this.getVelocity().add(0, -0.00000000000000000001, 0));
            }
        } else {
            this.setVelocity(this.getVelocity().x, 0, this.getVelocity().z);
        }
        this.move(MovementType.SELF, this.getVelocity());
    }

    @Override
    protected void initGoals() {
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putInt("idd", id);
        nbt.putBoolean("isOn", isOn);
        return super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if(nbt.contains("idd")){
            id = nbt.getInt("idd");
        }
        if(nbt.contains("isOn")){
            isOn = nbt.getBoolean("isOn");
        }
    }

    public int getID(){
        return id;
    }

    @Override
    public boolean cannotDespawn() {
        return true;
    }

    @Override
    public boolean isCollidable() {
        return true;
    }

    @Override
    public void takeKnockback(double strength, double x, double z) {
        super.takeKnockback(0, x, z);
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return super.handleFallDamage(0, 0, damageSource);
    }

    public static DefaultAttributeContainer.Builder createNoAIEntityAttributes() {
        return PathAwareEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 0.0)
                .add(EntityAttributes.GENERIC_ARMOR, 0.0);
    }
}
