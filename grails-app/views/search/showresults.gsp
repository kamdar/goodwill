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
    <table>
	    <thead>
	        <tr>
	            <td>ID</td>
                <td>Img</td>
	            <td>Desc</td>
	        </tr>
	    </thead>
	    <tbody>
        <g:each in="${myIDList}" status="i" var="row">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                <td width="5%">${row.th[0].text()}</td>
                <td width="5%"><img align="absleft" src="${row.th[1].img.@src}"/></td>
                <td width="50%" align="left">${row.th[1].a.text()}</td>
            </tr>
        </g:each> 
    	</tbody>
    </table>
  </div>
</body>

</html>