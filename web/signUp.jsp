<%--
  Created by IntelliJ IDEA.
  User: JFresh
  Date: 28.01.2020
  Time: 15:51
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
<form method="post" action="${pageContext.request.contextPath}/sign_up">
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
    <label>
        Conf Password:
        <input type="password" name="confPassword"/>
    </label>
    <br>
    <label>
        Email:
        <input type="email" name="email"/>
    </label>
    <br>
    <button type="submit">Sign Up</button>
</form>
<jsp:include page="bootstrapFooter.jsp"></jsp:include>
</body>
</html>
