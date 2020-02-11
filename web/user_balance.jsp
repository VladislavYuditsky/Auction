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
    <form method="post" action="${pageContext.request.contextPath}/replenish_balance">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">
                <fmt:message key="user.balance" bundle="${pc}"/> ${balance}
            </label>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">
                <fmt:message key="user.balance.replenish_sum" bundle="${pc}"/>
            </label>
            <div class="col-sm-6">
                <input type="text" name="replenishSum"/>
            </div>
        </div>
        <button type="submit" class="btn btn-dark">
            <fmt:message key="button.replenish" bundle="${pc}"/>
        </button>
    </form>
</div>

<jsp:include page="bootstrap_footer.jsp"/>
</body>
</html>