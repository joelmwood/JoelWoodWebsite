package com.wood.website;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value = "/experience", method = RequestMethod.GET)
	public String experience(Model model) {
	    String greetings = "Wait for it...";
	    model.addAttribute("message", greetings);
	 
	    return "experience";
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test(Model model) {
	    String greetings = "Greetings, Spring MVC!";
	    model.addAttribute("message", greetings);
	 
	    return "test";
	}
	
	@RequestMapping(value = "/samples", method = RequestMethod.GET)
	public String samples(Model model) {
	    String greetings = "Wait for it...";
	    model.addAttribute("message", greetings);
	 
	    return "samples";
	}
	
	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public String contact(Model model) {
	    String greetings = "Wait for it...";
	    model.addAttribute("message", greetings);
	 
	    return "contact";
	}
	
	@RequestMapping(value = "**", method = RequestMethod.GET)
	public String notFound(Model model) {
	    String greetings = "Uh-oh, something went wrong.";
	    model.addAttribute("message", greetings);
	 
	    return "notFound";
	}

}
