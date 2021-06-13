package dev.adrwas.mapbridge.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.adrwas.mapbridge.MapBridge;
import dev.adrwas.mapbridge.projects.Project;

public class OpenProjectCommand extends AbstractCommand {

	public OpenProjectCommand() {
		super("openproject", true);
	}

	@Override
	public void onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		
		if(args.length < 1) {
			player.sendMessage("§cCorrect syntax: /open <project>");
			return;
		}
		
		Project project = MapBridge.projectManager.findProjectFromPath(args[0]);
		
		if(project == null) {
			player.sendMessage("§cProject not found");
			return;
		}
		
		player.sendMessage("§cOpening...");
		project.open(player);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
