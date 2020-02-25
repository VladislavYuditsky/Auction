<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@include file="/jsp/parts/common.jsp" %>

<html>
<head>
    <title>Auction</title>
</head>
<body>
<jsp:include page="parts/navbar.jsp"/>

<div class="container mt-3">
    <form id="form" method="post" action="${pageContext.request.contextPath}/sign_in">

        <div class="form-group row">
            <label class="col-sm-2 col-form-label">
                <fmt:message key="user.login" bundle="${pc}"/>
            </label>
            <div class="col-sm-6">
                <input type="text" name="login" id="login" class="form-control" value="${requestScope.login}" required  onchange="document.getElementById('loginError').style.display='none'"/>
                <div id="loginError" style="display: none" class="invalid-feedback" ><fmt:message key="message.invalid_login" bundle="${pc}"/></div>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">
                <fmt:message key="user.password" bundle="${pc}"/>
            </label>
            <div class="col-sm-6">
                <input type="password" name="password" id="password" class="form-control" value="${requestScope.password}" required onchange="document.getElementById('passwordError').style.display='none'"/>
                <div id="passwordError" style="display: none" class="invalid-feedback" ><fmt:message key="message.invalid_password" bundle="${pc}"/></div>
            </div>
        </div>
        <div class="form-group row">
            <div class="col-sm-2">
                <div class="text-danger"><small>
                    <c:if test="${requestScope.message != null}">
                        <fmt:message key="${requestScope.message}" bundle="${pc}"/>
                    </c:if>
                </small></div>
            </div>
        </div>
        <button type="submit" class="btn btn-dark">
            <fmt:message key="button.sign_in" bundle="${pc}"/>
        </button>
    </form>
</div>
<script src="${pageContext.request.contextPath}/js/signInDataValidator.js"></script>
</body>
</html>
