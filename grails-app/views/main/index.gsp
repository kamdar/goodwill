<%@ page contentType="text/html;charset=ISO-8859-1" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
<meta name="layout" content="main"/>
<title>Insert title here</title>
</head>
<body>
  <div class="body">
    Hello how are you ${myText} <p>
    
    <g:form controller="search" action="showResults">
    <p>
    <g:textField name="searchField"/><p>
    <button type="submit" class="bluebutton"><span><span>SEARCH</span></span></button>
    </g:form>
    
    <g:link class="mylink" controller="search" action="showMySearches">my searches</g:link>
    
  </div>
</body>
</html>