package web.controller;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import web.LaunchWeb;
import web.data.Photo;
import web.data.provider.PhotoManager;


@RestController
public class WebController {

	@Autowired
	private PhotoManager photoManager;
	
	@RequestMapping(value = "/example", method = RequestMethod.GET)
	String healthCheck() {
		return "Lets check ";
	}
}