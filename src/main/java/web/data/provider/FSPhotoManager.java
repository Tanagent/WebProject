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
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import web.data.Photo;
import web.util.ResourceResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FSPhotoManager implements PhotoManager {
	
	/**
	 * We persist all the user related objects as JSON.
	 * 
	 * For more information about JSON and ObjectMapper, please see:
	 * http://www.journaldev.com/2324/jackson-json-proecessing-api-in-java-example-tutorial
	 * 
	 * or Google tons of tutorials.
	 */
	private static final ObjectMapper JSON = new ObjectMapper();
	
	/**
	 * Represents the  static Logger that can be used
	 * to log info on various levels throughout the
	 * FSPhotoManager class.
	 */
	private static Logger logger = LoggerFactory.getLogger(FSPhotoManager.class);
	
	/**
	 * Load the photo map from the local file.
	 * 
	 * @return a hashmap of all the photo information.
	 */
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
	
	/**
	 * Save and persist the photo map in the local file.
	 * 
	 * @param photoMap - the list of photo information in the local file.
	 */
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
	
	/**
	 * Handles adding a photo to our disk.
	 * An input stream is created as well as
	 * a corresponding output stream which
	 * directs to the correct user's folder.
	 * The "copy" method is utilized to store
	 * the information from our input stream
	 * into our output stream.
	 * 
	 * @param file - the photo to be uploaded
	 * @throws IOException - exception caused by IO issues.
	 * @throws NullPointerException - exception caused by a null object.
	 */
	public static void addPhoto(MultipartFile file) throws IOException, NullPointerException {
		logger.warn("Errors may be thrown in the PhotoManager addPhoto method");
		InputStream is = file.getInputStream();
    	OutputStream os = new FileOutputStream(ResourceResolver.getUserFile().getParent() + "/" + file.getOriginalFilename());
    	IOUtils.copy(is, os);
    	is.close();
    	os.close();
	}
	
	/**
	 * Handles listing all of the photo names in
	 * your local browser. This is done by listing
	 * all of your files in the user's directory.
	 * Each file is then iterated over and stored in
	 *  an ArrayList<String> Only the files that have the
	 *  extension .jpg are displayed.
	 *  
	 * @return a list of photo names.
	 */
	public static ArrayList<String> listPhotos() {
		logger.debug("UserID from PhotoManager listPhotos method: ");
		ArrayList<String> Ids = new ArrayList<String>();
		System.out.println("C:\\Users\\Brian\\workspace\\WebProject\\PhotoBucket\\");
		File[] files = new File("C:\\Users\\Brian\\workspace\\WebProject\\PhotoBucket\\").listFiles();
		
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
	
	/**
	 * Handles deleting a photo from the disk.
	 * This is done by opening the file corresponding
	 * to the appropriate path given the the userID and the
	 * photoID. This file is then deleted using the "delete"
	 * method.
	 * 
	 * @param photoId - the name of the photo. 
	 * @return - a String denoting the status of the deletion.
	 */
	public static String deletePicture(String photoId) {
		File file = new File("C:\\Users\\Brian\\workspace\\WebProject\\PhotoBucket\\" + photoId + ".jpg");
		
		if (file.delete()) {
			return "File Deleted";
		} else {
			return "File Not Deleted";
		}
	}
	
	/**
	 * Handles randomizing a photo from the disk.
	 * This is done by randomly choosing a photo
	 * in the array with the JAVA API.
	 * 
	 * @param list - the list of photos.
	 * @return - a random photo.
	 */
	public static String getRandomList(ArrayList<String> list) {
		int index = ThreadLocalRandom.current().nextInt(list.size());
		return list.get(index);
	}

}