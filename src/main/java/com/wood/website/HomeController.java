package com.wood.website;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
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

import java.net.InetAddress;
import javax.mail.*;
import javax.mail.internet.*;

import com.sun.mail.smtp.*;

/**
 * Handles requests for the application home page.
 */
@SuppressWarnings("unused")
@Controller
public class HomeController {
	public String temp = "notLoggedIn";
	public String loginLogout = null;

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private static final String DBUSER = "REMOVED";
	private static final String DBPASSWORD = "REMOVED";
	
//	CREATE DATABASE joelmwoo_WebsiteUsers;
//
//	CREATE TABLE joelmwoo_WebsiteUsers.user (
//	    ID int AUTO_INCREMENT NOT NULL,
//	    Email varchar(255) NOT NULL,
//	    Password varchar(255) NOT NULL,
//	    HasTempPassword tinyint NOT NULL,
//	    PRIMARY KEY (ID)
//	);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		
		
		logger.info("Welcome home! The client locale is {}.", locale);
		
//		Date date = new Date();
//		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
//		
//		String formattedDate = dateFormat.format(date);
//		
//		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Model model) {
//	    String greetings = "";
//	    model.addAttribute("message", greetings);
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
		String greetings = "Wait for it...";
	    model.addAttribute("message", greetings);
	 
