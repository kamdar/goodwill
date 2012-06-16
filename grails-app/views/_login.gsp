            <div id="loginBox" class="loginBox">
            <g:if test="${session?.user}">
            <div style="margin-top:20px">
            <div style="float:right;">
            <a href="#">Profile</a> |
            <g:link controller="user" action="logout">Logout</g:link><br>
            </div>
            Welcome back
            <span id="userFirstName">${session?.user?.firstName}!</span>
            <br><br>
            You have purchased (${session.user.purchasedSongs?.size() ?: 0}) songs.<br>
            </div>
            </g:if>
            <g:else>
            <g:form
            name="loginForm"
            url="[controller:'user',action:'login']">
            <div>Username:</div>
            <g:textField name="login" ></g:textField>
            <div>Password:</div>
            <g:passwordField name="password" />
            <input class="mylink" type="submit" value="Please Login" />
            </g:form>
            <g:renderErrors bean="${loginCmd}"></g:renderErrors>
            </g:else>
            </div>
