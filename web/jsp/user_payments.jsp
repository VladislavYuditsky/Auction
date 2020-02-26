<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="payments" class="java.util.ArrayList" scope="request"/>

<%@include file="/jsp/parts/common.jsp" %>

<html>
<head>
    <title>Auction</title>
</head>
<body>
<jsp:include page="parts/navbar.jsp"/>

<div class="container mt-3">
<jsp:include page="parts/payments_list.jsp"/>
<c:if test="${payments.size()==0}">
    <fmt:message key="payments.no_payments" bundle="${pc}"/>
</c:if>
</div>

<jsp:include page="/jsp/parts/footer.jsp"/>
</body>
</html>