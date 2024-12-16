package skyxnetwork.christmasPlugin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
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
        if (!(event.getEntity() instanceof Snowball snowball)) {
            return;
        }

        // Vérifie si le lanceur est un joueur
        if (!(snowball.getShooter() instanceof Player shooter)) {
            return;
        }

        // Vérifie si le lanceur a désactivé les messages
        UUID shooterUUID = shooter.getUniqueId();
        if (plugin.getMutedPlayers().contains(shooterUUID)) {
            return; // Ignore les messages si le joueur a désactivé les notifications
        }

        // Récupère l'entité touchée
        Entity hitEntity = event.getHitEntity();
        if (hitEntity instanceof Player hitPlayer) {
            // Envoie le message au lanceur
            shooter.sendMessage(ChatColor.GREEN + "You hit " + ChatColor.RED + hitPlayer.getName() + ChatColor.GREEN + " with a snowball!");

            // Envoie le message au joueur touché
            hitPlayer.sendMessage(ChatColor.RED + "You have been hit by a snowball!");
        }
    }
}