package com.wood.website;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.request.RequestScope;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.util.WebUtils;


/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	public String temp = "notLoggedIn";
	public String loginLogout = null;

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
	public String samples(Model model, HttpServletRequest request) {
		//HttpSession tempSession = request.getSession(false);
		//String session = tempSession.getAttribute("userID").toString();
	    if (loggedIn()) {	    			 
		    return "samples";
		} else {
			return "redirect:/login";
		}
		
//		String greetings = "Hello";
//	    model.addAttribute("message", greetings);		 
//	    return "samples";
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
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login(Model model){
		if(loggedIn()){
			return "samples";
		}else{
			model.addAttribute("message",  "Please Enter Your Login Details");
			return "login";
		}		
	}
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(Model model, HttpServletResponse response, HttpServletRequest request){
		loginLogout = null;
		
		try {
			setLoginHyperLink(response, request, loginLogout);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		temp = "notLoggedIn";
		
		HttpSession session = request.getSession(false);
		//setCookie(response, "");
		deleteCookies(response, request);
		session.invalidate();
		session = request.getSession();
		
		return "redirect:/login";
	}
	
	

	@RequestMapping(method = RequestMethod.POST, params = "submit")	
	public String submit(Model model, @ModelAttribute("loginBean") LoginBean loginBean, HttpServletResponse response, HttpServletRequest request){
//		logger.info(loginBean.getUserName());
//		logger.info(loginBean.getPassword());
		if(loginBean.getUserName() == "" || loginBean.getUserName() == " " || loginBean.getPassword() == "" || loginBean.getPassword() == " "){
			model.addAttribute("error", "Please Enter Details");
			return "login";
		}else{
			if (loginBean != null && loginBean.getUserName() != null && loginBean.getPassword() != null) {
				if (loginBean.getUserName().equals("test1") && loginBean.getPassword().equals("test1")) {
					//model.addAttribute("message",  "Welcome, " + loginBean.getUserName()+ ". ") ;
					//((HttpServletRequest) request).getSession().setAttribute("loggedInUser", loginBean.getUserName());
					HttpSession session = request.getSession();
					session.invalidate();
					session = request.getSession();
					session.setAttribute("userID", loginBean.getUserName());
					temp = loginBean.getUserName();
					setCookie(response, session.getAttribute("userID").toString());
					loginLogout = "Logout";
					try {
						setLoginHyperLink(response, request, loginLogout);
					} catch (ServletException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//String greetings = "Welcome to Samples, " + temp + "! ";
				    model.addAttribute("message", "Welcome to Samples, " + temp + "! ");
	//				return "redirect:samples";	
					return "samples";
				} else {
					model.addAttribute("error", "Invalid Details");
					return "login";
				}
			} else {
				model.addAttribute("error", "Please Enter Details");
				return "login";
			}
		}
	}
	@RequestMapping(method = RequestMethod.POST, params = "signup")	
	public String signup(Model model, @ModelAttribute("loginBean") LoginBean loginBean, HttpServletResponse response, HttpServletRequest request){
		if(loginBean.getUserName() == "" || loginBean.getUserName() == " " || loginBean.getPassword() == "" || loginBean.getPassword() == " "){
			model.addAttribute("error", "Please Enter Details");
			return "login";	
		}else{
			//model.addAttribute("error", "Unfortunately, sign up is currently disabled.");
			String greeting= "";
			String apos = "'";
			String un = apos + loginBean.getUserName() + apos;
			//Apos is just prep for SQL statement
			String pw = loginBean.getPassword();
			try{  
				//Class.forName("com.mysql.jdbc.Driver");
				Class.forName("com.mysql.cj.jdbc.Driver"); 
				Connection con=DriverManager.getConnection(  
				"jdbc:mysql://localhost:3306/users","root","root");  
				//here users is database name, root is username and password  
				Statement stmt=con.createStatement(); 			
				ResultSet rs=stmt.executeQuery("SELECT Password FROM User WHERE Username=("+un+")");			
				while(rs.next()) { 
					System.out.println(rs.getString(1));
					if(pw.equals(rs.getString(1))){
						greeting =  "Good Username/Password!";
					}else{
						greeting =  "Bad Username/Password!";
					}
				}
					con.close();  
				}catch(Exception e){ 
					System.out.println(e);
				
				}				

			MessageDigest messageDigest;
			try {
				messageDigest = MessageDigest.getInstance("SHA-256");
				messageDigest.update(pw.getBytes());
				String encryptedPW = new String(messageDigest.digest());
				pw = encryptedPW;
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			model.addAttribute("table", pw);
			return "login";	
		}
	}
	
//	private void setCookie(HttpServletResponse response, String sessionID) {
//		// TODO Auto-generated method stub
//		response.addCookie(new Cookie("ClientID", sessionID));
//	}
//
//	@RequestMapping("/")
//	private void setCookie(HttpServletResponse response) {
//		// TODO Auto-generated method stub
//		response.addCookie(new Cookie("myCookie", "userName"));
//
//	}
	
	@RequestMapping("/")
	private void setCookie(HttpServletResponse response, String userName) {
		// TODO Auto-generated method stub
		response.addCookie(new Cookie("ClientID", userName));

	}
	
	//@RequestMapping("/")
	private void deleteCookies(HttpServletResponse response, HttpServletRequest request) {
		// TODO Auto-generated method stub
		Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {	            
	                cookie.setValue(null);
	                cookie.setMaxAge(0);
	                response.addCookie(cookie);	           
	        }
	    }		
	}

//	@RequestMapping(value = "/success", method = RequestMethod.GET)
//	public String success(Model model, HttpServletRequest request) {
//	HttpSession tempSession = request.getSession(false);
//	String session = tempSession.getAttribute("userID").toString();
//		if(loggedIn() && session != null){
//			String greetings = "Welcome, " + temp + "! ";
//			model.addAttribute("message", greetings);
//			return "success";
//		}else{	
//			String greetings = "You must login first.";
//			model.addAttribute("message", greetings);
//			return "login";
//		}
//			
//	}

	private boolean loggedIn() {
		// TODO Auto-generated method stub
		if(temp.equals("notLoggedIn")){
			return false;
		}else{
			return true;
		}
	}
	
	public void setLoginHyperLink(HttpServletResponse response, HttpServletRequest request, String loginLogout) throws ServletException{
		HttpSession session = request.getSession();
	    //String username = (String)request.getAttribute("loginHyperlink");
	    session.setAttribute("loginHyperlink", loginLogout);
	}
	

}
