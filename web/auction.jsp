<jsp:useBean id="lot" scope="request" type="com.yuditsky.auction.entity.Lot"/>
<jsp:useBean id="maxBid" scope="request" type="com.yuditsky.auction.entity.Bid"/>
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
    <form method="post" action="${pageContext.request.contextPath}/auction">
        <div>
            <label>${lot.name}</label>
        </div>
        <div>
            <label>${lot.description}</label>
        </div>
        <div>
            <label>Location: ${lot.location}</label>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Current price: ${maxBid.sum}</label>
            <div class="col-sm-6">
                <input type="text" name="bidSum" class="form-control"/>
                <input type="hidden" name="lotId" value="${lot.id}"/>
            </div>
            <div class="col-sm-6">
                <button type="submit" class="btn btn-dark">Bid</button>
            </div>
        </div>
    </form>
</div>

<jsp:include page="bootstrapFooter.jsp"/>
<jsp:include page="bootstrapFooter.jsp"/>
</body>
</html>