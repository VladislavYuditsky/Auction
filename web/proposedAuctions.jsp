<%--
  Created by IntelliJ IDEA.
  User: JFresh
  Date: 29.01.2020
  Time: 4:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="auctions" class="java.util.ArrayList" scope="request"/>
<html>
<head>
    <jsp:include page="bootstrapHeader.jsp"/>
    <title>Title</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>

<c:if test="${auctions.size()!=0}">
    <c:forEach var="auction" items="${auctions}">
        <div class="card mb-3" >
            <div class="card-body">
                <a href="${pageContext.request.contextPath}/auction?lotId=${auction.lotId}"><h1>${auction.id}</h1></a>
            </div>
        </div>
    </c:forEach>
</c:if>

<c:if test="${auctions.size()==0}">
    Auctions are not proposed
</c:if>
<jsp:include page="bootstrapFooter.jsp"/>
</body>
</html>
