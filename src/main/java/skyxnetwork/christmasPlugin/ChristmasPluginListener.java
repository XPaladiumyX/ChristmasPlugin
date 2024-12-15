package skyxnetwork.christmasPlugin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ChristmasPluginListener implements Listener {

    private final ChristmasPlugin plugin;

    public ChristmasPluginListener(ChristmasPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerUseSnowballLauncher(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        // Vérifier si l'item est un Snowball Launcher
        if (item.getType() == Material.BLAZE_ROD && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();

            if (meta != null && meta.hasDisplayName() && meta.getDisplayName().contains("Snowball Launcher")) {
                // Récupérer les utilisations restantes
                int uses = meta.getCustomModelData();

                if (uses > 1) {
                    // Lancer une boule de neige
                    Snowball snowball = player.launchProjectile(Snowball.class);
                    snowball.setShooter(player);
                    player.sendMessage(ChatColor.AQUA + "Snowball launched! " + ChatColor.YELLOW + (uses - 1) + " uses remaining!");

                    // Réduire les utilisations restantes
                    meta.setCustomModelData(uses - 1);
                    meta.setDisplayName(meta.getDisplayName().replace("(" + uses + " uses)", "(" + (uses - 1) + " uses)"));
                    item.setItemMeta(meta);
                } else {
                    // Casser l'item
                    player.getInventory().setItemInMainHand(null);
                    player.sendMessage(ChatColor.RED + "Your Snowball Launcher has broken!");
                }

                event.setCancelled(true); // Empêche les interactions normales
            }
        }
    }
}