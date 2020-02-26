<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@include file="/jsp/parts/common.jsp" %>

<header class="navbar navbar-expand navbar-dark flex-column flex-md-row bd-navbar bg-dark">
    <a class="navbar-brand mr-0 mr-md-2" href="${pageContext.request.contextPath}/greeting">
        <fmt:message key="navbar.auction" bundle="${pc}"/>
    </a>

    <div class="navbar-nav-scroll">
        <ul class="navbar-nav bd-navbar-nav flex-row">
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
                <a class="nav-link" href="${pageContext.request.contextPath}/sell">
                    <fmt:message key="navbar.sell" bundle="${pc}"/>
                </a>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-item nav-link dropdown-toggle mr-md-2" href="#" id="bd-versions" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
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
                    <a class="nav-item nav-link dropdown-toggle mr-md-2" href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
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
                <a class="nav-link" href="${pageContext.request.contextPath}/sign_out">
                    <fmt:message key="button.sign_out" bundle="${pc}"/>
                </a>
            </li>
        </c:if>
    </ul>
</header>