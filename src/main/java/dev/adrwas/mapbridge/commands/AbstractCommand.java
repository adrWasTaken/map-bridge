package dev.adrwas.mapbridge.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class AbstractCommand {
	
	public final String command;
	public final boolean playerOnly;
	
	public static final HashMap<String, AbstractCommand> registeredCommands = new HashMap<String, AbstractCommand>();
	
	public AbstractCommand(String command, boolean playerOnly)
	{
		this.command = command;
		this.playerOnly = playerOnly;
	}
	
	public void register()
	{
		registeredCommands.put(command.toLowerCase(), this);
	}
	
	public abstract void onCommand(CommandSender sender, Command command, String label, String[] args);
	
	public abstract List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args);
	
	public static void handleCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if(registeredCommands.containsKey(command.getName().toLowerCase())) {
			AbstractCommand commandInstance = registeredCommands.get(command.getName().toLowerCase());
			
			if(commandInstance.playerOnly && !(sender instanceof Player))
				sender.sendMessage("§cThis is a player-only command!");
			
			else
				commandInstance.onCommand(sender, command, label, args);
		}
	}
	
	public static List<String> handleTabComplete(CommandSender sender, Command command, String label, String[] args)
	{
		if(registeredCommands.containsKey(command.getName().toLowerCase())) {
			AbstractCommand commandInstance = registeredCommands.get(command.getName().toLowerCase());
			
			if(commandInstance.playerOnly && !(sender instanceof Player))
				return new ArrayList<String>();
			
			else
			{
				List<String> options = commandInstance.onTabComplete(sender, command, label, args);
				
				if(options == null || args.length <= 0)
					return new ArrayList<String>();
				
				else
				{
					String lastArgument = args[args.length - 1];
					
					return
					options
					.stream()
					.filter((option) -> {
						return option.startsWith(lastArgument);
					})
					.collect(Collectors.toCollection(ArrayList::new));
				}
			}
		} else
			return new ArrayList<String>();
	}
}
