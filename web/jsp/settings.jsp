<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@include file="/jsp/parts/common.jsp" %>

<html>
<head>
    <title>Auction</title>
</head>
<body>
<jsp:include page="parts/navbar.jsp"/>

<div class="container mt-3">
    <form method="post" id="form" action="${pageContext.request.contextPath}/update_settings">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">
                <fmt:message key="user.password" bundle="${pc}"/>
            </label>
            <div class="col-sm-6">
                <input type="password" name="password" id="password" class="form-control" required onchange="document.getElementById('passwordError').style.display='none'"/>
                <div id="passwordError" style="display: none" class="invalid-feedback" ><fmt:message key="message.invalid_password" bundle="${pc}"/></div>
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
            <fmt:message key="button.save" bundle="${pc}"/>
        </button>
    </form>
</div>

<script src="${pageContext.request.contextPath}/js/settingsDataValidator.js"></script>
<jsp:include page="/jsp/parts/footer.jsp"/>
</body>
</html>