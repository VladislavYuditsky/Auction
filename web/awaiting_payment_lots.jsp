<%--
  Created by IntelliJ IDEA.
  User: JFresh
  Date: 30.01.2020
  Time: 13:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="lots" class="java.util.ArrayList" scope="request"/>
<html>
<head>
    <fmt:setLocale value="${sessionScope.locale}" scope="session"/>
    <fmt:setBundle basename="pageContent" var="pc" />

    <jsp:include page="bootstrap_header.jsp"/>
    <title>Auction</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<c:if test="${lots.size()!=0}">
    <c:forEach var="lot" items="${lots}">
        <div class="card mb-3" >
            <div class="card-body">
                <a href="${pageContext.request.contextPath}/auction?lotId=${lot.id}"><h5>${lot.name}</h5></a>
                <p class="card-text">${lot.description}</p>
            </div>
        </div>
    </c:forEach>
</c:if>
<c:if test="${lots.size()==0}">
    <fmt:message key="lots.no_lots" bundle="${pc}"/>
</c:if>
<jsp:include page="bootstrap_footer.jsp"/>
</body>
</html>