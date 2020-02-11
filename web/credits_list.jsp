<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="credits" class="java.util.ArrayList" scope="request"/>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pageContent" var="pc"/>

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
                    <input type="text" name="repaidSum"/>
                    <button type="submit">Repay</button>
                </form>
                </c:if>
                </p>
                <p class="card-text"><small class="text-muted">
                    <fmt:message key="credit.repaid_before" bundle="${pc}"/> ${credit.endDate}
                </small></p>
            </div>
        </div>
    </c:forEach>
</c:if>