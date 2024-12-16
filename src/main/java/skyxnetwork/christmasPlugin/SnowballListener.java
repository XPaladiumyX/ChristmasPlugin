package skyxnetwork.christmasPlugin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.UUID;

public class SnowballListener implements Listener {

    private final ChristmasPlugin plugin;

    public SnowballListener(ChristmasPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSnowballHit(ProjectileHitEvent event) {
        // Vérifie si le projectile est une boule de neige
        if (!(event.getEntity().getShooter() instanceof Player shooter)) {
            return;
        }

        // Vérifie si le projectile est une boule de neige
        if (!(event.getEntity() instanceof org.bukkit.entity.Snowball)) {
            return; // Ignore si ce n'est pas une boule de neige
        }

        // Vérifie si le joueur est muté
        UUID shooterUUID = shooter.getUniqueId();
        if (plugin.getMutedPlayers().contains(shooterUUID)) {
            return; // Ne pas afficher le message si le joueur a désactivé les messages
        }

        // Envoie un message au joueur touché (si applicable)
        Entity hitEntity = event.getHitEntity();
        if (hitEntity instanceof Player hitPlayer) {
            shooter.sendMessage(ChatColor.GREEN + "You hit " + ChatColor.RED + hitPlayer.getName() + ChatColor.GREEN + " with a snowball!");
        }
    }
}