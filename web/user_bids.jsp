<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pageContent" var="pc"/>

<jsp:useBean id="lots" class="java.util.ArrayList" scope="request"/>

<html>
<head>
    <jsp:include page="bootstrap_header.jsp"/>
    <title>Auction</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<jsp:include page="auctions_list.jsp"/>
<c:if test="${lots.size()==0}">
    <fmt:message key="bids.no_bids" bundle="${pc}"/>
</c:if>
<jsp:include page="bootstrap_footer.jsp"/>
</body>
</html>