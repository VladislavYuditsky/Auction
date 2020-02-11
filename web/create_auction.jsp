<jsp:useBean id="lot" scope="request" type="com.yuditsky.auction.entity.Lot"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.locale}" scope="session"/>
    <fmt:setBundle basename="pageContent" var="pc" />

    <jsp:include page="bootstrap_header.jsp"/>
    <title>Auction</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>

<div class="container mt-2">
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
        <label><fmt:message key="lot.start_price" bundle="${pc}"/></label>
    </div>

    <form method="post" action="${pageContext.request.contextPath}/create_auction?lotId=${lot.id}">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">
                <fmt:message key="auction.choose_type" bundle="${pc}"/>
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
            </label>
            <button type="submit" class="btn btn-dark">
                <fmt:message key="button.put_up" bundle="${pc}"/>
            </button>
        </div>
    </form>
</div>

<jsp:include page="bootstrap_footer.jsp"/>
</body>
</html>