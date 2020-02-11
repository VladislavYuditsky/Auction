<%--
  Created by IntelliJ IDEA.
  User: JFresh
  Date: 29.01.2020
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="cust" uri="http://auction.yuditsky.com" %>

<html>
<head>
    <fmt:setLocale value="${sessionScope.locale}" scope="session"/>
    <fmt:setBundle basename="pageContent" var="pc" />

    <jsp:include page="bootstrap_header.jsp"/>
    <title>Auction</title>
</head>
<body>
    <jsp:include page="navbar.jsp"/>
    <cust:GreetingTag/>
    <div class="container">
            <div class="col-md-12 row">
                <div class="auction-type col-md-12 col-md-6 row">
                    <h2><b><fmt:message key="greeting.directAuction" bundle="${pc}"/></b></h2>
                    <div class="about">
                        <fmt:message key="greeting.aboutDirectAuction" bundle="${pc}"/>
                    </div>
                    <br/>
                </div>
                <div class="auction-type col-md-12 col-md-6 row">
                    <h2><b><fmt:message key="greeting.reverseAuction" bundle="${pc}"/></b></h2>
                    <div class="about"><fmt:message key="greeting.aboutReverseAuction" bundle="${pc}"/></div>
                    <br/>
                </div>
            </div>
    </div>

<jsp:include page="bootstrap_footer.jsp"/>
</body>
</html>
