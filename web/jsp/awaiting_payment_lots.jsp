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
    <c:if test="${lots.size()!=0}">
        <c:forEach var="lot" items="${lots}">
            <div class="card mb-3">
                <div class="card-body">
                    <a href="${pageContext.request.contextPath}/auction?lotId=${lot.id}"><h5>${lot.name}</h5></a>
                    <p class="card-text">${lot.description}</p>
                </div>
            </div>
        </c:forEach>

        <c:set var="servletPath" value="/awaiting_payment_lots"/>
        <%@include file="parts/pagination.jsp"%>
    </c:if>
    <c:if test="${lots.size()==0}">
        <fmt:message key="lots.no_lots" bundle="${pc}"/>
    </c:if>
</div>

<jsp:include page="/jsp/parts/footer.jsp"/>
</body>
</html>