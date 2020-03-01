<jsp:useBean id="lot" scope="request" type="com.yuditsky.auction.entity.Lot"/>
<jsp:useBean id="bid" scope="request" type="com.yuditsky.auction.entity.Bid"/>
<jsp:useBean id="auction" scope="request" type="com.yuditsky.auction.entity.Auction"/>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@include file="/jsp/parts/common.jsp" %>

<html>
<head>
    <title>Auction</title>
</head>
<body>
<jsp:include page="parts/navbar.jsp"/>

<div class="container mt-3">
    <jsp:include page="parts/lot_info.jsp"/>

    <div>
        <label>
            <fmt:message key="auction.current_price" bundle="${pc}"/>
            <c:if test="${bid.sum != null}">
                ${bid.sum}
            </c:if>
            <c:if test="${bid.sum == null}">
                ${lot.startPrice}
            </c:if>
        </label>
    </div>

    <c:if test="${auction.status == 'ACTIVE'}">
        <form method="post" id="formWithNumber" action="${pageContext.request.contextPath}/create_bid?lotId=${lot.id}">
            <div class="form-group row">
                <div class="col-sm-2">
                    <input type="text" id="number" name="bidSum" class="form-control" required
                           onchange="document.getElementById('numberError').style.display='none'"/>
                    <div id="numberError" style="display: none" class="invalid-feedback"><fmt:message
                            key="message.invalid_value" bundle="${pc}"/></div>
                </div>
                <div class="col-sm-6">
                    <button type="submit" class="btn btn-dark">
                        <fmt:message key="button.bid" bundle="${pc}"/>
                    </button>
                </div>
            </div>
        </form>
    </c:if>

    <jsp:include page="parts/auction_management.jsp"/>

    <c:if test="${auction.winnerId == sessionScope.id}">
        <form method="post" action="${pageContext.request.contextPath}/create_payment?lotId=${lot.id}">
            <button type="submit" class="btn btn-dark">
                <fmt:message key="button.pay" bundle="${pc}"/>
            </button>
        </form>
        <form method="post" action="${pageContext.request.contextPath}/create_credit?lotId=${lot.id}">
            <button type="submit" class="btn btn-dark">
                <fmt:message key="button.credit" bundle="${pc}"/>
            </button>
        </form>
    </c:if>

    <div class="text-danger"><small>
        <c:if test="${requestScope.message != null}">
            <fmt:message key="${requestScope.message}" bundle="${pc}"/>
        </c:if>
    </small></div>

</div>

<jsp:include page="/jsp/parts/footer.jsp"/>
</body>
</html>

<script>
    <jsp:directive.include file="/js/numberValidator.js"/>
</script>