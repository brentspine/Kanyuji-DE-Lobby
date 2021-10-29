package de.kanyuji.lobby.commands;

import de.kanyuji.lobby.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DiscordCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(Main.PREFIX + "Dieser Befehl ist nur für InGame");
            return true;
        }
        Player player = (Player) sender;
        BaseComponent[] component =
                new ComponentBuilder(Main.PREFIX).bold(true)
                        .append("Hier ").color(ChatColor.GRAY).event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://kanyuji.de/discord")).bold(true)
                        .append("geht es zum ").color(ChatColor.GRAY)
                        .append("Discord").color(ChatColor.RED).create();

        player.spigot().sendMessage(component);
        return false;
    }

}
