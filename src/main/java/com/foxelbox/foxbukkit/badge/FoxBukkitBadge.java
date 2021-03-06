package com.foxelbox.foxbukkit.badge;

import com.foxelbox.dependencies.config.Configuration;
import com.foxelbox.foxbukkit.badge.commands.BAddCommand;
import com.foxelbox.foxbukkit.badge.commands.BInfoCommand;
import com.foxelbox.foxbukkit.badge.commands.BManageCommand;
import com.foxelbox.foxbukkit.badge.commands.BadgesCommand;
import com.foxelbox.foxbukkit.badge.database.DatabaseBadgeManager;
import com.foxelbox.foxbukkit.badge.database.DatabaseConnectionPool;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class FoxBukkitBadge extends JavaPlugin {
    public Configuration configuration;
    public DatabaseConnectionPool pool;
    public DatabaseBadgeManager databaseBadgeManager;

    public MergingBadgeManager globalBadgeManager;

    /**
     * Badge managers
     */

    public BadgeManager getGlobalBadgeManager() {
        return globalBadgeManager;
    }

    public void addBadgeManager(BadgeManager badgeManager) {
        globalBadgeManager.badgeManagers.add(badgeManager);
    }

    public void removeBadgeManager(BadgeManager badgeManager) {
        globalBadgeManager.badgeManagers.remove(badgeManager);
    }

    /**
     * Messaging
     */

    public void sendMessageTo(CommandSender ply, String message) {
        ply.sendMessage(prefixMessage(message));
    }

    public void broadcastMessage(String message) {
        getServer().broadcastMessage(prefixMessage(message));
    }

    private String prefixMessage(String message) {
        return "\u00a7d[FBB]\u00a7f " + message;
    }

    @Override
    public void onEnable() {
        super.onEnable();

        configuration = new Configuration(getDataFolder());
        pool = new DatabaseConnectionPool(this);
        databaseBadgeManager = new DatabaseBadgeManager(pool);

        getServer().getPluginCommand("badd").setExecutor(new BAddCommand(this));
        getServer().getPluginCommand("binfo").setExecutor(new BInfoCommand(this));
        getServer().getPluginCommand("bmanage").setExecutor(new BManageCommand(this));
        getServer().getPluginCommand("badges").setExecutor(new BadgesCommand(this));

        getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
            public void onBadgeChange(BadgeChangedEvent event) {
                if(event.getOldBadge() == null || event.getOldBadge().getLevel() == 0) {
                    // Badge awarded

                } else if(event.getNewBadge() == null || event.getNewBadge().getLevel() == 0) {
                    // Badge lost

                } else if(event.getNewBadge().getLevel() > event.getOldBadge().getLevel()) {
                    // Badge upgraded

                } else {
                    // Badge downgraded

                }
            }
        }, this);

        globalBadgeManager = new MergingBadgeManager();
        addBadgeManager(databaseBadgeManager);
    }

    @Override
    public void onDisable() {
        super.onDisable();

        globalBadgeManager.badgeManagers.clear();
    }
}
