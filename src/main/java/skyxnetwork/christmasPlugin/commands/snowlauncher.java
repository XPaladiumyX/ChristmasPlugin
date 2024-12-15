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

public class snowlauncher implements CommandExecutor {

    private final ChristmasPlugin plugin;
    private final int MAX_USES = 1000;

    public snowlauncher(ChristmasPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "This command is for players only!");
            return true;
        }

        if (args.length < 1 || !args[0].equalsIgnoreCase("snowlauncher")) {
            player.sendMessage(ChatColor.RED + "Usage: /christmas snowlauncher");
            return true;
        }

        // Create the Snowball Launcher
        ItemStack snowballLauncher = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = snowballLauncher.getItemMeta();

        if (meta != null) {
            String displayName = ChatColor.translateAlternateColorCodes('&', "&f&lSnowball &4&lLauncher");
            meta.setDisplayName(displayName + " (" + MAX_USES + " uses)");
            meta.setLore(Arrays.asList(
                    ChatColor.DARK_PURPLE + "Left click to shoot!"
            ));
            meta.setCustomModelData(MAX_USES); // Set max uses

            // Add custom tag
            PersistentDataContainer data = meta.getPersistentDataContainer();
            data.set(new NamespacedKey(plugin, "snow"), PersistentDataType.STRING, "snow");

            snowballLauncher.setItemMeta(meta);
        }

        // Give the item to the player
        player.getInventory().addItem(snowballLauncher);
        player.sendMessage(ChatColor.GREEN + "You received a Snowball Launcher!");
        return true;
    }
}