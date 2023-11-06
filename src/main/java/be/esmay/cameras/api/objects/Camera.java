package be.esmay.cameras.api.objects;

import be.esmay.cameras.CamerasPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class Camera {

    private final CamerasPlugin plugin;

    private ArmorStand armorStand;

    private int id;
    private String name;
    private Location location;

    private UUID owner;
    private final Set<UUID> members = new HashSet<>();

    public Camera(CamerasPlugin plugin, Location location) {
        this.plugin = plugin;
        this.location = location;
    }

    public void spawn() {
        if (this.armorStand != null) {
            this.armorStand.remove();
            this.armorStand = null;
        }

        World world = this.location.getWorld();
        if (world == null) return;

        this.armorStand = (ArmorStand) world.spawnEntity(this.location, EntityType.ARMOR_STAND);
        this.armorStand.setGravity(false);
        this.armorStand.setVisible(false);
        this.armorStand.setInvulnerable(true);
        this.armorStand.setSmall(true);
        this.armorStand.setMarker(true);
        this.armorStand.setMetadata("camera", new FixedMetadataValue(this.plugin, this.id));
    }

    public void check() {
        CameraRayTraceResult result = this.plugin.getCompatibilityLayer().performRayTrace(this.armorStand, 100);
        if (result.getHitEntity() == null) return;

        if (!(result.getHitEntity() instanceof Player)) return;
        Player player = (Player) result.getHitEntity();

        if (player.getUniqueId().equals(this.owner)) return;
        if (this.members.contains(player.getUniqueId())) return; // Should probably add settings :

        Player owner = Bukkit.getPlayer(this.owner);
        if (owner == null || !owner.isOnline()) return;
    }

}
