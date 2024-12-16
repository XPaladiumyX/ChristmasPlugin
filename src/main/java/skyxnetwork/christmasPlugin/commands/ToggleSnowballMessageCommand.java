package skyxnetwork.christmasPlugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import skyxnetwork.christmasPlugin.ChristmasPlugin;

public class ToggleSnowballMessageCommand implements CommandExecutor {

    private final ChristmasPlugin plugin;

    public ToggleSnowballMessageCommand(ChristmasPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Vérifier si le command sender est un joueur
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "This command is for players only!");
            return true;
        }

        // Vérifier si le joueur est déjà dans la liste des joueurs avec le message désactivé
        if (plugin.getMutedPlayers().contains(player.getUniqueId())) {
            plugin.getMutedPlayers().remove(player.getUniqueId());
            player.sendMessage(ChatColor.GREEN + "Snowball hit messages are now " + ChatColor.BOLD + "ON" + ChatColor.GREEN + "!");
        } else {
            plugin.getMutedPlayers().add(player.getUniqueId());
            player.sendMessage(ChatColor.RED + "Snowball hit messages are now " + ChatColor.BOLD + "OFF" + ChatColor.RED + "!");
        }

        return true;
    }
}