package dev.adrwas.mapbridge.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import dev.adrwas.mapbridge.MapBridge;
import dev.adrwas.mapbridge.projects.Project;

public class CreateProjectCommand extends AbstractCommand {

	public CreateProjectCommand() {
		super("createproject", true);
	}

	@Override
	public void onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		
		if(args.length < 1) {
			sender.sendMessage("§cSyntax: /createproject <path>");
			return;
		}
		
		String path = args[0];
		
		File dir = new File(MapBridge.getInstance().getDataFolder() + File.separator + "directories" + File.separator + path);
		
		if(dir.exists()) {
			sender.sendMessage("§cThis already exists");
			return;
		}
		
		dir.mkdir();
		
		String id = Integer.toHexString(MapBridge.getInstance().random.nextInt());
		
		Project project = new Project(path, id);
		
		try {
			MapBridge.projectManager.addProject(project);
			player.sendMessage("§aThis project was successfully created!");
		} catch (IOException e) {
			player.sendMessage("§cSorry, there was an error creating this project.");
			e.printStackTrace();
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		return Arrays.asList("name", "epic");
	}

}
