package be.esmay.cameras;

import be.esmay.cameras.api.objects.Camera;
import be.esmay.cameras.compatibility.CompatibilityLayer;
import be.esmay.cameras.compatibility.CompatibilityManager;
import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class CamerasPlugin extends JavaPlugin {

    @Getter private static CamerasPlugin instance;
    @Getter private final PaperCommandManager commandManager;

    @Getter private CompatibilityLayer compatibilityLayer;

    private final Set<Camera> cameras = new HashSet<>();

    public CamerasPlugin() {
        instance = this;

        this.commandManager = new PaperCommandManager(this);
    }

    @Override
    public void onEnable() {
        CompatibilityManager compatibilityManager = new CompatibilityManager();
        this.compatibilityLayer = compatibilityManager.getCompatibilityLayer();
    }

    public Collection<Camera> getCameras() {
        return Collections.unmodifiableCollection(this.cameras);
    }
}
