package dev.adrwas.mapbridge.projects;

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
}
