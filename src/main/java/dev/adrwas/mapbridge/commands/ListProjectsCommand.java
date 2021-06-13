package dev.adrwas.mapbridge.commands;

import java.io.File;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import dev.adrwas.mapbridge.MapBridge;
import dev.adrwas.mapbridge.projects.Project;

public class ListProjectsCommand extends AbstractCommand {

	public ListProjectsCommand() {
		super("projects", true);
	}

	@Override
	public void onCommand(CommandSender sender, Command command, String label, String[] args) {
		sender.sendMessage("§f§lYou have the following projects:");
		for(Project project : MapBridge.projectManager.projects) {
			String path = project.getPath();
			File file = new File(path);
			sender.sendMessage("§f- §e" + file.getName() + " §fin §7" + file.getParent());
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		// TODO Auto-generated method stub
		return null;
	}

}
