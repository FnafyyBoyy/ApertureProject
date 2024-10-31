package de.fnafhc.apertureproject.utils;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import qouteall.imm_ptl.core.portal.Portal;
import qouteall.q_misc_util.my_util.DQuaternion;

import javax.swing.text.html.parser.Entity;

public class RandomUtils {

    public static Portal isPortalAtPosition(World world, BlockPos pos) {
        for (Portal portal : world.getEntitiesByClass(Portal.class,
                new Box(pos.toCenterPos().add(-0.6, -0.6, -0.6), pos.toCenterPos().add(0.6, 0.6,0.6)), p -> true)) {
            return portal;
        }
        return null;
    }

    public static Direction getPortalDirection(Portal portal) {
        float yaw = portal.getYaw();

        if (yaw >= -45 && yaw < 45) {
            return Direction.SOUTH;
        } else if (yaw >= 45 && yaw < 135) {
            return Direction.WEST;
        } else if (yaw >= 135 || yaw < -135) {
            return Direction.NORTH;
        } else {
            return Direction.EAST;
        }
    }

    public static Direction getDirectionFromQuaternion(DQuaternion quaternion) {
        Vec3d forward = new Vec3d(0, 0, -1);

        Vec3d rotatedVec = quaternion.rotate(forward);

        return Direction.getFacing(rotatedVec.x, rotatedVec.y, rotatedVec.z);
    }
}
