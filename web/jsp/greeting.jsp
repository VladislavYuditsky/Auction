<%@ taglib prefix="cust" uri="http://auction.yuditsky.com" %>

<%@include file="/jsp/parts/common.jsp" %>

<html>
<head>
    <title>Auction</title>
</head>
<body>
<jsp:include page="parts/navbar.jsp"/>

<div class="container mt-3">
    <cust:GreetingTag/>
    <div class="col-md-12 row">
        <div class="auction-type col-md-12 col-md-6 row">
            <h2><b><fmt:message key="greeting.directAuction" bundle="${pc}"/></b></h2>
            <div class="about">
                <fmt:message key="greeting.aboutDirectAuction" bundle="${pc}"/>
            </div>
            <br/>
        </div>
        <div class="auction-type col-md-12 col-md-6 row">
            <h2><b><fmt:message key="greeting.reverseAuction" bundle="${pc}"/></b></h2>
            <div class="about"><fmt:message key="greeting.aboutReverseAuction" bundle="${pc}"/></div>
            <br/>
        </div>
    </div>
</div>

</body>
</html>
