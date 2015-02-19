package web.data.provider;

import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import web.data.Photo;
import web.util.ResourceResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FSPhotoManager implements PhotoManager {
	
	private static final ObjectMapper JSON = new ObjectMapper();
	
	@SuppressWarnings("unchecked")
	private HashMap<String, Photo> getPhotoMap() {
		HashMap<String, Photo> photoMap = null;
		File userFile = ResourceResolver.getUserFile();
		if (userFile.exists()) {
			try {
				photoMap = JSON.readValue(userFile, HashMap.class);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			photoMap = new HashMap<String, Photo>();
		}
		return photoMap;
	}
	
	private void persistPhotoMap(HashMap<String, Photo> photoMap) {
		try {
			JSON.writeValue(ResourceResolver.getUserFile(), photoMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Photo getPhoto(String photoId) {
		HashMap<String, Photo> photoMap = getPhotoMap();
		return (Photo) photoMap.get(photoId);
	}

	@Override
	public void updatePhoto(Photo photo) {
		HashMap<String, Photo> photoMap = getPhotoMap();
		photoMap.put(photo.getId(), photo);
		persistPhotoMap(photoMap);
	}

	@Override
	public void deletePhoto(String photoId) {
		HashMap<String, Photo> photoMap = getPhotoMap();
		photoMap.remove(photoId);
		persistPhotoMap(photoMap);
	}

	@Override
	public List<Photo> listAllPhotos() {
		HashMap<String, Photo> photoMap = getPhotoMap();
		return new ArrayList<Photo>(photoMap.values());
	}
}