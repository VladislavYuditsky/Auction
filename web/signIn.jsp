<%--
  Created by IntelliJ IDEA.
  User: JFresh
  Date: 27.01.2020
  Time: 15:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="bootstrapHeader.jsp"/>
    <title>Auction</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
    <form method="post" action="${pageContext.request.contextPath}/sign_in">
        <label>
            Login:
            <input type="text" name="login"/>
        </label>
        <br>
        <label>
            Password:
            <input type="password" name="password"/>
        </label>
        <br>
        <button type="submit">Sign In</button>
    </form>
    <jsp:include page="bootstrapFooter.jsp"/>
</body>
</html>
