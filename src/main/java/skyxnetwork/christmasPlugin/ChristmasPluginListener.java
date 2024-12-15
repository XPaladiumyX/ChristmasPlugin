package skyxnetwork.christmasPlugin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ChristmasPluginListener implements Listener {

    private final ChristmasPlugin plugin;
    private final NamespacedKey tagKey;

    public ChristmasPluginListener(ChristmasPlugin plugin) {
        this.plugin = plugin;
        this.tagKey = new NamespacedKey(plugin, "snow"); // Create a key for the custom tag
    }

    @EventHandler
    public void onPlayerUseSnowballLauncher(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        // Vérifier si l'item est un Blaze Rod
        if (item.getType() == Material.BLAZE_ROD && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();

            if (meta != null) {
                PersistentDataContainer data = meta.getPersistentDataContainer();

                // Vérifier si le nom exact et la balise "snow" sont présents
                String expectedName = ChatColor.translateAlternateColorCodes('&', "&f&lSnowball &4&lLauncher");
                if (meta.hasDisplayName() && meta.getDisplayName().equals(expectedName) &&
                        data.has(tagKey, PersistentDataType.STRING) &&
                        "snow".equals(data.get(tagKey, PersistentDataType.STRING))) {

                    // Récupérer les utilisations restantes
                    int uses = meta.getCustomModelData();

                    if (uses > 1) {
                        // Lancer une boule de neige
                        Snowball snowball = player.launchProjectile(Snowball.class);
                        snowball.setShooter(player);
                        player.sendMessage(ChatColor.AQUA + "Snowball launched! " + ChatColor.YELLOW + (uses - 1) + " uses remaining!");

                        // Réduire les utilisations restantes
                        meta.setCustomModelData(uses - 1);
                        meta.setDisplayName(expectedName + " (" + (uses - 1) + " uses)");
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
}