<%--
  Created by IntelliJ IDEA.
  User: JFresh
  Date: 30.01.2020
  Time: 13:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="payments" class="java.util.ArrayList" scope="request"/>
<html>
<head>
    <jsp:include page="bootstrapHeader.jsp"/>
    <title>Auction</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<jsp:include page="paymentsList.jsp"/>
<c:if test="${payments.size()==0}">
    No payments
</c:if>
<jsp:include page="bootstrapFooter.jsp"/>
<jsp:include page="bootstrapFooter.jsp"/>
</body>
</html>