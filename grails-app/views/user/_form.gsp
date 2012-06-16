<%@ page import="goodwill.User" %>



<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'userName', 'error')} ">
	<label for="userName">
		<g:message code="user.userName.label" default="User Name" />
		
	</label>
	<g:textField name="userName" value="${userInstance?.userName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'lastLoggedin', 'error')} required">
	<label for="lastLoggedin">
		<g:message code="user.lastLoggedin.label" default="Last Loggedin" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="lastLoggedin" precision="day"  value="${userInstance?.lastLoggedin}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'searches', 'error')} ">
	<label for="searches">
		<g:message code="user.searches.label" default="Searches" />
		
	</label>
	<g:select name="searches" from="${goodwill.Search.list()}" multiple="multiple" optionKey="id" size="5" value="${userInstance?.searches*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'userEmail', 'error')} ">
	<label for="userEmail">
		<g:message code="user.userEmail.label" default="User Email" />
		
	</label>
	<g:textField name="userEmail" value="${userInstance?.userEmail}"/>
</div>

