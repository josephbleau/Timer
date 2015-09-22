package com.josephbleau.bukkit.timer.actions;

import com.josephbleau.bukkit.timer.TimerManager;
import com.josephbleau.bukkit.timer.exception.TimerNotFoundException;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import java.util.logging.Logger;

/**
 * Handle /timer show [player name] [timer name].
 *
 * Send a message to the given player with the status of the named timer.
 */
public class ShowHandler extends ActionHandler{
    private Server server;

    private final String permissionsName = "show";
    private final String configColorStringName = "show_color";
    private String colorString = "�d";

    public ShowHandler(Logger logger, Configuration configuration, TimerManager timerManager, Server server) {
        super(logger, configuration, timerManager);
        this.server = server;

        if (!configuration.getString(configColorStringName).isEmpty()) {
            this.colorString = configuration.getString(configColorStringName);
        }
    }

    @SuppressWarnings("deprecation") // Server#getPlayer is the only way to get a player by name.
    @Override
    public boolean handle(CommandSender commandSender, String[] args) {
        if(!commandSender.hasPermission(getFullPermissionsName())) {
            return false;
        }

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
            player.sendMessage(this.colorString + "This ability is still on cooldown! Time until available: " + prettyTimeString);
        } catch (TimerNotFoundException e) {
            commandSender.sendMessage("Timer by the name of '" + timerName + "' could not be found.");
            return false;
        }

        return true;
    }

    @Override
    protected String getActionPermissionsName() {
        return permissionsName;
    }
}
