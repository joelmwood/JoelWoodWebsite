<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<head>
	<link rel="stylesheet" type="text/css" href="resources/css/loginForm.css">
	<script type="text/javascript" src="resources/js/loginForm.js"></script>
</head>
<%@include file="jspSections/header.jsp" %>
<head><title>Login</title></head>
<br/><br/><br/><br/>
<main class="flexLogin">
<div class="tab">
	
  <button class="tablinks" onclick="openCity(event, 'Login')" id="defaultOpen">Log In</button>
  <button class="tablinks" onclick="openCity(event, 'Signup')">Sign Up</button>
</div>

<div id="Login" class="tabcontent">
${message}
  <form:form name="submitForm" method="POST">
        <div align="center">
            <table>
                <tr>
                    <td>User Name</td>
                    <td><input type="text" name="userName" placeholder="username"/></td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td><input type="password" name="password" placeholder="password"/></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" value="Submit" name="submit"/> <button type="reset" value="Reset">Reset</button></td>
                </tr>
            </table>
            <div style="color: red">${error}</div>
        </div>
   </form:form>
</div>

<div id="Signup" class="tabcontent">
   <form:form name="submitForm" method="POST">
        <div align="center">
            <table>
                <tr>
                    <td>User Name</td>
                    <td><input type="text" name="userName" placeholder="Currently disabled"/></td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td><input type="password" name="password" placeholder="Currently disabled"/></td>
                </tr>
                <tr>
                    <td>Repeat Password</td>
                    <td><input type="repeatPassword" name="repeatPassword" placeholder="Currently disabled"/></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" value="Submit" name="signup"/> <button type="reset" value="Reset">Reset</button></td>
                </tr>
            </table>
            <div style="color: red">${error}</div>
        </div>
   </form:form>
</div>


<script>
	//Get the element with id="defaultOpen" and click on it
	document.getElementById("defaultOpen").click();
</script>
</main>
<%@include file="jspSections/footer.jsp" %>

