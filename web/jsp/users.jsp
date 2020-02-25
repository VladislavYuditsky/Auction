<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="users" class="java.util.ArrayList" scope="request"/>

<%@include file="/jsp/parts/common.jsp" %>

<html>
<head>
    <title>Auction</title>
</head>
<body>
<jsp:include page="parts/navbar.jsp"/>

<div class="container mt-3">
    <jsp:include page="parts/users_list.jsp"/>
    <c:if test="${users.size()==0}">
        <fmt:message key="users.no_users" bundle="${pc}"/>
    </c:if>
</div>

</body>
</html>