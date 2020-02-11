<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="lot" scope="request" type="com.yuditsky.auction.entity.Lot"/>
<html>
<head>
    <fmt:setLocale value="${sessionScope.locale}" scope="session"/>
    <fmt:setBundle basename="pageContent" var="pc"/>

    <jsp:include page="bootstrap_header.jsp"/>
    <title>Auction</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<form method="post" action="${pageContext.request.contextPath}/edit_lot?lotId=${lot.id}">
    <label>
        <fmt:message key="lot.name" bundle="${pc}"/>
        <input type="text" name="name" value="${lot.name}"/>
    </label>
    <br>
    <label>
        <fmt:message key="lot.description" bundle="${pc}"/>
        <input type="text" name="description" value="${lot.description}"/>
    </label>
    <br>
    <label>
        <fmt:message key="lot.location" bundle="${pc}"/>
        <input type="text" name="location" value="${lot.location}"/>
    </label>
    <br>
    <label>
        <fmt:message key="lot.start_price" bundle="${pc}"/>
        <input type="text" name="startPrice" value="${lot.startPrice}"/>
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
