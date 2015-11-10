package com.foxelbox.foxbukkit.badge;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class BadgeChangedEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    protected Badge oldBadge;
    protected Badge newBadge;
    protected boolean cancelled = false;

    public BadgeChangedEvent(Player player, Badge oldBadge, Badge newBadge) {
        super(player);
        this.oldBadge = oldBadge;
        this.newBadge = newBadge;
    }

    public BadgeChangedEvent(Player player, Badge newBadge) {
        this(player, null, newBadge);
    }

    public Badge getNewBadge() {
        return newBadge;
    }

    public Badge getOldBadge() {
        return oldBadge;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }
}
