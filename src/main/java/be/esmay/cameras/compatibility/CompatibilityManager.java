package be.esmay.cameras.compatibility;

import be.esmay.cameras.CamerasPlugin;
import be.esmay.cameras.api.objects.CameraRayTraceResult;
import org.bukkit.Bukkit;
import org.bukkit.FluidCollisionMode;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;

public final class CompatibilityManager {

    private static final String BUKKIT_VERSION = Bukkit.getServer().getClass().getPackage().getName();
    private static final String VERSION = BUKKIT_VERSION.substring(BUKKIT_VERSION.lastIndexOf('.') + 1);

    public CompatibilityLayer getCompatibilityLayer() {
        try {
            Class<?> nmsClass = Class.forName("be.esmay.cameras.compatibility.versions." + VERSION);
            CamerasPlugin.getInstance().getLogger().info("Using compatibility layer for version " + VERSION);
            return (CompatibilityLayer) nmsClass.getConstructors()[0].newInstance();
        } catch (Exception ignored) {
            CamerasPlugin.getInstance().getLogger().warning("Your server version (" + VERSION + ") is not supported by Cameras. " +
                    "Loading a fallback compatibility layer but this may cause issues so you are advised to update your server to the latest version.");
            return new CompatibilityLayer() {
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
            };
        }
    }
}
