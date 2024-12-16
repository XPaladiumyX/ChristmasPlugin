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

public class ChristmasCommand implements CommandExecutor {

    private final ChristmasPlugin plugin;
    private final int MAX_USES = 1000;

    public ChristmasCommand(ChristmasPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Vérifier que le sender est un joueur
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "This command is for players only!");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "Usage: /christmas <armor|snowlauncher|toggle>");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "armor":
                giveChristmasArmor(player);
                break;

            case "snowlauncher":
                giveSnowballLauncher(player);
                break;

            case "toggle":
                toggleSnowballMessage(player);
                break;

            default:
                player.sendMessage(ChatColor.RED + "Unknown subcommand. Use: /christmas <armor|snowlauncher|toggle>");
                break;
        }

        return true;
    }

    private void giveChristmasArmor(Player player) {
        if (!player.hasPermission("skyxnetwork.christmas.armor")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            return;
        }

        player.getInventory().addItem(createArmorPiece(Material.LEATHER_HELMET, "&4&lChristmas &f&lHelmet"));
        player.getInventory().addItem(createArmorPiece(Material.LEATHER_CHESTPLATE, "&4&lChristmas &f&lChestplate"));
        player.getInventory().addItem(createArmorPiece(Material.LEATHER_LEGGINGS, "&4&lChristmas &f&lLeggings"));
        player.getInventory().addItem(createArmorPiece(Material.LEATHER_BOOTS, "&4&lChristmas &f&lBoots"));

        player.sendMessage(ChatColor.GREEN + "You have received the full Christmas Armor set!");
    }

    private ItemStack createArmorPiece(Material material, String displayName) {
        ItemStack item = new ItemStack(material);

        if (material == Material.LEATHER_HELMET ||
                material == Material.LEATHER_CHESTPLATE ||
                material == Material.LEATHER_LEGGINGS ||
                material == Material.LEATHER_BOOTS) {

            ItemMeta meta = item.getItemMeta();
            if (meta instanceof org.bukkit.inventory.meta.LeatherArmorMeta leatherMeta) {
                // Définir la couleur rouge
                leatherMeta.setColor(org.bukkit.Color.RED);
                leatherMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
                leatherMeta.setLore(Arrays.asList(ChatColor.DARK_PURPLE + "Part of the Christmas Set"));
                PersistentDataContainer data = leatherMeta.getPersistentDataContainer();
                data.set(new NamespacedKey(plugin, "christmas_armor"), PersistentDataType.STRING, "true");
                item.setItemMeta(leatherMeta);
            }
        }
        return item;
    }

    private void giveSnowballLauncher(Player player) {
        if (!player.hasPermission("skyxnetwork.christmas.snowlauncher")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            return;
        }

        ItemStack snowballLauncher = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = snowballLauncher.getItemMeta();

        if (meta != null) {
            String displayName = ChatColor.translateAlternateColorCodes('&', "&f&lSnowball &4&lLauncher");
            meta.setDisplayName(displayName + " (" + MAX_USES + " uses)");
            meta.setLore(Arrays.asList(
                    ChatColor.DARK_PURPLE + "Left click to shoot!"
            ));
            meta.setCustomModelData(MAX_USES); // Nombre d'utilisations max

            PersistentDataContainer data = meta.getPersistentDataContainer();
            data.set(new NamespacedKey(plugin, "snow"), PersistentDataType.STRING, "snow");

            snowballLauncher.setItemMeta(meta);
        }

        player.getInventory().addItem(snowballLauncher);
        player.sendMessage(ChatColor.GREEN + "You have received a Snowball Launcher!");
    }

    private void toggleSnowballMessage(Player player) {
        if (plugin.getMutedPlayers().contains(player.getUniqueId())) {
            plugin.getMutedPlayers().remove(player.getUniqueId());
            player.sendMessage(ChatColor.GREEN + "Snowball hit messages are now " + ChatColor.BOLD + "ON" + ChatColor.GREEN + "!");
        } else {
            plugin.getMutedPlayers().add(player.getUniqueId());
            player.sendMessage(ChatColor.RED + "Snowball hit messages are now " + ChatColor.BOLD + "OFF" + ChatColor.RED + "!");
        }
    }
}