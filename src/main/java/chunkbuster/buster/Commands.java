package chunkbuster.buster;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("chunkbuster")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                if(args.length < 1) {
                    sender.sendMessage("Usage: /chunkbuster get");
                } else {
                    if(args[0].equalsIgnoreCase("get")) {
                        if(sender.hasPermission("chunkbuster.get")) {
                            // TODO: Gives the user a CUSTOM chunk buster

                            player.getInventory().addItem(Buster.plugin.chunkbuster);
                        }
                    }
                }
            }
        }
        return true;
    }

}
