package com.foxelbox.foxbukkit.badge.commands;

import com.foxelbox.foxbukkit.badge.BadgeDescriptor;
import com.foxelbox.foxbukkit.badge.FoxBukkitBadge;
import com.foxelbox.foxbukkit.badge.database.DatabaseBadge;
import com.google.common.base.Strings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class BInfoCommand implements CommandExecutor {
    private final FoxBukkitBadge plugin;

    public BInfoCommand(FoxBukkitBadge plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(args.length > 0) {
            // Info
            BadgeDescriptor badgeDescriptor = plugin.getGlobalBadgeManager().getBadge(args[0]);
            plugin.sendMessageTo(commandSender, "ID: " + badgeDescriptor.getId());
            plugin.sendMessageTo(commandSender, "Name: " + badgeDescriptor.getName());
            plugin.sendMessageTo(commandSender, "Description: " + badgeDescriptor.getDescription());
            plugin.sendMessageTo(commandSender, "Max level: " + badgeDescriptor.getMaxLevel());
            if(badgeDescriptor.getMaxLevel() > 1) {
                plugin.sendMessageTo(commandSender, "Levels: " + String.join(", ", badgeDescriptor.getLevelNames()));
            }
        } else {
            // List
            ArrayList<String> badges = new ArrayList<>();
            for(BadgeDescriptor badgeDescriptor : plugin.getGlobalBadgeManager().getBadges()) {
                badges.add(((badgeDescriptor instanceof DatabaseBadge) ? "\u00a77@\u00a7f" : "") + badgeDescriptor.getId());
            }
            plugin.sendMessageTo(commandSender, String.join(", ", badges));
        }
        return true;
    }
}
