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
	
  <button class="tablinks" onclick="openCity(event, 'Login');resetForm();" id="defaultOpen">Log In</button>
  <button class="tablinks" onclick="openCity(event, 'Signup');resetForm();">Sign Up</button>
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
                    <td>Password</td>
                    <td><input type="password" name="password" placeholder="password" required/></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" value="Submit" name="submit"/> <button type="reset" value="Reset" id="resetLogin">Reset</button></td>
                </tr>
            </table>
            <div id="forgotPasswordLink" ><a href="requestPasswordReset" >Forgot Password?</a></div>
            <div style="color: red">${error}</div>
        </div>
   </form:form>
</div>

<div id="Signup" class="tabcontent">
   <form:form name="submitForm" id="signupForm" method="POST" onsubmit="return checkPasswordsMatch()">
        <div align="center">
            <table>
                <tr>
                    <td>Email</td>
                    <td><input type="email" name="userName" placeholder="janedoe@email.com" required/></td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td><input id="newPassword" type="password" name="password"  placeholder="password" required pattern="^([a-zA-Z0-9!@#$%^&*]{8,20})$"  title="One Uppercase character, one lowercase character, one of these special characters (i.e. !@#$%^&*) and one number." "/></td>
                </tr>
                <tr>
                    <td>Repeat Password</td>
                    <td><input id="confirmPassword" type="password" name="repeatPassword" placeholder="repeat password" required pattern="^([a-zA-Z0-9!@#$%^&*]{8,20})$" title="One Uppercase character, one lowercase character, one of these special characters (i.e. !@#$%^&*) and one number." onkeyup="checkPasswordsMatch()"/>
                    	<br/><span id="passwordsMatch" style="color:red; font-size:12"></span>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td><input id="submitButton" type="submit" value="Submit" name="signup"/> <button type="reset" value="Reset" id="resetSignup">Reset</button></td>
                </tr>
            </table>
            
            <p id="passwordsMatch"></p>
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

