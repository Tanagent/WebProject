package web.data;

import java.util.Date;

/**
 * The basic photo object.
 */
public class Photo {
	
	// The unique user ID
	private String id;
	// The unique name
	private String name;
	// The name of the person creating the object.
	private String owner;
	// The timestamp when the photo information is being created.
	private String creationTime = new Date(System.currentTimeMillis()).toString();
	// The path to a specific file.
	private String filePath;
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setOwner(String owner) {
		if(owner == null) {
			owner = "N/A";
		}
		this.owner = owner;
	}
	
	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public String getCreationTime() {
		return creationTime;
	}
	
	public String getFilePath() {
		return filePath;
	}
}
