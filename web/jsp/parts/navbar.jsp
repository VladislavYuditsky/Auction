<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@include file="/jsp/parts/common.jsp" %>

<%--<nav class="navbar navbar-expand-lg navbar-dark bg-dark">--%>
<%--    <a class="navbar-brand" href="${pageContext.request.contextPath}/greeting">--%>
<%--        <fmt:message key="navbar.auction" bundle="${pc}"/>--%>
<%--    </a>--%>
<%--    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"--%>
<%--            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">--%>
<%--        <span class="navbar-toggler-icon"></span>--%>
<%--    </button>--%>

<%--    <div class="collapse navbar-collapse" id="navbarSupportedContent">--%>
<%--        <ul class="navbar-nav mr-auto">--%>
<%--            <li class="nav-item active">--%>
<%--                <a class="nav-link" href="${pageContext.request.contextPath}/auctions?auctionType=direct">--%>
<%--                    <fmt:message key="auction.direct" bundle="${pc}"/>--%>
<%--                </a>--%>
<%--            </li>--%>
<%--            <li class="nav-item active">--%>
<%--                <a class="nav-link" href="${pageContext.request.contextPath}/auctions?auctionType=revers">--%>
<%--                    <fmt:message key="auction.revers" bundle="${pc}"/>--%>
<%--                </a>--%>
<%--            </li>--%>
<%--        </ul>--%>
<%--        <c:if test="${sessionScope.id==null}">--%>
<%--            <ul class="navbar-nav">--%>
<%--                <li class="nav-item active">--%>
<%--                <form method="post" action="${pageContext.request.contextPath}/sign_in">--%>
<%--                    <button class="btn btn-outline-light" type="submit">--%>
<%--                        <fmt:message key="button.sign_in" bundle="${pc}"/>--%>
<%--                    </button>--%>
<%--                </form>--%>
<%--                </li>--%>
<%--                <li class="nav-item active">--%>
<%--                <form method="post" action="${pageContext.request.contextPath}/sign_up">--%>
<%--                    <button class="btn btn-outline-light" type="submit">--%>
<%--                        <fmt:message key="button.sign_up" bundle="${pc}"/>--%>
<%--                    </button>--%>
<%--                </form>--%>
<%--                </li>--%>
<%--            </ul>--%>
<%--        </c:if>--%>
<%--        <c:if test="${sessionScope.id!=null}">--%>
<%--            <ul class="navbar-nav">--%>
<%--                <li class="nav-item active">--%>
<%--                    <a class="nav-link" href="${pageContext.request.contextPath}/sell">--%>
<%--                        <fmt:message key="navbar.sell" bundle="${pc}"/>--%>
<%--                    </a>--%>
<%--                </li>--%>
<%--                <li class="nav-item dropdown mr-3">--%>
<%--                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"--%>
<%--                       data-toggle="dropdown"--%>
<%--                       aria-haspopup="true" aria-expanded="false">--%>
<%--                            ${sessionScope.login}--%>
<%--                    </a>--%>
<%--                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">--%>
<%--                        <a class="dropdown-item"--%>
<%--                           href="${pageContext.request.contextPath}/user_lots?id=${sessionScope.id}">--%>
<%--                            <fmt:message key="navbar.my_lots" bundle="${pc}"/>--%>
<%--                        </a>--%>
<%--                        <a class="dropdown-item" href="${pageContext.request.contextPath}/user_bids">--%>
<%--                            <fmt:message key="navbar.my_bids" bundle="${pc}"/>--%>
<%--                        </a>--%>
<%--                        <a class="dropdown-item" href="${pageContext.request.contextPath}/awaiting_payment_lots">--%>
<%--                            <fmt:message key="navbar.awaiting_payment" bundle="${pc}"/>--%>
<%--                        </a>--%>
<%--                        <a class="dropdown-item" href="${pageContext.request.contextPath}/user_payments">--%>
<%--                            <fmt:message key="navbar.my_payments" bundle="${pc}"/>--%>
<%--                        </a>--%>
<%--                        <a class="dropdown-item" href="${pageContext.request.contextPath}/user_credits">--%>
<%--                            <fmt:message key="navbar.my_credits" bundle="${pc}"/>--%>
<%--                        </a>--%>
<%--                        <a class="dropdown-item" href="${pageContext.request.contextPath}/settings">--%>
<%--                            <fmt:message key="navbar.settings" bundle="${pc}"/>--%>
<%--                        </a>--%>
<%--                        <a class="dropdown-item" href="${pageContext.request.contextPath}/user_balance">--%>
<%--                            <fmt:message key="navbar.my_balance" bundle="${pc}"/>--%>
<%--                        </a>--%>
<%--                    </div>--%>
<%--                </li>--%>
<%--                <c:if test="${sessionScope.role=='ADMIN'}">--%>
<%--                    <li class="nav-item dropdown mr-3">--%>
<%--                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownAdm" role="button"--%>
<%--                           data-toggle="dropdown"--%>
<%--                           aria-haspopup="true" aria-expanded="false">--%>
<%--                            <fmt:message key="navbar.admin_panel" bundle="${pc}"/>--%>
<%--                        </a>--%>
<%--                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">--%>
<%--                            <a class="dropdown-item" href="${pageContext.request.contextPath}/proposed_auctions">--%>
<%--                                <fmt:message key="navbar.proposed_auctions" bundle="${pc}"/>--%>
<%--                            </a>--%>
<%--                            <a class="dropdown-item" href="${pageContext.request.contextPath}/users">--%>
<%--                                <fmt:message key="navbar.users" bundle="${pc}"/>--%>
<%--                            </a>--%>
<%--                        </div>--%>
<%--                    </li>--%>
<%--                </c:if>--%>
<%--                <li class="nav-item active">--%>
<%--                    <a class="nav-link" href="${pageContext.request.contextPath}/change_locale?page=${pageContext.request.servletPath}&query=${pageContext.request.queryString}">--%>
<%--                        <fmt:message key="language" bundle="${pc}"/>--%>
<%--                    </a>--%>
<%--                </li>--%>
<%--                <li class="nav-item active">--%>
<%--                    <form method="post" action="${pageContext.request.contextPath}/sign_out">--%>
<%--                        <button class="btn btn-outline-light" type="submit">--%>
<%--                            <fmt:message key="button.sign_out" bundle="${pc}"/>--%>
<%--                        </button>--%>
<%--                    </form>--%>
<%--                </li>--%>
<%--            </ul>--%>
<%--        </c:if>--%>
<%--    </div>--%>
<%--</nav>--%>


