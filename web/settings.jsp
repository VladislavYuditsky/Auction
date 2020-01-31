<%--
  Created by IntelliJ IDEA.
  User: JFresh
  Date: 30.01.2020
  Time: 13:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="lots" class="java.util.ArrayList" scope="request"/>
<html>
<head>
    <jsp:include page="bootstrapHeader.jsp"/>
    <title>Auction</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<div class="container mt-5">
    <form method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Password :</label>
            <div class="col-sm-6">
                <input type="password" name="password" class="form-control" placeholder="Password"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Email :</label>
            <div class="col-sm-6">
                <input type="email" name="email" class="form-control" placeholder="Email" value="${email}"/>
            </div>
        </div>
        <button type="submit" class="btn btn-dark">Save</button>
    </form>
</div>
<jsp:include page="bootstrapFooter.jsp"/>
</body>
</html>