	    return "samples";
	}
	
	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public String contact(Model model) {    
	    if (loggedIn()) {	    			 
		    return "contact";
		} else {
		    String greetings = "Contact Details are on the experience page. This page is proof of redirects when detecting a user not logged in.";
			model.addAttribute("message", greetings);
			return "login";
		}
	}
	
	@RequestMapping(value = "/requestPasswordReset", method = RequestMethod.GET)
	public String requestPasswordReset(Model model) {
	    String greetings = "";
	    model.addAttribute("message", greetings);
	 
	    return "requestPasswordReset";
	}
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.GET)
	public String changePassword(Model model) {
	    String greetings = "";
	    model.addAttribute("message", greetings);
	 
	    return "changePassword";
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
		String un = loginBean.getUserName();
		String pw = loginBean.getPassword();
		if(loginBean.getUserName() == "" || loginBean.getUserName() == " " || loginBean.getPassword() == "" || loginBean.getPassword() == " "){
			model.addAttribute("error", "Please Enter Details");
			return "login";
		}else{
			if (checkDBForAccount(un) && checkDBForCorrectPassword(un, encryptUserPassword(pw))) {
			//if (checkDBForAccount(un) && checkDBForCorrectPassword(un, pw)) {
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
					e.printStackTrace();
				}
				
				if(checkDBForTempPasswordStatus(un)){
					model.addAttribute("message", "Please update from a temporary password.");
					return "changePassword";
				}else{
					//String greetings = "Welcome to Samples, " + temp + "! ";
				    model.addAttribute("message", "Welcome to Samples, " + temp + "! ");
//					return "redirect:samples";	
					return "samples";
						
				//	model.addAttribute("error", "Invalid Password");
				//	return "login";
				}
				
			} else {
				model.addAttribute("error", "Invalid Email/Password");
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
			if(!(checkDBForAccount(loginBean.getUserName()))){				
					if(checkPasswordMeetsPolicy(loginBean.getPassword())){
					String greeting= "";
					String un = loginBean.getUserName();
					String pw = loginBean.getPassword();
					pw = encryptUserPassword(pw);
					
					model.addAttribute("message", "Please sign in to verify credentials.");
					addUser(un, pw);
					return "login";
				}else{
					model.addAttribute("error", "Password does meet minimum requirements.");
					return "login";
				}
			}else{				
				model.addAttribute("error", "Email already exists. Please use another email, or reset password if password has been forgotten.");
				return "login";
			}
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, params = "changePassword")	
	public String changePassword(Model model, @ModelAttribute("loginBean") LoginBean loginBean, HttpServletResponse response, HttpServletRequest request){
		String un = loginBean.getUserName();
		String pw = loginBean.getNewPassword();
		if(loginBean.getUserName() == "" || loginBean.getUserName() == " " || loginBean.getPassword() == "" || loginBean.getPassword() == " "){
			model.addAttribute("error", "Please Enter Email and Passwords");
			return "changePassword";
		}else{
			pw = encryptUserPassword(pw);
			if (checkDBForAccount(un) && checkDBForCorrectPassword(un, pw)){
				changeUserPasswordToPermPassword(un, pw);
				model.addAttribute("message", "Password Successfully Changed.");
				return "samples";
			}else{
				model.addAttribute("error", "Invalid Email/Password.");
				return "changePassword";
			}
		}
	}	
	
	@RequestMapping(method = RequestMethod.POST, params = "resetPassword")	
	public String resetPassword(Model model, @ModelAttribute("loginBean") LoginBean loginBean, HttpServletResponse response, HttpServletRequest request){
//		logger.info(loginBean.getUserName());
//		logger.info(loginBean.getPassword());
		String un = loginBean.getUserName();
		//String pw = loginBean.getPassword();
		if(loginBean.getUserName() == "" || loginBean.getUserName() == " " ){
			model.addAttribute("error", "Please Enter Email Address");
			return "requestPasswordReset";
		}else{
			if (checkDBForAccount(un)) {
				String usersTempPassword = createRandomTempPassword();
				System.out.println(usersTempPassword);
				sendNewTempPasswordToUsersEmail(un, usersTempPassword);
				changeUserPasswordToTempPassword(un, encryptUserPassword(usersTempPassword));
				model.addAttribute("message", "An email has been sent to you containing a temporary password.");
				return "login";
			} else {
				model.addAttribute("error", "Invalid Email");
				return "requestPasswordReset";
			}
		} 			
	}

	private String createRandomTempPassword() {
		String randomChars01 = "abcdefghijklmnopqrstuvwxyz";
		String randomChars02 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String randomChars03 = "1234567890";
		String randomChars04 = "!@#$%^&*";
        
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 3) { // length of the random string.
            int index = (int) (rnd.nextFloat() * randomChars01.length());
            salt.append(randomChars01.charAt(index));
        } while (salt.length() < 6) { // length of the random string.
            int index = (int) (rnd.nextFloat() * randomChars02.length());
            salt.append(randomChars02.charAt(index));
        } while (salt.length() < 9) { // length of the random string.
            int index = (int) (rnd.nextFloat() * randomChars03.length());
            salt.append(randomChars03.charAt(index));
        } while (salt.length() < 12) { // length of the random string.
            int index = (int) (rnd.nextFloat() * randomChars04.length());
            salt.append(randomChars04.charAt(index));
        }
        String randomString = salt.toString();
        return randomString;
	}

	private void addUser(String un, String pw) {
		String greeting = "";
		String apos = "'";
//		String tempUN = apos + un + apos;
//		pw = apos + pw + apos;
		try{  
			//Class.forName("com.mysql.jdbc.Driver"); Deprecated
//			Class.forName("com.mysql.jdbc.Driver");
//			java.sql.Connection conn;
//			conn = DriverManager.getConnection(
//			 "jdbc:mysql://localhost:3306/joelmwoo_WebsiteUsers?user=joelmwoo_website&SpurtMinkHunterSprig90=blah&autoReconnect=true&useSSL=false");
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			java.sql.Connection conn;  
			conn = DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/joelmwoo_WebsiteUsers?autoReconnect=true&useSSL=false", DBUSER, DBPASSWORD);
			//here joelmwoo_WebsiteUsers is database name, joelmwoo_website is username and SpurtMinkHunterSprig90 is the password  
			Statement stmt=conn.createStatement(); 			
			stmt.executeUpdate
					("INSERT INTO user (Email,Password, HasTempPassword) VALUES ('" + un + "', '" + pw + "', '0')");			
			
			conn.close();  
			}catch(Exception e){ 
				System.out.println(e);			
			}
//		tempUN = "";
		
	}

	private boolean checkPasswordMeetsPolicy(String pw){		
		String pattern = "^([a-zA-Z0-9!@#$%^&*]{8,20})$";
		if(pw.matches(pattern)){
			return true;
		}else{
			return false;
		}
		
	}

	private boolean checkDBForAccount(String un)  {
		boolean accountExists = false;
		//String apos is used for prepared SQL statement
		String greeting = "";
//		String apos = "'";
//		String tempUN = apos + un + apos;
		try{  
			//Class.forName("com.mysql.jdbc.Driver"); Deprecated
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			java.sql.Connection conn;
//			  
			conn = DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/joelmwoo_WebsiteUsers?autoReconnect=true&useSSL=false", DBUSER, DBPASSWORD);  
			//here joelmwoo_WebsiteUsers is database name, joelmwoo_website is username and SpurtMinkHunterSprig90 is the password  
			Statement stmt=conn.createStatement(); 			
			ResultSet rs=stmt.executeQuery("SELECT Email FROM user WHERE Email=('"+un+"')");			
			while(rs.next()) { 				
				if(un.equals(rs.getString(1))){
					//System.out.println(rs.getString(1));
					accountExists = true;
				}else{
					accountExists = false;
				}
			}
			conn.close();  
			}catch(Exception e){ 
				System.out.println(e);
			
			}
//		tempUN = "";
		return accountExists;
	}
	
	private boolean checkDBForTempPasswordStatus(String un) {
		boolean hasTempPassword = false;
		//String apos is used for prepared SQL statementString apos = "'";
		String apos = "'";
//		un = apos + un + apos;
		try{  
			//Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			java.sql.Connection conn;
//			  
			conn = DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/joelmwoo_WebsiteUsers?autoReconnect=true&useSSL=false", DBUSER, DBPASSWORD);  
			//here joelmwoo_WebsiteUsers is database name, joelmwoo_website is username and SpurtMinkHunterSprig90 is the password  
			Statement stmt=conn.createStatement(); 			
			ResultSet rs=stmt.executeQuery("SELECT HasTempPassword FROM user WHERE Email=('"+un+"')");			
			while(rs.next()) { 
//				System.out.println(rs.getString(1));
				int isTempPassword = Integer.parseInt(rs.getString(1));
				if(isTempPassword == 1){
					hasTempPassword = true;
				}else{
					hasTempPassword = false;
				}
			}
			conn.close();  
			}catch(Exception e){ 
				System.out.println(e);
			
			}
//		System.out.println(hasTempPassword);
		return hasTempPassword;
	}
	
	private boolean checkDBForCorrectPassword(String un, String pw){
		boolean correctPassword = false;
		//String apos is used for prepared SQL statementString apos = "'";
		String apos = "'";
//		un = apos + un + apos;
		try{  
			//Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			java.sql.Connection conn;  
			conn = DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/joelmwoo_WebsiteUsers?autoReconnect=true&useSSL=false", DBUSER, DBPASSWORD);  
			//here joelmwoo_WebsiteUsers is database name, joelmwoo_website is username and SpurtMinkHunterSprig90 is the password  
			Statement stmt=conn.createStatement(); 			
			ResultSet rs=stmt.executeQuery("SELECT Password FROM user WHERE Email=('"+un+"')");			
			while(rs.next()) { 
				//System.out.println(rs.getString(1));
				if(pw.equals(rs.getString(1))){
					correctPassword = true;
				}else{
					correctPassword = false;
				}
			}
			conn.close();  
			}catch(Exception e){ 
				System.out.println(e);
			
			}
		return correctPassword;
	}
	
	private void changeUserPasswordToTempPassword(String un, String pw) {
//		String greeting = "";
//		String apos = "'";
//		String tempUN = apos + un + apos;
//		pw = apos + pw + apos;
		try{  
			//Class.forName("com.mysql.jdbc.Driver"); Deprecated
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			java.sql.Connection conn;  
			conn = DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/joelmwoo_WebsiteUsers?autoReconnect=true&useSSL=false", DBUSER, DBPASSWORD);  
			//here joelmwoo_WebsiteUsers is database name, 
			//joelmwoo_website is username, and
			//SpurtMinkHunterSprig90 is the password
			Statement stmt=conn.createStatement(); 			
			stmt.executeUpdate
					("UPDATE user SET Password='"+ pw + "', HasTempPassword='1' WHERE Email='" + un + "';");			
			
			conn.close();  
			}catch(Exception e){ 
				System.out.println(e);			
			}
//		tempUN = "";
		
	}
	
	private void changeUserPasswordToPermPassword(String un, String pw) {
//		String apos = "'";
//		String tempUN = apos + un + apos;
//		pw = apos + pw + apos;
		try{  
			//Class.forName("com.mysql.jdbc.Driver"); Deprecated
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			java.sql.Connection conn;  
			conn = DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/joelmwoo_WebsiteUsers?autoReconnect=true&useSSL=false", DBUSER, DBPASSWORD);  
			//here joelmwoo_WebsiteUsers is database name, joelmwoo_website is username and SpurtMinkHunterSprig90 is the password  
			Statement stmt=conn.createStatement(); 			
			stmt.executeUpdate
					("UPDATE user SET Password='"+ pw + "', HasTempPassword='0' WHERE Email='" + un + "';");			
			
			conn.close();  
			}catch(Exception e){ 
				System.out.println(e);			
			}
//		tempUN = "";
		
	}
	
	private String encryptUserPassword(String pw){
		
		String passwordToHash = pw;
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(passwordToHash.getBytes());
            //Get the hash's bytes 
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } 
        catch (NoSuchAlgorithmException e) 
        {
            e.printStackTrace();
        }
        //System.out.println(generatedPassword);
   
		return generatedPassword;
	}
	
	@RequestMapping("/")
	private void setCookie(HttpServletResponse response, String userName) {
		response.addCookie(new Cookie("ClientID", userName));

	}
	
	//@RequestMapping("/")
	private void deleteCookies(HttpServletResponse response, HttpServletRequest request) {
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
	
	private void sendNewTempPasswordToUsersEmail(String un, String pw){
	      // Recipient's email ID needs to be mentioned.
	      String to = un;

	      // Sender's email ID needs to be mentioned
	      String from = "do_not_reply@joelmwood.com";

	      // Assuming you are sending email from localhost
	      String host = "localhost";

	      // Get system properties
	      Properties properties = System.getProperties();

	      // Setup mail server
	      properties.setProperty("mail.smtp.host", host);

	      // Get the default Session object.
	      Session session = Session.getDefaultInstance(properties);

	      try {
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

	         // Set Subject: header field
	         message.setSubject("Password Reset Request");

	         // Now set the actual message
	         message.setText("A password reset has been requested. If this was sent in error, please contact mail@joelmwood.com \n" + 
					"Temporary Password: " + pw);

	         // Send message
	         Transport.send(message);
	         System.out.println("Sent message successfully....");
	      }catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
	   }
}
