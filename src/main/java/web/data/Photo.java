package web.data;

import java.util.Date;


public class Photo {
	private String id;
	private String name;
	private String owner;
	private String creationTime = new Date(System.currentTimeMillis()).toString();
	
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
}
