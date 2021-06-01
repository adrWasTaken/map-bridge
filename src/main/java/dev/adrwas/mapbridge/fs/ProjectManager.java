package dev.adrwas.mapbridge.fs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import dev.adrwas.mapbridge.MapBridge;
import dev.adrwas.mapbridge.projects.Project;

public class ProjectManager {
	
	public static List<Project> projects;
	
	public static List<Project> getProjects() throws IOException {
		List<Project> projects = new ArrayList<Project>();
		
		File data = new File(MapBridge.getInstance().getDataFolder().getPath() + File.separator + ".projects");
		if(data.exists()) {
			System.out.println("Found .projects file, reading!");
			
			JsonParser parser = new JsonParser();
			try(FileReader reader = new FileReader(data)) {
				Object obj = parser.parse(reader);
				JsonObject jsonObject = (JsonObject) obj;
				
				for(Entry<String, JsonElement> entry : jsonObject.getAsJsonObject("projects").entrySet())
					projects.add(new Project(entry.getKey(), entry.getValue().getAsJsonObject().get("id").getAsString()));
			}
		} else {
			System.out.println("No .projects file found, creating one!");
			
			data.createNewFile();
			PrintStream out = new PrintStream(new FileOutputStream(data));
			out.println("{}");
			out.close();
		}
		
		File projectsDir = new File(MapBridge.getInstance().getDataFolder().getPath() + File.separator + "directories");
		if(!projectsDir.exists())
			projectsDir.mkdir();
		
		return projects;
	}
	
	public static Project findProjectFromPath(String path) {
		for(Project project : projects)
			if(project.getPath().equals(path))
				return project;
		return null;
	}
	
	public static Project findProjectFromID(String id) {
		for(Project project : projects)
			if(project.getId().equals(id))
				return project;
		return null;
	}
	
	public static void loadProjectWorld(Project project) { // test
		System.out.println("Loading project world " + project.getPath() + "... (ID: " + project.getId() + ")");
		String worldName = "bridge-" + project.getId(); // TODO configure prefix
		
		if(Bukkit.getWorld(worldName) != null) {
			System.out.println("World is already loaded");
		}
		
		else {
			System.out.println("World is not loaded");
			
			File dir = new File(worldName);
			
			if(dir.exists()) {
				System.out.println("World directory exists");
				try {
					FileUtils.deleteDirectory(dir);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			System.out.println("Copying from " + MapBridge.getInstance().getDataFolder().getPath() + File.separator + "directories" + File.separator + project.getPath());
			try {
				FileUtils.copyDirectory(new File(MapBridge.getInstance().getDataFolder().getPath() + File.separator + "directories" + File.separator + project.getPath()), dir);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			WorldCreator creator = new WorldCreator(worldName);
			
			creator.createWorld();
		}
	}
}
