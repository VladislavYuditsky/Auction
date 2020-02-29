<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@include file="/jsp/parts/common.jsp" %>

<html>
<head>
    <title>Auction</title>
</head>
<body>
<jsp:include page="parts/navbar.jsp"/>

<div class="container mt-3">
<form id="form" method="post" action="${pageContext.request.contextPath}/create_user">
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">
            <fmt:message key="user.login" bundle="${pc}"/>
        </label>
        <div class="col-sm-6">
            <input type="text" name="login" id="login" value="${requestScope.login}" class="form-control" required onchange="document.getElementById('loginError').style.display='none'"/>
            <div id="loginError" style="display: none" class="invalid-feedback" ><fmt:message key="message.invalid_login" bundle="${pc}"/></div>
            <div class="text-danger col-sm-2"><small><c:if test="${requestScope.message != null}">
                <fmt:message key="${requestScope.message}" bundle="${pc}"/>
            </c:if></small></div>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">
            <fmt:message key="user.password" bundle="${pc}"/>
        </label>
        <div class="col-sm-6">
            <input type="password" name="password" id="password" value="${requestScope.password}" class="form-control" required onchange="document.getElementById('passwordError').style.display='none'"/>
            <div id="passwordError" style="display: none" class="invalid-feedback" ><fmt:message key="message.invalid_password" bundle="${pc}"/></div>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">
            <fmt:message key="user.password.conf" bundle="${pc}"/>
        </label>
        <div class="col-sm-6">
            <input type="password" name="confPassword" id="confPassword" value="${requestScope.password}" class="form-control" required onchange="document.getElementById('passwordsNotMatch').style.display='none'"/>
            <div id="passwordsNotMatch" style="display: none" class="invalid-feedback" ><fmt:message key="message.passwords_not_match" bundle="${pc}"/></div>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">
            <fmt:message key="user.email" bundle="${pc}"/>
        </label>
        <div class="col-sm-6">
            <input type="email" name="email" id="email" value="${requestScope.email}" class="form-control" required onchange="document.getElementById('emailError').style.display='none'"/>
            <div id="emailError" style="display: none" class="invalid-feedback" ><fmt:message key="message.invalid_email" bundle="${pc}"/></div>
        </div>
    </div>
    <button type="submit" class="btn btn-dark">
        <fmt:message key="button.sign_up" bundle="${pc}"/>
    </button>
</form>
</div>
<jsp:include page="/jsp/parts/footer.jsp"/>
</body>
</html>

<script type="module" src="${pageContext.request.contextPath}/js/signUpDataValidator.js"></script>
