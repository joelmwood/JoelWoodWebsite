<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  	<meta name="google-site-verification" content="fVyBfsBm0bd25ijon-X_QemW4NuOvfiTkNTgKBt-90I" />
  	<meta name="viewport" content="width=device-width, initial-scale=1.0">
  	<spring:url value="resources/css/styles.css" var="coreCss" />
	<link href="${coreCss}" rel="stylesheet" />
	<link rel="icon" 
      type="image/png" 
      href="resources/images/favicon.ico">
  	<!--<link href="https://fonts.googleapis.com/css?family=Muli%7CRoboto:400,300,500,700,900" rel="stylesheet">-->
	
	<script type="application/ld+json">
	{
  		"@context": "http://schema.org",
  		"@type": "Person",
		"image": "http://www.joelmwood.com/resources/images/me.png",
  		"name": "Joel M Wood",
  		"url": "http://www.joelmwood.com",
  		"sameAs": [
    		"https://github.com/joelmwood",
    		"https://www.linkedin.com/in/joel-wood-99b206a7/"
  		]
	}
	</script>
</head>
<body>	
	<div class="main-nav">
        <ul class="nav">
          <li class="name">Joel M Wood</li>
          <li><a href="/">Home</a></li>
          <li><a href="experience">Experience</a></li>
          <!-- <li><a href="samples">Samples</a></li> -->
          <li><a href="contact">Contact</a></li>
         <% if (session.getAttribute("loginHyperlink") == null) { %>
			    <li><a href="login">Login</a></li> 
			<% } else {%>
			   <li><a href="logout">Logout</a></li>
			<% } %>
        </ul>
	</div>