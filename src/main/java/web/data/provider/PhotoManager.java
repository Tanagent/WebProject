package web.data.provider;

import java.util.List;

import web.data.Photo;

public interface PhotoManager {
	
	/**
	 * Get the photo information object based on
	 * the given photoId.
	 * 
	 * If the photo information does not exist,
	 * simply create one.
	 * 
	 * @param photoId
	 * @return -  the Photo object
	 */
	public Photo getPhoto(String photoId);
	
	/**
	 * Update the given photo object and persist it.
	 * 
	 * If the user does not exist before, this
	 * method will create a new record; otherwise,
	 * it will overwrite whatever is currently
	 * being stored.
	 * 
	 * @param photo - object
	 */
	public void updatePhoto(Photo photo);
	
	/**
	 * Delete the given photo from the storage.
	 * 
	 * @param photoId
	 */
	public void deletePhoto(String photoId);
	
	/**
	 * List all the current users in the storage.
	 * 
	 * @return - the list of photo information.
	 */
	public List<Photo> listAllPhotos();
}
