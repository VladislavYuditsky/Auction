<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pageContent" var="pc"/>

<jsp:useBean id="auctions" class="java.util.ArrayList" scope="request"/>

<html>
<head>
    <jsp:include page="bootstrap_header.jsp"/>
    <title>Auction</title>
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
    <fmt:message key="auctions.not_proposed" bundle="${pc}"/>
</c:if>
<jsp:include page="bootstrap_footer.jsp"/>
</body>
</html>