<header class="navbar navbar-expand navbar-dark flex-column flex-md-row bd-navbar bg-dark">
    <a class="navbar-brand mr-0 mr-md-2" href="${pageContext.request.contextPath}/greeting">
        <fmt:message key="navbar.auction" bundle="${pc}"/>
    </a>

    <div class="navbar-nav-scroll">
        <ul class="navbar-nav bd-navbar-nav flex-row">
            <c:if test="${sessionScope.id!=null}">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/auctions?auctionType=direct">
                        <fmt:message key="auction.direct" bundle="${pc}"/>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/auctions?auctionType=revers">
                        <fmt:message key="auction.revers" bundle="${pc}"/>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/sell">
                        <fmt:message key="navbar.sell" bundle="${pc}"/>
                    </a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-item nav-link dropdown-toggle mr-md-2" href="#" id="bd-versions"
                       data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            ${sessionScope.login}
                    </a>
                    <div class="dropdown-menu dropdown-menu-right" aria-labelledby="bd-versions">
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
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle mr-md-2" href="#" id="navbarDropdown" role="button"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <fmt:message key="navbar.admin_panel" bundle="${pc}"/>
                        </a>
                        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="bd-versions">
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/proposed_auctions">
                                <fmt:message key="navbar.proposed_auctions" bundle="${pc}"/>
                            </a>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/users">
                                <fmt:message key="navbar.users" bundle="${pc}"/>
                            </a>
                        </div>
                    </li>
                </c:if>
                <li class="nav-item">
                    <a class="nav-link"
                       href="${pageContext.request.contextPath}/change_locale?page=${pageContext.request.servletPath}&query=${pageContext.request.queryString}">
                        <fmt:message key="language" bundle="${pc}"/>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/sign_out">
                        <fmt:message key="button.sign_out" bundle="${pc}"/>
                    </a>
                </li>
            </c:if>
        </ul>
    </div>

    <ul class="navbar-nav flex-row ml-md-auto d-none d-md-flex">
        <c:if test="${sessionScope.id==null}">
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/sign_in">
                    <fmt:message key="button.sign_in" bundle="${pc}"/>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/sign_up">
                    <fmt:message key="button.sign_up" bundle="${pc}"/>
                </a>
            </li>
        </c:if>
        <c:if test="${sessionScope.id!=null}">
            <li class="nav-item">
                <a class="nav-link"
                   href="${pageContext.request.contextPath}/change_locale?page=${pageContext.request.servletPath}&query=${pageContext.request.queryString}">
                    <fmt:message key="language" bundle="${pc}"/>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/sign_out">
                    <fmt:message key="button.sign_out" bundle="${pc}"/>
                </a>
            </li>
        </c:if>
    </ul>
</header>

<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
        integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>