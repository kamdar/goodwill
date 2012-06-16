
<%@ page import="goodwill.Search" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'search.label', default: 'Search')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-search" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-search" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="activeState" title="${message(code: 'search.activeState.label', default: 'Active State')}" />
					
						<g:sortableColumn property="emailSummary" title="${message(code: 'search.emailSummary.label', default: 'Email Summary')}" />
					
						<g:sortableColumn property="searchString" title="${message(code: 'search.searchString.label', default: 'Search String')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${searchInstanceList}" status="i" var="searchInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${searchInstance.id}">${fieldValue(bean: searchInstance, field: "activeState")}</g:link></td>
					
						<td><g:formatBoolean boolean="${searchInstance.emailSummary}" /></td>
					
						<td>${fieldValue(bean: searchInstance, field: "searchString")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${searchInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
