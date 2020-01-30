<%--
  Created by IntelliJ IDEA.
  User: JFresh
  Date: 30.01.2020
  Time: 13:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="lots" class="java.util.ArrayList" scope="request"/>
<html>
<head>
    <jsp:include page="bootstrapHeader.jsp"/>
    <title>Auction</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<c:if test="${lots.size()!=0}">
    <c:forEach var="lot" items="${lots}">
        <div class="card mb-3" >
            <div class="card-body">
                <h5>${lot.name}</h5>
                <p class="card-text">
                        ${lot.description}
                        <form method="post" action="${pageContext.request.contextPath}/new_payment?lotId=${lot.id}">
                            <button type="submit">Pay</button>
                        </form>
                </p>
               <%-- <p class="card-text"><small class="text-muted">${lot.location}</small></p>--%>
            </div>
        </div>
    </c:forEach>
</c:if>
<c:if test="${lots.size()==0}">
    No lots
</c:if>
<jsp:include page="bootstrapFooter.jsp"/>
<jsp:include page="bootstrapFooter.jsp"/>
</body>
</html>