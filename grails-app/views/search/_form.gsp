<%@ page import="goodwill.Search" %>



<div class="fieldcontain ${hasErrors(bean: searchInstance, field: 'activeState', 'error')} ">
	<label for="activeState">
		<g:message code="search.activeState.label" default="Active State" />
		
	</label>
	<g:checkBox name="activeState" value="${searchInstance?.activeState}" />
</div>

<div class="fieldcontain ${hasErrors(bean: searchInstance, field: 'emailSummary', 'error')} ">
	<label for="emailSummary">
		<g:message code="search.emailSummary.label" default="Email Summary" />
		
	</label>
	<g:checkBox name="emailSummary" value="${searchInstance?.emailSummary}" />
</div>

<div class="fieldcontain ${hasErrors(bean: searchInstance, field: 'searchString', 'error')} ">
	<label for="searchString">
		<g:message code="search.searchString.label" default="Search String" />
		
	</label>
	<g:textField name="searchString" value="${searchInstance?.searchString}"/>
</div>

