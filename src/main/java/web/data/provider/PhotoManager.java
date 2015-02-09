package web.data.provider;

import java.util.List;

import web.data.Photo;

public interface PhotoManager {
	public Photo getPhoto(String photoId);
	
	public void updatePhoto(Photo photo);
	
	public void deleteUser(String photoId);
	
	public List<Photo> listAllPhotos();
}
