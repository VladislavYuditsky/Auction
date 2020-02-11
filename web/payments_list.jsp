<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pageContent" var="pc"/>

<jsp:useBean id="payments" class="java.util.ArrayList" scope="request"/>

<c:if test="${payments.size()!=0}">
    <c:forEach var="payment" items="${payments}">
        <div class="card mb-3">
            <div class="card-body">
                <h3>
                    <fmt:message key="payment.id" bundle="${pc}"/> ${payment.id}
                </h3>
                <p class="card-text">
                    <fmt:message key="payment.goods_id" bundle="${pc}"/> ${payment.lotId}<br>
                    <fmt:message key="payment.payer_id" bundle="${pc}"/> ${payment.payerId}<br>
                    <fmt:message key="payment.sum" bundle="${pc}"/> ${payment.sum}<br>
                </p>
                <p class="card-text"><small class="text-muted">
                        ${payment.date}
                </small></p>
            </div>
        </div>
    </c:forEach>
</c:if>