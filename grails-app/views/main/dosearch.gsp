<%@ page contentType="text/html;charset=ISO-8859-1" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
<meta name="layout" content="main"/>
<title>search result</title>
</head>
<body>
Doing the result
  <div class="body">
  <p><p>
  <% myIDList.each { row -> %>
    <p>${row.th[0].text()}
	<img align="absmiddle" src="${row.th[1].img.@src}"/>
	<br>
	<% } %>
  </div>
</body>

</html>