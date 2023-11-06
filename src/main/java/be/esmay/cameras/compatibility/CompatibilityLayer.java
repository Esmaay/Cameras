package be.esmay.cameras.compatibility;

import be.esmay.cameras.api.objects.CameraRayTraceResult;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public interface CompatibilityLayer {

    CameraRayTraceResult performRayTrace(LivingEntity shooter, double range);

    String getRayTraceResult(Player player, int range);

}
