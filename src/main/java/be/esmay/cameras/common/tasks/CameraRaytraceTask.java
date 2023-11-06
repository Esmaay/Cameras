package be.esmay.cameras.common.tasks;

import be.esmay.cameras.CamerasPlugin;
import be.esmay.cameras.api.objects.Camera;
import lombok.RequiredArgsConstructor;
import org.bukkit.scheduler.BukkitRunnable;

@RequiredArgsConstructor
public final class CameraRaytraceTask extends BukkitRunnable {

    private final CamerasPlugin plugin;

    @Override
    public void run() {
        this.plugin.getCameras().forEach(Camera::check);
    }

}
