package skyxnetwork.christmasPlugin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ChristmasPluginListener implements Listener {

    private final ChristmasPlugin plugin;
    private final NamespacedKey tagKey;

    public ChristmasPluginListener(ChristmasPlugin plugin) {
        this.plugin = plugin;
        this.tagKey = new NamespacedKey(plugin, "snow"); // Custom tag key
    }

    @EventHandler
    public void onPlayerUseSnowballLauncher(PlayerInteractEvent event) {
        // Verify correct hand usage
        if (event.getHand() != EquipmentSlot.HAND) return;

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        // Check if the item is a Blaze Rod with metadata
        if (item.getType() == Material.BLAZE_ROD && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();

            if (meta != null) {
                PersistentDataContainer data = meta.getPersistentDataContainer();
                String expectedName = ChatColor.translateAlternateColorCodes('&', "&f&lSnowball &4&lLauncher");

                // Verify item name and custom tag
                if (meta.hasDisplayName() && meta.getDisplayName().startsWith(expectedName) &&
                        data.has(tagKey, PersistentDataType.STRING) &&
                        "snow".equals(data.get(tagKey, PersistentDataType.STRING))) {

                    // Get remaining uses
                    int uses = meta.getCustomModelData();

                    if (uses > 1) {
                        // Launch snowball
                        Snowball snowball = player.launchProjectile(Snowball.class);
                        snowball.setShooter(player);

                        // Only show the message every 100 uses, or at 10 remaining
                        if (uses % 100 == 0 || uses == 10) {
                            player.sendMessage(ChatColor.AQUA + "Snowball launched! " + ChatColor.YELLOW + (uses - 1) + " uses remaining!");
                        }

                        // Update remaining uses
                        meta.setCustomModelData(uses - 1);
                        meta.setDisplayName(expectedName + " (" + (uses - 1) + " uses)");
                        item.setItemMeta(meta);
                    } else {
                        // Break the item when out of uses
                        player.getInventory().setItemInMainHand(null);
                        player.sendMessage(ChatColor.RED + "Your Snowball Launcher has broken!");
                    }

                    event.setCancelled(true); // Prevent default interaction
                }
            }
        }
    }
}
