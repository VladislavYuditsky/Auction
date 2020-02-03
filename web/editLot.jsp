<%--
  Created by IntelliJ IDEA.
  User: JFresh
  Date: 30.01.2020
  Time: 1:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="lot" scope="request" type="com.yuditsky.auction.entity.Lot"/>
<html>
<head>
    <jsp:include page="bootstrapHeader.jsp"/>
    <title>Title</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<form method="post" action="${pageContext.request.contextPath}/edit_lot?lotId=${lot.id}">
    <label>
        Name:
        <input type="text" name="name" value="${lot.name}"/>
    </label>
    <br>
    <label>
        Description:
        <input type="text" name="description" value="${lot.description}"/>
    </label>
    <br>
    <label>
        Location:
        <input type="text" name="location" value="${lot.location}"/>
    </label>
    <br>
    <label>
        Start price:
        <input type="text" name="startPrice" value="${lot.startPrice}"/>
    </label>
    <br>
    <br>
    <button type="submit">Save</button>
</form>
<jsp:include page="bootstrapFooter.jsp"/>
</body>
</html>
