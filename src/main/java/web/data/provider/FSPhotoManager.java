package web.data.provider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import web.data.Photo;
import web.util.ResourceResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FSPhotoManager implements PhotoManager {
	
	private static final ObjectMapper JSON = new ObjectMapper();
	private static Logger logger = LoggerFactory.getLogger(FSPhotoManager.class);
	
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
	
	public static void getPhoto(String userId, String photoId, HttpServletResponse response) throws FileNotFoundException, IOException {
		logger.warn("Photos must be of type jpg");
		File photoFile = new File(ResourceResolver.getUserFile(userId).getParent() + "/" + photoId + ".jpg");
		InputStream is = new FileInputStream(photoFile);
		OutputStream os = response.getOutputStream();
		IOUtils.copy(is, os);
		is.close();
		os.close();
		response.setContentType("image/jpeg");
		response.flushBuffer();
	}
	
	public static void addPhoto(String userId, MultipartFile file) throws IOException, NullPointerException {
		logger.warn("Errors may be thrown in the PhotoManager addPhoto method");
		logger.debug("UserID from the PhotoManager addPhoto method: " + userId);
		InputStream is = file.getInputStream();
    	OutputStream os = new FileOutputStream(ResourceResolver.getUserFile(userId).getParent() + "/" + file.getOriginalFilename());
    	IOUtils.copy(is, os);
    	is.close();
    	os.close();
	}
	
	public static ArrayList<String> listPhotos(String userId) {
		logger.debug("UserID from PhotoManager listPhotos method: " + userId);
		ArrayList<String> Ids = new ArrayList<String>();
		System.out.println(ResourceResolver.getUserFile(userId).getParent() + "/");
		File[] files = new File(ResourceResolver.getUserFile(userId).getParent() + "/").listFiles();
		
		for (int i = 0; i < files.length; i++) {
			logger.debug("iteration count: " + i);
			System.out.println(files[i].getName());
			logger.debug("ith file name: " + files[i].getName());
		}
		
		for (File file: files) {
			if (file.getName().contains(".jpg"))
				Ids.add(file.getName().replace(".jpg", ""));
		}
		return Ids;
	}
}