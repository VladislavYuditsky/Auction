<%--
  Created by IntelliJ IDEA.
  User: JFresh
  Date: 30.01.2020
  Time: 13:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <jsp:include page="bootstrapHeader.jsp"/>
    <title>Auction</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>

<div class="container mt-2">
    <form method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Balance: ${balance}</label>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Replenish sum: </label>
            <div class="col-sm-6">
                <input type="text" name="replenishSum"/>
            </div>
        </div>
        <button type="submit" class="btn btn-dark">Replenish</button>
    </form>
</div>

<jsp:include page="bootstrapFooter.jsp"/>
<jsp:include page="bootstrapFooter.jsp"/>
</body>
</html>