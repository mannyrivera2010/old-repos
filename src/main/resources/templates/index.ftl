<html>
	<head>
	</head>
	<body>
		FreeMarker Hello World Example : ${message} <br>
		
		<#list languages as language>
		    ${language_index + 1}. ${language} <br>
		</#list>
	</body>
</html>