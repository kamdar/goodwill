<%@ page contentType="text/html;charset=ISO-8859-1" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
<meta name="layout" content="main"/>
<title>search result</title>
</head>
<body>

  <g:render contextPath=".." template="login"/>

  <div class="body">
  <p><p>
        My searches are ${mysearches}  
  </div>
  
  


<form action="https://www.shopgoodwill.com/buyers/dologin.asp" method="post">
<table>
<tbody>
<tr>
<td class="boldme">
<label for="buyerid">Buyer </label>
</td>
<td>
<input type="text" name="buyerid">
</td>
</tr>

<tr>
<td class="boldme">
<label for="buyerpasswd">Buyer Password</label>
</td>
<td>
<input type="password" name="buyerpasswd">
</td>

</tr>

<tr>
<td class="boldme">&nbsp;</td>
<td>
<input type="submit" value="Sign In" name="submit">
</td>
</tr>

</tbody>
</table>

</form>



  
</body>

</html>