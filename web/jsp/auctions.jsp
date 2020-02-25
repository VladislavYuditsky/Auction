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
    <jsp:include page="parts/auctions_list.jsp"/>

    <c:if test="${lots.size()==0}">
        <fmt:message key="auctions.no_auctions" bundle="${pc}"/>
    </c:if>
</div>

</body>
</html>
