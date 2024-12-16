package skyxnetwork.christmasPlugin;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class SnowballDamageListener implements Listener {

    @EventHandler
    public void onSnowballHit(EntityDamageByEntityEvent event) {
        // Vérifier si l'entité endommagée est un joueur
        Entity damaged = event.getEntity();
        if (damaged instanceof Player player) {

            // Vérifier si l'attaque provient d'une boule de neige
            if (event.getDamager() instanceof Projectile projectile && projectile.getShooter() instanceof Player) {
                // Vérifier que le projectile est bien une boule de neige
                if (projectile.getName().equalsIgnoreCase("snowball")) {
                    // Appliquer 0.1 point de dégâts
                    event.setDamage(0.1);
                }
            }
        }
    }
}
