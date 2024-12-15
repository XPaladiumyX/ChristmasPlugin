package skyxnetwork.christmasPlugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class snowlauncher implements CommandExecutor {

    private final int MAX_USES = 1000;

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

        // Create the Snowball Launcher item
        ItemStack snowballLauncher = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = snowballLauncher.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "Snowball " + ChatColor.DARK_RED + "" + ChatColor.BOLD + "Launcher (" + MAX_USES + " uses)");
            meta.setLore(Arrays.asList(
                    ChatColor.DARK_PURPLE + "Left click to shoot!"
            ));
            meta.setCustomModelData(MAX_USES); // Set custom uses data
        }

        snowballLauncher.setItemMeta(meta);

        // Give the item to the player
        player.getInventory().addItem(snowballLauncher);
        player.sendMessage(ChatColor.GREEN + "You received a Snowball Launcher!");
        return true;
    }
}