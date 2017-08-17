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

<main>
<div id="LoginTab" >
<div class="tab">
	
  <button class="tablinks" onclick="openCity(event, 'Login');resetForm();" id="defaultOpen">Change/Update Password</button>
  
</div>

<div id="Login" class="tabcontent">
${message}
  <form:form name="submitForm" id="submitForm" method="POST">
        <div align="center">
            <table>
            	<tr>
                    <td>Email</td>
                    <td><input type="email" name="userName" placeholder="janedoe@email.com" required/></td>
                </tr>
            	<tr>
                    <td>Old Password</td>
                    <td><input id="oldPassword" type="password" name="oldPassword"  placeholder="Old Password" required pattern="^([a-zA-Z0-9!@#$%^&*]{8,20})$"  title="One Uppercase character, one lowercase character, one of these special characters (i.e. !@#$%^&*) and one number."/></td>
                </tr>
                <tr>
                    <td>New Password</td>
                    <td><input id="newPassword" type="password" name="newPassword"  placeholder="New Password" required pattern="^([a-zA-Z0-9!@#$%^&*]{8,20})$"  title="One Uppercase character, one lowercase character, one of these special characters (i.e. !@#$%^&*) and one number."/></td>
                </tr>
                <tr>
                    <td>Repeat New Password</td>
                    <td><input id="confirmPassword" type="password" name="repeatPassword" placeholder="Repeat New password" required pattern="^([a-zA-Z0-9!@#$%^&*]{8,20})$" title="One Uppercase character, one lowercase character, one of these special characters (i.e. !@#$%^&*) and one number." onkeyup="checkPasswordsMatch()"/>
                    	<br/><span id="passwordsMatch" style="color:red; font-size:12"></span>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td><input id="submitButton" type="submit" value="Submit" name="changePassword"/> <button type="reset" value="Reset" id="resetLogin">Reset</button></td>
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

</div>
${table}
</main>
<%@include file="jspSections/footer.jsp" %>

