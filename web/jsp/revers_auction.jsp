<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="lot" scope="request" type="com.yuditsky.auction.entity.Lot"/>
<jsp:useBean id="bid" scope="request" type="com.yuditsky.auction.entity.Bid"/>
<jsp:useBean id="auction" scope="request" type="com.yuditsky.auction.entity.Auction"/>

<%@include file="/jsp/parts/common.jsp" %>

<html>
<head>
    <title>Auction</title>
</head>
<body>
<jsp:include page="parts/navbar.jsp"/>

<div class="container mt-3">
    <jsp:include page="parts/lot_info.jsp"/>

    <div class="form-group row">
        <label class="col-sm-2 col-form-label">
            <fmt:message key="lot.start_price" bundle="${pc}"/> ${lot.startPrice}
        </label>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">
            <fmt:message key="auction.current_price" bundle="${pc}"/>
            <c:if test="${bid.sum!=null}">
                ${bid.sum}
            </c:if>
            <c:if test="${bid.sum == null}">
                ?
            </c:if>
        </label>
        <c:if test="${auction.status == 'ACTIVE'}">
            <div class="col-sm-2">
                <form method="post" action="${pageContext.request.contextPath}/show_price?lotId=${lot.id}">
                    <button type="submit" class="btn btn-dark">
                        <fmt:message key="button.show_price" bundle="${pc}"/>
                    </button>
                </form>
            </div>
            <div class="col-sm-1">
                <form method="post" action="${pageContext.request.contextPath}/buy?lotId=${lot.id}">
                    <button type="submit" class="btn btn-dark">
                        <fmt:message key="button.buy" bundle="${pc}"/>
                    </button>
                </form>
            </div>
            <div class="text-danger col-sm-3"><small>
                <c:if test="${requestScope.message != null}">
                    <fmt:message key="${requestScope.message}" bundle="${pc}"/>
                </c:if>
            </small></div>
        </c:if>
    </div>

    <jsp:include page="parts/auction_management.jsp"/>
</div>
</form>
</div>

<jsp:include page="/jsp/parts/footer.jsp"/>
</body>
</html>