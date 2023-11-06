package be.esmay.cameras.compatibility.versions;

import be.esmay.cameras.api.objects.CameraRayTraceResult;
import be.esmay.cameras.compatibility.CompatibilityLayer;
import org.bukkit.FluidCollisionMode;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;

public final class v1_20_R2 implements CompatibilityLayer {

    @Override
    public CameraRayTraceResult performRayTrace(LivingEntity player, double range) {
        RayTraceResult result = player.getWorld()
                .rayTrace(player.getEyeLocation(), player.getLocation().getDirection(), range, FluidCollisionMode.NEVER, true, 0.2, entity ->
                        entity != player);

        if (result == null) {
            return new CameraRayTraceResult(null);
        }

        if (result.getHitEntity() == null) {
            return new CameraRayTraceResult(null);
        }

        Entity entity = result.getHitEntity();
        if (!(entity instanceof LivingEntity) || entity instanceof ArmorStand) {
            return new CameraRayTraceResult(null);
        }

        LivingEntity livingEntity = (LivingEntity) entity;
        return new CameraRayTraceResult(livingEntity);
    }

    @Override
    public String getRayTraceResult(Player player, int range) {
        RayTraceResult result = player.getWorld()
                .rayTrace(player.getEyeLocation(), player.getLocation().getDirection(), range, FluidCollisionMode.NEVER, true, 0.2, null);
        return result != null ? result.toString() : "No result found";
    }

}
