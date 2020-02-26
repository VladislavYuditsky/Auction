<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="/jsp/parts/common.jsp" %>

<html>
<head>
    <title>Auction</title>
</head>
<body>
<jsp:include page="parts/navbar.jsp"/>

<div class="container mt-3">
    <form method="post" id="formWithNumber" action="${pageContext.request.contextPath}/replenish_balance">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">
                <fmt:message key="user.balance" bundle="${pc}"/> ${requestScope.balance}
            </label>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">
                <fmt:message key="user.balance.replenish_sum" bundle="${pc}"/>
            </label>
            <div class="col-sm-6">
                <input type="text" id="number" name="replenishSum" class="form-control" required onchange="document.getElementById('numberError').style.display='none'"/>
                <div id="numberError" style="display: none" class="invalid-feedback" ><fmt:message key="message.invalid_value" bundle="${pc}"/></div>
            </div>
        </div>
        <button type="submit" class="btn btn-dark">
            <fmt:message key="button.replenish" bundle="${pc}"/>
        </button>
    </form>
</div>

<script src="${pageContext.request.contextPath}/js/numberValidator.js"></script>
<jsp:include page="/jsp/parts/footer.jsp"/>
</body>
</html>