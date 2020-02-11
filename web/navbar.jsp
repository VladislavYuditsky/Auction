<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pageContent" var="pc"/>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/greeting">
        <fmt:message key="navbar.auction" bundle="${pc}"/>
    </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="${pageContext.request.contextPath}/auctions?auctionType=direct">
                    <fmt:message key="auction.direct" bundle="${pc}"/>
                </a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="${pageContext.request.contextPath}/auctions?auctionType=revers">
                    <fmt:message key="auction.revers" bundle="${pc}"/>
                </a>
            </li>
        </ul>
        <c:if test="${sessionScope.id==null}">
            <ul class="navbar-nav">
                <form method="post" action="${pageContext.request.contextPath}/sign_in">
                    <button class="btn btn-outline-light" type="submit">
                        <fmt:message key="button.sign_in" bundle="${pc}"/>
                    </button>
                </form>
                <form method="post" action="${pageContext.request.contextPath}/sign_up">
                    <button class="btn btn-outline-light" type="submit">
                        <fmt:message key="button.sign_up" bundle="${pc}"/>
                    </button>
                </form>
            </ul>
        </c:if>
        <c:if test="${sessionScope.id!=null}">
            <ul class="navbar-nav">
                <li class="nav-item active">
                    <a class="nav-link" href="${pageContext.request.contextPath}/create_lot">
                        <fmt:message key="navbar.sell" bundle="${pc}"/>
                    </a>
                </li>
                <li class="nav-item dropdown mr-3">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                       data-toggle="dropdown"
                       aria-haspopup="true" aria-expanded="false">
                            ${sessionScope.login}
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <a class="dropdown-item"
                           href="${pageContext.request.contextPath}/user_lots?id=${sessionScope.id}">
                            <fmt:message key="navbar.my_lots" bundle="${pc}"/>
                        </a>
                        <a class="dropdown-item" href="${pageContext.request.contextPath}/user_bids">
                            <fmt:message key="navbar.my_bids" bundle="${pc}"/>
                        </a>
                        <a class="dropdown-item" href="${pageContext.request.contextPath}/awaiting_payment_lots">
                            <fmt:message key="navbar.awaiting_payment" bundle="${pc}"/>
                        </a>
                        <a class="dropdown-item" href="${pageContext.request.contextPath}/user_payments">
                            <fmt:message key="navbar.my_payments" bundle="${pc}"/>
                        </a>
                        <a class="dropdown-item" href="${pageContext.request.contextPath}/user_credits">
                            <fmt:message key="navbar.my_credits" bundle="${pc}"/>
                        </a>
                        <a class="dropdown-item" href="${pageContext.request.contextPath}/settings">
                            <fmt:message key="navbar.settings" bundle="${pc}"/>
                        </a>
                        <a class="dropdown-item" href="${pageContext.request.contextPath}/user_balance">
                            <fmt:message key="navbar.my_balance" bundle="${pc}"/>
                        </a>
                    </div>
                </li>
                <c:if test="${sessionScope.role=='ADMIN'}">
                    <li class="nav-item dropdown mr-3">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownAdm" role="button"
                           data-toggle="dropdown"
                           aria-haspopup="true" aria-expanded="false">
                            <fmt:message key="navbar.admin_panel" bundle="${pc}"/>
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/proposed_auctions">
                                <fmt:message key="navbar.proposed_auctions" bundle="${pc}"/>
                            </a>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/users">
                                <fmt:message key="navbar.users" bundle="${pc}"/>
                            </a>
                        </div>
                    </li>
                </c:if>
                <li class="nav-item active">
                    <a class="nav-link" href="${pageContext.request.contextPath}/change_locale?page=${pageContext.request.servletPath}&query=${pageContext.request.queryString}">
                        <fmt:message key="language" bundle="${pc}"/>
                    </a>
                </li>
                <li class="nav-item active">
                    <form method="post" action="${pageContext.request.contextPath}/sign_out">
                        <button class="btn btn-outline-light" type="submit">
                            <fmt:message key="button.sign_out" bundle="${pc}"/>
                        </button>
                    </form>
                </li>
            </ul>
        </c:if>
    </div>
</nav>