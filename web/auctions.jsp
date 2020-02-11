<%--
  Created by IntelliJ IDEA.
  User: JFresh
  Date: 29.01.2020
  Time: 4:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="lots" class="java.util.ArrayList" scope="request"/>

<%@ taglib prefix="cust" uri="http://auction.yuditsky.com" %>

<html>
<head>
    <fmt:setLocale value="${sessionScope.locale}" scope="session"/>
    <fmt:setBundle basename="pageContent" var="pc" />


    <jsp:include page="bootstrap_header.jsp"/>
    <title>Auction</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<jsp:include page="auctions_list.jsp"/>
<c:if test="${lots.size()==0}">
    <fmt:message key="auctions.no_auctions" bundle="${pc}"/>
</c:if>
<%--<cust:FooterTag/>--%>
<jsp:include page="bootstrap_footer.jsp"/>
</body>
</html>
