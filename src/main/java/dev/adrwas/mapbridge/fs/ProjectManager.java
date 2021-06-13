package dev.adrwas.mapbridge.fs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import dev.adrwas.mapbridge.MapBridge;
import dev.adrwas.mapbridge.projects.Project;

public class ProjectManager {
	
	public List<Project> projects;
	
	public ProjectManager() {
		projects = new ArrayList<Project>();
		
		this.refresh();
	}

	public void addProject(Project newProject) throws IOException {
		projects.add(newProject);
		
		JsonObject jsonObject = new JsonObject();
		jsonObject.add("projects", new JsonObject());
		
		for(Project project : projects)
		{
			JsonObject subObject = new JsonObject();
			subObject.addProperty("id", project.getId());
			
			jsonObject.get("projects").getAsJsonObject().add(project.getPath(), subObject);
		}
				
		File data = getProjectsDataFile();
		FileWriter writer = new FileWriter(data, false);
		
		writer.write(jsonObject.toString());
		
		writer.close();
	}
	
 	public void refresh() {
		File data = new File(
			MapBridge.getInstance().getDataFolder().getPath() + File.separator + ".projects"
		);
		
		if(data.exists()) {
			JsonParser parser = new JsonParser();
			
			try(FileReader reader = new FileReader(data)) {
				Object object = parser.parse(reader);
				JsonObject jsonObject = (JsonObject) object;
				
				for(Entry<String, JsonElement> entry : jsonObject.getAsJsonObject("projects").entrySet())
					projects.add(new Project(entry.getKey(), entry.getValue().getAsJsonObject().get("id").getAsString()));
			} catch(IOException exception) {
				exception.printStackTrace();
				Bukkit.getPluginManager().disablePlugin(MapBridge.getInstance());
			}
		} else {
			try {
				data.createNewFile();
				
				PrintStream out = new PrintStream(new FileOutputStream(data));
				out.println("{}");
				out.close();
			} catch(IOException exception) {
				exception.printStackTrace();
				Bukkit.getPluginManager().disablePlugin(MapBridge.getInstance());
			}
		}
	}

	public Project findProjectFromPath(String path) {
		for(Project project : projects)
			if(project.getPath().equals(path))
				return project;
		return null;
	}
	
	public Project findProjectFromID(String id) {
		for(Project project : projects)
			if(project.getId().equals(id))
				return project;
		return null;
	}
	
	public static File getProjectsDataFile() {
		File file = new File(MapBridge.getInstance().getDataFolder().getPath() + File.separator + ".projects");
		if(!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return file;
	}
	
 	public static World loadProjectWorld(Project project) throws IOException { // test
		System.out.println("Loading project world " + project.getPath() + "... (ID: " + project.getId() + ")");
		String worldName = "bridge-" + project.getId(); // TODO configure prefix
		
		if(Bukkit.getWorld(worldName) != null) {
			System.out.println("World is already loaded");
			return Bukkit.getWorld(worldName);
		}
		
		else {
			System.out.println("World is not loaded");
			
			File dir = new File(worldName);
			
			if(dir.exists()) {
				System.out.println("World directory exists");
				FileUtils.deleteDirectory(dir);
			}
			
			System.out.println("Copying from " + MapBridge.getInstance().getDataFolder().getPath() + File.separator + "directories" + File.separator + project.getPath());
			FileUtils.copyDirectory(new File(MapBridge.getInstance().getDataFolder().getPath() + File.separator + "directories" + File.separator + project.getPath()), dir);
			
			WorldCreator creator = new WorldCreator(worldName);
			
			return creator.createWorld();
		}
	}
}
