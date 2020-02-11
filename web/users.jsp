<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pageContent" var="pc"/>

<jsp:useBean id="users" class="java.util.ArrayList" scope="request"/>

<html>
<head>
    <jsp:include page="bootstrap_header.jsp"/>
    <title>Auction</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<jsp:include page="users_list.jsp"/>
<c:if test="${users.size()==0}">
    <fmt:message key="users.no_users" bundle="${pc}"/>
</c:if>
<jsp:include page="bootstrap_footer.jsp"/>
</body>
</html>