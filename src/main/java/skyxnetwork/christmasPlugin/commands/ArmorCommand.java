package skyxnetwork.christmasPlugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import skyxnetwork.christmasPlugin.ChristmasPlugin;

import java.util.Arrays;

public class ArmorCommand implements CommandExecutor {

    private final ChristmasPlugin plugin;

    public ArmorCommand(ChristmasPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "This command is for players only!");
            return true;
        }

        if (!player.hasPermission("skyxnetwork.christmas.armor")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            return true;
        }

        // Créer le set d'armure
        player.getInventory().addItem(createArmorPiece(Material.LEATHER_HELMET, "&4&lChristmas &f&lHelmet"));
        player.getInventory().addItem(createArmorPiece(Material.LEATHER_CHESTPLATE, "&4&lChristmas &f&lChestplate"));
        player.getInventory().addItem(createArmorPiece(Material.LEATHER_LEGGINGS, "&4&lChristmas &f&lLeggings"));
        player.getInventory().addItem(createArmorPiece(Material.LEATHER_BOOTS, "&4&lChristmas &f&lBoots"));

        player.sendMessage(ChatColor.GREEN + "You have received the full Christmas Armor set!");
        return true;
    }

    private ItemStack createArmorPiece(Material material, String displayName) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
            meta.setLore(Arrays.asList(ChatColor.DARK_PURPLE + "Part of the Christmas Set"));
            PersistentDataContainer data = meta.getPersistentDataContainer();
            data.set(new NamespacedKey(plugin, "christmas_armor"), PersistentDataType.STRING, "true");
            item.setItemMeta(meta);
        }

        return item;
    }
}
