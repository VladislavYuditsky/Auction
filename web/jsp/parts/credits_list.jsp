<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="credits" class="java.util.ArrayList" scope="request"/>

<%@include file="/jsp/parts/common.jsp" %>


<c:if test="${credits.size()!=0}">
    <c:forEach var="credit" items="${credits}">
        <div class="card mb-3">
            <div class="card-body">
                <h3>
                    <fmt:message key="credit.id" bundle="${pc}"/> ${credit.id}
                </h3>
                <p class="card-text">
                        <fmt:message key="credit.sum" bundle="${pc}"/> ${credit.sum}<br>
                        <fmt:message key="credit.balance" bundle="${pc}"/> ${credit.balance}<br>
                        <fmt:message key="credit.borrower_id" bundle="${pc}"/> ${credit.borrowerId}
                    <c:if test="${credit.balance > 0}">
                <form method="post" action="${pageContext.request.contextPath}/repay_credit?creditId=${credit.id}">
                    <input type="text" id="number" name="repaidSum" class="form-control" required
                           onchange="document.getElementById('numberError').style.display='none'"/>
                    <div id="numberError" style="display: none" class="invalid-feedback"><fmt:message
                            key="message.invalid_value" bundle="${pc}"/></div>
                    <button type="submit">Repay</button>
                </form>
                </c:if>
                <c:if test="${requestScope.creditId == credit.id}">
                    <div class="text-danger"><small>
                        <c:if test="${requestScope.message != null}">
                            <fmt:message key="${requestScope.message}" bundle="${pc}"/>
                        </c:if>
                    </small></div>
                </c:if>
                </p>
                <p class="card-text"><small class="text-muted">
                    <fmt:message key="credit.repaid_before" bundle="${pc}"/> ${credit.endDate}
                </small></p>
            </div>
        </div>
    </c:forEach>
</c:if>

<script>
    <jsp:directive.include file="/js/numberValidator.js"/>
</script>