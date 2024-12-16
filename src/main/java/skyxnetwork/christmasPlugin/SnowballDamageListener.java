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
            if (!(projectile.getName().equalsIgnoreCase("snowball"))) {
                return;
            }

            // Vérifie si le shooter a désactivé les messages
            UUID shooterUUID = shooter.getUniqueId();
            if (plugin.getMutedPlayers().contains(shooterUUID)) {
                return; // N'affiche pas le message si le joueur a désactivé les messages
            }

            // Applique 0.1 point de dégâts et envoie le message
            event.setDamage(0.1);
            hitPlayer.sendMessage(ChatColor.RED + "You have been hit by a snowball!");
        }
    }
}