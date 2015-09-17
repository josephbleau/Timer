package com.josephbleau.bukkit.timer.actions;

import com.josephbleau.bukkit.timer.TimerManager;
import com.josephbleau.bukkit.timer.exception.TimerNotFoundException;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Logger;

/**
 * Handle /timer show [player name] [timer name].
 *
 * Send a message to the given player with the status of the named timer.
 */
public class ShowHandler extends ActionHandler{
    private Server server;

    public ShowHandler(Logger logger, TimerManager timerManager, Server server) {
        super(logger, timerManager);
        this.server = server;
    }

    @SuppressWarnings("deprecation") // Server#getPlayer is the only way to get a player by name.
    @Override
    public boolean handle(CommandSender commandSender, String[] args) {
        if (args.length != 3) {
            return false;
        }

        String playerName = args[1];
        String timerName = args[2];

        Player player = server.getPlayer(playerName);
        if (player == null) {
            commandSender.sendMessage("That player is not online.");
            return false;
        }

        try {
            String prettyTimeString = getTimerManager().getPrettyTimeLeft(timerName);
            player.sendMessage("§dThis ability is still on cooldown! Time until available: " + prettyTimeString);
        } catch (TimerNotFoundException e) {
            commandSender.sendMessage("Timer by the name of '" + timerName + "' could not be found.");
            return false;
        }

        return true;
    }
}
