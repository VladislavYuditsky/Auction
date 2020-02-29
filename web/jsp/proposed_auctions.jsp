<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="auctions" class="java.util.ArrayList" scope="request"/>

<%@include file="/jsp/parts/common.jsp" %>

<html>
<head>
    <title>Auction</title>
</head>
<body>
<jsp:include page="parts/navbar.jsp"/>

<div class="container mt-3">
    <c:choose>
        <c:when test="${auctions.size()!=0}">
            <c:forEach var="auction" items="${auctions}">
                <div class="card mb-3" >
                    <div class="card-body">
                        <a href="${pageContext.request.contextPath}/auction?lotId=${auction.lotId}"><h1>${auction.id}</h1></a>
                    </div>
                </div>
            </c:forEach>

            <c:set var="servletPath" value="/proposed_auctions"/>
            <%@include file="parts/pagination.jsp"%>
        </c:when>

        <c:otherwise>
            <fmt:message key="auctions.not_proposed" bundle="${pc}"/>
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="/jsp/parts/footer.jsp"/>
</body>
</html>
