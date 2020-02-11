<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pageContent" var="pc"/>

<html>
<head>
    <jsp:include page="bootstrap_header.jsp"/>
    <title>Auction</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<div class="container mt-2">
    <form method="post" action="${pageContext.request.contextPath}/update_settings">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">
                <fmt:message key="user.password" bundle="${pc}"/>
            </label>
            <div class="col-sm-6">
                <input type="password" name="password" class="form-control" placeholder="Password"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">
                <fmt:message key="user.email" bundle="${pc}"/>
            </label>
            <div class="col-sm-6">
                <input type="email" name="email" class="form-control" placeholder="Email" value="${email}"/>
            </div>
        </div>
        <button type="submit" class="btn btn-dark">
            <fmt:message key="button.save" bundle="${pc}"/>
        </button>
    </form>
</div>
<jsp:include page="bootstrap_footer.jsp"/>
</body>
</html>