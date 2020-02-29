<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="lots" class="java.util.ArrayList" scope="request"/>

<%@include file="/jsp/parts/common.jsp" %>

<html>
<head>
    <title>Auction</title>
</head>
<body>
<jsp:include page="parts/navbar.jsp"/>

<div class="container mt-3">
    <c:choose>
        <c:when test="${lots.size()==0}">
            <fmt:message key="lots.no_lots" bundle="${pc}"/>
        </c:when>

        <c:otherwise>
            <jsp:include page="parts/lots_list.jsp"/>

            <c:set var="servletPath" value="/user_lots"/>
            <%@include file="parts/pagination.jsp" %>
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="/jsp/parts/footer.jsp"/>
</body>
</html>