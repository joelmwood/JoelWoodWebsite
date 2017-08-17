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
	
  <button class="tablinks" onclick="openCity(event, 'Login');resetForm();" id="defaultOpen">Forgot Password</button>
  
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
                    <td></td>
                    <td><input type="submit" value="Submit" name="resetPassword"/> <button type="reset" value="Reset" id="resetLogin">Reset</button></td>
                </tr>
            </table>
            <p>An email will be sent to the entered email address with a randomly generated, one-time-use password. A new password can and will be set before logging in.</p>
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

