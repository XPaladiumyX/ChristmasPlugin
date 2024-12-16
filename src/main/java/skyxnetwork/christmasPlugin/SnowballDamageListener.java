package skyxnetwork.christmasPlugin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.UUID;

public class SnowballDamageListener implements Listener {

    private final ChristmasPlugin plugin;

    public SnowballDamageListener(ChristmasPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSnowballHit(EntityDamageByEntityEvent event) {
        // Vérifie si l'entité endommagée est un joueur
        Entity damaged = event.getEntity();
        if (!(damaged instanceof Player hitPlayer)) {
            return;
        }

        // Vérifie si l'attaque provient d'une boule de neige
        if (event.getDamager() instanceof Projectile projectile && projectile.getShooter() instanceof Player shooter) {
            // Vérifie que le projectile est bien une boule de neige
            if (!projectile.getName().equalsIgnoreCase("snowball")) {
                return;
            }

            // Appliquer 0.1 point de dégâts (toujours, indépendamment du toggle)
            event.setDamage(0.1);

            // Vérifie si le joueur tireur a désactivé les messages
            UUID shooterUUID = shooter.getUniqueId();
            if (!plugin.getMutedPlayers().contains(shooterUUID)) {
                // Envoie le message uniquement si les messages ne sont pas désactivés
                hitPlayer.sendMessage(ChatColor.RED + "You have been hit by a snowball!");
            }
        }
    }
}