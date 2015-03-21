package web.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import web.data.Photo;
import web.data.provider.FSPhotoManager;
import web.data.provider.PhotoManager;
import web.util.ResourceResolver;
import web.LaunchWeb;

/**
 * This is the controller used by Spring framework.
 * 
 * The basic function of this controller is to map
 * each HTTP API Path to the correspondent method.
 */

@SuppressWarnings("unused")
@RestController
@Controller
public class WebController implements java.io.Serializable {

	/**
	 * When the class instance is annotated with
	 * {@link Autowired}, it will be looking for the actual
	 * instance from the defined beans.
	 * 
	 * In our project, all the beans are defined in
	 * the {@link LaunchWeb} class.
	 */
	@Autowired
	private PhotoManager photoManager;
	
	/**
	 * Represents the static Logger that can be used
	 * to register logs throughout our WebController class.
	 */
	private static Logger logger = LoggerFactory.getLogger(WebController.class);
	
	/**
	 * This is a simple example of how the HTTP API works.
	 * It returns a String "OK" in the HTTP reponse.
	 * To try it, run the web application locally,
	 * in your web browser, type the link:
	 * http://localhost:8080/example
	 * @return
	 */
	@RequestMapping(value = "/example", method = RequestMethod.GET)
	String healthCheck() {
		// TODO: Replace the following return with your GitHub ID
		// in the exact format: Name (GitHubID)
		// For instance,
		// return "John Smith (smithj01)";
		// Then, run the application locally and check your changes
		// with the URL: http://localhost:8080/
		return "Lets check ";
	}
	
	/**
     * This is a simple example of how to use a data manager
     * to retrieve the data and return it as an HTTP response.
     * 
     * Note, when it returns from the Spring, it will be
     * automatically converted to JSON format.
     * 
     * Try it in your web browser:
     * 	http://localhost:8080/album/user/user101
     */
    @RequestMapping(value = "/album/user/{photoId}", method = RequestMethod.GET)
    Photo getPhoto(@PathVariable("photoId") String photoId) {
    	Photo photo = photoManager.getPhoto(photoId);
        return photo;
    }
    
    /**
     * This is an example of sending an HTTP POST request to
     * update a user's information (or create the user if not
     * exists before).
     *
     * You can test this with a HTTP client by sending
     *  http://localhost:8080/album/user/user101
     *  	name=John major=CS
     *
     * Note, the URL will not work directly in browser, because
     * it is not a GET request. You need to use a tool such as
     * curl.
     *
     * @param id
     * @param name
     * @param owner
     * @return the updated photo information.
     */
    @RequestMapping(value = "/album/user/{photoId}", method = RequestMethod.POST)
    Photo updatePhoto(
    		@PathVariable("photoId") String id,
    		@RequestParam("name") String name,
    		@RequestParam(value = "owner", required = false) String owner) {
    	Photo photo = new Photo();
    	photo.setId(id);
    	photo.setOwner(owner);
    	photo.setName(name);
    	photoManager.updatePhoto(photo);
    	return photo;
    }

    /**
     * This API deletes the user. It uses HTTP DELETE method.
     *
     * @param userId
     */
    @RequestMapping(value = "/album/user/{photoId}", method = RequestMethod.DELETE)
    void deletePhoto(
    		@PathVariable("photoId") String photoId) {
    	photoManager.deletePhoto(photoId);
    }

    /**
     * This API lists all the photos in the current database.
     *
     * @return the photo list.
     */
    @RequestMapping(value = "/album/users/list", method = RequestMethod.GET)
    List<Photo> listAllPhotos() {
    	return photoManager.listAllPhotos();
    }

    /*********** Web UI Test Utility **********/
    /**
     * This method provide a simple web UI for you to test the different
     * functionalities used in this web service.
     */
    @RequestMapping(value = "/album/home", method = RequestMethod.GET)
    ModelAndView getPhotoHomepage() {
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("photos", listAllPhotos());
        modelAndView.addObject("pictures", listPhotos());
        return modelAndView;
    }
    
    /**
     * This method prompts the user to upload a file.
     * @return The string prompting the user.
     */
    @RequestMapping(value="/upload", method=RequestMethod.GET)
    public @ResponseBody String provideUploadInfo() {
        return "You can upload a file by posting to this same URL.";
    }
    
