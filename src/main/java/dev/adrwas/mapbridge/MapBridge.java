package dev.adrwas.mapbridge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import dev.adrwas.mapbridge.commands.AbstractCommand;
import dev.adrwas.mapbridge.commands.CreateProjectCommand;
import dev.adrwas.mapbridge.commands.ListProjectsCommand;
import dev.adrwas.mapbridge.commands.OpenProjectCommand;
import dev.adrwas.mapbridge.fs.ProjectManager;

public class MapBridge extends JavaPlugin {
	
	private static MapBridge instance;
	
	public static ProjectManager projectManager;
	
	public Random random;
	
	@Override
	public void onEnable() {
		instance = this;
		
		projectManager = new ProjectManager();
		
		this.random = new Random();
		
		ArrayList<AbstractCommand> commands = new ArrayList<AbstractCommand>(
			Arrays.asList(
				new CreateProjectCommand(),
				new ListProjectsCommand(),
				new OpenProjectCommand()
			)
		);
		
		commands.forEach(command -> {
			command.register();
		});
	}
	
	@Override //FIXME temp
	public boolean onCommand(CommandSender sender, Command command, String label, String args[]) {
//		if(command.getName().equalsIgnoreCase("dir")) {
//			String search = args[0];
//			sender.sendMessage("�eSearching for �7" + search + "�e...");
//			
//			File dir = new File(getDataFolder().getPath() + File.separator + "directories" + File.separator + search);
//			if(dir.exists() && dir.isDirectory()) {
//				if(ProjectManager.findProjectFromPath(search) != null) {
//					sender.sendMessage("�cThis is a project!");
//				} else {
//					sender.sendMessage("�aFound this folder!\n�6Subfolders:");
//					for(File file : dir.listFiles()) {
//						if(!file.isDirectory()) continue;
//						sender.sendMessage("�f- �d" + file.getName());
//					}
//				}
//			} else {
//				sender.sendMessage("�cThis folder doesnt exist");
//			}
//		} else 
//		if(command.getName().equalsIgnoreCase("createproject")) {
//			if(args.length < 1) {
//				sender.sendMessage("�cSyntax: /createproject <path>");
//				return false;
//			}
//			
//			File dir = new File(this.getDataFolder() + File.separator + "directories" + File.separator + args[0]);
//			
//			if(dir.exists()) {
//				sender.sendMessage("�cThis already exists");
//				return false;
//			}
//			
//			dir.mkdir();
//			
//			File data = new File(MapBridge.getInstance().getDataFolder().getPath() + File.separator + ".projects");
//			if(data.exists()) {
//				JsonParser parser = new JsonParser();
//				try(FileReader reader = new FileReader(data)) {
//					Object obj = parser.parse(reader);
//					JsonObject jsonObject = (JsonObject) obj;
//					
//					JsonObject newObj = new JsonObject();
//					newObj.addProperty("id", Integer.toHexString(new Random().nextInt()));
//					
//					jsonObject.get("projects").getAsJsonObject().add(args[0], newObj);
//
//					String toWrite = jsonObject.toString();
//					
//					reader.close();
//					
//					FileWriter writer = new FileWriter(data, false);
//					
//					writer.write(toWrite);
//					
//					writer.close();
//				} catch (FileNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//			} else {
//				System.out.println("No .projects file found, creating one!");
//				
//				try {
//					data.createNewFile();
//					PrintStream out = new PrintStream(new FileOutputStream(data));
//					out.println("{}");
//					out.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//			}
//		}
		AbstractCommand.handleCommand(sender, command, label, args);
		return false;
	}
	
	@Override //FIXME temp
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String args[]) {
		return AbstractCommand.handleTabComplete(sender, command, label, args);
	}
	
	public static MapBridge getInstance() {		
		return instance;
	}
}
