<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="auction" scope="request" type="com.yuditsky.auction.entity.Auction"/>
<jsp:useBean id="lot" scope="request" type="com.yuditsky.auction.entity.Lot"/>

<%@include file="/jsp/parts/common.jsp" %>

<c:if test="${sessionScope.role == 'ADMIN' && auction.status != 'COMPLETED'}">
    <div class="form-group row">
        <div class="col-sm-1">
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
        </div>
        <c:if test="${auction.status == 'WAITING'}">
            <form method="post" action="${pageContext.request.contextPath}/deny?auctionId=${auction.id}">
                <div class="col-sm-1">
                    <button type="submit" class="btn btn-dark">
                        <fmt:message key="button.deny" bundle="${pc}"/>
                    </button>
                </div>
            </form>
            <form method="post"
                  action="${pageContext.request.contextPath}/block?userId=${lot.ownerId}&auctionId=${auction.id}">
                <div class="col-sm-1">
                    <button type="submit" class="btn btn-dark">
                        <fmt:message key="button.block_author" bundle="${pc}"/>
                    </button>
                </div>
            </form>
        </c:if>
    </div>
</c:if>