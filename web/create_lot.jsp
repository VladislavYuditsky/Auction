<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.locale}" scope="session"/>
    <fmt:setBundle basename="pageContent" var="pc" />

    <jsp:include page="bootstrap_header.jsp"/>
    <title>Auction</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<form method="post" action="${pageContext.request.contextPath}/create_lot">
    <label>
        <fmt:message key="lot.name" bundle="${pc}"/>
        <input type="text" name="name"/>
    </label>
    <br>
    <label>
        <fmt:message key="lot.description" bundle="${pc}"/>
        <input type="text" name="description"/>
    </label>
    <br>
    <label>
        <fmt:message key="lot.location" bundle="${pc}"/>
        <input type="text" name="location"/>
    </label>
    <br>
    <label>
        <fmt:message key="lot.start_price" bundle="${pc}"/>
        <input type="text" name="startPrice"/>
    </label>
    <br>
    <br>
    <button type="submit">
        <fmt:message key="button.save" bundle="${pc}"/>
    </button>
</form>
<jsp:include page="bootstrap_footer.jsp"/>
</body>
</html>
