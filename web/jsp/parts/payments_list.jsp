<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="payments" class="java.util.ArrayList" scope="request"/>

<%@include file="/jsp/parts/common.jsp" %>

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