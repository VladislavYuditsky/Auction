<jsp:useBean id="lot" scope="request" type="com.yuditsky.auction.entity.Lot"/>
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
    <div>
        <label>
            Check all indicated data! For non-normative language and other offensive content,
            you will be blocked by the administrator!
        </label>
    </div>
    <div>
        <label>${lot.name}</label>
    </div>
    <div>
        <label>${lot.description}</label>
    </div>
    <div>
        <label>Location: ${lot.location}</label>
    </div>
    <div>
        <label>Start price: ${lot.startPrice}</label>
    </div>

    <form method="post" action="${pageContext.request.contextPath}/create_auction?lotId=${lot.id}">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">
                Choose auction type:
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="auctionType" id="direct" value="direct" checked>
                    <label class="form-check-label" for="direct">
                        Direct
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="auctionType" id="revers" value="revers">
                    <label class="form-check-label" for="revers">
                        Revers
                    </label>
                </div>
            </label>
            <button type="submit" class="btn btn-dark">Put up</button>
        </div>
    </form>
</div>

<jsp:include page="bootstrapFooter.jsp"/>
</body>
</html>