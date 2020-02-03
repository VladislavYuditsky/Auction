<%--
  Created by IntelliJ IDEA.
  User: JFresh
  Date: 29.01.2020
  Time: 4:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="lots" class="java.util.ArrayList" scope="request"/>
<html>
<head>
    <jsp:include page="bootstrapHeader.jsp"/>
    <title>Title</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<jsp:include page="auctionsList.jsp"/>
<c:if test="${lots.size()==0}">
    Auctions are not currently running
</c:if>
<jsp:include page="bootstrapFooter.jsp"/>
</body>
</html>