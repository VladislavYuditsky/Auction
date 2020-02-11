<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pageContent" var="pc"/>

<jsp:useBean id="payments" class="java.util.ArrayList" scope="request"/>

<html>
<head>
    <jsp:include page="bootstrap_header.jsp"/>
    <title>Auction</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<jsp:include page="payments_list.jsp"/>
<c:if test="${payments.size()==0}">
    <fmt:message key="payments.no_payments" bundle="${pc}"/>
</c:if>
<jsp:include page="bootstrap_footer.jsp"/>
</body>
</html>