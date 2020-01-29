<%--
  Created by IntelliJ IDEA.
  User: JFresh
  Date: 30.01.2020
  Time: 1:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="bootstrapHeader.jsp"/>
    <title>Title</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<form method="post" action="${pageContext.request.contextPath}/add_new_lot">
    <label>
        Name:
        <input type="text" name="name"/>
    </label>
    <br>
    <label>
        Description:
        <input type="text" name="description"/>
    </label>
    <br>
    <label>
        Location:
        <input type="text" name="location"/>
    </label>
    <br>
    <label>
        Start price:
        <input type="text" name="startPrice"/>
    </label>
    <br>
    <label>
        Min price:
        <input type="text" name="minPrice"/>
    </label>
    <br>
    <button type="submit">Save</button>
</form>
<jsp:include page="bootstrapFooter.jsp"/>
</body>
</html>
