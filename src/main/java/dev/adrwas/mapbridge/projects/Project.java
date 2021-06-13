package dev.adrwas.mapbridge.projects;

import org.bukkit.World;
import org.bukkit.entity.Player;

import dev.adrwas.mapbridge.fs.ProjectManager;

public class Project {
	
	private String path;
	private final String id;
	
	public Project(String path, String id) {
		this.path = path;
		this.id = id;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void open(Player player) {
		try {
			World world = ProjectManager.loadProjectWorld(this);
			player.sendMessage("§cDone");
			
			if(world == null)
				player.sendMessage("§cThere was an internal error loading this project.");
			
			else
				player.sendMessage("§eTeleporting");
				player.teleport(world.getSpawnLocation());
			
		} catch(Exception exception) {
			player.sendMessage("§cThere was an internal error loading this project."); // TODO replace with fancier message
			exception.printStackTrace();
		}
	}
}
