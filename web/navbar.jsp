<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/greeting">Auction</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="${pageContext.request.contextPath}/main?auctionType=direct">Direct </a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="${pageContext.request.contextPath}/main?auctionType=revers">Revers</a>
            </li>
        </ul>
        <c:if test="${sessionScope.id==null}">
            <ul class="navbar-nav">
                <form method="post" action="${pageContext.request.contextPath}/sign_in">
                    <button class="btn btn-outline-light" type="submit">Sign In</button>
                </form>
                <form method="post" action="${pageContext.request.contextPath}/sign_up">
                    <button class="btn btn-outline-light" type="submit">Sign Up</button>
                </form>
            </ul>
        </c:if>
        <c:if test="${sessionScope.id!=null}">
            <ul class="navbar-nav">
                <li class="nav-item active">
                    <a class="nav-link" href="${pageContext.request.contextPath}/add_new_lot">Sell </a>
                </li>
                <li class="nav-item dropdown mr-3">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                       data-toggle="dropdown"
                       aria-haspopup="true" aria-expanded="false">
                            ${sessionScope.login}
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <a class="dropdown-item" href="${pageContext.request.contextPath}/user_lots?id=${sessionScope.id}">
                            My lots</a>
                        <a class="dropdown-item" href="${pageContext.request.contextPath}/user_bids">My bids</a>
                        <a class="dropdown-item" href="${pageContext.request.contextPath}/awaiting_payment">
                            Awaiting payment</a>
                        <a class="dropdown-item" href="#">My payments</a>
                        <a class="dropdown-item" href="#">My Credits</a>
                        <a class="dropdown-item" href="#">Settings</a>
                        <a class="dropdown-item" href="#">Replenish balance</a>
                    </div>
                </li>
                <c:if test="${sessionScope.role=='ADMIN'}">
                    <li class="nav-item dropdown mr-3">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownAdm" role="button"
                           data-toggle="dropdown"
                           aria-haspopup="true" aria-expanded="false">
                                Admin panel
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item" href="">Proposed auctions</a>
                            <a class="dropdown-item" href="#">Users</a>
                        </div>
                    </li>
                </c:if>
                <li class="nav-item active">
                    <form method="post" action="${pageContext.request.contextPath}/sign_out">
                        <button class="btn btn-outline-light" type="submit">Sign Out</button>
                    </form>
                </li>
            </ul>
        </c:if>
    </div>
</nav>