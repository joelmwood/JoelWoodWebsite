/**
 * 
 */
function checkPasswordsMatch(){
	var pass1 = document.getElementById("newPassword").value;
	var pass2 = document.getElementById("confirmPassword").value;
	
	if(pass1 != pass2){
		document.getElementById("passwordsMatch").innerHTML = "Passwords do not Match.";
		document.getElementById("newPassword").style.borderColor = "red";
		document.getElementById("confirmPassword").style.borderColor = "red";
		document.getElementById("submitButton").disabled = true;
	}else{
		document.getElementById("passwordsMatch").innerHTML = "";
		document.getElementById("newPassword").style.borderColor = "";
		document.getElementById("confirmPassword").style.borderColor = "";
		document.getElementById("submitButton").disabled = false;
	}
}

function resetForm(){
	document.getElementById("submitForm").reset();
    document.getElementById("signupForm").reset();
}

function openCity(evt, cityName) {
		
    // Declare all variables
    var i, tabcontent, tablinks;

    // Get all elements with class="tabcontent" and hide them
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }

    // Get all elements with class="tablinks" and remove the class "active"
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }

    // Show the current tab, and add an "active" class to the button that opened the tab
   
    
    document.getElementById(cityName).style.display = "block";
    evt.currentTarget.className += " active";
}





