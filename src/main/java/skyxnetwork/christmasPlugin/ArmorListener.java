package skyxnetwork.christmasPlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.UUID;

public class ArmorListener implements Listener {

    private final ChristmasPlugin plugin;
    private final NamespacedKey armorKey;
    private final HashMap<UUID, Long> cooldowns = new HashMap<>();

    public ArmorListener(ChristmasPlugin plugin) {
        this.plugin = plugin;
        this.armorKey = new NamespacedKey(plugin, "christmas_armor");
    }

    @EventHandler
    public void onPlayerSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();

        // Vérifier si le joueur porte le full set
        if (isWearingFullSet(player)) {
            UUID playerId = player.getUniqueId();

            // Vérifier le cooldown
            long currentTime = System.currentTimeMillis();
            if (cooldowns.containsKey(playerId) && currentTime - cooldowns.get(playerId) < 180000) { // 3 minutes
                long remainingTime = (180000 - (currentTime - cooldowns.get(playerId))) / 1000;
                player.sendMessage(ChatColor.RED + "You must wait " + remainingTime + " seconds before spawning another Snowman!");
                return;
            }

            // Spawn le Snow Golem
            Snowman snowman = player.getWorld().spawn(player.getLocation(), Snowman.class);
            snowman.setCustomName(ChatColor.translateAlternateColorCodes('&', "&4&lChristmas &f&lSnowman"));
            snowman.setCustomNameVisible(true);
            snowman.setInvulnerable(false);

            // Définir une durée de vie de 30 secondes
            Bukkit.getScheduler().runTaskLater(plugin, snowman::remove, 600L); // 600 ticks = 30 secondes

            // Enregistrer le cooldown
            cooldowns.put(playerId, currentTime);
            player.sendMessage(ChatColor.GREEN + "You have spawned a Christmas Snowman! It will disappear in 30 seconds.");
        }
    }

    private boolean isWearingFullSet(Player player) {
        ItemStack helmet = player.getInventory().getHelmet();
        ItemStack chestplate = player.getInventory().getChestplate();
        ItemStack leggings = player.getInventory().getLeggings();
        ItemStack boots = player.getInventory().getBoots();

        return isChristmasArmor(helmet) && isChristmasArmor(chestplate) &&
                isChristmasArmor(leggings) && isChristmasArmor(boots);
    }

    private boolean isChristmasArmor(ItemStack item) {
        if (item == null || item.getType() == Material.AIR) return false;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return false;

        PersistentDataContainer data = meta.getPersistentDataContainer();
        return data.has(armorKey, PersistentDataType.STRING) &&
                "true".equals(data.get(armorKey, PersistentDataType.STRING));
    }
}