    /**
     * This method uploads a picture from your computer and
     * stores it in a folder "Photobucket". After the file
     * has been uploaded, 
     * 
     * 
     * @param name - the name of the file.
     * @param file - the uploaded file.
     * @return The string confirming that the file has been uploaded.
     */
    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload(
    		@RequestParam("name") String name, 
            @RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream = 
                        new BufferedOutputStream(
                        		new FileOutputStream(
                        				new File("C:\\Users\\Brian\\workspace\\WebProject\\PhotoBucket\\" + name + ".jpg")));
                stream.write(bytes);
                stream.close();
                return "You successfully uploaded " + name + "!";
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name + " because the file was empty.";
        }
    }
    
    /**
     * This method displays a specific photo, that is stored in the "PhotoBucket"
     * folder, in an HTML browser corresponding to the link photo/{photo}. In this
     * case, the {photo} is the name of a photo, without the extension, that was 
     * previously uploaded using the "handleFileUpload" method.
     * 
     * Try it in your local browser at
     * http://localhost:8080/photo/{photo}
     * First upload a file and then use the name of the photo
     * you uploaded as the {photo} to have it displayed
     * in your browser.
     * 
     * @param photo - the name of the photo uploaded.
     * @param response - the incoming HTTP response
     * @throws IOException - exception caused by I/O issues.
     * @throws FileNotFoundException - exception caused by not finding the file.
     */
    @RequestMapping(value = "/photo/{photo}", method = RequestMethod.GET)
    void getPhoto(
            @PathVariable("photo") String photo,
            HttpServletResponse response) throws IOException, FileNotFoundException {
			logger.warn("Photos must be of type jpg");
			File photoFile = new File("C:\\Users\\Brian\\workspace\\WebProject\\PhotoBucket\\" + photo + ".jpg");
			InputStream is = new FileInputStream(photoFile);
			OutputStream os = response.getOutputStream();
			IOUtils.copy(is, os);
			is.close();
			os.close();
			response.setContentType("image/jpeg");
			response.flushBuffer();
    }
    
    /**
     * This method displays a random photo stored in the PhotoBuket folder, in an HTML
     * browser corresponding to the link photo/random.
     * 
     * This method calls the static "getRandom" method in the FSPhotoManager class and 
     * passes in the "listPhotos" method that lists all the name of the photos that are
     * in the folder. 
     * 
     * Make sure that there is at least one photo in PhotoBucket or else the link will
     * not work.
     * 
     * @param response - the incoming HTML response.
     * @throws IOException - exception caused by IO issues.
     * @throws FileNotFoundException - exception caused by not finding the file.
     */
    @RequestMapping(value = "/photo/random", method = RequestMethod.GET)
    void getRandom(HttpServletResponse response) throws IOException, FileNotFoundException {
		logger.warn("Photos must be of type jpg");
		File photoFile = new File("C:\\Users\\Brian\\workspace\\WebProject\\PhotoBucket\\" + FSPhotoManager.getRandomList(FSPhotoManager.listPhotos()) + ".jpg");
		InputStream is = new FileInputStream(photoFile);
		OutputStream os = response.getOutputStream();
		IOUtils.copy(is, os);
		is.close();
		os.close();
		response.setContentType("image/jpeg");
		response.flushBuffer();
    }
    
    /**
     * This method deletes a photo when the delete button is clicked on the home page.
     * 
     * This method calls the static "deletePhoto" method in the FSPhotoManager class.
     * This static method has all the coding involved in deleting a specific photo.
     * We want to abstract this portion out for modular purposes.
     * 
     * Try it in your local browser at
     * http://localhost:8080/album/home
     * Make sure you have uploaded some photos first then
     * click on the photo you would like to delete.
     * 
     * @param photo - the photo we want to delete.
     * @return - a String telling us if it failed or not.
     * @throws IOException - exception caused by IO issues (unable to delete)
     */
    @RequestMapping(value = "/photo/{photo}", method = RequestMethod.DELETE)
    String deletePicture(
            @PathVariable("photo") String photo) throws IOException {
    	logger.info("Photo: " + photo);
    	logger.debug("Calling the deletePhoto method in the WebController class");
    	return FSPhotoManager.deletePicture(photo);
    }
    
    /**
     * This method lists all of the photo ID's associated with a given user.
     * This is done by iterating through the "PhotoBucket" folder.
     * 
     * This method calls the static "listPhotos" method in the FSPhotoManager
     * class. This static method handles all of the coding involved in listing
     * each photo ID. We want to abstract this portion out for modularity purposes
     * 
     * Try it in your local browser at
     * http://localhost:8080/album/home
     * Upload several different photos first and then go to this link.
     * It will display all of the photo names.
     * 
     * @return - the list of photo names in the form of a String.
     */
    @RequestMapping(value = "/photo", method = RequestMethod.GET)
    List<String> listPhotos() {
		return FSPhotoManager.listPhotos();
    }
}