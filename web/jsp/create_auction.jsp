<jsp:useBean id="lot" scope="request" type="com.yuditsky.auction.entity.Lot"/>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@include file="/jsp/parts/common.jsp" %>

<html>
<head>
    <title>Auction</title>
</head>
<body>
<jsp:include page="parts/navbar.jsp"/>

<div class="container mt-3">
    <div>
        <label>
            <fmt:message key="auction.warning" bundle="${pc}"/>
        </label>
    </div>
    <div>
        <label>${lot.name}</label>
    </div>
    <div>
        <label>${lot.description}</label>
    </div>
    <div>
        <label><fmt:message key="lot.location" bundle="${pc}"/> ${lot.location}</label>
    </div>
    <div>
        <label><fmt:message key="lot.start_price" bundle="${pc}"/> ${lot.startPrice}</label>
    </div>

    <form method="post" action="${pageContext.request.contextPath}/create_auction?lotId=${lot.id}">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">
                <fmt:message key="auction.choose_type" bundle="${pc}"/>
            </label>
            <div class="col-sm-6">
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="auctionType" id="direct" value="direct" checked>
                    <label class="form-check-label" for="direct">
                        <fmt:message key="auction.direct" bundle="${pc}"/>
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="auctionType" id="revers" value="revers">
                    <label class="form-check-label" for="revers">
                        <fmt:message key="auction.revers" bundle="${pc}"/>
                    </label>
                </div>
            </div>
        </div>
        <button type="submit" class="btn btn-dark">
            <fmt:message key="button.put_up" bundle="${pc}"/>
        </button>
    </form>
</div>

<jsp:include page="/jsp/parts/footer.jsp"/>
</body>
</html>