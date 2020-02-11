<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pageContent" var="pc"/>

<jsp:useBean id="lot" scope="request" type="com.yuditsky.auction.entity.Lot"/>
<jsp:useBean id="bid" scope="request" type="com.yuditsky.auction.entity.Bid"/>
<jsp:useBean id="auction" scope="request" type="com.yuditsky.auction.entity.Auction"/>

<html>
<head>
    <jsp:include page="bootstrap_header.jsp"/>
    <title>Auction</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>

<div class="container mt-2">
    <div>
        <label>${lot.name}</label>
    </div>
    <div>
        <label>${lot.description}</label>
    </div>
    <div>
        <label>
            <fmt:message key="lot.location" bundle="${pc}"/> ${lot.location}
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
        <label>
            <fmt:message key="lot.start_price" bundle="${pc}"/> ${lot.startPrice}
        </label>
        <c:if test="${auction.status == 'ACTIVE'}">
            <form method="post" action="${pageContext.request.contextPath}/show_price?lotId=${lot.id}">
                <div class="col-sm-6">
                    <button type="submit" class="btn btn-dark">
                        <fmt:message key="button.show_price" bundle="${pc}"/>
                    </button>
                </div>
            </form>
            <form method="post" action="${pageContext.request.contextPath}/buy?lotId=${lot.id}">
                <div class="col-sm-6">
                    <input type="hidden" name="lotId" value="${lot.id}"/>
                    <button type="submit" class="btn btn-dark">
                        <fmt:message key="button.buy" bundle="${pc}"/>
                    </button>
                </div>
            </form>
        </c:if>

        <c:if test="${sessionScope.role == 'ADMIN' && auction.status != 'COMPLETED'}">
            <div>
                <form method="post"
                      action="${pageContext.request.contextPath}/change_auction_status?auctionId=${auction.id}">
                    <button type="submit" class="btn btn-dark">
                        <c:if test="${auction.status == 'WAITING'}">
                            <fmt:message key="button.start" bundle="${pc}"/>
                        </c:if>
                        <c:if test="${auction.status == 'ACTIVE'}">
                            <fmt:message key="button.stop" bundle="${pc}"/>
                        </c:if>
                    </button>
                </form>
                <c:if test="${auction.status == 'WAITING'}">
                    <form method="post" action="${pageContext.request.contextPath}/deny?auctionId=${auction.id}">
                        <button type="submit" class="btn btn-dark">
                            <fmt:message key="button.deny" bundle="${pc}"/>
                        </button>
                    </form>
                    <form method="post" action="${pageContext.request.contextPath}/block?userId=${lot.ownerId}&auctionId=${auction.id}">
                        <button type="submit" class="btn btn-dark">
                            <fmt:message key="button.block_author" bundle="${pc}"/>
                        </button>
                    </form>
                </c:if>
            </div>
        </c:if>
    </div>
</div>
</form>
</div>

<jsp:include page="bootstrap_footer.jsp"/>
</body>
</html>